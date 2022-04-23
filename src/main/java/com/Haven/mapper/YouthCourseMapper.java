package com.Haven.mapper;

import com.Haven.entity.YouthCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface YouthCourseMapper extends BaseMapper<YouthCourse> {
}
