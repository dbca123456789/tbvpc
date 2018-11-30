package com.gxu.tbvp.controller;

import com.gxu.tbvp.domain.ScenicBuyrecord;
import com.gxu.tbvp.service.ScenicBuyrecordService;
import com.gxu.tbvp.service.ScenicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

//基于物品的协同过滤算法
@RestController
public class recommendScenicsController {

    //设置购买值和浏览值的权重
    final static double buyCountParameter = 0.8;
    final static double visitParameter= 0.2;

    @Resource
    private ScenicBuyrecordService scenicBuyrecordService;

    @Resource
    private ScenicService scenicService;

    @RequestMapping("/recommendScenics")
    public Map<String, Double> recommendedScenics(String scenic){
        //获取景点id
        int scenicId = scenicService.getScenicByName(scenic);
        //查无此景点
        if (scenicId == -1){
            return null;
        }
        //查询出所有购买了此景点的游客
        List<ScenicBuyrecord> scenicBuyrecordList = scenicBuyrecordService.selectByscenicId(scenicId);
        //Map<用户id,用户对此景点的权重评分>
        Map<Integer, Double> scenicMap1 = new HashMap<>();
        int count = 0;
        for (ScenicBuyrecord scenicBuyrecord : scenicBuyrecordList){
            count += 1;
            scenicMap1.put(scenicBuyrecord.getUserid(), scenicBuyrecord.getAccessCount()*visitParameter + scenicBuyrecord.getBuyCount()*buyCountParameter);
        }

        //查询出所有景点
        int[] scenicIds = scenicService.selectAllScenicsId();

        //皮尔逊相关系数
        Map<Integer, Double> scenicSimMap = new TreeMap<>();

        for (int id :scenicIds) {
            if (id != scenicId) {
                //计算出购买了主景点的所用用户，对其它景点的权重矩阵，若用户没有购买则权重为0
                //select * from buyRecord where scenicId = id and userId = userId;
                Map<Integer, Double> scenicMap2 = new HashMap<>();
                for (int userId : scenicMap1.keySet()){
                    scenicMap2.put(userId, getUserScenicWeiths(id, userId));
                }

                //计算皮尔逊相关系数
                double sim = getUserSimilar(scenicMap1, scenicMap2);
                scenicSimMap.put(id, sim);
            }
        }

        //排序
        scenicSimMap = sortByValueDescending(scenicSimMap);
        //查询景点中文名字
        Map<String, Double> chiNameMap = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : scenicSimMap.entrySet()) {
            chiNameMap.put(scenicService.selectScenicByKey(entry.getKey()), entry.getValue());
            System.out.println("key= " + scenicService.selectScenicByKey(entry.getKey()) + " and value= " + entry.getValue());
        }

        return chiNameMap;
    }

    private double getUserScenicWeiths(int id, int userId) {
        double weigth = 0;
        //查询该用户购买过该景点的所有记录
        List<ScenicBuyrecord> scenicBuyrecordList = scenicBuyrecordService.getUserScenicBuyrecord(id, userId);
        if (scenicBuyrecordList.size() == 0 || scenicBuyrecordList == null) {
            return 0;
        }
        for (ScenicBuyrecord scenicBuyrecord:scenicBuyrecordList){
            weigth += scenicBuyrecord.getAccessCount()*visitParameter + scenicBuyrecord.getBuyCount()*buyCountParameter;
        }
        weigth = weigth/scenicBuyrecordList.size();
        return weigth;
    }

    //Claculate Pearson Correlation Coefficient
    public static double getUserSimilar(Map<Integer, Double> pm1, Map<Integer, Double> pm2) {
        int n = 0;// 数量n
        int sxy = 0;// Σxy=x1*y1+x2*y2+....xn*yn
        int sx = 0;// Σx=x1+x2+....xn
        int sy = 0;// Σy=y1+y2+...yn
        int sx2 = 0;// Σx2=(x1)2+(x2)2+....(xn)2
        int sy2 = 0;// Σy2=(y1)2+(y2)2+....(yn)2
        for (Map.Entry<Integer, Double> pme : pm1.entrySet()) {
            Integer key = pme.getKey();
            Double x = pme.getValue();
            Double y = pm2.get(key);
            if (x != null && y != null) {
                n++;
                sxy += x * y;
                sx += x;
                sy += y;
                sx2 += Math.pow(x, 2);
                sy2 += Math.pow(y, 2);
            }
        }
        // p=(Σxy-Σx*Σy/n)/Math.sqrt((Σx2-(Σx)2/n)(Σy2-(Σy)2/n));
        double sd = sxy - sx * sy / n;
        double sm = Math.sqrt((sx2 - Math.pow(sx, 2) / n) * (sy2 - Math.pow(sy, 2) / n));
        return Math.abs(sm == 0 ? 1 : sd / sm);
    }


    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    /*public static void main(String[] args) {
        Map<Integer, Double> Probs = new TreeMap<Integer, Double>();

        Probs.put(1, 0.5);
        Probs.put(2, 1.5);
        Probs.put(3, 0.2);
        Probs.put(4, 10.2);
        Probs = sortByValueDescending(Probs);
        System.out.println("基于value值的降序，排序输出结果为：");
        for (Map.Entry<Integer, Double> entry : Probs.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }*/

}
