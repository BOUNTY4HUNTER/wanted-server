package community.gdsc.wanted.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import community.gdsc.wanted.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByUsername(String username);

    void deleteById(Integer id);
}

