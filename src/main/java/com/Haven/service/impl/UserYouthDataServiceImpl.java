package com.Haven.service.impl;

import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.DTO.UserYouthReloadDataDTO;
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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.Haven.utils.CalendarCheckUtil.checkTime;
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


    //TODO = CRUD 增删改查
    //TODO - 信息增加

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

        //查询用户 判断是否存在
        if (checkYouthData(userid))
            return; //用户已存在

        //构建UserYouthData对象
        UserYouthData userYouthData = buildUserYouthData(userid, nid, cron, realName);

        //插入数据库
        userYouthDataMapper.insert(userYouthData);

        //绑定邮箱和图片路径
        if (Objects.nonNull(email)) bindEmail(email, userYouthData);
        bindImage(userYouthData);

        //注册定时任务
        userYouthTaskService.registerTask(userYouthData);
        //成功
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

    //TODO - 信息删除

    public void removeUserYouthData(String userid) {

        //查询用户信息
        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );

        if (Objects.isNull(userYouthData)) return; //用户不存在

        //删除对应imagePath
        userResultImageMapper.delete(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, userYouthData.getImageId())
        );

        //删除对应email
        userEmailInfoMapper.delete(
                new LambdaQueryWrapper<UserEmailInfo>()
                        .select()
                        .eq(UserEmailInfo::getUuid, userYouthData.getEmailId())
        );

        //删除用户
        userYouthDataMapper.deleteById(userYouthData);

        //删除定时任务
        userYouthTaskService.removeTask(userYouthData);

        //删除成功
    }

    public void removeUserYouthData(UserYouthInfoVO userYouthInfo) {
        removeUserYouthData(userYouthInfo.getUserid());
    }

    //TODO - 信息修改

    public void updateUserYouthData(String userid, String nid, String cron, String email, String realName) {

        //查询并实例化UserYouthData实体类
        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );

        if (Objects.isNull(userYouthData)) return;

        //修改数据
        userYouthData
                .settingNid(nid)
                .settingCron(cron)
                .settingRealName(realName);

        //UserYouthData类上传数据库
        userYouthDataMapper.updateById(userYouthData);

        if (Objects.nonNull(email)) bindEmail(email, userYouthData);
    }

    public void updateUserYouthData(UserYouthInfoVO userYouthInfo) {
        updateUserYouthData(userYouthInfo.getUserid(), userYouthInfo.getNid(), userYouthInfo.getCron(), userYouthInfo.getEmail(), userYouthInfo.getRealName());
    }

    //TODO - 信息查询

    public UserYouthDataDTO queryUserYouthInfoOne(String userid) {

        return userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        ).toUserYouthDataDTO();

    }

    public List<UserYouthDataDTO> queryUserYouthInfoByStatus(boolean isFinish) {

        fixStatus();

        List<UserYouthDataDTO> youthInfoVOList = new ArrayList<>();

        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::isStatus, isFinish)))

            youthInfoVOList.add(userYouthData.toUserYouthDataDTO());

        return youthInfoVOList;
    }

    public List<UserYouthDataDTO> queryUserYouthInfoAll() {

        List<UserYouthDataDTO> youthInfoVOList = new ArrayList<>();

        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()))
            youthInfoVOList.add(userYouthData.toUserYouthDataDTO());

        return youthInfoVOList;
    }

    public void fixStatus() {

        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()))

            if (checkTime(userYouthData.getLastFinishTime()))
                userYouthDataMapper.updateById(userYouthData.settingStatus(true));
    }



    private void bindEmail(String email, UserYouthData userYouthData) {

        if (CommonUtil.checkEmail(email)) {

            if (Objects.nonNull(
                    userEmailInfoMapper.selectOne(
                            new LambdaQueryWrapper<UserEmailInfo>()
                                    .select()
                                    .eq(UserEmailInfo::getUuid, userYouthData.getEmailId())
                    )))

                userYouthDataMapper.updateById(userYouthData);

            userEmailInfoMapper.insert(
                    UserEmailInfo
                            .builder()
                            .uuid(userYouthData.getEmailId())
                            .email(email)
                            .build()
            );

        }
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
    public UserYouthReloadDataDTO selectYouthDataDTO(String userid) {

        return selectYouthData(userid).toUserYouthReloadDataDTO();

    }

    private void bindImage(UserYouthData userYouthData) {

        String curImagePath = initImage(userYouthData);

        userResultImageMapper.insert(
                UserResultImage
                        .builder()
                        .uuid(userYouthData.getImageId())
                        .currentImagePath(curImagePath)
                        .historyImagePath(new ArrayList<>(List.of(curImagePath)))
                        .build()
        );


    }
}