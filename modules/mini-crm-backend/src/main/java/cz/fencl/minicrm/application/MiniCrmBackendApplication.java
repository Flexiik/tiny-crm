package cz.fencl.minicrm.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cz.fencl.minicrm")
public class MiniCrmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniCrmBackendApplication.class, args);
    }
}
