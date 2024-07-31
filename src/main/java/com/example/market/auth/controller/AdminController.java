package com.example.market.auth.controller;

import com.example.market.auth.dto.UserDto;
import com.example.market.auth.service.AdminService;
import com.example.market.auth.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 관리자의 사업자 전환 신청 목록 확인
    @GetMapping("/business-application-list")
    public ResponseEntity<List<UserDto>> businessApplicationList() {
        return ResponseEntity.ok(adminService.businessApplicationList());
    }


    // 관리자의 사업자 전환 신청 수락
    @PostMapping("/business-application-approval/{uuid}")
    public ResponseEntity<UserDto> businessApplactionApproval(
            @RequestParam("uuid")
            UUID uuid
    ) {
        return ResponseEntity.ok(adminService.businessApplcationApproval(uuid));
    }



    // 관리자의 사업자 전환 신청 거절
    @PostMapping("/business-application-rejection/{uuid}")
    public ResponseEntity<Void> businessApplcationRejection(
            @RequestParam("uuid")
            UUID uuid
    ) {
        adminService.businessApplcationRejection(uuid);
        return ResponseEntity.ok().build();
    }
}
