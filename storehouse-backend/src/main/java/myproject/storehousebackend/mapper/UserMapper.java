package myproject.storehousebackend.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import myproject.storehousebackend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hou
 * @since 2025-02-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage pageC(IPage<User> page);
}
