package community.gdsc.wanted.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.domain.Lost;
import community.gdsc.wanted.dto.LostListResponseDTO;
import community.gdsc.wanted.dto.LostModifyRequestDTO;
import community.gdsc.wanted.dto.LostResponseDTO;
import community.gdsc.wanted.dto.LostWriteRequestDTO;
import community.gdsc.wanted.repository.LostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LostService {
    private final LostRepository lostRepository;

    // 글 작성 메서드
    public void writeLost(LostWriteRequestDTO lostWriteRequestDTO) {
        Lost lost = lostWriteRequestDTO.toEntity();
        lostRepository.save(lost);
    }

    // 글 수정 메서드
    public void modifyLost(Integer id, LostModifyRequestDTO modifiedLost) {
        Lost lost = lostRepository.findById(id).get();

        if (!modifiedLost.getTitle().isBlank()) {
            lost.setTitle(modifiedLost.getTitle());
        }
        if (!modifiedLost.getContent().isBlank()) {
            lost.setContent(modifiedLost.getContent());
        }
        if (modifiedLost.getReward() != null) {
            lost.setReward(modifiedLost.getReward());
        }
        if (!modifiedLost.getX().isBlank()) {
            lost.setX(modifiedLost.getX());
        }
        if (!modifiedLost.getY().isBlank()) {
            lost.setY(modifiedLost.getY());
        }
        if (!modifiedLost.getAddress().isBlank()) {
            lost.setAddress(modifiedLost.getAddress());
        }

        lostRepository.save(lost);
    }

    // 글 삭제 메서드
    public void deleteLost(Integer id) {
        Lost lost = lostRepository.findById(id).get();

        lost.setIsDeleted(1);
        lostRepository.save(lost);
    }

    // 글 조회 메서드
    public LostResponseDTO viewLost(Integer id) {
        Lost lost = lostRepository.findById(id).get();
        return lost.toViewResponse();
    }

    // 글 리스트 메서드
    public List<LostListResponseDTO> listLost() {
        List<Lost> lostList = lostRepository.findAll();
        List<LostListResponseDTO> responseList = lostList.stream()
            .map(lost -> lost.toListResponse())
            .collect(Collectors.toList());
        return responseList;
    }
}
