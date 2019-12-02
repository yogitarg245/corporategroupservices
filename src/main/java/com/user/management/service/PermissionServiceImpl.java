package com.user.management.service;

import com.user.management.persistance.entities.Permission;
import com.user.management.persistance.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Permission findPermissionById(Integer id) {
        return permissionRepository.getOne(id);
    }
}
