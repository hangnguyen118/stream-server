package com.example.streamserver.repository;

import com.example.streamserver.entity.AppRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional(readOnly = true)
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findByRolename(String rolename);
}
