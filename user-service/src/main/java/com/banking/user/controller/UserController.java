package com.banking.user.controller;

import com.banking.user.model.UserAccountInfo;
import com.banking.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/account-info")
    public ResponseEntity<UserAccountInfo> getUserAccountInfo(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserAccountInfo(userId));
    }
}