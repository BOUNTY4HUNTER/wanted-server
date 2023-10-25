package community.gdsc.wanted.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import community.gdsc.wanted.domain.Found;
import community.gdsc.wanted.service.FoundService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/found")
public class FoundController {
    private final FoundService foundService;

    //게시글 작성하기
    @PostMapping("/")
    public ResponseEntity<String> writeFound(@ModelAttribute Found found) {
        try {
            foundService.writeFound(found);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully done");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
        }
    }

    //수정
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyFound(@PathVariable("id") Integer id, @ModelAttribute Found modifiedFound) {
        try {
            foundService.modifyFound(modifiedFound);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
        }
    }

    //삭제
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFound(@PathVariable("id") Integer id) {
        try {
            foundService.deleteFound(id);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
        }
    }
}
