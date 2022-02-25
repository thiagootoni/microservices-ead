package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.services.impl.UtilServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilServiceImpl utilService;

    public Page<CourseDto> getAllCoursesByUser(UUID userId, Pageable pageable){
        String url = utilService.createUrlGetAllCoursesByUser(userId, pageable);
        log.info("Started request. Request URL: {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
            ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            return new PageImpl<>(result.getBody().getContent());
        }catch (HttpStatusCodeException e){
            log.error("Error request /courses {} ", e);
        }
        log.info("Finished request /courses userId {} ", userId);
        return null;
    }
}
