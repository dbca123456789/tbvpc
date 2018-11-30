package com.gxu.tbvp.service.serviceImpl;

import com.github.pagehelper.util.StringUtil;
import com.gxu.tbvp.domain.Produce;
import com.gxu.tbvp.mapper.ProduceMapper;
import com.gxu.tbvp.service.ProduceService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProduceServiceImpl extends BaseService<Produce> implements ProduceService {

    @Resource
    private ProduceMapper produceMapper;

    @Override
    public int getScenicByName(String scenic) {
        Example example = new Example(Produce.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(scenic)){
            criteria.andLike("title", '%'+ scenic + "%");
        }
        List<Produce> produceList = selectByExample(example);
        if (produceList.size() != 0){
            return produceList.get(0).getProduceid();
        }
        return -1;
    }

    @Override
    public int[] selectAllScenicsId() {
        int[] scenics = produceMapper.selectAllScenicsId();
        return scenics;
    }
}
