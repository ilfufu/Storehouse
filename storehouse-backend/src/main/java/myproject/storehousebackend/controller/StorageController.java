package myproject.storehousebackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import myproject.storehousebackend.common.QueryPageParam;
import myproject.storehousebackend.common.Result;
import myproject.storehousebackend.entity.Storage;
import myproject.storehousebackend.entity.User;
import myproject.storehousebackend.service.StorageService;
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
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    // add storage
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage) {
        return storageService.save(storage) ? Result.suc() : Result.fail();
    }

    // change storage
    @PutMapping("/mod")
    public Result mod(@RequestBody Storage storage) {
        return storageService.updateById(storage) ? Result.suc() : Result.fail();
    }

    // delete storage
    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        return storageService.removeById(id) ? Result.suc() : Result.fail();
    }

    // search by name
    @GetMapping("/findByName")
    public Result findByAccount(@RequestParam("name") String name) {
        List list = storageService.lambdaQuery().eq(Storage::getName, name).list();
        return list.size() > 0 ? Result.suc() : Result.fail();
    }

    // search storage in certain page
    @PostMapping("/search")
    public Result search(@RequestBody QueryPageParam query) {
        Page<Storage> page = new Page<>(query.getPagenum(), query.getPagesize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        LambdaQueryWrapper<Storage> la = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            la.like(Storage::getName, name);
        }

        IPage presult = storageService.page(page, la);

        return Result.suc(presult.getRecords(), page.getTotal());
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List list = storageService.list();
        return Result.suc(list, (long)0);
    }
}
