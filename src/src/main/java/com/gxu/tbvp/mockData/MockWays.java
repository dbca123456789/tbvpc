package com.gxu.tbvp.mockData;

import com.gxu.tbvp.domain.Ways;
import com.gxu.tbvp.exception.SelfJSONResult;
import com.gxu.tbvp.service.WaysService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
public class MockWays {
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 101, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) executor.getQueue();

    @Resource
    private WaysService waysService;

    //随机生成start-end之间的数
    public static double getDoubleNum(int start, int end) {
        double f = (double)(Math.random()*(end - start +1)+start);
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static int getNum(int start, int end) {
        return (int)(Math.random()*(end - start +1)+start);
    }

    public static int getWaysId() {
//        double[] appWaysWeiths = {45, 26.8, 12.8, 8.5, 6.9};
        double temp = getDoubleNum(0,100);
        //0：飞机
        if (temp < 45) {
            return 0;
        } else if (temp >= 45 & temp < 71.8){
            return 1;//火车
        } else if (temp >= 71.8 & temp <84.6) {
            return 2;
        } else if (temp >= 84.6 & temp < 93.1){
            return 3;
        } else if (temp >= 93.1 & temp <100){
            return 4;
        }
        return 5;
    }

    @RequestMapping("/mockWays")
    public SelfJSONResult mockWays() throws InterruptedException {
        //线程数量
        final int threadSize = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int j = 0; j < (int)countDownLatch.getCount(); j++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Ways> waysList = new ArrayList<>();
                        for (int i = 0; i < 10000; i++) {
                            Ways ways = new Ways();
                            //0飞机 204165，1火车 178121，2汽车 31006，3自驾 86627，4其它 19920
                            int index = getWaysId();
                            if (index == 0){
                                //车程花费
                                ways.setAmount(getDoubleNum(800, 4000));
                            } else {
                                ways.setAmount(getDoubleNum(100, 2000));
                            }
                            ways.setWay(index);
                            waysList.add(ways);
                        }
                        int temp = waysService.insertBach(waysList);
//                        if (temp == 1){
//                            System.out.println("success");
//                        } else {
//                            System.out.println("fail");
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
//                    System.out.println("Thread " + countDownLatch.getCount() + " success");
                    System.out.println(countDownLatch);
                }
            });
        }
        countDownLatch.await();
        if (0 == (int)countDownLatch.getCount()){
            return SelfJSONResult.ok("success");
        }else {
            return SelfJSONResult.errorMsg("插入失败");
        }
    }
}
