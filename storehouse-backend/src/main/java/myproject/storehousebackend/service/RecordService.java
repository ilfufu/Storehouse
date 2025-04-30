package myproject.storehousebackend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import myproject.storehousebackend.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hou
 * @since 2025-04-29
 */
public interface RecordService extends IService<Record> {
    IPage mypage(IPage<Record> page, Wrapper wrapper);
}
