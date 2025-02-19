package myproject.storehousebackend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import myproject.storehousebackend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hou
 * @since 2025-02-18
 */
public interface UserService extends IService<User> {

    IPage pageC(IPage<User> page);
}
