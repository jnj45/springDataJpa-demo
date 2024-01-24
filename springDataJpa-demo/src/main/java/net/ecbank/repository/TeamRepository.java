package net.ecbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ecbank.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
