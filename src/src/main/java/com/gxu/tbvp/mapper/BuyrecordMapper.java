package com.gxu.tbvp.mapper;

import com.gxu.tbvp.domain.Buyrecord;
import com.gxu.tbvp.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BuyrecordMapper extends MyMapper<Buyrecord> {
    int countPeopleByPrice(Map<String, Object> map);

    List<Buyrecord> getUserScenicBuyrecord(@Param("id") int id, @Param("userId") int userId);
}