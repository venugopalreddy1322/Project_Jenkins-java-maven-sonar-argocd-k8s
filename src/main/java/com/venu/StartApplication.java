package com.venu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class StartApplication {

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("title", "Sprint boot application");
        model.addAttribute("msg", "CI: JENKINS:application is packaged using Maven, test by SonarQube and stored articat to another Git reporitory, CD:ArgoCD: deployed on to Kubernetes using ArgoCD operator ");
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
