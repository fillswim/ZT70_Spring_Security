package com.fillswim.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    // Страница для всех работников
    @GetMapping("/")
    public String getInfoForAllEmployees() {
        return "view_for_all_employees";
    }

    // Страница для HR
    @GetMapping("/hr_info")
    public String getInfoOnlyForHR() {
        return "view_for_hr";
    }

    // Страница для Managers
    @GetMapping("/manager_info")
    public String getInfoOnlyForManagers() {
        return "view_for_managers";
    }



}
