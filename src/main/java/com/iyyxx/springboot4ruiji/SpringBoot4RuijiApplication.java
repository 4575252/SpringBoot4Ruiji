package com.iyyxx.springboot4ruiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication
public class SpringBoot4RuijiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot4RuijiApplication.class, args);
    }

}
