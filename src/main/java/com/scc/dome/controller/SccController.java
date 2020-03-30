package com.scc.dome.controller;

import com.scc.dome.service.SccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/scc")
public class SccController {

    @Autowired
    private SccService sccService;

    @PostMapping("/t")
    public void test(@RequestParam("file") MultipartFile file) {

        sccService.ttt(file);
        System.out.println("1");
    }

    /**
     * 上传解析zip
     */
    @PostMapping("/zip")
    public void uploadZip(@RequestParam("file") MultipartFile file) {
        sccService.uploadZip(file);
    }

    @GetMapping("test")
    public void test(){
        System.out.println(111);
        sccService.test();
    }
}
