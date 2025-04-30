package com.atozdev.uptimemoniter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {

    @GetMapping("/settings/modal")
    public String getSettingsModal() {
        return "settings";
    }
}
