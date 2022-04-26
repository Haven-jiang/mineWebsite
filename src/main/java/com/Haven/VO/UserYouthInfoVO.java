package com.Haven.VO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 青年大学习信息面向前端类 UserYouthInfoVO
 *
 * @author HavenJust
 * @date 11:43 周一 25 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserYouthInfoVO implements Serializable {

    /**
     * 学号(必填)
     */
    @NotBlank(message = "学号/姓名不能为空")
    private String userid;

    /**
     * 班级团委(必填)
     */
    @NotBlank(message = "团委不能为空")
    private String nid;

    /**
     * 真实姓名(截图水印 选填)
     */

    private String realName;

    /**
     * 邮箱(选填)
     */

    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 执行时间(选填)
     */

    private String cron;

}
