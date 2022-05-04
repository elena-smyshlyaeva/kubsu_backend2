package com.example.kubsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("/com/example/kubsu/config/servlet")
public class KubsuApplication {

    public static void main(String[] args) {
        SpringApplication.run(KubsuApplication.class, args);
    }

}
