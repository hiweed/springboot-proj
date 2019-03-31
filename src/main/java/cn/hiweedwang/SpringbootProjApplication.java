package cn.hiweedwang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class SpringbootProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProjApplication.class, args);
    }

}
