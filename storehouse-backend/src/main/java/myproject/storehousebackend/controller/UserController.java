package myproject.storehousebackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import myproject.storehousebackend.common.QueryPageParam;
import myproject.storehousebackend.common.Result;
import myproject.storehousebackend.entity.Menu;
import myproject.storehousebackend.entity.User;
import myproject.storehousebackend.service.MenuService;
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

    @Autowired
    private MenuService menuService;

    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.save(user) ? Result.suc() : Result.fail();
    }

    @PutMapping("/mod")
    public Result mod(@RequestBody User user) {
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        return userService.removeById(id) ? Result.suc() : Result.fail();
    }

    @GetMapping("/findByAccount")
    public Result findByAccount(@RequestParam("account") String account) {
        List list = userService.lambdaQuery().eq(User::getAccount, account).list();
        return list.size() > 0 ? Result.suc() : Result.fail();
    }

    @PostMapping("/search")
    public Result search(@RequestBody QueryPageParam query) {
        Page<User> page = new Page<>(query.getPagenum(), query.getPagesize());

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String gender = (String) param.get("gender");
        String roleid = (String) param.get("roleId");

        LambdaQueryWrapper<User> la = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            la.like(User::getName, name);
        }
        if(StringUtils.isNotBlank(gender)){
            la.eq(User::getGender, gender);
        }
        if(StringUtils.isNotBlank(roleid)){
            la.eq(User::getRoleId, roleid);
        }

        IPage presult = userService.page(page, la);

        return Result.suc(presult.getRecords(), page.getTotal());
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List lis = userService.lambdaQuery()
                .eq(User::getAccount, user.getAccount())
                .eq(User::getPassword, user.getPassword())
                .list();
        if(!lis.isEmpty()){
            User user1 = (User) lis.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user", user1);
            res.put("menu", menuList);
            return Result.suc(res, (long)1);
        }
        return Result.fail();
    }
}
