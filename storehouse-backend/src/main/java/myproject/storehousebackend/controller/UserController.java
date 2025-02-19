package myproject.storehousebackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import myproject.storehousebackend.common.QueryPageParam;
import myproject.storehousebackend.common.Result;
import myproject.storehousebackend.entity.User;
import myproject.storehousebackend.service.UserService;
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
 * @since 2025-02-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getUsers() {
        return userService.list();
    }

    // add user
    @PostMapping("/save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    // change user
    @PutMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    // add or change user
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    // delete user
    @DeleteMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    // search user
    @GetMapping("/listP")
    public List<User> listP(@RequestBody User user) {
        LambdaQueryWrapper<User> la = new LambdaQueryWrapper<User>();
        la.like(User::getName, user.getName());
        return userService.list(la);
    }

    // search user in certain page
    @GetMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<User> page = new Page<>(query.getPagenum(), query.getPagesize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");

        LambdaQueryWrapper<User> la = new LambdaQueryWrapper<>();
        la.like(User::getName, name);

        IPage presult = userService.page(page, la);

        return Result.suc(presult.getRecords(), page.getTotal());
    }

    // return all user in certain page, use self-made method
    @GetMapping("/listPageC")
    public Result listPageC(@RequestBody QueryPageParam query) {
        Page<User> page = new Page<>(query.getPagenum(), query.getPagesize());

        IPage presult = userService.pageC(page);

        return Result.suc(presult.getRecords(), presult.getTotal());
    }
}
