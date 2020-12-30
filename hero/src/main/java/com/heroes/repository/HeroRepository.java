package com.heroes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heroes.model.Hero;

public interface HeroRepository extends JpaRepository<Hero, Integer> {

}
