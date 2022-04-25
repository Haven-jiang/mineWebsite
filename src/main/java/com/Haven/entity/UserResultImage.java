package com.Haven.entity;

import com.Haven.DTO.FinishLogDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 青年大学习结果图片面向数据库类 UserResultImage
 *
 * @author HavenJust
 * @date 19:50 周日 24 四月 2022年
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResultImage implements Serializable {

    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    private String currentImagePath;

    private List<String> historyImagePath;


    public byte[] getHistoryImagePath() {
        return JSON.toJSONBytes(historyImagePath);
    }

    public void setHistoryImagePath(byte[] finishHistory) {
        this.historyImagePath = JSON.parseObject(finishHistory, List.class);
    }

    public void putSendHistory(String historyImagePath) {
        this.historyImagePath.add(historyImagePath);
    }

}
