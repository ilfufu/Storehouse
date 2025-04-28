package myproject.storehousebackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import myproject.storehousebackend.common.QueryPageParam;
import myproject.storehousebackend.common.Result;
import myproject.storehousebackend.entity.Goods;
import myproject.storehousebackend.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hou
 * @since 2025-04-28
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    // add storage
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        return goodsService.save(goods) ? Result.suc() : Result.fail();
    }

    // change storage
    @PutMapping("/mod")
    public Result mod(@RequestBody Goods goods) {
        return goodsService.updateById(goods) ? Result.suc() : Result.fail();
    }

    // delete storage
    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        return goodsService.removeById(id) ? Result.suc() : Result.fail();
    }

    // search by name
    @GetMapping("/findByName")
    public Result findByAccount(@RequestParam("name") String name) {
        List list = goodsService.lambdaQuery().eq(Goods::getName, name).list();
        return list.size() > 0 ? Result.suc() : Result.fail();
    }

    // search storage in certain page
    @PostMapping("/search")
    public Result search(@RequestBody QueryPageParam query) {
        Page<Goods> page = new Page<>(query.getPagenum(), query.getPagesize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String storage = (String) param.get("storage");
        String goodstype = (String) param.get("goodstype");

        LambdaQueryWrapper<Goods> la = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            la.like(Goods::getName, name);
        }
        if(StringUtils.isNotBlank(storage)){
            la.like(Goods::getStorage, storage);
        }
        if(StringUtils.isNotBlank(goodstype)){
            la.like(Goods::getGoodstype, goodstype);
        }

        IPage presult = goodsService.page(page, la);

        return Result.suc(presult.getRecords(), page.getTotal());
    }
}
