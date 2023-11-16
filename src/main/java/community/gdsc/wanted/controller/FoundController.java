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

import community.gdsc.wanted.dto.FoundListResponseDTO;
import community.gdsc.wanted.dto.FoundModifyRequestDTO;
import community.gdsc.wanted.dto.FoundResponseDTO;
import community.gdsc.wanted.dto.FoundWriteRequestDTO;
import community.gdsc.wanted.service.FoundService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/found")
public class FoundController {
    private final FoundService foundService;

    //게시글 작성하기
    @PostMapping("")
    public ResponseEntity<String> writeFound(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @RequestBody
        FoundWriteRequestDTO foundWriteRequestDto
    ) {
        foundService.writeFound(foundWriteRequestDto, authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    //수정
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyFound(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @PathVariable("id")
        Integer id,
        @RequestBody
        FoundModifyRequestDTO modifyRequestDto
    ) {
        foundService.modifyFound(id, modifyRequestDto, authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFound(
        @RequestHeader("Authorization")
        String authorizationHeader,
        @PathVariable("id")
        Integer id
    ) {
        foundService.deleteFound(id, authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    //조회
    @GetMapping("/{id}")
    public ResponseEntity<FoundResponseDTO> viewFound(
        @PathVariable("id")
        Integer id
    ) {
        FoundResponseDTO viewedFound = foundService.viewFound(id);
        return ResponseEntity.status(HttpStatus.OK).body(viewedFound);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FoundListResponseDTO>> listFound() {
        List<FoundListResponseDTO> foundList = foundService.listFound();
        return ResponseEntity.status(HttpStatus.OK).body(foundList);
    }
}



