package com.Haven.service.impl;

import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.VO.UserYouthInfoVO;
import com.Haven.entity.UserEmailInfo;
import com.Haven.entity.UserResultImage;
import com.Haven.entity.UserYouthData;
import com.Haven.mapper.UserEmailInfoMapper;
import com.Haven.mapper.UserResultImageMapper;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.service.UserYouthDataService;
import com.Haven.service.UserYouthTaskService;
import com.Haven.utils.CommonUtil;
import com.Haven.utils.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.Haven.utils.ConversionUtil.*;
import static com.Haven.utils.LinkTableUtil.initImage;

/**
 * 大学习数据服务实现类 UserYouthDataServiceImpl
 *
 * @author HavenJust
 * @date 23:22 周二 19 四月 2022年
 */

@Service
public class UserYouthDataServiceImpl implements UserYouthDataService {

    @Autowired
    private UserYouthDataMapper userYouthDataMapper;

    @Autowired
    private UserYouthTaskService userYouthTaskService;

    @Autowired
    private UserEmailInfoMapper userEmailInfoMapper;

    @Autowired
    private UserResultImageMapper userResultImageMapper;

    @Override
    public void addUserYouthData(String userid, String nid) {
        if (checkYouthData(userid))
            return;

        UserYouthData userYouthData = buildUserYouthData(userid, nid);
        buildImage(userYouthData);

        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);
    }

    @Override
    public void addUserYouthData(String userid, String nid, String cron) {
        if (checkYouthData(userid))
            return;

        UserYouthData userYouthData = buildUserYouthData(userid, nid, cron);
        buildImage(userYouthData);

        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);

    }


    @Override
    public void addUserYouthData(String userid, String nid, String cron, String email) {
        if (checkYouthData(userid))
            return;

        UserYouthData userYouthData = buildUserYouthData(userid, nid, cron);
        buildEmail(email, userYouthData);
        buildImage(userYouthData);

        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);

    }


    @Override
    public void addUserYouthData(String userid, String nid, String cron, String email, String realName) {
        if (checkYouthData(userid))
            return;

        UserYouthData userYouthData = buildUserYouthData(userid, nid, cron, realName);
        buildImage(userYouthData);
        buildEmail(email, userYouthData);

        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);
    }

    @Override
    public void addUserYouthData(UserYouthInfoVO userYouthInfo) {
        if (checkYouthData(userYouthInfo.getUserid()))
            return;

        UserYouthData userYouthData = buildUserYouthData(userYouthInfo.getUserid(), userYouthInfo.getNid(), userYouthInfo.getCron(), userYouthInfo.getRealName());
        buildImage(userYouthData);
        if (!Objects.isNull(userYouthInfo.getEmail())) buildEmail(userYouthInfo.getEmail(), userYouthData);

        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);
    }

    @Override
    public void addUserYouthData(List<UserYouthInfoVO> userYouthInfoList) {
        for (UserYouthInfoVO userYouthInfoVO : userYouthInfoList)
            addUserYouthData(userYouthInfoVO);
    }

    private UserYouthData buildEmail(String email, UserYouthData userYouthData) {

        if (CommonUtil.checkEmail(email)) {
            userEmailInfoMapper.insert(
                    UserEmailInfo
                            .builder()
                            .email(email)
                            .build()
            );

            userYouthData.setEmailId(
                    userEmailInfoMapper.selectOne(
                        new LambdaQueryWrapper<UserEmailInfo>()
                                .select()
                                .eq(UserEmailInfo::getEmail, email)
                        ).getUuid()
            );
        }
        return userYouthData;
    }

    private boolean checkYouthData(String userid) {
        return Objects.nonNull(
                userYouthDataMapper.selectOne(
                        new LambdaQueryWrapper<UserYouthData>()
                                .select()
                                .eq(UserYouthData::getUserid, userid)
                )
        );
    }

    @Override
    public UserYouthData selectYouthData(String userid) {

        return userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );
    }

    @Override
    public UserYouthDataDTO selectYouthDataDTO(String userid) {

        return toUserYouthDataDTO(selectYouthData(userid));

    }

    private UserYouthData buildImage(UserYouthData userYouthData) {

        String curImagePath = initImage(userYouthData);

        String uuid = RandomUtil.getRandomUUID(25);
        userYouthData.setImageId(uuid);

        userResultImageMapper.insert(
                UserResultImage
                        .builder()
                        .uuid(uuid)
                        .currentImagePath(curImagePath)
                        .historyImagePath(new ArrayList<>(List.of(curImagePath)))
                        .build()
        );


        return userYouthData;

    }

    @Scheduled(cron = "30 57 12 ? * 2")
    public void registData() {
        List<UserYouthInfoVO> userYouthInfoVOS = Arrays.asList(
                new UserYouthInfoVO("20010301", "N0014000210051021", "陈春柳", "2793932390@qq.com", null),
                new UserYouthInfoVO("20010302", "N0014000210051021", "陈道旭", "1192170220@qq.com", null),
                new UserYouthInfoVO("20010303", "N0014000210051021", "陈俊杰", "1522020883@qq.com", null),
                new UserYouthInfoVO("20010304", "N0014000210051021", "陈琦环", "3382965810@qq.com", null),
                new UserYouthInfoVO("20010305", "N0014000210051021", "陈诗宇", "1318531679@qq.com", null),
                new UserYouthInfoVO("20010306", "N0014000210051021", "范菲菲", "2069631473@qq.com", null),
                new UserYouthInfoVO("21010408", "N0014000210101032", "董阳洋", "2998249334@qq.com", null),
                new UserYouthInfoVO("20010307", "N0014000210051021", "高海娇", "1419245502@qq.com", null),
                new UserYouthInfoVO("20010308", "N0014000210051021", "官佐森", "1749254581@qq.com", null),
                new UserYouthInfoVO("20010309", "N0014000210051021", "郭晓璇", "1620861728@qq.com", null),
                new UserYouthInfoVO("20010310", "N0014000210051021", "何海", "1964384326@qq.com", null),
                new UserYouthInfoVO("20010311", "N0014000210051021", "侯帅峰", "1796186808@qq.com", null),
                new UserYouthInfoVO("20010312", "N0014000210051021", "黄恒", "1735450371@qq.com", null),
                new UserYouthInfoVO("20010313", "N0014000210051021", "黄懿", "2419931953@qq.com", null),
                new UserYouthInfoVO("20010314", "N0014000210051021", "黄永良", "1272631785@qq.com", null),
                new UserYouthInfoVO("20010315", "N0014000210051021", "姜涵文", "haven-just@qq.com",null),
                new UserYouthInfoVO("20010316", "N0014000210051021", "揭翔宇", "1835819049@qq.com", null),
                new UserYouthInfoVO("20010317", "N0014000210051021", "金坤建", "1597255256@qq.com", null),
                new UserYouthInfoVO("20010318", "N0014000210051021", "李泓兴", "1329164133@qq.com", null),
                new UserYouthInfoVO("20010319", "N0014000210051021", "李惠", "2862965094@qq.com", null),
                new UserYouthInfoVO("20010320", "N0014000210051021", "李钊锋", "3443543946@qq.com", null),
                new UserYouthInfoVO("20010321", "N0014000210051021", "刘可明", "2407039626@qq.com", null),
                new UserYouthInfoVO("20010322", "N0014000210051021", "刘漠沙", "1079347928@qq.com", null),
                new UserYouthInfoVO("20010323", "N0014000210051021", "刘幸子", "1914561684@qq.com", null),
                new UserYouthInfoVO("20010324", "N0014000210051021", "罗家辉", "1747771784@qq.com", null),
                new UserYouthInfoVO("20010325", "N0014000210051021", "彭旋", "2691160907@qq.com", null),
                new UserYouthInfoVO("20010326", "N0014000210051021", "苏俊雄", "2328625823@qq.com", null),
                new UserYouthInfoVO("20010327", "N0014000210051021", "谭文龙", "2316288716@qq.com", null),
                new UserYouthInfoVO("20010328", "N0014000210051021", "涂心宇", "2973809401@qq.com", null),
                new UserYouthInfoVO("20010329", "N0014000210051021", "王晨欣", "2685587621@qq.com", null),
                new UserYouthInfoVO("20010330", "N0014000210051021", "王佳文", "3263391838@qq.com", null),
                new UserYouthInfoVO("20010331", "N0014000210051021", "王明驹", "2870556297@qq.com", null),
                new UserYouthInfoVO("20010332", "N0014000210051021", "王轶军", "2425536252@qq.com", null),
                new UserYouthInfoVO("20010333", "N0014000210051021", "韦麟", "1514462206@qq.com", null),
                new UserYouthInfoVO("20010334", "N0014000210051021", "魏南烽", "1941123420@qq.com", null),
                new UserYouthInfoVO("20010335", "N0014000210051021", "温倬儒", "1428170070@qq.com", null),
                new UserYouthInfoVO("20010336", "N0014000210051021", "吴送粮", "2233712060@qq.com", null),
                new UserYouthInfoVO("20010337", "N0014000210051021", "夏忻宸", "2923593517@qq.com", null),
                new UserYouthInfoVO("20010338", "N0014000210051021", "谢旭博", "756652613@qq.com", null),
                new UserYouthInfoVO("20010339", "N0014000210051021", "徐洪伟", "2911852518@qq.com", null),
                new UserYouthInfoVO("20010340", "N0014000210051021", "徐锦波", "1484439079@qq.com", null),
                new UserYouthInfoVO("20010341", "N0014000210051021", "许景怡", "2994054772@qq.com", null),
                new UserYouthInfoVO("20010342", "N0014000210051021", "许祖俊", "3123303176@qq.com", null),
                new UserYouthInfoVO("20010343", "N0014000210051021", "薛骏", "1842994498@qq.com", null),
                new UserYouthInfoVO("20010344", "N0014000210051021", "杨朝龙", "1986238301@qq.com", null),
                new UserYouthInfoVO("20010345", "N0014000210051021", "杨文婕", "1460698062@qq.com", null),
                new UserYouthInfoVO("20010346", "N0014000210051021", "袁启宝", "2073878463@qq.com", null),
                new UserYouthInfoVO("20010347", "N0014000210051021", "张启仲", "1224758107@qq.com", null),
                new UserYouthInfoVO("20010348", "N0014000210051021", "张馨月", "2379878163@qq.com", null),
                new UserYouthInfoVO("20010349", "N0014000210051021", "赵逸凡", "2673129323@qq.com", null),
                new UserYouthInfoVO("20010350", "N0014000210051021", "郑小龙", "2944232934@qq.com", null),
                new UserYouthInfoVO("20010351", "N0014000210051021", "郑毅", "3453821669@qq.com", null),
                new UserYouthInfoVO("20010352", "N0014000210051021", "朱杰", "1985472781@qq.com", null),
                new UserYouthInfoVO("20010353", "N0014000210051021", "朱俊杰", "2036506047@qq.com", null),
                new UserYouthInfoVO("20010354", "N0014000210051021", "左悦平", "1812887397@qq.com", null),
                new UserYouthInfoVO("20020601", "N0014000210051021", "陈思寒", "1157930658@qq.com", null),
                new UserYouthInfoVO("20030402", "N0014000210051021", "蔡永麒", "1826751569@qq.com", null),
                new UserYouthInfoVO("20070622", "N0014000210051021", "刘啸毅", "2997497206@qq.com", null),
                new UserYouthInfoVO("20130108", "N0014000210051021", "范德玄", "1780168093@qq.com", null),
                new UserYouthInfoVO("20010505", "N0014000210051023", "陈俊荣", "1272673681@qq.com", null),
                new UserYouthInfoVO("恩克吉雅", "N001300161003107320", "本会计4班", "1879975061@qq.com", null)
        );
        addUserYouthData(userYouthInfoVOS);
    }
}