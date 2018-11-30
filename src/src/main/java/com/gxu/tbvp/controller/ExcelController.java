package com.gxu.tbvp.controller;

import com.gxu.tbvp.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
public class ExcelController {
    @Resource
    private UserService userService;
    @Resource
    private RegionService regionService;
    @Resource
    private WaysService waysService;
    @Resource
    private AccessrecordService accessrecordService;
    @Resource
    private BuyrecordService buyrecordService;

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1000));

    @RequestMapping(value="/exportProvince")
    public void exportProvince(HttpServletResponse response) throws InterruptedException,IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("测试表");
        //设置导出的表的名字
        String fileName = "Province.xls";
        int rowNum =1;
        String [] headers = {"省份","人数"};
        HSSFRow row = sheet.createRow(0);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        for(int i=0;i<headers.length;i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);

        }
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
//                    System.out.println(countDownLatch);
                }
            });
        }
        //所有子线程 执行完成之后 主线程再继续向下
        countDownLatch.await();
        System.out.print(maplist);
        for (Map map : maplist) {
            HSSFRow rown = sheet.createRow(rowNum);
            String key = (String) map.get("name");
            int value = (int)map.get("value");
            rown.createCell(0).setCellValue(key);
            rown.createCell(1).setCellValue(value);
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();


    }

    @RequestMapping(value = "exportWays")
    public void exportWays(HttpServletResponse response)throws InterruptedException,IOException{
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("测试表");
        //设置导出的表的名字
        String fileName = "游客出行方式.xls";
        int rowNum =1;
        String [] headers = {"出行方式","占比"};
        HSSFRow row = sheet.createRow(0);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        for(int i=0;i<headers.length;i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);

        }
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
        for (Map map : maplist) {
            HSSFRow rown = sheet.createRow(rowNum);
            String key = (String) map.get("name");
            double value = (double)map.get("y");
            rown.createCell(0).setCellValue(key);
            rown.createCell(1).setCellValue(value);
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private static final String[] years = {"2016","2017","2018"};
    private static final String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    private static final String[] days = {"31","28","31","30","31","30","31","31","30","31","30","31"};
    @RequestMapping("exportAccessRecord")
    public void exportAccessRecord(HttpServletResponse response)throws InterruptedException,IOException{
        CountDownLatch countDownLatch = new CountDownLatch(36);
        ArrayList<ArrayList> arrayList = new ArrayList();
        Map maps = new HashMap();
        for (int i=0; i<12;i++){
            maps.put(months[i],days[i]);
        }
        for (String year : years ){
            ArrayList<Integer> countList = new ArrayList();
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
        System.out.print(arrayList);
        HSSFWorkbook workbook = new HSSFWorkbook();
        //设置导出的表的名字
        String fileName = "游客出行方式.xls";
        String [] headers = {"月份","访问量"};
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        int i = 6;
        for (ArrayList<Integer> list : arrayList) {
            int rowNum =1;
            HSSFSheet sheet = workbook.createSheet("201"+i+"年");
            HSSFRow row = sheet.createRow(0);
            for(int j=0;j<headers.length;j++) {
                HSSFCell cell = row.createCell(j);
                HSSFRichTextString text = new HSSFRichTextString(headers[j]);
                cell.setCellValue(text);
                cell.setCellStyle(style);
            }
            int k =1;
            for (Integer x :list){
                HSSFRow rown = sheet.createRow(rowNum);
                rown.createCell(0).setCellValue(k+"月");
                rown.createCell(1).setCellValue(x);
                k++;
                rowNum++;
            }
            i++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    @RequestMapping("exportUserAge")
    public void exportUserAge(HttpServletResponse response) throws InterruptedException,IOException{
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        final String[] agesChi = "12岁以下,12-18岁,18-30岁,30-60岁,60岁以上".split(",");
        final int[] ages = {0,12,18,30,60,90};

        HashMap<String, int[]> hashMap = new HashMap<String, int[]>();
        for (int i = 0; i < ages.length -1; i++) {
            int[] put = {ages[i], ages[i+1]};
            hashMap.put(agesChi[i], put);
        }

        ArrayList<Map> mapArrayList = new ArrayList<>();
        for(String agec:agesChi){
            Map map = new HashMap();
            int temp =0;
            for (String agec1:agesChi){
                if (agec.contains(agec1)){
                    String drilldown = "a" + temp;
                    map.put("drilldown",drilldown);
                }
                temp ++;
            }

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, Object> map1 = new HashedMap();
                        map1.put("firstAge",(int)hashMap.get(agec)[0]);
                        map1.put("lastAge",(int)hashMap.get(agec)[1]);
                        map.put("name", agec);
                        map.put("y",userService.countAge(map1));
                        mapArrayList.add(map);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("游客年龄段统计");
        //设置导出的表的名字
        String fileName = "游客年龄段统计.xls";
        int rowNum =1;
        String [] headers = {"年龄段","人数"};
        HSSFRow row = sheet.createRow(0);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        for(int i=0;i<headers.length;i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);

        }
        for (Map map : mapArrayList) {
            HSSFRow rown = sheet.createRow(rowNum);
            String key = (String) map.get("name");
            Integer value = (Integer) map.get("y");
            rown.createCell(0).setCellValue(key);
            rown.createCell(1).setCellValue(value);
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();

    }
    @RequestMapping("exportConsumption")
    public void exportConsumption(HttpServletResponse response)throws InterruptedException,IOException{
        CountDownLatch countDownLatch = new CountDownLatch(10);
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
                if(map.containsKey(String.valueOf(consumption))){
                    arrayList.add((Integer) map.get(String.valueOf(consumption)));
                }
            }
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("游客年龄段统计");
        //设置导出的表的名字
        String fileName = "游客年龄段统计.xls";
        int rowNum =1;
        String [] headers = {"年龄段","人数"};
        HSSFRow row = sheet.createRow(0);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        for(int i=0;i<headers.length;i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);

        }
        String[] data = {"200以内", "200-300", "300-500", "500-800", "800-1k", "1k-2k", "2k-3k", "3-4k", "4-5k", "5k以上"};
        int i = 0;
        for (Integer x : arrayList) {
            HSSFRow rown = sheet.createRow(rowNum);
            String key = data[i];
            Integer value = x;
            rown.createCell(0).setCellValue(key);
            rown.createCell(1).setCellValue(value);
            rowNum++;
            i++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
