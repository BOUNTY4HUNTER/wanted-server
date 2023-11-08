package community.gdsc.wanted.service;

import java.util.List;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.DTO.ModifyRequestDto;
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
    public void modifyFound(Integer id, ModifyRequestDto modifiedFound) {
        Found found = foundRepository.findById(id).get();

        if (!modifiedFound.getTitle().isBlank()) {
            found.setTitle(modifiedFound.getTitle());
        }
        if (!modifiedFound.getContent().isBlank()) {
            found.setContent(modifiedFound.getContent());
        }

        foundRepository.save(found);
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