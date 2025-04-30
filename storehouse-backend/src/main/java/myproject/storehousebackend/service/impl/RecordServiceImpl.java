package myproject.storehousebackend.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import myproject.storehousebackend.entity.Record;
import myproject.storehousebackend.mapper.RecordMapper;
import myproject.storehousebackend.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hou
 * @since 2025-04-29
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Resource
    private RecordMapper recordMapper;
    @Override
    public IPage mypage(IPage<Record> page, Wrapper wrapper){
        return recordMapper.mypage(page,wrapper);
    }
}
