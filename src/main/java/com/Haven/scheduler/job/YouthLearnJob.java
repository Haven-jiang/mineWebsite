package com.Haven.scheduler.job;

import com.Haven.DTO.ResponsePackDTO;
import com.Haven.service.JxYouthService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

//jobDataMap持久化
@Component
@PersistJobDataAfterExecution
public class YouthLearnJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JxYouthService jxYouthService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            String data = (String) context.getJobDetail().getJobDataMap().get("data");
            ResponsePackDTO responsePackDTO = new ResponsePackDTO(404, "");
            while (responsePackDTO.getStatus() != 200) responsePackDTO = jxYouthService.postData(data);
            //TODO: 发邮件
        } catch (IOException e) {
            e.printStackTrace();
        }

//        jxYouthService.postData(new UserYouthData());
    }
}
