package com.Haven.service.impl;

import com.Haven.DTO.FinishLogDTO;
import com.Haven.DTO.ResponsePackDTO;
import com.Haven.DTO.UserYouthReloadDataDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.entity.YouthCourse;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.mapper.YouthCourseMapper;
import com.Haven.service.JxYouthService;
import com.Haven.utils.HttpsClientUtil;
import com.Haven.utils.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.Haven.utils.CalendarCheckUtil.checkTime;
import static com.Haven.utils.ConversionUtil.getCurrentCourse;
import static com.Haven.utils.HttpsClientUtil.doDownloadString;
import static com.Haven.utils.HttpsClientUtil.doGet;

@Service
public class JxYouthServiceImpl implements JxYouthService {

    @Autowired
    private UserYouthDataMapper userYouthDataMapper;

    @Autowired
    private YouthCourseMapper youthCourseMapper;

    @Override
    public ResponsePackDTO postData(String data) {

//        http://osscache.vol.jxmfkj.com/html/h5_index.html?accessToken=eiwjro34

        String uuid = RandomUtil.getRandomUUID(28);
        String getUrl = "http://osscache.vol.jxmfkj.com/html/h5_index.html?accessToken=" + uuid + "&openid=" + uuid;
        String postUrl = "http://osscache.vol.jxmfkj.com/pub/vol/volClass/join?accessToken=" + uuid;
        String userid = JSON.parseObject(data, UserYouthReloadDataDTO.class).getCardNo();

        ResponsePackDTO status = new ResponsePackDTO(4000, null);

        if (doGet(getUrl).getStatus() == 200) {

            status = HttpsClientUtil.doPostJson(postUrl, data);
            if (status.getStatus() == 200 && status.getContent().contains(userid)) {

                //TODO: userid post ok ??????????????????
                userYouthDataMapper.update(
                        new UserYouthData(), new LambdaUpdateWrapper<UserYouthData>()
                                .set(UserYouthData::isStatus, true)
                                .eq(UserYouthData::getUserid, userid)
                );
                updateFinish(userid);
            }
        }
        return status;
    }

    @Override
    public ResponsePackDTO postData(UserYouthData user) {

        String uuid = RandomUtil.getRandomUUID(28);
        String getUrl = "http://osscache.vol.jxmfkj.com/html/h5_index.html?accessToken=" + uuid + "&openid=" + uuid;
        String postUrl = "http://osscache.vol.jxmfkj.com/pub/vol/volClass/join?accessToken=" + uuid;
        String data = JSON.toJSONString(user.toUserYouthReloadDataDTO());

        ResponsePackDTO status = new ResponsePackDTO(4000, null);

        if (doGet(getUrl).getStatus() == 200) {

            status = HttpsClientUtil.doPostJson(postUrl, data);
            if (status.getStatus() == 200 && status.getContent().contains(user.getUserid())) {
                //TODO: userid post ok ??????????????????
                userYouthDataMapper.update(
                        new UserYouthData(), new LambdaUpdateWrapper<UserYouthData>()
                                .set(UserYouthData::isStatus, true)
                                .eq(UserYouthData::getUserid, user.getUserid())
                );
                updateFinish(user.getUserid());
            }
        }
        return status;
    }

    private void updateFinish(String userid) {

        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );
        userYouthData.setLastFinishTime(LocalDateTime.now());
        userYouthData.putFinishHistory(new FinishLogDTO(LocalDateTime.now(), getCurrentCourse()));
        userYouthDataMapper.updateById(userYouthData);

    }

    private void fix() {
        for (UserYouthData userYouthData : userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::isStatus, false))) {

            postData(userYouthData);

        }

    }

    @Override
    @Scheduled(cron = "0 30 21 ? * 5")
    public void checkAndFix() {
         while (userYouthDataMapper.selectList(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::isStatus, false)) != null)
             fix();

    }

    @Override
    @Scheduled(cron = "59 59 23 ? * 1")
    public void updateResult() {
        userYouthDataMapper.update(
                new UserYouthData(), new LambdaUpdateWrapper<UserYouthData>()
                        .set(UserYouthData::isStatus, false)
        );


    }

    @Override
    @Scheduled(cron = "15 30 12 ? * 2")
    public void updateCourse() {

        YouthCourse course = youthCourseMapper.selectOne(
                new LambdaQueryWrapper<YouthCourse>()
                        .select()
                        .eq(YouthCourse::getCourse, "course")
        );


        boolean hasCourse = !Objects.isNull(course);

        if (hasCourse) if (checkTime(course.getUpdateTime())) return;

        String content = doDownloadString("http://osscache.vol.jxmfkj.com/html/assets/js/course_data.js");

        StringBuilder reverse = new StringBuilder(content).reverse();
        String jsons = new StringBuilder(reverse.substring(0, reverse.lastIndexOf("{")+1)).reverse().toString();
        course = JSON.parseObject(JSON.parseObject(jsons).get("result").toString(), YouthCourse.class).setCourse().setUpdateTime();
        if (hasCourse)
            youthCourseMapper.updateById(course);
        else
            youthCourseMapper.insert(course);
    }
}
