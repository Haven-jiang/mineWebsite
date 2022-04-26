package com.Haven.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YouthCourse {

    /**
     * 固定值 "course"
     */

    private String course;

    /**
     * 课程id
     */

    private String id;

    /**
     * 标题
     */

    private String title;

    /**
     * 课程地址
     */

    private String uri;

    /**
     * 更新时间
     */

    @TableField(fill = FieldFill.UPDATE)
//    @JsonIgnore
    private LocalDateTime updateTime;

    /**
     * 使MybatisPlus永远传参数"course"
     * 默认值固定
     *
     * @param course 混淆MybatisPlus功能
     */

    public void setCourse(String course) {
        this.course = "course";
    }

    /**
     * 默认值固定
     *
     * @return 链式编程
     */

    public YouthCourse setCourse() {
        this.course = "course";
        return this;
    }


    public void setUpdateTime(LocalDateTime localDateTime) {
        this.updateTime = LocalDateTime.now();
    }

    public YouthCourse setUpdateTime() {
        this.updateTime = LocalDateTime.now();
        return this;
    }
}
