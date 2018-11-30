package com.gxu.tbvp.mapper;

import com.gxu.tbvp.domain.Scenic;
import com.gxu.tbvp.utils.MyMapper;

public interface ScenicMapper extends MyMapper<Scenic> {


    int[] selectAllScenicsId();
    String selectScenicByKey(int key);

}