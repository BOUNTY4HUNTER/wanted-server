package community.gdsc.wanted.service;

import java.util.List;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.domain.Found;
import community.gdsc.wanted.repository.FoundRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoundService {
    private final FoundRepository foundRepository;

    // 글 작성
    public void writeFound(Found found) {

        foundRepository.save(found);
    }

    // 글 수정
    public void modifyFound(Found modifiedFound) {
        foundRepository.save(modifiedFound);
    }

    // 글 삭제
    public void deleteFound(Integer id) {

        foundRepository.deleteById(id);
    }

    public Found viewFound(Integer id) {
        return foundRepository.findById(id).get();
    }

    public List<Found> listFound() {
        return foundRepository.findAll();
    }
}