package com.gxu.tbvp.controller;

import com.gxu.tbvp.service.BuyrecordService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("buyRecord")
public class BuyRecordController {
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 101, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) executor.getQueue();

    @Resource
    private BuyrecordService buyrecordService;

    @RequestMapping("/getConsumption")
    public ArrayList<Integer> getConsumption() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        int[] consumptions = {0,200,300,500,800,1000,2000,3000,4000,5000};
        Map<String, int[]> intMap = new HashMap<>();
        for (int i = 0; i < consumptions.length-1 ; i++) {
            int[] put = {consumptions[i], consumptions[i+1]};
            intMap.put(String.valueOf(consumptions[i]),put);
        }
        intMap.put("5000", new int[]{5000, 10000});

        ArrayList<Map> arrayMapList = new ArrayList<>();
        for (int consumption : consumptions){
            executor.execute(new Runnable() {
                Map tempMap = new HashMap();
                @Override
                public void run() {
                    try {
                        Map<String, Object> map = new HashedMap();
                        map.put("firstPrice",(int)intMap.get(String.valueOf(consumption))[0]);
                        map.put("lastPrice",(int)intMap.get(String.valueOf(consumption))[1]);
                        tempMap.put(String.valueOf(consumption), buyrecordService.countPeopleByPrice(map));
                        System.out.println(tempMap);
                        arrayMapList.add(tempMap);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (Integer consumption: consumptions){
            for (Map map:arrayMapList){
                if (map.containsKey(String.valueOf(consumption))){//map.get(consumption)!=null &&
                    arrayList.add((Integer)map.get(String.valueOf(consumption)));
                }
            }
        }
        return arrayList;
    }
}