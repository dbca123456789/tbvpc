package com.gxu.tbvp.service;

import com.gxu.tbvp.domain.Accessrecord;

import javax.xml.crypto.Data;
import java.util.List;

public interface AccessrecordService extends IService<Accessrecord> {

    int bachInsert(List<Accessrecord> accessrecordList);
    int selectAccessCountByTime(String startTime,String endTime);
}
