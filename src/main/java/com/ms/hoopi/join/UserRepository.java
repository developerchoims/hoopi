package com.ms.hoopi.join;

import com.ms.hoopi.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

    /**
     * @param id
     * @return String
     */
    default String findRoleByUserId(String id){
        String role = findById(id).get().getUsersRole();
        return role;
    };

    Users findByUsersId(String usersId);

}
