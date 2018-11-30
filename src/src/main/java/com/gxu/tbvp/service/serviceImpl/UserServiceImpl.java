package com.gxu.tbvp.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.gxu.tbvp.domain.User;
import com.gxu.tbvp.domain.UserRole;
import com.gxu.tbvp.mapper.UserMapper;
import com.gxu.tbvp.mapper.UserRoleMapper;
import com.gxu.tbvp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public PageInfo<User> selectByPage(User user, int start, int length) {
        int page = start/length+1;
        Example example = new Example(User.class);
        //使用mybatis Example Criteria like 模糊查询
        Example.Criteria criteria = example.createCriteria();

        if (StringUtil.isNotEmpty(user.getUsername())){
            criteria.andLike("username", "%" + user.getUsername());
        }
        if (user.getId() != null) {
            criteria.andEqualTo("id", user.getId());
        }
        if (user.getEnable() != null) {
            criteria.andEqualTo("enable", user.getEnable());
        }

        //分页查询
        PageHelper.startPage(page, length);
        List<User> userList = selectByExample(example);
        return new PageInfo<>(userList);
    }

    @Override
    public User selectByUsername(String username) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("username", username);
        List<User> userList = selectByExample(example);
        if(userList.size()>0){
            return userList.get(0);
        }
        return null;
    }

    //ADDDDDDDDDDDDDDDDDDDDDDD

    //ADDDDDDDDDDDD


    @Override
    //操作失败，则回滚
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void delUser(Integer userid) {
        //删除用户表
        mapper.deleteByPrimaryKey(userid);
        //删除用户角色表
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userid",userid);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public int insertBach(List<User> userList) {
        try {
            userMapper.insertList(userList);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int countSex(int userSex) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sex",userSex);
        int count = userMapper.selectCountByExample(example);
        return count;
    }

    @Override
    public void autoIncrement() {
        userMapper.autoIncrement();
    }

    @Override
    public int countAge(Map<String, Object> ageMap) {
        int count = userMapper.countAge(ageMap);
        return count;
    }
}
