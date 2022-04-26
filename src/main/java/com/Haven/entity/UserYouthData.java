package com.Haven.entity;

import com.Haven.DTO.CronTaskDTO;
import com.Haven.DTO.FinishLogDTO;
import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.DTO.UserYouthReloadDataDTO;
import com.Haven.VO.UserYouthInfoVO;
import com.Haven.utils.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import static com.Haven.utils.ConversionUtil.*;

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
     * 真实姓名
     */

    private String realName;

    /**
     * token - 随机token
     */

    @JSONField(serialize = false)
    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    /**
     * status - 大学习完成状态
     */

    private boolean status;

    /**
     * 任务名称
     */

    @JSONField(serialize = false)
    private String jobName;

    /**
     * 任务组
     */

    @JSONField(serialize = false)
    private String jobGroup;

    /**
     * 触发器名称
     */

    @JSONField(serialize = false)
    private String triggerName;

    /**
     * 触发器组
     */

    @JSONField(serialize = false)
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

    @JSONField(serialize = false)
    private List<FinishLogDTO> finishHistory;

    /**
     * 邮件发送历史
     */

    @JSONField(serialize = false)
    private List<FinishLogDTO> sendHistory;

    /**
     * 邮箱id
     */

    @JSONField(serialize = false)
    private String emailId;

    /**
     * 图片id
     */

    @JSONField(serialize = false)
    private String imageId;

    /**
     * 普通的set方法 链式编程
     *
     * @param nid 参数
     * @return 返回值
     */

    public UserYouthData settingNid(String nid) {
        if (Objects.nonNull(nid)) this.nid = nid;
        return this;
    }

    /**
     * 普通的set方法 链式编程
     *
     * @param cron 参数
     * @return 返回值
     */

    public UserYouthData settingCron(String cron) {
        if (Objects.nonNull(cron)) this.cron = cron;
        return this;
    }

    /**
     * 普通的set方法 链式编程
     *
     * @param realName 参数
     * @return 返回值
     */

    public UserYouthData settingRealName(String realName) {
        if (Objects.nonNull(realName)) this.realName = realName;
        return this;
    }

    /**
     * 重写方法返回this
     * @param status - 当前用户完成状态
     * @return - 返回this 链式编程
     */

    public UserYouthData settingStatus(boolean status) {
        this.status = status;
        return this;
    }

    /*=========================数据库类型存储转换=========================*/

    /**
     * 重写get/set方法 - 数据库类型不匹配的存储转换
     * @return 数据库类型/本地类型
     */

    public byte[] getFinishHistory() {
        return JSON.toJSONBytes(finishHistory);
    }

    /**
     * 重写get/set方法 - 数据库类型不匹配的存储转换
     * @param finishHistory 数据库类型/本地类型
     */

    public void setFinishHistory(byte[] finishHistory) {
        this.finishHistory = conversionJsonList(finishHistory);
    }

    /**
     * 重写get/set方法 - 数据库类型不匹配的存储转换
     * @return 数据库类型/本地类型
     */

    @JSONField(serialize = false)
    public List<FinishLogDTO> gettingFinishHistory() {
        return finishHistory;
    }

    /**
     * 重写get/set方法 - 数据库类型不匹配的存储转换
     * @param finishLog 数据库类型/本地类型
     */

    public void putFinishHistory(FinishLogDTO finishLog) {
        this.finishHistory.add(finishLog);
    }

    /**
     * 重写get/set方法 - 数据库类型不匹配的存储转换
     * @return 数据库类型/本地类型
     */

    @JSONField(serialize = false)
    public byte[] getSendHistory() {
        return JSON.toJSONBytes(sendHistory);
    }

    /**
     * 重写get/set方法 - 数据库类型不匹配的存储转换
     * @param sendHistory 数据库类型/本地类型
     */

    public void setSendHistory(byte[] sendHistory) {
        this.sendHistory = conversionJsonList(sendHistory);
    }

    /**
     * 重写get/set方法 - 数据库类型不匹配的存储转换
     * @param finishLog 数据库类型/本地类型
     */

    public void putSendHistory(FinishLogDTO finishLog) {
        this.sendHistory.add(finishLog);
    }

    /**
     * 嵌套类型JSON的实例化转化方法
     * @param content JSON类型
     * @return 实体类型
     */

    @JSONField(serialize = false)
    private List<FinishLogDTO> conversionJsonList(byte[] content) {
        List<FinishLogDTO> finish = new ArrayList<>();
        List<JSONObject> list = JSON.parseObject(content, List.class);
        if (Objects.isNull(list)) return finish;
        for (JSONObject obj : list) if (!obj.isEmpty())
            finish.add(JSON.parseObject(obj.toString(), FinishLogDTO.class));
        return finish;
    }

    /*============================默认值设置==================================*/

    /**
     * jobKey triggerKey 默认值设置
     */

    public void formatting() {
        this.setJobName("j-jx-" + userid);
        this.setJobGroup("job-YouthLearn");
        this.setTriggerName("t-jx-" + userid);
        this.setTriggerGroup("trigger-YouthLearn");
    }

    /*============================转换方法===============================*/

    /**
     * 转换类
     * @return 要转换成的类实例
     */

    public UserYouthInfoVO toUserYouthInfoVO() {
        return UserYouthInfoVO.builder()
                       .cron(cron)
                       .email(getEmailById(emailId))
                       .nid(nid)
                       .realName(realName)
                       .userid(userid)
                .build();
    }

    /**
     * 转换类
     * @return 要转换成的类实例
     */

    public UserYouthReloadDataDTO toUserYouthReloadDataDTO() {
        return UserYouthReloadDataDTO
                .builder()
                .course(getCurrentCourse())
                .cardNo(userid)
                .subOrg(null)
                .nid(nid)
                .build();
    }

    /**
     * 转换类
     * @return 要转换成的类实例
     */

    public CronTaskDTO toCronTaskDTO() {
        return CronTaskDTO.builder()
                .content(JSON.toJSONString(toUserYouthReloadDataDTO()))
                .jobKey(new JobKey(jobName, jobGroup))
                .triggerKey(new TriggerKey(triggerName, triggerGroup))
                .cron(cron)
                .build();
    }

    @JsonIgnore
    public UserYouthDataDTO toUserYouthDataDTO() {
        String jsonString = JSON.toJSONString(this);
        UserYouthDataDTO userYouthDataDTO = JSON.parseObject(jsonString, UserYouthDataDTO.class);
        userYouthDataDTO.setFinishHistory(finishHistory);
        userYouthDataDTO.setSendHistory(sendHistory);
        userYouthDataDTO.setCurrentImagePath(getImagePathById(imageId));
        userYouthDataDTO.setHistoryImagePath(getImagePathListById(imageId));
        userYouthDataDTO.setEmail(getEmailById(emailId));
        return userYouthDataDTO;
    }


    /*============================重写Builder类与方法===============================*/


    public static UserYouthDataBuilder builder() {
        return new UserYouthDataBuilder()
                .cron(RandomUtil.getRandomCron())
                .finishHistory(new ArrayList<>(List.of(new FinishLogDTO())))
                .sendHistory(new ArrayList<>());
    }

    public static class UserYouthDataBuilder {

        public UserYouthDataBuilder nid(String nid) {
            if (Objects.nonNull(nid)) this.nid = nid;
            return this;
        }

        public UserYouthDataBuilder cron(String cron) {
            if (Objects.nonNull(cron)) this.cron = cron;
            return this;
        }

        public UserYouthDataBuilder realName(String realName) {
            if (Objects.nonNull(realName)) this.realName = realName;
            return this;
        }


        public UserYouthDataBuilder userid(String userid) {

            this.userid = userid;
            this.jobName = "j-jx-" + userid;
            this.jobGroup = "job-YouthLearn";
            this.triggerName = "t-jx-" + userid;
            this.triggerGroup = "trigger-YouthLearn";
            return this;
        }
    }

}
