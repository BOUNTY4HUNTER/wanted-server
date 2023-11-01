package community.gdsc.wanted.service;

import java.util.List;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.domain.Lost;
import community.gdsc.wanted.repository.LostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LostService {
    private final LostRepository lostRepository;

    // 글 작성 메서드
    public void writeLost(Lost lost) {
        lostRepository.save(lost);
    }

    // 글 수정 메서드
    public void modifyLost(Lost modifiedLost) {
        lostRepository.save(modifiedLost);
    }

    // 글 삭제 메서드
    public void deleteLost(Integer id) {
        lostRepository.deleteById(id);
    }

    // 글 조회 메서드
    public Lost viewLost(Integer id) {
        return lostRepository.findById(id).get();
    }

    // 글 리스트 메서드
    public List<Lost> listLost() {
        return lostRepository.findAll();
    }
}
