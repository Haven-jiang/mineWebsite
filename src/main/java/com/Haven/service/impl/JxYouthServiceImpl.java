package com.Haven.service.impl;

import com.Haven.DTO.FinishLogDTO;
import com.Haven.DTO.ResponsePackDTO;
import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.VO.UserYouthInfoVO;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.Haven.utils.ConversionUtil.getCurrentCourse;
import static com.Haven.utils.ConversionUtil.toUserYouthDataDTO;

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
        String userid = JSON.parseObject(data, UserYouthDataDTO.class).getCardNo();

        ResponsePackDTO status = new ResponsePackDTO(4000, null);

        if (HttpsClientUtil.doGet(getUrl).getStatus() == 200) {

            status = HttpsClientUtil.doPostJson(postUrl, data);
            if (status.getStatus() == 200 && status.getContent().contains(userid)) {

                //TODO: userid post ok 添加至数据库
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
        String data = JSON.toJSONString(toUserYouthDataDTO(user));

        ResponsePackDTO status = new ResponsePackDTO(4000, null);

        if (HttpsClientUtil.doGet(getUrl).getStatus() == 200) {

            status = HttpsClientUtil.doPostJson(postUrl, data);
            if (status.getStatus() == 200 && status.getContent().contains(user.getUserid())) {
                //TODO: userid post ok 添加至数据库
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
    public void updateCourse(String courseId) {
        youthCourseMapper.update(
                new YouthCourse(), new LambdaUpdateWrapper<YouthCourse>()
                        .set(YouthCourse::getId, courseId)
        );
    }

    @Override
    public void updateCourse(String courseId, String title, String uri) {
        YouthCourse course = YouthCourse.builder()
                .course("course")
                .id(courseId)
                .title(title)
                .uri(uri)
                .build();
        if (!Objects.isNull(
                youthCourseMapper.selectOne(
                        new LambdaQueryWrapper<YouthCourse>()
                                .select()
                                .eq(YouthCourse::getCourse, "course")
                )))
            youthCourseMapper.updateById(course);
        else
            youthCourseMapper.insert(course);
    }

    @Override
    @Scheduled(cron = "15 30 12 ? * 2")
    public void updateCourse() {
        HttpsClientUtil.doGet("http://127.0.0.1/function/youthlearn/jiangxi/getcourse");
    }
}
