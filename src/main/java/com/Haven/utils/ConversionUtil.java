package com.Haven.utils;

import com.Haven.DTO.EmailContentDTO;
import com.Haven.DTO.EmailInfoDTO;
import com.Haven.DTO.UserYouthReloadDataDTO;
import com.Haven.entity.UserEmailInfo;
import com.Haven.entity.UserResultImage;
import com.Haven.entity.UserYouthData;
import com.Haven.entity.YouthCourse;
import com.Haven.mapper.UserEmailInfoMapper;
import com.Haven.mapper.UserResultImageMapper;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.mapper.YouthCourseMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.Haven.utils.RandomUtil.getRandomUUID;

@Component
public class ConversionUtil {

    private static YouthCourseMapper youthCourseMapper;
    private static UserEmailInfoMapper userEmailInfoMapper;
    private static UserYouthDataMapper userYouthDataMapper;
    private static UserResultImageMapper userResultImageMapper;

    public ConversionUtil(YouthCourseMapper youthCourseMapper,
                          UserEmailInfoMapper userEmailInfoMapper,
                          UserYouthDataMapper userYouthDataMapper,
                          UserResultImageMapper userResultImageMapper) {

        ConversionUtil.youthCourseMapper = youthCourseMapper;
        ConversionUtil.userEmailInfoMapper = userEmailInfoMapper;
        ConversionUtil.userYouthDataMapper = userYouthDataMapper;
        ConversionUtil.userResultImageMapper = userResultImageMapper;
    }

    public static UserYouthData toUserYouthData(String json) {
        UserYouthReloadDataDTO userYouthReloadDataDTO = JSON.parseObject(json, UserYouthReloadDataDTO.class);
        return userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userYouthReloadDataDTO.getCardNo())
        );
    }

    public static String getCurrentCourse() {
        return youthCourseMapper.selectOne(
                new LambdaQueryWrapper<YouthCourse>()
                        .select()
                        .eq(YouthCourse::getCourse, "course")
        ).getId();
    }

    public static String getCurrentCourseUri() {
        return youthCourseMapper.selectOne(
                new LambdaQueryWrapper<YouthCourse>()
                        .select()
                        .eq(YouthCourse::getCourse, "course")
        ).getUri();
    }

    public static UserYouthData buildUserYouthData(String userid, String nid, String cron, String realName) {

        if (Objects.isNull(cron)) cron = RandomUtil.getRandomCron();

        return UserYouthData.builder()
                .imageId(getRandomUUID(28))
                .emailId(getRandomUUID(28))
                .nid(nid)
                .realName(realName)
                .userid(userid)
                .cron(cron)
                .build();
    }

    public static String getEmailById(String uuid) {

        return userEmailInfoMapper.selectOne(
                new LambdaQueryWrapper<UserEmailInfo>()
                        .select()
                        .eq(UserEmailInfo::getUuid, uuid)
        ).getEmail();
    }

    public static String getImagePathById(String uuid) {

        return userResultImageMapper.selectOne(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, uuid)
        ).getCurrentImagePath();
    }

    public static List<String> getImagePathListById(String uuid) {

        return userResultImageMapper.selectOne(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, uuid)
        ).getHistoryImagePathObj();

    }

    public static EmailInfoDTO buildEmailInfoDTO(UserYouthData userYouthData) throws IOException {
        return EmailInfoDTO.builder()
                .recipient(getEmailById(userYouthData.getEmailId()))
                .subject("????????? - ??????????????? uuid:" + userYouthData.getUserid() + " ?????????")
                .inlines(new ArrayList<>(List.of(new EmailInfoDTO.Inline("finish", new File(getImagePathById(userYouthData.getImageId()))))))
                .text(buildEmailContentOne(
                        EmailContentDTO
                                .builder()
                                .username(userYouthData.getUserid())
                                .service(userYouthData.getTriggerName())
                                .textH1("??????????????????????????????")
                                .textH2("????????????:")
                                .namespace("??????: ???????????????")
                                .result(userYouthData.isStatus() ?"??????":"??????")
                                .timeTask(userYouthData.getCron())
                                .finishTime(userYouthData.getLastFinishTime().toString())
                                .build()
                ))
                .sentDate(new Date())
                .build();
    }

    public static String buildEmailContentOne(EmailContentDTO content) throws IOException {

        //????????????html??????
        String fileName = "pod-scale-alarm.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(EmailContentDTO.class).error("?????????????????????fileName:{}", fileName, e);
        } finally {
            inputStream.close();
            fileReader.close();
        }

        String contentText = content.getTextH1() + "<br>" + content.getTextH2();
        //????????????header
        String header = "<td>??????(Namespace)</td><td>??????(Service)</td><td>??????(Result)</td><td>????????????(TimingTask)</td><td>????????????(Finish Date And Time)</td>";
        StringBuilder linesBuffer = new StringBuilder();
        linesBuffer.append("<tr><td>" + content.getNamespace() + "</td><td>" + content.getService() + "</td><td>" + content.getResult() + "</td>" +
                "<td>" + content.getTimeTask() + "</td><td>" + content.getFinishTime() + "</td></tr>");

        //??????
        String emailHeadColor = "#10fa81";
        String date = LocalDateTime.now().toString();
        //??????html????????????????????????
        String htmlText = MessageFormat.format(buffer.toString(), emailHeadColor, content.getUsername() ,contentText, date, header, linesBuffer.toString());

        //??????????????????
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
        return htmlText;
    }
}
