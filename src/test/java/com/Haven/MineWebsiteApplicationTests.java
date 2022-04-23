package com.Haven;

import com.Haven.entity.UserYouthData;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.service.EmailSendService;
import com.Haven.service.JxYouthService;
import com.Haven.service.UserYouthDataService;
import com.Haven.utils.ConversionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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


    @Test
    void contextLoads() throws IOException {
        String path = MineWebsiteApplication.class.getResource("").getPath();
        String localPath = path.substring(0, path.lastIndexOf("target")) + "target/";
        System.out.println("localPath = " + path);

        userYouthDataService.addUserYouthData("20010315", "N00140xxxxxxxxx", "random", "1476378219@qq.com");
        String data = JSON.toJSONString(ConversionUtil.toUserYouthDataDTO(
                userYouthDataMapper.selectOne(
                        new LambdaQueryWrapper<UserYouthData>()
                                .select()
                                .eq(UserYouthData::getUserid, "20010315")
                )
        ));
        emailSendService.sendMimeMail(ConversionUtil.buildEmailInfoDTO(ConversionUtil.toUserYouthData(data)));
//        String userid = "20115942ser33";
//
//        UserYouthData userYouthData = userYouthDataMapper.selectOne(
//                new LambdaQueryWrapper<UserYouthData>()
//                        .select()
//                        .eq(UserYouthData::getUserid, userid)
//        );
//        userYouthData.setLastFinishTime(LocalDateTime.now());
//        userYouthData.putFinishHistory(new FinishLogDTO(LocalDateTime.now(), getCurrentCourse()));
//        userYouthDataMapper.updateById(userYouthData);
    }

}
