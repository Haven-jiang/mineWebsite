package com.Haven.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {

    /**
     * 任务名称
     */

    private String taskName;

    /**
     * 任务组
     */

    private String taskGroupName;

    /**
     * 触发器名称
     */

    private String triggerName;

    /**
     * 触发器组
     */

    private String triggerGroupName;

    /**
     * 1 启用 2 禁用
     */

    private String taskStatus;

    /**
     * taskCron 表达式
     */

    private String taskCron;

    /**
     * 上次执行时间
     */

    private String lastExecuteTime;

    /**
     * 上次执行时间
     */

    private String nextExecuteTime;

    /**
     * 参数
     */

    private Map<String, Object> parames;

    /**
     * 创建时间
     */

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    /**
     * 修改时间
     */

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;
}
