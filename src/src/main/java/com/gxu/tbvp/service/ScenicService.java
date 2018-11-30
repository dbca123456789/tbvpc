package com.gxu.tbvp.service;

import com.github.pagehelper.PageInfo;
import com.gxu.tbvp.domain.Scenic;

public interface ScenicService extends IService<Scenic>  {

    int getScenicByName(String scenic);

    int[] selectAllScenicsId();

    String selectScenicByKey(Integer key);

    PageInfo<Scenic> selectByPage(Scenic scenic, int start, int length);

    void delScenic(Integer scenicid);
}
