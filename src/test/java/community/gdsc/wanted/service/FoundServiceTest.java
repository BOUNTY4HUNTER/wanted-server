package community.gdsc.wanted.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import community.gdsc.wanted.repository.FoundRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class FoundServiceTest {
    @Autowired
    FoundService foundService;
    @Autowired
    FoundRepository foundRepository;
}
