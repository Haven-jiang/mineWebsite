package com.Haven.entity;

import com.Haven.DTO.FinishLogDTO;
import com.Haven.utils.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 青年大学习数据面向数据库类 UserYouthData
 *
 * @author HavenJust
 * @date 18:36 周二 19 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user_youth_data")
public class UserYouthData implements Serializable {

    /**
     * nid - 团委id
     */

    private String nid;

    /**
     * userid - 姓名/学号
     */

    private String userid;

    /**
     * token - 随机token
     */

    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    /**
     * status - 大学习完成状态
     */

    private boolean status;

    /**
     * 任务名称
     */

    private String jobName;

    /**
     * 任务组
     */

    private String jobGroup;

    /**
     * 触发器名称
     */

    private String triggerName;

    /**
     * 触发器组
     */

    private String triggerGroup;

    /**
     * 定时表 default "0 30 12 ? * 2" - "59 59 23 ? * 5"
     */

    private String cron;

    /**
     * 创建时间
     */

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 最近完成时间
     */

    private LocalDateTime lastFinishTime;

    /**
     * 完成历史
     */

    private List<FinishLogDTO> finishHistory;

    /**
     * 完成历史
     */

    private List<FinishLogDTO> sendHistory;


    private String emailId;

    public byte[] getFinishHistory() {
        return JSON.toJSONBytes(finishHistory);
    }

    public void setFinishHistory(byte[] finishHistory) {
        this.finishHistory = conversionJsonList(finishHistory);
    }

    public List<FinishLogDTO> getFinishHistoryObj() {
        return finishHistory;
    }

    public void putFinishHistory(FinishLogDTO finishLog) {
        this.finishHistory.add(finishLog);
    }

    public byte[] getSendHistory() {
        return JSON.toJSONBytes(sendHistory);
    }

    public void setSendHistory(byte[] sendHistory) {
        this.sendHistory = conversionJsonList(sendHistory);
    }

    public UserYouthData formatting() {
        this.setJobName("j-jx-" + userid);
        this.setJobGroup("job-YouthLearn");
        this.setTriggerName("t-jx-" + userid);
        this.setTriggerGroup("trigger-YouthLearn");
        return this;
    }

    public UserYouthData(
            String nid,
            String userid,
            String uuid,
            boolean status) {

        this.nid = nid;
        this.userid = userid;
        this.uuid = uuid;
        this.status = status;
        this.formatting();
    }

    /**
     * 重写方法返回this
     * @param status - 当前用户完成状态
     * @return - 返回this 链式编程
     */

    public UserYouthData setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public static UserYouthDataBuilder builder() {
        return new UserYouthDataBuilder()
                .cron(RandomUtil.getRandomCron())
                .finishHistory(new ArrayList<>(List.of(new FinishLogDTO())))
                .sendHistory(new ArrayList<>());
    }

    public static class UserYouthDataBuilder {

        public UserYouthDataBuilder userid(String userid) {

            this.userid = userid;
            this.jobName = "j-jx-" + userid;
            this.jobGroup = "job-YouthLearn";
            this.triggerName = "t-jx-" + userid;
            this.triggerGroup = "trigger-YouthLearn";
            return this;
        }
    }

    private List<FinishLogDTO> conversionJsonList(byte[] content) {
        List<FinishLogDTO> finish = new ArrayList<>();
        List<JSONObject> list = JSON.parseObject(content, List.class);
        for (JSONObject obj : list) if (!obj.isEmpty())
            finish.add(JSON.parseObject(obj.toString(), FinishLogDTO.class));
        return finish;
    }
}
