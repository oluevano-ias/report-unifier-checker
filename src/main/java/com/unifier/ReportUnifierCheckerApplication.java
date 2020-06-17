package com.unifier;

import com.unifier.service.UnifierCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportUnifierCheckerApplication {
    @Autowired
    UnifierCheckerService unifierCheckerService;
    public static void main(String[] args) {
        SpringApplication.run(ReportUnifierCheckerApplication.class, args);
    }
}
