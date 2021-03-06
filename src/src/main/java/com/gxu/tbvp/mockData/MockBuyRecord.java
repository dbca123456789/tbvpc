package com.gxu.tbvp.mockData;

import com.gxu.tbvp.domain.Accessrecord;
import com.gxu.tbvp.domain.Buyrecord;
import com.gxu.tbvp.domain.Produce;
import com.gxu.tbvp.exception.SelfJSONResult;
import com.gxu.tbvp.service.BuyrecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
public class MockBuyRecord {
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 101, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) executor.getQueue();

    @Resource
    private BuyrecordService buyrecordService;

    //随机生成start-end之间的数
    public static int getNum(int start, int end) {
        return (int)(Math.random()*(end - start +1)+start);
    }
    public static double getDoubleNum(int start, int end) {
        double f = (double)(Math.random()*(end - start +1)+start);
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //返回浏览次数
    public static int getTotaltime(){
        //(1-10:0.6),(11-20:0.2),(20-30:0.15)(30-100:0.05)
        double temp = getDoubleNum(0,1);
        if (temp <= 0.6){
            return getNum(1,10);
        } else if (temp <= 0.8) {
            return getNum(11,20);
        } else if (temp <= 0.95) {
            return getNum(21,30);
        } else if (temp <= 1) {
            return getNum(31,100);
        }
        return getNum(1,10);
    }

    //返回购买数量
    public static int getBuycount(){
        //(1:0.6),(2:0.2),(3-10:0.15)(10-40:0.05)
        double temp = getDoubleNum(0,1);
        if (temp <= 0.6){
            return 1;
        } else if (temp <= 0.8) {
            return 2;
        } else if (temp <= 0.95) {
            return getNum(3,10);
        } else if (temp <= 1) {
            return getNum(10,40);
        }
        return 1;
    }

    @RequestMapping("/MockBuyRecord")
    public SelfJSONResult mockBuyRecord() throws InterruptedException {
        //设置线程数量
        final int threadSize = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        MockDate mockDate = new MockDate();

        for (int i = 0; i < (int)countDownLatch.getCount(); i++) {
            List<Buyrecord> buyrecordList = new ArrayList<>();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 10000; j++) {
                            Buyrecord buyrecord = new Buyrecord();
                            buyrecord.setUserid(getNum(0, 700000));
                            int index = getNum(1, 1455);
                            buyrecord.setBuyproduceid(index);
                            buyrecord.setBuyPrice(buyrecordService.selectProducePrice(index));
                            buyrecord.setBuytool(0);
                            buyrecord.setTotaltime(getTotaltime());
                            buyrecord.setBuycount(getBuycount());
                            try {
                                buyrecord.setBuytime(mockDate.RondomDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            buyrecordList.add(buyrecord);
                        }
                        int temp = buyrecordService.insertList(buyrecordList);
                        if (temp == 1){
                            System.out.println(countDownLatch);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
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

/*    @RequestMapping("/changeProducePrice")
    public SelfJSONResult changePrice() {
        List<Produce> produceList = new ArrayList<>();
        produceList = buyrecordService.selectAllProducePrice();
        for (Produce produce:produceList) {
            String string = produce.getPrice();
            produce.setPrice(string.substring(1,string.length()-1));
        }
        int temp = buyrecordService.updatePrice(produceList);
        return SelfJSONResult.ok("success");
    }*/
}
