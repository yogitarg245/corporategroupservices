package com.user.management.service;

import com.user.management.persistance.entities.Permission;
import org.springframework.stereotype.Service;

@Service
public interface PermissionService {

    Permission findPermissionById(Integer id);
}
