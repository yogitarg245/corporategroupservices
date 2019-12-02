package com.user.management.controller;

import com.user.management.model.AppData;
import com.user.management.persistance.entities.CorporateGroupEntity;
import com.user.management.persistance.entities.User;
import com.user.management.service.CorporateGroupService;
import com.user.management.service.UserService;
import com.user.management.validationImpl.ValidationErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class CorporateGroupController {

    @Autowired
    private CorporateGroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationErrorHandling validationErrorHandling;

    @RequestMapping(value = "/createcorporategroup", method = RequestMethod.POST)
    public ResponseEntity<?> createCorporateGroup(@RequestBody @Valid AppData appData, BindingResult bindingResult) {

        ResponseEntity<?> responseEntity = validationErrorHandling.validateErrors(bindingResult);
        if (responseEntity != null) {
            return responseEntity;
        }

        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();
        if (groupService.findCorporateGroupByName(appData.getName()) == null) {
            CorporateGroupEntity corporateGroupEntity = new CorporateGroupEntity();
            corporateGroupEntity.setName(appData.getName());
            corporateGroupEntity.setEmail(appData.getEmail());
            corporateGroupEntity.setCreatedDate(appData.getCreatedDate());

            try {
                groupService.saveCorporateGroup(corporateGroupEntity);
                response.put("output", true);
                response.put("msg", "Success");
                entity = new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("output", false);
                response.put("msg", "failed");
                entity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
                e.printStackTrace();
            }
        } else {
            response.put("output", false);
            response.put("msg", "User already exist");
            entity = new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
        }
        return entity;
    }

    @RequestMapping(value = {"/getcorporategroup"}, method = RequestMethod.GET)
    public ResponseEntity<?> getCorporateGroup(@RequestParam(name = "id",required = false) Integer id) {
        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();

        try {
            Set<User> users=null;
            List<CorporateGroupEntity> corporateGroupEntities = null;
            CorporateGroupEntity corporateGroupEntity = null;
            if (id != null) {
                corporateGroupEntity= groupService.findCorporateGroupById(id);
                users=corporateGroupEntity.getUser();
                corporateGroupEntity.setUser(users);
                response.put("output", corporateGroupEntity);
                response.put("output", users);

                response.put("msg", "Success");
                entity = new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                corporateGroupEntities = groupService.findAll();

                response.put("output", corporateGroupEntities);
                response.put("msg", "Success");
                entity = new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("msg", "failed");
            entity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        return entity;
    }

    @RequestMapping(value = "/updatecorporategroup", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCorporateGroup(@RequestParam(name = "email") String email,@RequestBody AppData appData,BindingResult bindingResult) {

        ResponseEntity<?> responseEntity = validationErrorHandling.validateErrors(bindingResult);
        if (responseEntity != null) {
            return responseEntity;
        }

        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();
        if (groupService.findCorporateGroupByName(appData.getName()) == null) {
            CorporateGroupEntity corporateGroupEntity = new CorporateGroupEntity();
            corporateGroupEntity.setName(appData.getName());
            corporateGroupEntity.setEmail(appData.getEmail());
            corporateGroupEntity.setCreatedDate(appData.getCreatedDate());

            try {
                groupService.saveCorporateGroup(corporateGroupEntity);
                response.put("output", true);
                response.put("msg", "Success");
                entity = new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                response.put("output", false);
                response.put("msg", "failed");
                entity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
                e.printStackTrace();
            }
        } else {
            response.put("output", false);
            response.put("msg", "User already exist");
            entity = new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
        }
        return entity;
    }

    @RequestMapping(value = {"/deletecorporategroup"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestParam(name = "name") String name) {

        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();

        try {
            if (name != null) {
                groupService.deleteCorporateGroup(name);
                response.put("output", "User deleted");
                response.put("msg", "Success");
                entity = new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("msg", "failed");
            entity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        return entity;
    }

}
