package com.company.config;

import com.company.data.entities.Role;
import com.company.enums.RoleType;
import com.company.errors.exceptions.RoleNotFoundException;
import com.company.repositories.RoleRepository;
import com.company.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleService roleService;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        createRoleIfNotFound(RoleType.MEDICAL_PHYSICIAN);
        createRoleIfNotFound(RoleType.PATIENT);
        alreadySetup = true;
    }

    @Transactional
    private Role createRoleIfNotFound(
            RoleType roleType) {
        Role role;
        try {
            role = roleService.findByRoleType(roleType.name());
        } catch(RoleNotFoundException e) {
                role = new Role();
                role.setRoleType(roleType);
                roleService.createRole(role);
        }
        return role;
    }
}
