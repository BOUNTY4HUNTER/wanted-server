package community.gdsc.wanted.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import community.gdsc.wanted.domain.Lost;

public interface LostRepository extends JpaRepository<Lost, Integer> {
}
