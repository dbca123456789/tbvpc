package com.gxu.tbvp.mapper;

import com.gxu.tbvp.domain.Produce;
import com.gxu.tbvp.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ProduceMapper extends MyMapper<Produce> {
    double selectProducePrice(int produceId);

    List<Produce> selectByProdeceName(Map map);

    int[] selectAllScenicsId();
}