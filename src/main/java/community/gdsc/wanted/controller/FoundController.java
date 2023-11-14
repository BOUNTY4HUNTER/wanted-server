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
import org.springframework.web.bind.annotation.RequestMapping;

import community.gdsc.wanted.dto.FoundListResponseDto;
import community.gdsc.wanted.dto.FoundModifyRequestDto;
import community.gdsc.wanted.dto.FoundResponseDto;
import community.gdsc.wanted.dto.FoundWriteRequestDto;
import community.gdsc.wanted.service.FoundService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/found")
public class FoundController {
    private final FoundService foundService;

    //게시글 작성하기
    @PostMapping("/")
    public ResponseEntity<String> writeFound(@RequestBody FoundWriteRequestDto foundWriteRequestDto) {
        foundService.writeFound(foundWriteRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully done");
    }

    //수정
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyFound(@PathVariable("id") Integer id,
        @RequestBody FoundModifyRequestDto modifyRequestDto) {
        foundService.modifyFound(id, modifyRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully done");
    }

    //삭제
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFound(@PathVariable("id") Integer id) {
        foundService.deleteFound(id);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    //조회
    @GetMapping("{id}")
    public ResponseEntity<FoundResponseDto> viewFound(@PathVariable("id") Integer id) {
        FoundResponseDto viewedFound = foundService.viewFound(id);
        return ResponseEntity.status(HttpStatus.OK).body(viewedFound);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FoundListResponseDto>> listFound() {
        List<FoundListResponseDto> foundList = foundService.listFound();
        return ResponseEntity.status(HttpStatus.OK).body(foundList);
    }
}



