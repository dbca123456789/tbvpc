package com.gxu.tbvp.controller;

import com.gxu.tbvp.service.AccessrecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/accessRecord")
public class AccessRecordController {
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000));
    @Resource
    private AccessrecordService accessrecordService;
    private static final String[] years = {"2016","2017","2018"};
    private static final String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    private static final String[] days = {"31","28","31","30","31","30","31","31","30","31","30","31"};

    @RequestMapping("/getAccessCount")
    public ArrayList<ArrayList> getAccessCount()throws ParseException, InterruptedException{
        CountDownLatch countDownLatch = new CountDownLatch(36);
        ArrayList arrayList = new ArrayList();
        Map maps = new HashMap();
        for (int i=0; i<12;i++){
            maps.put(months[i],days[i]);
        }
        for (String year : years ){
            ArrayList countList = new ArrayList();
            Map map = new HashMap();
            for (String month : months){
                String startTime = year+"-"+month+"-"+"01"+" 00:00:00";
                String endTime = year+"-"+month+"-"+maps.get(month)+" 23:59:59";
//                int count = accessrecordService.selectAccessCountByTime(startTime,endTime);
//                countList.add(count);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int count = accessrecordService.selectAccessCountByTime(startTime,endTime);
                            countList.add(count);
                            map.put(year+month,count);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
                });
            }

            arrayList.add(countList);

        }
        countDownLatch.await();
        return arrayList;
    }


}
