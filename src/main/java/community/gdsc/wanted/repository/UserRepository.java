package community.gdsc.wanted.repository;

import community.gdsc.wanted.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByUsername(String username);

    void deleteById(Integer id);

    List<User> findByCoin(Integer coin);
}

