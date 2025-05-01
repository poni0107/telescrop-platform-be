package com.it.telescopeplatform.telescope.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.telescope.models.Telescope;

public interface TelescopeRepository extends JpaRepository<Telescope, Long> {
    List<Telescope> findByAvailableTrue();
}