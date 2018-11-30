package com.gxu.tbvp.service;

import com.gxu.tbvp.domain.Buyrecord;
import com.gxu.tbvp.domain.Produce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BuyrecordService extends IService<Buyrecord> {
    int insertList(List<Buyrecord> buyrecordList);

    List<Produce> selectAllProducePrice();

    double selectProducePrice(int produceId);

    int updatePrice(List<Produce> produceList);

    int countPeopleByPrice(Map<String, Object> map);

    List<Buyrecord> selectByscenicId(int scenicId);

    List<Buyrecord> getUserScenicBuyrecord(int id, int userId);
}
