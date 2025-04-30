package myproject.storehousebackend.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import myproject.storehousebackend.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hou
 * @since 2025-04-29
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage mypage(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
