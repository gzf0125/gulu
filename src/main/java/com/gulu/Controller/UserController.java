package com.gulu.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gulu.Common.R;
import com.gulu.Entity.User;
import com.gulu.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 移动端用户登录
     * @param phone
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map phone, HttpSession session ){
        log.info(phone.toString());
        //获取手机号
        String phone1 = phone.get("phone").toString();

        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone1);
        User user = userService.getOne(queryWrapper);
        if (user==null){
            //判断当前手机号是否为新用户，如果是就自动完成注册
            user=new User();
            user.setPhone(phone1);
            user.setStatus(1);
            userService.save(user);
        }
        if (user.getStatus()==0){
            return R.error("该账号已禁用");
        }
        session.setAttribute("user",user.getId());
        return R.success(user);
    }

    @PostMapping("/loginout")
    public R<String> loginout(HttpServletRequest request){
        request.getSession().removeAttribute("userphone");
        return R.success("退出成功");

    }
}
