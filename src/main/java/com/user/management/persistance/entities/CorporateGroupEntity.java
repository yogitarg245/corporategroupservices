package com.user.management.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "corporate_group")
public class CorporateGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "corporate_group_id")
    private Integer corporateGroupId;

    @Column(name = "name")
    private String name;

    @Column(name = "email_address")
    private String email;

    @Column(name = "created_date")
    private Date createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "corporateGroupEntity", fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<User> user;
}