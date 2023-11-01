package community.gdsc.wanted.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import community.gdsc.wanted.domain.Lost;
import community.gdsc.wanted.service.LostService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lost")
public class LostController {
    private final LostService lostService;

    // 글 작성
    @PostMapping("/")
    public ResponseEntity<String> writeLost(@ModelAttribute Lost lost) {
        try {
            lostService.writeLost(lost);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
        }
    }

    // 글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyLost(@PathVariable("id") Integer id, @ModelAttribute Lost modifiedLost) {
        try {
            lostService.modifyLost(modifiedLost);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
        }
    }

    // 글 삭제
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLost(@PathVariable("id") Integer id) {
        try {
            lostService.deleteLost(id);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + e.getMessage());
        }
    }

    // 글 조회
    @GetMapping("{id}")
    public ResponseEntity<Lost> viewLost(@PathVariable("id") Integer id) {
        try {
            Lost viewedLost = lostService.viewLost(id);
            if (viewedLost != null) {
                return ResponseEntity.status(HttpStatus.OK).body(viewedLost);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
