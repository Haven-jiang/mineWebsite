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

//        try {
//            String data = JSON.toJSONString(toUserYouthDataDTO(
//                    userYouthDataMapper.selectOne(
//                            new LambdaQueryWrapper<UserYouthData>()
//                                    .select()
//                                    .eq(UserYouthData::getUserid, "20010315")
//                    )
//            ));
//            ResponsePackDTO responsePackDTO = new ResponsePackDTO(404, "");
//            while (responsePackDTO.getStatus() != 200) responsePackDTO = jxYouthService.postData(data);
//
//            //TODO: 下载创建初始化截图
//            UserYouthData userYouthData = addImagePath(toUserYouthData(data));
//
//            //TODO: 截图添加水印
//            markImageByText(userYouthData.getUserid() + ' ' + userYouthData.getRealName(), getImagePathById(userYouthData.getImageId()), null, null);
//
//            //TODO: 发邮件
//            if (!userYouthData.getEmailId().isEmpty())
//                emailSendService.sendMimeMail(buildEmailInfoDTO(userYouthData));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        ArrayList<UserYouthInfoVO> userYouthInfoVOS = new ArrayList<>(List.of(new UserYouthInfoVO("20010625", "N0014000210051024", "万佳莹", "1621312272@qq.com", "59 59 22 ? * 2"),
//                new UserYouthInfoVO("恩克吉雅", "N0013001610031073", "20本会计4班", "1879975061@qq.com", "59 59 22 ? * 2")));
//        UserYouthData userYouthData = userYouthDataMapper.selectOne(
//                new LambdaQueryWrapper<UserYouthData>()
//                        .select()
//                        .eq(UserYouthData::getUserid, "恩克吉雅")
//        );
//        userYouthData.setCron("6 7 23 ? * 2");
//        userYouthTask.modifyTask(userYouthData);
//        userYouthDataService.addUserYouthData(userYouthInfoVOS);
//        jxYouthService.updateCourse();
//
//        String userid = "20010315";
//        userYouthDataService.addUserYouthData(userid, "123214", null, "1817856261@qq.com", "姜涵文");
//
//        UserYouthData userYouthData = userYouthDataMapper.selectOne(
//                new LambdaQueryWrapper<UserYouthData>()
//                        .select()
//                        .eq(UserYouthData::getUserid, userid)
//        );
//
//        userYouthData.setLastFinishTime(LocalDateTime.now());
//        userYouthDataMapper.updateById(userYouthData);
//
//        //TODO: 下载创建初始化截图
//        addImagePath(userYouthData);
//
//        //TODO: 截图添加水印
//        markImageByText(userYouthData.getUserid() + ' ' + userYouthData.getRealName(), getImagePathById(userYouthData.getImageId()), null, null);
//
//        //TODO: 发邮件
//        if (!userYouthData.getEmailId().isEmpty())
//            emailSendService.sendMimeMail(buildEmailInfoDTO(userYouthData));

//        userYouthData.setLastFinishTime(LocalDateTime.now());
//        userYouthData.putFinishHistory(new FinishLogDTO(LocalDateTime.now(), getCurrentCourse()));
//        userYouthDataMapper.updateById(userYouthData);
//
//
//        String path = MineWebsiteApplication.class.getResource("").getPath();
//        String localPath = path.substring(0, path.lastIndexOf("target")) + "target/";
//        System.out.println("localPath = " + path);
//
//        userYouthDataService.addUserYouthData("20010315", "N00140xxxxxxxxx", "random", "1476378219@qq.com");
//        String data = JSON.toJSONString(ConversionUtil.toUserYouthDataDTO(
//                userYouthDataMapper.selectOne(
//                        new LambdaQueryWrapper<UserYouthData>()
//                                .select()
//                                .eq(UserYouthData::getUserid, "20010315")
//                )
//        ));
//        emailSendService.sendMimeMail(ConversionUtil.buildEmailInfoDTO(ConversionUtil.toUserYouthData(data)));
    }

}
