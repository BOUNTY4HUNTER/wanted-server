package community.gdsc.wanted.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.auth.TokenProvider;
import community.gdsc.wanted.domain.Lost;
import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.dto.LostListResponseDTO;
import community.gdsc.wanted.dto.LostModifyRequestDTO;
import community.gdsc.wanted.dto.LostResponseDTO;
import community.gdsc.wanted.dto.LostWriteRequestDTO;
import community.gdsc.wanted.exception.NotFoundException;
import community.gdsc.wanted.exception.UnauthorizedException;
import community.gdsc.wanted.repository.LostRepository;
import community.gdsc.wanted.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LostService {
    private final LostRepository lostRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    private Lost getAuthenticatedLost(Integer id, String authHeader)
        throws NotFoundException, UnauthorizedException {
        Optional<Lost> lost = lostRepository.findById(id);

        Integer userId = tokenProvider.getUserIdFromAuthHeader(authHeader);
        Optional<User> user = userRepository.findById(userId);

        if (lost.isEmpty()) {
            throw new NotFoundException();
        }

        Lost lostEntity = lost.get();

        if (lostEntity.getId() != tokenProvider.getUserIdFromAuthHeader(authHeader)) {
            throw new UnauthorizedException();
        }

        if (Boolean.TRUE.equals(user.isEmpty()
            || user.get().getIsDeleted())
            || Boolean.TRUE.equals(lostEntity.getIsDeleted())) {
            throw new NotFoundException();
        }

        return lostEntity;
    }

    // 글 작성 메서드
    public void writeLost(LostWriteRequestDTO lostWriteRequestDTO, String authHeader) {
        Integer userId = tokenProvider.getUserIdFromAuthHeader(authHeader);

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UnauthorizedException();
        }

        Lost lost = lostWriteRequestDTO.toEntity(user.get());
        lostRepository.save(lost);
    }

    // 글 수정 메서드
    public void modifyLost(Integer id, LostModifyRequestDTO modifiedLost, String authHeader) throws NotFoundException {
        Lost lost = getAuthenticatedLost(id, authHeader);

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
    public void deleteLost(Integer id, String authHeader) {
        Lost lost = getAuthenticatedLost(id, authHeader);

        lost.setIsDeleted(true);
        lostRepository.save(lost);
    }

    // 글 조회 메서드
    public LostResponseDTO viewLost(Integer id) throws NotFoundException {
        Optional<Lost> lost = lostRepository.findById(id);

        if (lost.isEmpty()) {
            throw new NotFoundException();
        }

        return lost.get().toViewResponse();
    }

    // 글 리스트 메서드
    public List<LostListResponseDTO> listLost() {
        List<Lost> lostList = lostRepository.findAll();
        return lostList.stream()
            .map(Lost::toListResponse)
            .toList();
    }
}
