package com.Haven.service.impl;

import com.Haven.DTO.CronTaskDTO;
import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.entity.YouthCourse;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.mapper.YouthCourseMapper;
import com.Haven.service.UserYouthDataService;
import com.Haven.service.UserYouthTaskService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.Haven.utils.ConversionUtil.*;

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

    @Override
    public void addUserYouthData(String userid, String nid) {
        if (checkYouthData(userid))
            return;

        UserYouthData userYouthData = newUserYouthData(userid, nid);
        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);
    }

    @Override
    public void addUserYouthData(String userid, String nid, String cron) {
        if (checkYouthData(userid))
            return;

        UserYouthData userYouthData = newUserYouthData(userid, nid, cron);
        userYouthDataMapper.insert(userYouthData);
        userYouthTaskService.registerTask(userYouthData);
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
}