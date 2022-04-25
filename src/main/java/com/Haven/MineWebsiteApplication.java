package com.Haven;

import com.Haven.VO.UserYouthInfoVO;
import com.Haven.service.UserYouthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

import static com.Haven.utils.CommonUtil.getLocalPath;

@SpringBootApplication
public class MineWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MineWebsiteApplication.class, args);
    }

}