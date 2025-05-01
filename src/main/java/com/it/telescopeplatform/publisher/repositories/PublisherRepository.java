package com.it.telescopeplatform.publisher.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.publisher.models.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}