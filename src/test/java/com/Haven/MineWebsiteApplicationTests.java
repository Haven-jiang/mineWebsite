package com.Haven;

import com.Haven.DTO.FinishLogDTO;
import com.Haven.entity.UserYouthData;
import com.Haven.mapper.UserYouthDataMapper;
import com.Haven.service.JxYouthService;
import com.Haven.service.UserYouthDataService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.Haven.utils.ConversionUtil.getCurrentCourse;

@SpringBootTest
class MineWebsiteApplicationTests {

    @Autowired
    private UserYouthDataService userYouthDataService;

    @Autowired
    private JxYouthService jxYouthService;

    @Autowired
    private UserYouthDataMapper userYouthDataMapper;

    @Test
    void contextLoads() throws IOException {
        userYouthDataService.addUserYouthData("2011542ser13", "N00140xxxxxxxxx");
        String userid = "2011542ser13";

        UserYouthData userYouthData = userYouthDataMapper.selectOne(
                new LambdaQueryWrapper<UserYouthData>()
                        .select()
                        .eq(UserYouthData::getUserid, userid)
        );
        userYouthData.setLastFinishTime(LocalDateTime.now());
        userYouthData.putFinishHistory(LocalDateTime.now().toString(), getCurrentCourse());
        userYouthDataMapper.updateById(userYouthData);
    }

}
