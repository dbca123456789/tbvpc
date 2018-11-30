package com.gxu.tbvp.controller;

import com.gxu.tbvp.service.RegionService;
import com.gxu.tbvp.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("userJson")
public class SexJsonController {
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 101, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) executor.getQueue();

    @Resource
    private UserService userService;

    @RequestMapping("/getSexJson")
    public Map getSexJson() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final int[] sexs =  {0,1};
        final String[] bgs = "boy,girl".split(",");

        Map sexBG = new HashMap();
        for (int i = 0; i < 2; i++) {
            sexBG.put(bgs[i], sexs[i]);
        }

        Map sexMap = new HashMap();
        for (String bg:bgs) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        sexMap.put(bg, userService.countSex((int)sexBG.get(bg)));
                        System.out.println(sexMap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        return sexMap;
    }
}
