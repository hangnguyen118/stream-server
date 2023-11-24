package com.example.streamserver.controller;

import com.example.streamserver.dto.RoleDto;
import com.example.streamserver.entity.AppRole;
import com.example.streamserver.entity.AuthResponse;
import com.example.streamserver.repository.AppRoleRepository;
import com.example.streamserver.service.RoleDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final AppRoleRepository appRoleRepository;
    @PostMapping("/add")
    public ResponseEntity<AuthResponse> addRole(@RequestBody RoleDto roleDto, HttpServletResponse response){
        if(appRoleRepository.existsByRoleName(roleDto.getRoleName())){
            return ResponseEntity.badRequest().body(new AuthResponse("Role đã tồn tại!!"));
        }
        AppRole role = new AppRole();
        role.setRoleName(roleDto.getRoleName());
        appRoleRepository.save(role);

        return ResponseEntity.ok(new AuthResponse("Đã tạo role thành công!"));
    }
}
