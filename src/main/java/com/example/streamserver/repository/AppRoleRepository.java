package com.example.streamserver.repository;

import com.example.streamserver.entity.AppRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, UUID> {
    Optional<AppRole> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);

}
