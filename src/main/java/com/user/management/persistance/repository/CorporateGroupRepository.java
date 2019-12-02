package com.user.management.persistance.repository;

import com.user.management.persistance.entities.CorporateGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateGroupRepository extends JpaRepository<CorporateGroupEntity,Integer> {
    CorporateGroupEntity findCorporateGroupByName(String name);
}
