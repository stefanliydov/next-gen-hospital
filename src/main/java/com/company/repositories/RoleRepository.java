package com.company.repositories;

import com.company.data.entities.Role;
import com.company.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
