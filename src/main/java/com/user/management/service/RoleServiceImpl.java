package com.user.management.service;

import com.user.management.persistance.entities.Role;
import com.user.management.persistance.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleById(Integer id) {
        return roleRepository.getOne(id);
    }
}
