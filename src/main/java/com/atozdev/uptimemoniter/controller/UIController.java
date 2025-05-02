package com.atozdev.uptimemoniter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UIController {

    @GetMapping("index")
    public String menu(){
        return "index";
    }

    @GetMapping("analytics-ui/{jobTitle}")
    public String analyticDashboard(@PathVariable String jobTitle){
        return  "analyticsDashboard";
    }

    @GetMapping("dashboard")
    public String dashboard(){
        return "dashboard";
    }
}
