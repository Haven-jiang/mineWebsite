package com.Haven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.Haven.utils.CommonUtils.getLocalPath;

@SpringBootApplication
public class MineWebsiteApplication {

    public static void main(String[] args) {
        System.out.println(getLocalPath());
        SpringApplication.run(MineWebsiteApplication.class, args);
    }

}
