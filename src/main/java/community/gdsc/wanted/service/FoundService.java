package community.gdsc.wanted.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import community.gdsc.wanted.auth.TokenProvider;
import community.gdsc.wanted.domain.Found;
import community.gdsc.wanted.domain.User;
import community.gdsc.wanted.dto.FoundListResponseDto;
import community.gdsc.wanted.dto.FoundModifyRequestDto;
import community.gdsc.wanted.dto.FoundResponseDto;
import community.gdsc.wanted.dto.FoundWriteRequestDto;
import community.gdsc.wanted.exception.NotFoundException;
import community.gdsc.wanted.exception.UnauthorizedException;
import community.gdsc.wanted.repository.FoundRepository;
import community.gdsc.wanted.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoundService {
    private final FoundRepository foundRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    private Found getAuthenticatedFound(Integer id, String authHeader)
        throws NotFoundException, UnauthorizedException {
        Optional<Found> found = foundRepository.findById(id);

        if (found.isEmpty()) {
            throw new NotFoundException();
        }

        Found foundEntity = found.get();

        if (foundEntity.getId() != tokenProvider.getUserIdFromAuthHeader(authHeader)) {
            throw new UnauthorizedException();
        }

        if (Boolean.TRUE.equals(foundEntity.getIsDeleted())) {
            throw new NotFoundException();
        }

        return foundEntity;
    }

    // 글 작성
    public void writeFound(FoundWriteRequestDto foundWriteRequestDto, String authHeader) {
        Integer userId = tokenProvider.getUserIdFromAuthHeader(authHeader);
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UnauthorizedException();
        }

        Found found = foundWriteRequestDto.toEntity(user.get());
        foundRepository.save(found);
    }

    // 글 수정
    public void modifyFound(Integer id, FoundModifyRequestDto modifiedFound, String authHeader) {
        Found found = getAuthenticatedFound(id, authHeader);

        if (!modifiedFound.getTitle().isBlank()) {
            found.setTitle(modifiedFound.getTitle());
        }

        if (!modifiedFound.getX().isBlank()) {
            found.setX(modifiedFound.getX());
        }

        if (!modifiedFound.getY().isBlank()) {
            found.setY(modifiedFound.getY());
        }

        if (!modifiedFound.getAddress().isBlank()) {
            found.setAddress(modifiedFound.getAddress());
        }

        foundRepository.save(found);
    }

    // 글 삭제
    public void deleteFound(Integer id, String authHeader) {
        Found found = getAuthenticatedFound(id, authHeader);
        found.setIsDeleted(true);

        foundRepository.save(found);
    }

    public FoundResponseDto viewFound(Integer id) {
        Optional<Found> found = foundRepository.findById(id);

        if (found.isEmpty()) {
            throw new NotFoundException();
        }

        return found.get().toViewResponse();
    }

    public List<FoundListResponseDto> listFound() {
        List<Found> foundList = foundRepository.findAll();

        return foundList.stream()
            .map(Found::toListResponse)
            .toList();
    }
}