package com.Haven.DTO;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 大学习信息组合面向传输类 UserYouthDataDTO
 *
 * @author HavenJust
 * @date 21:05 周二 26 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserYouthDataDTO implements Serializable {

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
     * status - 大学习完成状态
     */

    private boolean status;

    /**
     * 定时表 default "0 30 12 ? * 2" - "59 59 23 ? * 5"
     */


    private String cron;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 修改时间
     */

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
     * 邮件发送历史
     */

    private List<FinishLogDTO> sendHistory;

    /**
     * 邮箱地址
     */

    private String email;

    /**
     * 本次大学习图片地址
     */

    private String currentImagePath;

    /**
     * 所有大学习图片地址
     */

    private List<String> historyImagePath;

}
