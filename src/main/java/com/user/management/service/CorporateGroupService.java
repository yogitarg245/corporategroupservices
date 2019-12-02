package com.user.management.service;

import com.user.management.persistance.entities.CorporateGroupEntity;
import com.user.management.persistance.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CorporateGroupService {

    CorporateGroupEntity findCorporateGroupByName(String name);

    void saveCorporateGroup(CorporateGroupEntity corporateGroupEntity);

    CorporateGroupEntity findCorporateGroupById(Integer corporateGroupId);

    List<CorporateGroupEntity> findAll();

    void deleteCorporateGroup(String name);
}
