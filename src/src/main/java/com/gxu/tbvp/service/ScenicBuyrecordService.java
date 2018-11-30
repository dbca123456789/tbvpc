package com.gxu.tbvp.service;

import com.gxu.tbvp.domain.ScenicBuyrecord;

import java.util.List;

public interface ScenicBuyrecordService extends IService<ScenicBuyrecord> {
    int insertList(List<ScenicBuyrecord> scenicBuyrecordList);

    List<ScenicBuyrecord> selectByscenicId(int scenicId);

    List<ScenicBuyrecord> getUserScenicBuyrecord(int id, int userId);
}
