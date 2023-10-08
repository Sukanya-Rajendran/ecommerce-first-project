package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepositoy extends JpaRepository<Role,Long> {

    Role findByName(String name);



    @Modifying
    @Query("UPDATE Role r SET r.name = :newName WHERE r.id = :roleId")
    void updateRoleNameById(Long roleId, String newName);

}
