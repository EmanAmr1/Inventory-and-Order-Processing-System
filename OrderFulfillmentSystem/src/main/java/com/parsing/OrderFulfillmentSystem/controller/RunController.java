package com.parsing.OrderFulfillmentSystem.controller;

import com.parsing.OrderFulfillmentSystem.Service.RunningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/run")
public class RunController {

    private final RunningService runningService;

    public RunController(RunningService runningService) {
        this.runningService = runningService;
    }

    @PostMapping("/start")
    public ResponseEntity<Object> run (@RequestParam String DirectoryPath){
        try {
            runningService.run(DirectoryPath);
        }catch (Exception e){
            e.printStackTrace();
            return (ResponseEntity<Object>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    return ResponseEntity.ok().build();
    }
}
