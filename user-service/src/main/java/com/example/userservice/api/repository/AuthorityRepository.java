package com.example.userservice.api.repository;

import com.example.userservice.api.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthorityName(String name);
}
