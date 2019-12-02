package com.user.management.persistance.repository;

import com.user.management.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>,RevisionRepository<User,Integer,Integer> {
    User findUserByEmail(String email);

    @Query(value = "select r.name from user u inner join user_roles ur on(u.user_id=ur.user_id) " +
            " inner join role r on(ur.role_id=r.role_id) inner join role_permission rp on(r.role_id=rp.role_id) " +
            " inner join permission p on(rp.permission_id=p.permission_id) where u.email=? ", nativeQuery = true)
    List<String> getAuthoritiesByEmail(String email);

    @Query(value = "select u.enabled from User u where u.email =:email", nativeQuery = true)
    boolean getUserIsEnable(@Param("email") String email);

    @Query(value = "SELECT * FROM user u where u.email = :email AND u.first_name = :firstName AND u.last_name = :lastName",
            nativeQuery=true )
    List<User> findUserByEmailAndFirstNameAndLastName(@Param("email") String email, @Param("firstName") String firstName, @Param("lastName")String lastName);
}
