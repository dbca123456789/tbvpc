package com.gxu.tbvp.controller;

import com.gxu.tbvp.service.WaysService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/ways")
public class WaysController {
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 101, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) executor.getQueue();

    @Resource
    private WaysService waysService;
    @RequestMapping("/getWay")
    public ArrayList<Map> getWay() throws InterruptedException{
        ArrayList<Map> wayslist = new ArrayList<>();
        final int[] wayIds = {0,1,2,3,4};
        String[] ways = "飞机,火车,汽车,自驾,其他".split(",");
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        Map maps = new HashMap();
        int count = waysService.selectWaysCount();
        for (int i=0; i<5;i++){
            maps.put(ways[i],wayIds[i]);
        }
        ArrayList<Map> maplist = new ArrayList<>();
        for(String way:ways){
            Map wayMap = new HashMap();
            if (way.equals("飞机")){
                wayMap.put("sliced","true");
                wayMap.put("selected","true");
            }
            maplist.add(wayMap);
            executor.execute(new Runnable(){
                public void run() {
                    try {
                        wayMap.put("name",way);
                        double per = (double)(100*waysService.selectWaysById((int)maps.get(way))/count);
                        wayMap.put("y",per);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
//所有子线程 执行完成之后 主线程再继续向下
        countDownLatch.await();
        return maplist;
    }
}
