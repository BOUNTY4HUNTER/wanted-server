package community.gdsc.wanted.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.domain.Lost;
import community.gdsc.wanted.dto.ListResponseDTO;
import community.gdsc.wanted.dto.ViewResponseDTO;
import community.gdsc.wanted.dto.WriteRequestDTO;
import community.gdsc.wanted.repository.LostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LostService {
    private final LostRepository lostRepository;

    // 글 작성 메서드 수정
    public void writeLost(WriteRequestDTO writeRequestDTO) {
        Lost lost = writeRequestDTO.toEntity();
        lostRepository.save(lost);
    }

    // 글 수정 메서드 해야됨
    public void modifyLost(Lost modifiedLost) {
        lostRepository.save(modifiedLost);
    }

    // 글 삭제 메서드 수정
    public void deleteLost(Integer id) throws Exception {
        Lost lost = lostRepository.findById(id).get();

        lost.setIsDeleted(1);
        lostRepository.save(lost);
    }

    // 글 조회 메서드 수정
    public ViewResponseDTO viewLost(Integer id) {
        Lost lost = lostRepository.findById(id).get();
        return lost.toViewResponse();
    }

    // 글 리스트 메서드 수정
    public List<ListResponseDTO> listLost() {
        List<Lost> lostList = lostRepository.findAll();
        List<ListResponseDTO> responseList = lostList.stream()
            .map(lost -> lost.toListResponse())
            .collect(Collectors.toList());
        return responseList;
    }
}
