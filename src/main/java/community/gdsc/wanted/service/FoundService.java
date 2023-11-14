package community.gdsc.wanted.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.domain.Found;
import community.gdsc.wanted.dto.FoundListResponseDto;
import community.gdsc.wanted.dto.FoundModifyRequestDto;
import community.gdsc.wanted.dto.FoundResponseDto;
import community.gdsc.wanted.dto.FoundWriteRequestDto;
import community.gdsc.wanted.repository.FoundRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoundService {
    private final FoundRepository foundRepository;

    // 글 작성
    public void writeFound(FoundWriteRequestDto foundWriteRequestDto) {
        Found found = foundWriteRequestDto.toEntity();
        foundRepository.save(found);
    }

    // 글 수정
    public void modifyFound(Integer id, FoundModifyRequestDto modifiedFound) {
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
        Found found = foundRepository.findById(id).get();
        found.setIs_deleted(1);
        foundRepository.save(found);
    }

    public FoundResponseDto viewFound(Integer id) {
        Found found = foundRepository.findById(id).get();
        return found.toViewResponse();
    }

    public List<FoundListResponseDto> listFound() {
        List<Found> foundList = foundRepository.findAll();
        List<FoundListResponseDto> responseList = foundList.stream()
            .map(found -> found.toListResponse())
            .collect(Collectors.toList());
        return responseList;
    }
}