package com.ead.course.services.impl;

import com.ead.course.services.UtilService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    @Override
    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
        return "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }

    @Override
    public String createUrlFindById(UUID userId) {
        return "/users/" + userId;
    }

    @Override
    public String createUrlToNotifySubscription(UUID userId){
        return "/users/" + userId + "/courses/subscription";
    }
}
