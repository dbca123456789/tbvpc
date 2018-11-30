package com.gxu.tbvp.service.serviceImpl;

import com.gxu.tbvp.domain.Accessrecord;
import com.gxu.tbvp.mapper.AccessrecordMapper;
import com.gxu.tbvp.service.AccessrecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AccessrecordServiceImpl extends BaseService<Accessrecord> implements AccessrecordService {

    @Resource
    private AccessrecordMapper accessrecordMapper;

    @Override
    public int bachInsert(List<Accessrecord> accessrecordList) {
        try {
            accessrecordMapper.insertList(accessrecordList);
            return 1;
        } catch (Exception e){
            return 0;
        }
    }
    @Override
    public int selectAccessCountByTime(String startTime,String endTime){
        return accessrecordMapper.selectAccessCountByTime(startTime,endTime);
    }
}
