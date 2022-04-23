package com.Haven.utils;

import com.Haven.DTO.CronTaskDTO;
import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.entity.YouthCourse;
import com.Haven.mapper.YouthCourseMapper;
import com.Haven.service.UserYouthDataService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionUtil {

    private static YouthCourseMapper youthCourseMapper;

    public ConversionUtil(YouthCourseMapper youthCourseMapper) {
        ConversionUtil.youthCourseMapper = youthCourseMapper;
    }

    public static String getCurrentCourse() {
        return youthCourseMapper.selectOne(
                new LambdaQueryWrapper<YouthCourse>()
                        .select()
                        .eq(YouthCourse::getCourse, "course")
        ).getId();
    }

    public static UserYouthDataDTO toUserYouthDataDTO(UserYouthData userYouthData) {
        return UserYouthDataDTO
                .builder()
                .course(
                        youthCourseMapper.selectOne(
                                new LambdaQueryWrapper<YouthCourse>()
                                        .select()
                                        .eq(YouthCourse::getCourse, "course")
                        ).getId()
                )
                .cardNo(userYouthData.getUserid())
                .subOrg(null)
                .nid(userYouthData.getNid())
                .build();
    }

    public static CronTaskDTO toCronTaskDTO(UserYouthData userYouthData) {
        return CronTaskDTO.builder()
                .content(JSON.toJSONString(toUserYouthDataDTO(userYouthData)))
                .jobKey(new JobKey(userYouthData.getJobName(), userYouthData.getJobGroup()))
                .triggerKey(new TriggerKey(userYouthData.getTriggerName(), userYouthData.getTriggerGroup()))
                .cron(userYouthData.getCron())
                .build();
    }

    public static UserYouthData newUserYouthData(String userid, String nid) {
        return UserYouthData.builder()
                .nid(nid)
                .userid(userid)
                .build();
    }

    public static UserYouthData newUserYouthData(String userid, String nid, String cron) {
        return UserYouthData.builder()
                .nid(nid)
                .userid(userid)
                .cron(cron)
                .build();
    }
}
