package com.it.telescopeplatform.accessory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.accessory.models.Accessory;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
}