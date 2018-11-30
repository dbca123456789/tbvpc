package com.gxu.tbvp.mapper;

import com.gxu.tbvp.domain.Accessrecord;
import com.gxu.tbvp.utils.MyMapper;
import org.apache.ibatis.annotations.Param;


public interface AccessrecordMapper extends MyMapper<Accessrecord> {
    Integer selectAccessCountByTime(@Param("startTime")String startTime, @Param("endTime")String endTime);
}