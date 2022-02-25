package com.ead.course.clients;

import com.ead.course.dtos.NotifySubscriptionDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.exceptions.ElementNotFoundException;
import com.ead.course.services.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class AuthUserClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilService utilsService;

    @Value("${ead.api.url.authuser}")
    String AUTHUSER_URI;

    public Page<UserDto> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        List<UserDto> searchResult = null;
        String url = AUTHUSER_URI + utilsService.createUrlGetAllUsersByCourse(courseId, pageable);
        log.info("Request URL: {} ", url);
        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {
            };
            ResponseEntity<ResponsePageDto<UserDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());
        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);
        return new PageImpl<>(searchResult);
    }

    public ResponseEntity<UserDto> getById(UUID userId) {

        log.info("Started method getById into AuthUserMs.");
        String url = AUTHUSER_URI + utilsService.createUrlFindById(userId);
        log.info("Request URL: {} ", url);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
        }catch (HttpStatusCodeException e){
            log.error("Error request /courses {} ", e);
            throw new ElementNotFoundException("User " + userId + "not found: " + e.getMessage());
        }

    }

    public void notifySubscribeUserIntoCourse(UUID courseId, UUID userId){
        log.info("Started method notify subscribe user into course.");
        String url = AUTHUSER_URI + utilsService.createUrlToNotifySubscription(userId);
        log.info("Request URL: {} ", url);
        NotifySubscriptionDto subscriptionDto = NotifySubscriptionDto.builder()
                .courseId(courseId)
                .build();
        try {
            restTemplate.postForObject(url,subscriptionDto, Void.class);
        }catch (HttpStatusCodeException e){
            log.error("Error notifying subscription to AuthUser MS. ", e);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        log.info("Notify subscribe user into course successfully");

    }
}
