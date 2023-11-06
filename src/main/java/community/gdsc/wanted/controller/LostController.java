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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import community.gdsc.wanted.domain.Lost;
import community.gdsc.wanted.dto.ListResponseDTO;
import community.gdsc.wanted.dto.ViewResponseDTO;
import community.gdsc.wanted.dto.WriteRequestDTO;
import community.gdsc.wanted.service.LostService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/lost")
public class LostController {
    private final LostService lostService;

    // 글 작성 수정
    @PostMapping("/")
    public ResponseEntity<String> writeLost(@RequestBody WriteRequestDTO writeRequestDTO) throws Exception {
        lostService.writeLost(writeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    // 글 수정 해야됨
    @PutMapping("/{id}")
    public ResponseEntity<String> modifyLost(@PathVariable("id") Integer id, @ModelAttribute Lost modifiedLost)
        throws Exception {
        lostService.modifyLost(modifiedLost);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    // 글 삭제 수정
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLost(@PathVariable("id") Integer id) throws Exception {
        lostService.deleteLost(id);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    // 글 조회 수정
    @GetMapping("/{id}")
    public ResponseEntity<ViewResponseDTO> viewLost(@PathVariable("id") Integer id) throws Exception {
        ViewResponseDTO viewedLost = lostService.viewLost(id);
        return ResponseEntity.status(HttpStatus.OK).body(viewedLost);
    }

    // 글 리스트 수정
    @GetMapping("/list")
    public ResponseEntity<List<ListResponseDTO>> listLost() throws Exception {
        List<ListResponseDTO> lostList = lostService.listLost();
        return ResponseEntity.status(HttpStatus.OK).body(lostList);
    }
}
