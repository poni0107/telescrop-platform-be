package com.it.telescopeplatform.publisher.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.telescopeplatform.configs.errorhandlers.ResourceNotFoundException;
import com.it.telescopeplatform.publisher.dtos.PublisherDto;
import com.it.telescopeplatform.publisher.models.Publisher;
import com.it.telescopeplatform.publisher.repositories.PublisherRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherRepository repository;

    @GetMapping
    public List<Publisher> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Publisher getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public Publisher create(@RequestBody PublisherDto publisherDto) {
        var publisher = new Publisher();
        publisher.setName(publisherDto.getName());
        publisher.setContactEmail(publisherDto.getContactEmail());
        publisher.setContactPhone(publisherDto.getContactPhone());
        publisher.setAddress(publisherDto.getAddress());
        publisher.setWebsite(publisherDto.getWebsite());
        return repository.save(publisher);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public Publisher update(@PathVariable Long id, @RequestBody PublisherDto publisher) {
        var existingPublisher = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
        existingPublisher.setName(publisher.getName());
        existingPublisher.setContactEmail(publisher.getContactEmail());
        existingPublisher.setContactPhone(publisher.getContactPhone());
        existingPublisher.setAddress(publisher.getAddress());
        existingPublisher.setWebsite(publisher.getWebsite());
        return repository.save(existingPublisher);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}