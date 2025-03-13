package com.jupiterlabs.megaplay_cache.controller;

import com.jupiterlabs.megaplay_cache.api.MotherShip;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiTest {
    private Logger LOGGER = LoggerFactory.getLogger(ApiTest.class);
    @Autowired
    MotherShip motherShip;

    @RequestMapping("/results")
    public String test(HttpServletRequest request) {
        LOGGER.info("Request from " + request.getRemoteAddr());
        return motherShip.getContent();

    }


}
