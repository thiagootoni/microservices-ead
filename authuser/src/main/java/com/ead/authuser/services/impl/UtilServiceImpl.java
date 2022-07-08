package com.ead.authuser.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl {

    //public static final String COURSE_URI = "http://localhost:8082";

    @Value("${ead.api.url.course}")
    String COURSE_URI;

    public String createUrlGetAllCoursesByUser(UUID userId, Pageable pageable) {

        return  COURSE_URI + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }
}
