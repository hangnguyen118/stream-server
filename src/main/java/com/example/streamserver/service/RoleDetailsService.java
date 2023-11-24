package com.example.streamserver.service;

import com.example.streamserver.entity.AppRole;
import com.example.streamserver.repository.AppRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleDetailsService {
    private final AppRoleRepository appRoleRepository;
    public Optional<AppRole> findByRoleName(String roleName) {
        return appRoleRepository.findByRoleName(roleName);
    }
    public List<AppRole> getAllRoles() {
        return appRoleRepository.findAll();
    }
}
