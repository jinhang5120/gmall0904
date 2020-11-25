package com.jh.gmall;

import com.jh.gmall.mapper.UmsMemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired(required = false)
    UmsMemberMapper umsMemberMapper;
    @Test
    public void test01(){
        System.out.println("++++++++++++++");
        System.out.println("umsMemberMapper.selectById(1) = " + umsMemberMapper.selectById(1));
    }
}
