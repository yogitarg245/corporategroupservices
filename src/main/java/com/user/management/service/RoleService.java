package com.user.management.service;

import com.user.management.persistance.entities.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    Role findRoleById(Integer id);
}
