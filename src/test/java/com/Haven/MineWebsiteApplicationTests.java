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

//        List<UserYouthInfoVO> userYouthInfoVOS = Arrays.asList(
//                new UserYouthInfoVO("20010301", "N0014000210051021", "陈春柳", "2793932390@qq.com", null),
//                new UserYouthInfoVO("20010302", "N0014000210051021", "陈道旭", "1192170220@qq.com", null),
//                new UserYouthInfoVO("20010303", "N0014000210051021", "陈俊杰", "1522020883@qq.com", null),
//                new UserYouthInfoVO("20010304", "N0014000210051021", "陈琦环", "3382965810@qq.com", null),
//                new UserYouthInfoVO("20010305", "N0014000210051021", "陈诗宇", "1318531679@qq.com", null),
//                new UserYouthInfoVO("20010306", "N0014000210051021", "范菲菲", "2069631473@qq.com", null),
//                new UserYouthInfoVO("21010408", "N0014000210101032", "董阳洋", "2998249334@qq.com", null),
//                new UserYouthInfoVO("20010307", "N0014000210051021", "高海娇", "1419245502@qq.com", null),
//                new UserYouthInfoVO("20010308", "N0014000210051021", "官佐森", "1749254581@qq.com", null),
//                new UserYouthInfoVO("20010309", "N0014000210051021", "郭晓璇", "1620861728@qq.com", null),
//                new UserYouthInfoVO("20010310", "N0014000210051021", "何海", "1964384326@qq.com", null),
//                new UserYouthInfoVO("20010311", "N0014000210051021", "侯帅峰", "1796186808@qq.com", null),
//                new UserYouthInfoVO("20010312", "N0014000210051021", "黄恒", "1735450371@qq.com", null),
//                new UserYouthInfoVO("20010313", "N0014000210051021", "黄懿", "2419931953@qq.com", null),
//                new UserYouthInfoVO("20010314", "N0014000210051021", "黄永良", "1272631785@qq.com", null),
//                new UserYouthInfoVO("20010315", "N0014000210051021", "姜涵文", "haven-just@qq.com",null),
//                new UserYouthInfoVO("20010316", "N0014000210051021", "揭翔宇", "1835819049@qq.com", null),
//                new UserYouthInfoVO("20010317", "N0014000210051021", "金坤建", "1597255256@qq.com", null),
//                new UserYouthInfoVO("20010318", "N0014000210051021", "李泓兴", "1329164133@qq.com", null),
//                new UserYouthInfoVO("20010319", "N0014000210051021", "李惠", "2862965094@qq.com", null),
//                new UserYouthInfoVO("20010320", "N0014000210051021", "李钊锋", "3443543946@qq.com", null),
//                new UserYouthInfoVO("20010321", "N0014000210051021", "刘可明", "2407039626@qq.com", null),
//                new UserYouthInfoVO("20010322", "N0014000210051021", "刘漠沙", "1079347928@qq.com", null),
//                new UserYouthInfoVO("20010323", "N0014000210051021", "刘幸子", "1914561684@qq.com", null),
//                new UserYouthInfoVO("20010324", "N0014000210051021", "罗家辉", "1747771784@qq.com", null),
//                new UserYouthInfoVO("20010325", "N0014000210051021", "彭旋", "2691160907@qq.com", null),
//                new UserYouthInfoVO("20010326", "N0014000210051021", "苏俊雄", "2328625823@qq.com", null),
//                new UserYouthInfoVO("20010327", "N0014000210051021", "谭文龙", "2316288716@qq.com", null),
//                new UserYouthInfoVO("20010328", "N0014000210051021", "涂心宇", "2973809401@qq.com", null),
//                new UserYouthInfoVO("20010329", "N0014000210051021", "王晨欣", "2685587621@qq.com", null),
//                new UserYouthInfoVO("20010330", "N0014000210051021", "王佳文", "3263391838@qq.com", null),
//                new UserYouthInfoVO("20010331", "N0014000210051021", "王明驹", "2870556297@qq.com", null),
//                new UserYouthInfoVO("20010332", "N0014000210051021", "王轶军", "2425536252@qq.com", null),
//                new UserYouthInfoVO("20010333", "N0014000210051021", "韦麟", "1514462206@qq.com", null),
//                new UserYouthInfoVO("20010334", "N0014000210051021", "魏南烽", "1941123420@qq.com", null),
//                new UserYouthInfoVO("20010335", "N0014000210051021", "温倬儒", "1428170070@qq.com", null),
//                new UserYouthInfoVO("20010336", "N0014000210051021", "吴送粮", "2233712060@qq.com", null),
//                new UserYouthInfoVO("20010337", "N0014000210051021", "夏忻宸", "2923593517@qq.com", null),
//                new UserYouthInfoVO("20010338", "N0014000210051021", "谢旭博", "756652613@qq.com", null),
//                new UserYouthInfoVO("20010339", "N0014000210051021", "徐洪伟", "2911852518@qq.com", null),
//                new UserYouthInfoVO("20010340", "N0014000210051021", "徐锦波", "1484439079@qq.com", null),
//                new UserYouthInfoVO("20010341", "N0014000210051021", "许景怡", "2994054772@qq.com", null),
//                new UserYouthInfoVO("20010342", "N0014000210051021", "许祖俊", "3123303176@qq.com", null),
//                new UserYouthInfoVO("20010343", "N0014000210051021", "薛骏", "1842994498@qq.com", null),
//                new UserYouthInfoVO("20010344", "N0014000210051021", "杨朝龙", "1986238301@qq.com", null),
//                new UserYouthInfoVO("20010345", "N0014000210051021", "杨文婕", "1460698062@qq.com", null),
//                new UserYouthInfoVO("20010346", "N0014000210051021", "袁启宝", "2073878463@qq.com", null),
//                new UserYouthInfoVO("20010347", "N0014000210051021", "张启仲", "1224758107@qq.com", null),
//                new UserYouthInfoVO("20010348", "N0014000210051021", "张馨月", "2379878163@qq.com", null),
//                new UserYouthInfoVO("20010349", "N0014000210051021", "赵逸凡", "2673129323@qq.com", null),
//                new UserYouthInfoVO("20010350", "N0014000210051021", "郑小龙", "2944232934@qq.com", null),
//                new UserYouthInfoVO("20010351", "N0014000210051021", "郑毅", "3453821669@qq.com", null),
//                new UserYouthInfoVO("20010352", "N0014000210051021", "朱杰", "1985472781@qq.com", null),
//                new UserYouthInfoVO("20010353", "N0014000210051021", "朱俊杰", "2036506047@qq.com", null),
//                new UserYouthInfoVO("20010354", "N0014000210051021", "左悦平", "1812887397@qq.com", null),
//                new UserYouthInfoVO("20020601", "N0014000210051021", "陈思寒", "1157930658@qq.com", null),
//                new UserYouthInfoVO("20030402", "N0014000210051021", "蔡永麒", "1826751569@qq.com", null),
//                new UserYouthInfoVO("20070622", "N0014000210051021", "刘啸毅", "2997497206@qq.com", null),
//                new UserYouthInfoVO("20130108", "N0014000210051021", "范德玄", "1780168093@qq.com", null),
//                new UserYouthInfoVO("20010505", "N0014000210051023", "陈俊荣", "1272673681@qq.com", null),
//                new UserYouthInfoVO("恩克吉雅", "N0013001610031073", "20本会计4班", "1879975061@qq.com", null)
//        );
//        ArrayList<UserYouthInfoVO> userYouthInfoVOS = new ArrayList<>(List.of(new UserYouthInfoVO("20010625", "N0014000210051024", "万佳莹", "1621312272@qq.com", "59 59 22 ? * 2"),
//                new UserYouthInfoVO("恩克吉雅", "N0013001610031073", "20本会计4班", "1879975061@qq.com", "59 59 22 ? * 2")));
        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, "恩克吉雅")
        );
        userYouthData.setCron("6 7 23 ? * 2");
        userYouthTask.modifyTask(userYouthData);
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
