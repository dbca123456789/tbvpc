package com.gxu.tbvp.mapper;

import com.gxu.tbvp.domain.ScenicBuyrecord;
import com.gxu.tbvp.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScenicBuyrecordMapper extends MyMapper<ScenicBuyrecord> {
    List<ScenicBuyrecord> getUserScenicBuyrecord(@Param("scenicid") int id,@Param("userid") int userid);
}