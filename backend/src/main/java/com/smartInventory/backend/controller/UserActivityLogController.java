package com.smartInventory.backend.controller;

import com.smartInventory.backend.model.UserActivityLog;
import com.smartInventory.backend.service.UserActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-activities")
public class UserActivityLogController {
    @Autowired
    private UserActivityLogService userActivityLogService;

    //fetch api to get all users activity log
    @GetMapping("/{username}")
    public ResponseEntity<List<UserActivityLog>> getUserActivityLogs(@PathVariable String username) {
        List<UserActivityLog> logs = userActivityLogService.getUserActivityLogs(username);
        return ResponseEntity.ok(logs);
    }
}
