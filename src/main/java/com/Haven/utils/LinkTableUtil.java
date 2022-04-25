package com.Haven.utils;

import com.Haven.entity.UserResultImage;
import com.Haven.entity.UserYouthData;
import com.Haven.mapper.UserResultImageMapper;
import com.Haven.mapper.UserYouthDataMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.Haven.utils.CommonUtil.getImagePath;
import static com.Haven.utils.ConversionUtil.getCurrentCourse;
import static com.Haven.utils.ConversionUtil.getCurrentCourseUri;
import static com.Haven.utils.DownloadUtil.downloadFile;

/**
 * 联合表初始化工具类 LinkTableUtil
 *
 * @author HavenJust
 * @date 20:00 周日 24 四月 2022年
 */

@Component
public class LinkTableUtil {

    private static UserResultImageMapper userResultImageMapper;

    private static UserYouthDataMapper userYouthDataMapper;

    public LinkTableUtil(UserResultImageMapper userResultImageMapper, UserYouthDataMapper userYouthDataMapper) {
        LinkTableUtil.userResultImageMapper = userResultImageMapper;
        LinkTableUtil.userYouthDataMapper = userYouthDataMapper;
    }

    public static UserYouthData addImagePath(UserYouthData userYouthData) {

        String curImagePath = initImage(userYouthData);

        UserResultImage userResultImage = userResultImageMapper.selectOne(
                new LambdaQueryWrapper<UserResultImage>()
                        .select()
                        .eq(UserResultImage::getUuid, userYouthData.getImageId())
        );

        userResultImage.setCurrentImagePath(curImagePath);
        userResultImage.putSendHistory(curImagePath);

        userResultImageMapper.updateById(userResultImage);

        return userYouthData;
    }

    public static String initImage(UserYouthData userYouthData) {

        String curDirPath = (getImagePath() + userYouthData.getUserid() + "_snapshot").replaceAll("%20", " ");
        String curImagePath = curDirPath + "/finish_" + getCurrentCourse() + ".jpg";

        if (new File(curImagePath).exists()) return curImagePath;

        String imageUrl = getCurrentCourseUri().substring(0, getCurrentCourseUri().lastIndexOf('/')) + "/images/end.jpg";

        File dir = new File(curDirPath);

        if (!dir.exists()) dir.mkdirs();

        downloadFile(imageUrl, curDirPath);

        new File(curDirPath + "/end.jpg").renameTo(new File(curImagePath));

        return curImagePath;
    }

}