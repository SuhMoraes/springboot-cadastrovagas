package com.suhmoraes.cadastrodeemprego.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suhmoraes.cadastrodeemprego.Emprego;

public interface EmpregoRepository extends JpaRepository<Emprego, Long> {

}
