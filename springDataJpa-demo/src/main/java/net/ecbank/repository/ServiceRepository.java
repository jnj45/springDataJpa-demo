package net.ecbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ecbank.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, String> {

	Optional<Service> findByServiceUrl(String serviceUrl);
	
}
