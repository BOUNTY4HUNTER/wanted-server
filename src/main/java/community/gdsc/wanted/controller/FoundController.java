package community.gdsc.wanted.controller;

import java.util.List;

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
        foundService.writeFound(found);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully done");
    }

    //수정
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyFound(@PathVariable("id") Integer id, @ModelAttribute Found modifiedFound) {
        foundService.modifyFound(modifiedFound);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    //삭제
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFound(@PathVariable("id") Integer id) {
        foundService.deleteFound(id);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    //조회
    @GetMapping("{id}")
    public ResponseEntity<Found> viewFound(@PathVariable("id") Integer id) {
        try {
            Found viewedFound = foundService.viewFound(id);
            if (viewedFound != null) {
                return ResponseEntity.status(HttpStatus.OK).body(viewedFound);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Found>> listFound() {
        try {
            List<Found> foundList = foundService.listFound();
            return ResponseEntity.status(HttpStatus.OK).body(foundList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}



