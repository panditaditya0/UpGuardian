package com.atozdev.uptimemoniter.controller;

import com.atozdev.uptimemoniter.services.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UIController {
    private final JobService jobService;

    public UIController(JobService jobService){
        this.jobService = jobService;
    }

    @GetMapping("index")
    public String menu(){
        return "index";
    }

    @GetMapping("analytics-ui/{jobTitle}")
    public String analyticDashboard(@PathVariable String jobTitle, Model model){
        List<Map<String, String>> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(Map.of("label", "Dashboard", "url", "/dashboard"));
        breadcrumbs.add(Map.of("label", jobTitle, "url", "/"));
        model.addAttribute("breadcrumbs", breadcrumbs);
        return jobService.fetchPageToBeRendered(jobTitle);
//        return "springServiceAnalytics";
//        return  "apiAnalytics";
    }

    @GetMapping("dashboard")
    public String dashboard(Model model){
        List<Map<String, String>> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(Map.of("label", "Dashboard", "url", "/dashboard"));
        model.addAttribute("breadcrumbs", breadcrumbs);
        return "dashboard";
    }

    @GetMapping("addAlert")
    public String addAlert(Model model){
        List<Map<String, String>> breadcrumbs = new ArrayList<>();

        breadcrumbs.add(Map.of("label", "Dashboard", "url", "/dashboard"));
        breadcrumbs.add(Map.of("label", "Create Alert", "url", "/"));

        model.addAttribute("breadcrumbs", breadcrumbs);
        return "addAlert";
    }

    @GetMapping("createAlert/{jobTitle}")
    public String addAlert(Model model,@PathVariable String jobTitle){
        List<Map<String, String>> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(Map.of("label", "Dashboard", "url", "/dashboard"));
        breadcrumbs.add(Map.of("label", "Create Alert", "url", "/"));
        model.addAttribute("breadcrumbs", breadcrumbs);
        return "createAlert/"+jobTitle;
    }
}
