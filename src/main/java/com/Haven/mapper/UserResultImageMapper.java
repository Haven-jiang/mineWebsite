package com.Haven.mapper;

import com.Haven.entity.UserResultImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 返回图片路径连接数据库类 UserResultImageMapper
 *
 * @author HavenJust
 * @date 20:08 周日 24 四月 2022年
 */

@Mapper
@Repository
public interface UserResultImageMapper extends BaseMapper<UserResultImage> {
}