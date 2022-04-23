package com.Haven.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailInfoDTO {

    /**
     * 接收者
     */

    private String recipient;

    /**
     * 主题
     */

    private String subject;

    /**
     * 内容
     */

    private String text;

    /**
     * 发送时间
     */

    private Date sentDate;

    /**
     * 抄送人邮箱
     */

    private String cc;

    /**
     * 密送
     */

    private String bcc;

    /**
     * 发送状态
     */

    private String status;

    /**
     * 日志error信息
     */

    private String error;

    /**
     * 附件
     */

    @JsonIgnore
    private MultipartFile[] multipartFiles;

}
