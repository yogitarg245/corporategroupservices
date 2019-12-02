package com.user.management.controller;

import com.user.management.model.AppData;
import com.user.management.persistance.entities.Permission;
import com.user.management.persistance.entities.Role;
import com.user.management.persistance.entities.User;
import com.user.management.response.JwtResponse;
import com.user.management.security.jwt.JwtProvider;
import com.user.management.service.CorporateGroupService;
import com.user.management.service.PermissionService;
import com.user.management.service.RoleService;
import com.user.management.service.UserService;
import com.user.management.validationImpl.ValidationErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private ValidationErrorHandling validationErrorHandling;

    @Autowired
    private CorporateGroupService groupService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody User loginRequest) {
        if (userService.getUserIsEnable(loginRequest.getEmail())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("authentication *******************" + authentication.getAuthorities());
            String jwt = jwtProvider.generateJwtToken(authentication);

            return ResponseEntity.ok(new JwtResponse(jwt));
        }
        return new ResponseEntity<>("User is not enabled",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody @Valid AppData appData, BindingResult bindingResult) {

        ResponseEntity<?> responseEntity = validationErrorHandling.validateErrors(bindingResult);
        if (responseEntity != null) {
            return responseEntity;
        }

        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();
        if (userService.findUserByEmail(appData.getEmail()) == null) {
            User user = new User();
            user.setEmail(appData.getEmail());
            user.setPassword(appData.getPassword());
            user.setFirstName(appData.getFirstName());
            user.setLastName(appData.getLastName());
            user.setExpiryDate(appData.getExpiryDate());
            user.setEnabled(appData.isEnabled());
            user.setCreatedDate(appData.getCreatedDate());
            user.setPostCode(appData.getPostCode());
            user.setAccountLocked(appData.isAccountLocked());
            user.setCorporateGroupEntity(groupService.findCorporateGroupById(appData.getCorporateGroupId()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleService.findRoleById(appData.getRoleId());
            roles.add(userRole);
            user.setRoles(roles);

            Set<Permission> permissions = new HashSet<>();
            Permission rolePermission = permissionService.findPermissionById(appData.getPermissionId());
            permissions.add(rolePermission);
            userRole.setPermissions(permissions);

            try {
                userService.saveUser(user);
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

    @RequestMapping(value = {"/getuser"}, method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@RequestParam(name = "email",required = false) String email) {
        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();

        try {
            List<User> users = null;
            User user = null;
            if (email != null) {
                user = userService.findUserByEmail(email);
                response.put("output", user);
                response.put("msg", "Success");
                entity = new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                users = userService.findAll();

                response.put("output", users);
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

    @RequestMapping(value = {"/getuserbynamelastnameemail"}, method = RequestMethod.GET)
    public ResponseEntity<?> getUserByNameLastNameAndEmail(@RequestParam(name = "email") String email,
                                                             @RequestParam(name = "firstName") String firstName,
                                                             @RequestParam(name = "lastName") String lastName) {
        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();

        try {
            if(email!=null&&firstName!=null&&lastName!=null) {
                List<User> user = null;
                user = userService.findUserByEmailAndFirstNameAndLastName(email, firstName, lastName);
                if (user.size() !=0) {
                    response.put("output", user);
                    response.put("msg", "Success");
                    entity = new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.put("output", "User not exist");
                    response.put("msg", "Failed");
                    entity = new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.put("msg", "failed");
            entity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
        return entity;
    }

    @RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestParam(name = "email") String email,@RequestBody AppData appData) {

        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();
        User user=userService.findUserByEmail(email);
        if (user != null) {
            user.setEmail(appData.getEmail());
            user.setPassword(appData.getPassword());
            user.setFirstName(appData.getFirstName());
            user.setLastName(appData.getLastName());
            user.setExpiryDate(appData.getExpiryDate());
            user.setEnabled(appData.isEnabled());
            user.setCreatedDate(appData.getCreatedDate());
            user.setPostCode(appData.getPostCode());
            user.setAccountLocked(appData.isAccountLocked());
            user.setCorporateGroupEntity(groupService.findCorporateGroupById(appData.getCorporateGroupId()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleService.findRoleById(appData.getRoleId());
            roles.add(userRole);
            user.setRoles(roles);

            Set<Permission> permissions = new HashSet<>();
            Permission rolePermission = permissionService.findPermissionById(appData.getPermissionId());
            permissions.add(rolePermission);
            userRole.setPermissions(permissions);

            try {
                userService.saveUser(user);
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

    @RequestMapping(value = {"/deleteuser"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@RequestParam(name = "email") String email) {
        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> response = new ManagedMap<>();

        try {
            if (email != null) {
                userService.deleteUser(email);
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