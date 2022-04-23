package com.Haven.utils;

import com.Haven.DTO.CronTaskDTO;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzUtil {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * @Description: 添加定时任务
     *
     * @param jobKey 任务job描述
     * @param triggerKey 任务触发器描述
     * @param jobClass 任务类 - 即执行哪个任务
     * @param cron 任务执行时间表
     * @param jsonData 任务数据
     */
    public static boolean addJob(
            JobKey jobKey,
            TriggerKey triggerKey,
            Class jobClass,
            String cron,
            String jsonData
    ) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("data", jsonData);
            JobDetail jobDetail = JobBuilder.newJob(jobClass).setJobData(jobDataMap).withIdentity(jobKey).build();

            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerKey);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));

            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            scheduler.scheduleJob(jobDetail, trigger);

            scheduler.start();

            if (!scheduler.isShutdown()) scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean addJob(CronTaskDTO taskDTO, Class jobClass) {
        return addJob(taskDTO.getJobKey(), taskDTO.getTriggerKey(), jobClass, taskDTO.getCron(), taskDTO.getContent());
    }

    /**
     * @Description: 修改一个任务的触发时间
     *
     * @param triggerKey 任务触发器描述
     * @param cron   时间设置，参考quartz说明文档
     */
    public static boolean modifyJobTime(
            TriggerKey triggerKey,
            String cron) {

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) return false;

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /* 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerKey);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);

                /* 方式一 ：调用 rescheduleJob 结束 */

                /* 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /* 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean modifyJobTime(CronTaskDTO taskDTO) {
        return modifyJobTime(taskDTO.getTriggerKey(), taskDTO.getCron());
    }

    /**
     * @Description: 移除一个任务
     *
     *
     * @param jobKey 任务描述
     * @param triggerKey 任务触发器描述
     */
    public static boolean removeJob(
            JobKey jobKey,
            TriggerKey triggerKey) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(jobKey);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean removeJob(CronTaskDTO taskDTO) {
        return removeJob(taskDTO.getJobKey(), taskDTO.getTriggerKey());
    }

    /**
     * @Description: 启动所有定时任务
     */
    public static void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
