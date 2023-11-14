package community.gdsc.wanted.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import community.gdsc.wanted.dto.LostListResponseDTO;
import community.gdsc.wanted.dto.LostModifyRequestDTO;
import community.gdsc.wanted.dto.LostResponseDTO;
import community.gdsc.wanted.dto.LostWriteRequestDTO;
import community.gdsc.wanted.service.LostService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lost")
public class LostController {
    private final LostService lostService;

    // 글 작성
    @PostMapping("/")
    public ResponseEntity<String> writeLost(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @RequestBody
        LostWriteRequestDTO lostWriteRequestDTO
    ) {
        lostService.writeLost(lostWriteRequestDTO, authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    // 글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyLost(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @PathVariable("id") Integer id,
        @RequestBody LostModifyRequestDTO lostModifyRequestDTO
    ) {
        lostService.modifyLost(id, lostModifyRequestDTO, authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    // 글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLost(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @PathVariable("id")
        Integer id
    ) {
        lostService.deleteLost(id, authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    // 글 조회
    @GetMapping("/{id}")
    public ResponseEntity<LostResponseDTO> viewLost(@PathVariable("id") Integer id) {
        LostResponseDTO viewedLost = lostService.viewLost(id);
        return ResponseEntity.status(HttpStatus.OK).body(viewedLost);
    }

    // 글 리스트
    @GetMapping("/list")
    public ResponseEntity<List<LostListResponseDTO>> listLost() {
        List<LostListResponseDTO> lostList = lostService.listLost();
        return ResponseEntity.status(HttpStatus.OK).body(lostList);
    }
}
