package com.whc.api;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {

    @Test
    void contextLoads() {
        String password = "whc12345";
        //一次加密的结果
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println(md5Hash.toHex());
        //带盐加密
        Md5Hash md5Hash1 = new Md5Hash(password,"salt");
        System.out.println(md5Hash1);
        //带盐迭代加密
        Md5Hash md5Hash2 = new Md5Hash(password,"salt",2);
        System.out.println(md5Hash2);
    }

}
