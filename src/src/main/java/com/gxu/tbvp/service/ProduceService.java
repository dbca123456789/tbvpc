package com.gxu.tbvp.service;

import com.gxu.tbvp.domain.Buyrecord;
import com.gxu.tbvp.domain.Produce;

import java.util.List;

public interface ProduceService extends IService<Produce> {
    int getScenicByName(String scenic);

    int[] selectAllScenicsId();
}
