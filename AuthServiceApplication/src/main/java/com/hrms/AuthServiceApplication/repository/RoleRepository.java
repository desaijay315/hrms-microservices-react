package com.hrms.AuthServiceApplication.repository;

import com.hrms.AuthServiceApplication.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(String role);

    @Query(value = "select count(*) from users_roles where role_id = ?1", nativeQuery = true)
    Long countRoleUsage(Long roleId);

}
