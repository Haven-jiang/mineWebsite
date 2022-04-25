package com.Haven.service;

import com.Haven.DTO.ResponsePackDTO;
import com.Haven.entity.UserYouthData;

import java.io.IOException;

public interface JxYouthService {

    ResponsePackDTO postData(String userid) throws IOException;

    ResponsePackDTO postData(UserYouthData user);

    void checkAndFix();

    void updateResult();

    void updateCourse(String courseId);

    void updateCourse(String courseId, String title, String uri);

    void updateCourse();
}
