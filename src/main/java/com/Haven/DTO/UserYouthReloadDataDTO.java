package com.Haven.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 大学习数据面向传输类 UserYouthDataDTO
 *
 * @author HavenJust
 * @date 22:47 周三 20 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserYouthReloadDataDTO implements Serializable {

    /**
     * nid - 团委id
     */

    private String nid;

    /**
     * cardNo - 姓名/学号
     */

    private String cardNo;

    /**
     * subOrg - 随机token
     */

    private String subOrg;

    /**
     * course - 课程id
     */

    private String course;
}
