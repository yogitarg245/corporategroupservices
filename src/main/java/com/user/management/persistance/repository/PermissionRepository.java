package com.user.management.persistance.repository;

import com.user.management.persistance.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    Permission findPermissionByName(String name);
}
