package com.lambdainfo.ldapservice.service;

import com.lambdainfo.ldapservice.domain.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
public class TodoService {

    @Value("${todo.host}")
    private String todoHost;

    @Value("${todo.auth-token}")
    private String authToken;

    @Value("${todo.get-all}")
    private String tasksUrl;


    @Autowired
    RestTemplate restTemplate;

    public Tasks getAllTasks() throws URISyntaxException {

        String uri = todoHost+tasksUrl;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+authToken);
        var requestEntity = new HttpEntity<>(headers);
        var response = restTemplate.exchange(uri, HttpMethod.GET,requestEntity, Tasks.class);
        if(response.hasBody())
        {
            return response.getBody();
        }
        return null;
    }
}
