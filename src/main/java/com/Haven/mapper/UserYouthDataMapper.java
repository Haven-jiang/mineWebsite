package com.Haven.mapper;

import com.Haven.entity.UserYouthData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 青年大学习数据连接数据库类 UserYouthDataMapper
 *
 * @author HavenJust
 * @date 22:49 周二 19 四月 2022年
 */

@Mapper
@Repository
public interface UserYouthDataMapper extends BaseMapper<UserYouthData> {
}