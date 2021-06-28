package com.suhmoraes.cadastrodeemprego.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suhmoraes.cadastrodeemprego.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);
}
