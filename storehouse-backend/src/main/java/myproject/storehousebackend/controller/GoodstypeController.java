package myproject.storehousebackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import myproject.storehousebackend.common.QueryPageParam;
import myproject.storehousebackend.common.Result;
import myproject.storehousebackend.entity.Goodstype;
import myproject.storehousebackend.service.GoodstypeService;
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
 * @since 2025-04-27
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {
    @Autowired
    private GoodstypeService goodstypeService;

    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype) {
        return goodstypeService.save(goodstype) ? Result.suc() : Result.fail();
    }

    @PutMapping("/mod")
    public Result mod(@RequestBody Goodstype goodstype) {
        return goodstypeService.updateById(goodstype) ? Result.suc() : Result.fail();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        return goodstypeService.removeById(id) ? Result.suc() : Result.fail();
    }

    // search by name
    @GetMapping("/findByName")
    public Result findByAccount(@RequestParam("name") String name) {
        List list = goodstypeService.lambdaQuery().eq(Goodstype::getName, name).list();
        return list.size() > 0 ? Result.suc() : Result.fail();
    }

    @PostMapping("/search")
    public Result search(@RequestBody QueryPageParam query) {
        Page<Goodstype> page = new Page<>(query.getPagenum(), query.getPagesize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        LambdaQueryWrapper<Goodstype> la = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            la.like(Goodstype::getName, name);
        }

        IPage presult = goodstypeService.page(page, la);

        return Result.suc(presult.getRecords(), page.getTotal());
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List list = goodstypeService.list();
        return Result.suc(list, (long)0);
    }
}
