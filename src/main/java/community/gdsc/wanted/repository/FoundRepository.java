package community.gdsc.wanted.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import community.gdsc.wanted.domain.Found;

public interface FoundRepository extends JpaRepository<Found, Integer> {
}
