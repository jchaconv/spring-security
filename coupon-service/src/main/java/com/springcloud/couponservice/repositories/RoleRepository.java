package com.springcloud.couponservice.repositories;

import com.springcloud.couponservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
