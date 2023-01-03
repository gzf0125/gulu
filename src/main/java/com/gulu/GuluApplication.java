package com.gulu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j//能省略get、set方法
@SpringBootApplication
public class GuluApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuluApplication.class,args);
        log.info("项目启动成功。。。");//输出日志
    }
}
