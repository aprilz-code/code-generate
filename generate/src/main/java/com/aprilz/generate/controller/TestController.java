package com.aprilz.generate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aprilz
 * @date 2023/2/27-16:07
 * @description TODO
 */
@RestController
@RequestMapping(value = "/")
public class TestController {

    @GetMapping("/")
    public String index() {
        return "isOk";
    }
}
