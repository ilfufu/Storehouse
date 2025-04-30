package myproject.storehousebackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import myproject.storehousebackend.common.QueryPageParam;
import myproject.storehousebackend.common.Result;
import myproject.storehousebackend.entity.Goods;
import myproject.storehousebackend.entity.Record;
import myproject.storehousebackend.service.GoodsService;
import myproject.storehousebackend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hou
 * @since 2025-04-29
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/save")
    public Result save(@RequestBody Record record) {
        Goods goods = goodsService.getById(record.getGoods());
        int n = record.getCount();
        if("2".equals(record.getAction())){
            n = -n;
            record.setCount(n);
        }
        int m = goods.getCount();
        if(n + m < 0){
            return Result.fail();
        }
        goods.setCount(n + m);
        goodsService.updateById(goods);
        return recordService.save(record) ? Result.suc() : Result.fail();
    }

    @PostMapping("/search")
    public Result search(@RequestBody QueryPageParam query) {
        Page<Record> page = new Page<>(query.getPagenum(), query.getPagesize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String storage = (String) param.get("storage");
        String goodstype = (String) param.get("goodstype");
        String roleId = (String) param.get("roleId");
        String userId = (String) param.get("userId");

        QueryWrapper<Record> qw = new QueryWrapper();
        qw.apply("a.goods=b.id and b.storage=c.id and b.goodstype=d.id");
        if(StringUtils.isNotBlank(roleId) && "2".equals(roleId)){
            qw.apply("a.userid="+userId);
        }
        if(StringUtils.isNotBlank(name)){
            qw.like("b.name",name);
        }
        if(StringUtils.isNotBlank(storage)){
            qw.eq("c.id",storage);
        }
        if(StringUtils.isNotBlank(goodstype)){
            qw.eq("d.id",goodstype);
        }

        IPage presult = recordService.mypage(page, qw);

        return Result.suc(presult.getRecords(), page.getTotal());
    }
}
