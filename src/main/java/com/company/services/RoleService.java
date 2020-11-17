package com.company.services;

import com.company.constants.Errors;
import com.company.data.entities.Role;
import com.company.enums.RoleType;
import com.company.errors.exceptions.RoleNotFoundException;
import com.company.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role findByRoleType(String roleType){
       return this.roleRepository
                .findByRoleType(RoleType.valueOf(roleType))
                .orElseThrow(() -> new RoleNotFoundException(Errors.ROLE_NOT_FOUND));
    }

    public void createRole(Role role) {
        this.roleRepository.save(role);
    }
}
