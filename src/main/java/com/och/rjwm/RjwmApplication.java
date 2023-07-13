package com.och.rjwm;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


// TODO: 把这些代码上传GITHUB
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class RjwmApplication {

    public static void main(String[] args) {
        SpringApplication.run(RjwmApplication.class, args);
    }

}
