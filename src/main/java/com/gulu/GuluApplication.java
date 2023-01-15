package com.gulu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j//能省略get、set方法
@SpringBootApplication
@ServletComponentScan//加入该注解才能扫描Web FIlter过滤器
@EnableTransactionManagement
@EnableCaching
public class GuluApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuluApplication.class,args);
        log.info("项目启动成功。。。");//输出日志
    }
}
