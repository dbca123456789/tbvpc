package com.gxu.tbvp.service.serviceImpl;

import com.gxu.tbvp.domain.Buyrecord;
import com.gxu.tbvp.domain.ScenicBuyrecord;
import com.gxu.tbvp.mapper.ScenicBuyrecordMapper;
import com.gxu.tbvp.mapper.ScenicMapper;
import com.gxu.tbvp.service.ScenicBuyrecordService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScenicBuyrecordServiceImpl extends BaseService<ScenicBuyrecord> implements ScenicBuyrecordService {

    @Resource
    private ScenicBuyrecordMapper scenicBuyrecordMapper;

    @Override
    public int insertList(List<ScenicBuyrecord> scenicBuyrecordList) {
        try {
            scenicBuyrecordMapper.insertList(scenicBuyrecordList);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<ScenicBuyrecord> selectByscenicId(int scenicId) {
        Example example = new Example(ScenicBuyrecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("scenicid", scenicId);
        List<ScenicBuyrecord> scenicBuyrecordList = scenicBuyrecordMapper.selectByExample(example);
        return scenicBuyrecordList;
    }

    @Override
    public List<ScenicBuyrecord> getUserScenicBuyrecord(int id, int userId) {
        try {
            List<ScenicBuyrecord> scenicBuyrecordList = scenicBuyrecordMapper.getUserScenicBuyrecord(id, userId);
            return scenicBuyrecordList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
