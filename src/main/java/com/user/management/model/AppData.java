package com.user.management.model;

import com.user.management.validation.EmailValidator;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class AppData {

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @NotNull
    private Integer corporateGroupId;

    @NotEmpty
    @NotNull
    @EmailValidator
    private String email;

    @NotEmpty
    @NotNull
    private String password;

    private boolean accountLocked;

    private boolean enabled;

    private Date createdDate;

    private Date expiryDate;

    private String postCode;

    private String name;

    private String description;

    private Integer roleId;

    private Integer permissionId;

    private Integer userId;

    private static final int EXPIRATION=60*24;

    public Date getExpiryDate() {
        return calculateExpiryDate(EXPIRATION);
    }

    public Date getCreatedDate() {
        return calculateCreatedDate();
    }

    private Date calculateCreatedDate() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE,0);
        return new Date(calendar.getTime().getTime());
    }

    private Date calculateExpiryDate(int expiration) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE,expiration);
        return new Date(calendar.getTime().getTime());
    }
}