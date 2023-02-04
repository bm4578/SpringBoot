package xyz.onlytype.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.onlytype.dao.SysUserDao;
import xyz.onlytype.service.impl.SysUserImpl;
import xyz.onlytype.vo.UserRoleVO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 白也
 * @title
 * @date 2023/1/28 6:44 下午
 */
@SpringBootTest
class SysUserControllerTest {
    @Autowired
    private SysUserImpl sysUser;
    @Autowired
    private SysUserDao userDao;
    @Test
    void register() {
//        Boolean test = sysUser.register("test", "123456", "2109961182@qq.com");
//        System.out.println(test);
        List<UserRoleVO> rolesByUser = userDao.findRolesByUserId("420029352e7efb783f9396feb1a5d5e2");
        System.out.println(rolesByUser);
    }
}