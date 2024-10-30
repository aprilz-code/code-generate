package com.aprilz.generate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Aprilz
 * @date 2023/2/27-16:07
 * @description TODO
 */
@RestController
@RequestMapping(value = "/")
public class TestController {

    @GetMapping("/")
    public void  index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }
}
