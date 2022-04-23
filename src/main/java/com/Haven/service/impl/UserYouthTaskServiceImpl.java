package com.Haven.service.impl;

import com.Haven.DTO.CronTaskDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.scheduler.job.YouthLearnJob;
import com.Haven.service.UserYouthDataService;
import com.Haven.service.UserYouthTaskService;
import com.Haven.utils.QuartzUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.Haven.utils.ConversionUtil.toCronTaskDTO;

@Service
public class UserYouthTaskServiceImpl implements UserYouthTaskService {

    @Override
    public boolean registerTask(UserYouthData userYouthData) {

        CronTaskDTO task = toCronTaskDTO(userYouthData);

        return QuartzUtil.addJob(task, YouthLearnJob.class);
    }

    @Override
    public boolean modifyTask(UserYouthData userYouthData) {

        CronTaskDTO task = toCronTaskDTO(userYouthData);

        return QuartzUtil.modifyJobTime(task);
    }

    @Override
    public boolean removeTask(UserYouthData userYouthData) {

        CronTaskDTO task = toCronTaskDTO(userYouthData);

        return QuartzUtil.removeJob(task);
    }
}
