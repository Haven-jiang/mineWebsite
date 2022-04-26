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
import static com.Haven.utils.RandomUtil.getRandomUUID;

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
    //TODO: CRUD 增删改查
    //TODO: 信息增加

    /**
     * 添加一个青年大学习用户信息
     *
     * @param userid 学号
     * @param nid 团委
     * @param cron 执行时间
     * @param email 邮箱地址
     * @param realName 真实姓名
     */

    @Override
    public void addUserYouthData(String userid, String nid, String cron, String email, String realName) {
        if (checkYouthData(userid))
            return;

        UserYouthData userYouthData = buildUserYouthData(userid, nid, cron, realName);
        if (Objects.nonNull(email)) buildEmail(email, userYouthData);
        buildImage(userYouthData);

        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);
    }

    /**
     * 添加一个青年大学习用户信息
     * @param userYouthInfo 封装前端类
     */

    @Override
    public void addUserYouthData(UserYouthInfoVO userYouthInfo) {
        addUserYouthData(userYouthInfo.getUserid(), userYouthInfo.getNid(), userYouthInfo.getCron(), userYouthInfo.getEmail(), userYouthInfo.getRealName());
    }

    /**
     * 添加一堆青年大学习用户信息
     * @param userYouthInfoList 一堆前端类
     */

    @Override
    public void addUserYouthData(List<UserYouthInfoVO> userYouthInfoList) {
        for (UserYouthInfoVO userYouthInfoVO : userYouthInfoList)
            addUserYouthData(userYouthInfoVO);
    }

    //TODO: 信息删除

    public void removeUserYouthData(String userid) {
        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );

        if (Objects.isNull(userYouthData))


        userResultImageMapper.delete(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, userYouthData.getImageId())
        );
        userEmailInfoMapper.delete(
                new LambdaQueryWrapper<UserEmailInfo>()
                        .select()
                        .eq(UserEmailInfo::getUuid, userYouthData.getEmailId())
        );

        userYouthDataMapper.deleteById(userYouthData);
    }

    //TODO: 信息修改

    //TODO: 信息查询

    private UserYouthData buildEmail(String email, UserYouthData userYouthData) {

        if (CommonUtil.checkEmail(email)) {

            String uuid = getRandomUUID(28);

            userYouthData.setEmailId(uuid);

            userEmailInfoMapper.insert(
                    UserEmailInfo
                            .builder()
                            .uuid(uuid)
                            .email(email)
                            .build()
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

        String uuid = getRandomUUID(25);
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
}