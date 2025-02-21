package myproject.storehousebackend.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import myproject.storehousebackend.entity.User;
import myproject.storehousebackend.mapper.UserMapper;
import myproject.storehousebackend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hou
 * @since 2025-02-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

}
