package com.gxu.tbvp.controller;

import com.gxu.tbvp.domain.Manager;
import com.gxu.tbvp.service.RegionService;
import com.gxu.tbvp.service.UserService;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


@RestController
@RequestMapping("/userJson")
public class ProvinceJsonController {

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) executor.getQueue();

    @Resource
    private RegionService regionService;

    @RequestMapping("/getProvince")
    public ArrayList<Map> test() throws InterruptedException {
        final int[] provinceIds = {110000,120000,130000,140000,150000,210000,220000,230000,310000,320000,330000,340000,350000,360000,370000,410000,420000,430000,440000,450000,460000,500000,510000,520000,530000,540000,610000,620000,630000,640000,650000,710000,810000,820000,990000};
        String[] provinces = "北京,天津,河北,山西,内蒙古,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西,海南,重庆,四川,贵州,云南,西藏,陕西,甘肃,青海,宁夏,新疆,台湾,香港,澳门,海外".split(",");
        final CountDownLatch countDownLatch = new CountDownLatch(35);
        Map maps = new HashMap();
        for (int i=0; i<35;i++){
            maps.put(provinces[i],provinceIds[i]);
        }
        ArrayList<Map> maplist = new ArrayList<>();
        for(String province:provinces){
            Map provinceMap = new HashMap();
            executor.execute(new Runnable(){
                public void run() {
                    try {
                        provinceMap.put("name",province);
                        provinceMap.put("value",regionService.selectCountProvinceById((int)maps.get(province)));
                        maplist.add(provinceMap);
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
