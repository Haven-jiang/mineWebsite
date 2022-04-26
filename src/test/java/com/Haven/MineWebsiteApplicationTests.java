package com.Haven;

import com.Haven.DTO.ResponsePackDTO;
import com.Haven.VO.UserYouthInfoVO;
import com.Haven.entity.UserYouthData;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.service.EmailSendService;
import com.Haven.service.JxYouthService;
import com.Haven.service.UserYouthDataService;
import com.Haven.service.UserYouthTaskService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Haven.utils.ConversionUtil.*;
import static com.Haven.utils.ConversionUtil.buildEmailInfoDTO;
import static com.Haven.utils.ImageWatermarkUtil.markImageByText;
import static com.Haven.utils.LinkTableUtil.addImagePath;

@SpringBootTest
class MineWebsiteApplicationTests {

    @Autowired
    private UserYouthDataService userYouthDataService;

    @Autowired
    private JxYouthService jxYouthService;

    @Autowired
    private UserYouthDataMapper userYouthDataMapper;

    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    private UserYouthTaskService userYouthTask;



//    private List<String> conversionJsonList(String content) {
//        List<String> finish = new ArrayList<>();
//        List<JSONObject> list = JSON.parseObject(content, List.class);
//        for (JSONObject obj : list) if (!obj.isEmpty())
//            finish.add(JSON.parseObject(obj.toString(), String.class));
//        return finish;
//    }

    @Test
    void contextLoads() throws IOException {
        jxYouthService.updateCourse();
    }

}
