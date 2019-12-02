package com.user.management.service;

import com.user.management.persistance.entities.CorporateGroupEntity;
import com.user.management.persistance.repository.CorporateGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateGroupServiceImpl implements CorporateGroupService{

    @Autowired
    private CorporateGroupRepository corporateGroupRepository;

    @Override
    public CorporateGroupEntity findCorporateGroupByName(String name) {
        return corporateGroupRepository.findCorporateGroupByName(name);
    }

    @Override
    public void saveCorporateGroup(CorporateGroupEntity corporateGroupEntity) {
        corporateGroupRepository.save(corporateGroupEntity);
    }

    @Override
    public CorporateGroupEntity findCorporateGroupById(Integer corporateGroupId) {
        return corporateGroupRepository.getOne(corporateGroupId);
    }

    @Override
    public List<CorporateGroupEntity> findAll() {
        return corporateGroupRepository.findAll().stream().limit(10).collect(Collectors.toList());
    }

    @Override
    public void deleteCorporateGroup(String name) {
        corporateGroupRepository.delete(findCorporateGroupByName(name));
    }
}
