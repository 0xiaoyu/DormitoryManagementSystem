package com.yu.controller;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.Code;
import com.yu.common.R;
import com.yu.domain.Housemaster;
import com.yu.service.HousemasterService;
import com.yu.utils.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/housemaster")
public class HousemasterController {

    @Autowired
    private HousemasterService service;


    @GetMapping()
    public R test(){
        return R.ok(Code.SUCCESS);
    }
    record LoginForm(String username, String password) {}
    @PostMapping("/login")
    public R login(@RequestBody LoginForm form) {
        System.out.println(form);
        Housemaster user = service.login(form.username, form.password);
        if (user == null)
            return R.error(Code.PASSWORD_ERROR);
        return R.ok(Map.of("token",JWTUtil.getToken(Map.of("id", user.getId()))));
//        return R.ok();
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return R.ok();
    }

    @GetMapping("/info")
    public R info(@RequestParam String token) {
        Map<String, Claim> map = JWTUtil.verifyToken(token);
        Integer id = map.get("id").asInt();
        Housemaster housemaster = service.getById(id);
        System.out.println(housemaster);
        return R.ok(housemaster);
    }

    record masterPage(Integer page, Integer limit,
                      String name, Integer dormitoryid) { }
    @PostMapping("/list")
    public R list(@RequestBody masterPage page) {
        Page<Housemaster> pages = new Page<>(page.page,page.limit);
        LambdaQueryWrapper<Housemaster> q = new LambdaQueryWrapper<>();
        q.like(StringUtils.hasText(page.name),Housemaster::getName,page.name);
        q.eq(page.dormitoryid!=null,Housemaster::getDormitoryid,page.dormitoryid);
        Page<Housemaster> list = service.page(pages,q);
        return R.ok(Map.of("items",list.getRecords(),"total",list.getTotal()));
    }

    /**
     * 添加后勤信息
     * @param housemaster 信息
     * @return 添加结果
     */
    @PostMapping("")
    public R add(@RequestBody Housemaster housemaster) {
        if (housemaster.getRoles()==null) {
            housemaster.setRoles(
                    switch (housemaster.getDormitoryid()) {
                        case -1 -> "admin";
                        case 0 -> "repair";
                        default -> "housem";
                    }
            );
        }
        boolean b = service.saveOrUpdate(housemaster);
        if (b)
            return R.ok();
        else
            return R.error();
    }

    /**
     * 删除后勤信息
     * @param id 后勤id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        boolean b = service.removeById(id);
        if (b)
            return R.ok();
        else
            return R.error();
    }

}
