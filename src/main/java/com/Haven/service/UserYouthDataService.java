package com.Haven.service;

import com.Haven.DTO.CronTaskDTO;
import com.Haven.DTO.UserYouthDataDTO;
import com.Haven.entity.UserYouthData;

/**
 * 青年大学习数据服务类 UserYouthDataService
 *
 * @author HavenJust
 * @date 23:21 周二 19 四月 2022年
 */

public interface UserYouthDataService {
    void addUserYouthData(String userid, String nid);

    void addUserYouthData(String userid, String nid, String cron);

    UserYouthData selectYouthData(String userid);

    UserYouthDataDTO selectYouthDataDTO(String userid);

}
