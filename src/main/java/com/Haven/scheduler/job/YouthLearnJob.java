package com.Haven.scheduler.job;

import com.Haven.DTO.ResponsePackDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.service.EmailSendService;
import com.Haven.service.JxYouthService;
import com.Haven.utils.ConversionUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.Haven.utils.ConversionUtil.*;
import static com.Haven.utils.ImageWatermarkUtil.markImageByText;
import static com.Haven.utils.LinkTableUtil.addImagePath;

//jobDataMap持久化
@Component
@PersistJobDataAfterExecution
public class YouthLearnJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JxYouthService jxYouthService;

    private final EmailSendService emailSendService;

    public YouthLearnJob(JxYouthService jxYouthService, EmailSendService emailSendService, UserYouthDataMapper userYouthDataMapper) {
        this.jxYouthService = jxYouthService;
        this.emailSendService = emailSendService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        jxYouthService.updateCourse();

        try {
            String data = (String) context.getJobDetail().getJobDataMap().get("data");
            ResponsePackDTO responsePackDTO = new ResponsePackDTO(404, "");
            while (responsePackDTO.getStatus() != 200) responsePackDTO = jxYouthService.postData(data);

            //TODO: 下载创建初始化截图
            UserYouthData userYouthData = addImagePath(toUserYouthData(data));

            //TODO: 截图添加水印
            markImageByText(userYouthData.getUserid() + ' ' + userYouthData.getRealName(), getImagePathById(userYouthData.getImageId()), null, null);

            //TODO: 发邮件
            if (!userYouthData.getEmailId().isEmpty())
                emailSendService.sendMimeMail(buildEmailInfoDTO(userYouthData));

        } catch (IOException e) {
            e.printStackTrace();
        }

//        jxYouthService.postData(new UserYouthData());
    }
}
