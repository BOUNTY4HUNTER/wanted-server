package community.gdsc.wanted.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import community.gdsc.wanted.domain.Lost;
import community.gdsc.wanted.dto.LostListResponseDTO;
import community.gdsc.wanted.dto.LostModifyRequestDTO;
import community.gdsc.wanted.dto.LostResponseDTO;
import community.gdsc.wanted.dto.LostWriteRequestDTO;
import community.gdsc.wanted.repository.LostRepository;

@SpringBootTest
@Transactional
class LostServiceTest {
    @Autowired
    LostService lostService;
    @Autowired
    LostRepository lostRepository;

    private Lost createdLost; // 글 수정, 삭제 테스트에서 작성 코드를 줄이기 위한 필드

    // 글 작성 테스트
    @Test
    public void writeLost() {
        LostWriteRequestDTO lostWriteRequestDTO = new LostWriteRequestDTO("test title", "test content",
            10000, "test x", "test y", "test address");

        lostService.writeLost(lostWriteRequestDTO);
        List<Lost> lostList = lostRepository.findAll();
        Lost savedLost = lostList.get(0);
        createdLost = savedLost;

        assertNotNull(savedLost);
        assertThat(lostWriteRequestDTO.getTitle()).isEqualTo(savedLost.getTitle());
        assertThat(lostWriteRequestDTO.getContent()).isEqualTo(savedLost.getContent());
        assertThat(lostWriteRequestDTO.getReward()).isEqualTo(savedLost.getReward());
        assertThat(lostWriteRequestDTO.getX()).isEqualTo(savedLost.getX());
        assertThat(lostWriteRequestDTO.getY()).isEqualTo(savedLost.getY());
        assertThat(lostWriteRequestDTO.getAddress()).isEqualTo(savedLost.getAddress());
    }

    // 글 수정 테스트
    @Test
    public void modifyLost() {
        writeLost();
        Integer modifyId = createdLost.getId();
        LostModifyRequestDTO modifyLost = new LostModifyRequestDTO("modify title", "modify content",
            10000, "modify x", "modify y", "modify address");

        lostService.modifyLost(modifyId, modifyLost);
        Lost savedLost = lostRepository.findById(modifyId).get();

        assertNotNull(savedLost);
        assertThat(modifyLost.getTitle()).isEqualTo(savedLost.getTitle());
        assertThat(modifyLost.getContent()).isEqualTo(savedLost.getContent());
        assertThat(modifyLost.getReward()).isEqualTo(savedLost.getReward());
        assertThat(modifyLost.getX()).isEqualTo(savedLost.getX());
        assertThat(modifyLost.getY()).isEqualTo(savedLost.getY());
        assertThat(modifyLost.getAddress()).isEqualTo(savedLost.getAddress());
    }

    // 글 삭제 테스트
    @Test
    public void deleteLost() {
        writeLost();
        Integer lostId = createdLost.getId();
        Lost deletedLost = lostRepository.findById(lostId).get();

        lostService.deleteLost(lostId);

        assertThat(deletedLost.getIsDeleted()).isEqualTo(1);
    }

    // 글 조회 테스트
    @Test
    public void viewLost() {
        writeLost();
        Integer viewId = createdLost.getId();

        LostResponseDTO viewLost = lostService.viewLost(viewId);

        assertNotNull(viewLost);
        assertThat(viewLost.getTitle()).isEqualTo(createdLost.getTitle());
        assertThat(viewLost.getContent()).isEqualTo(createdLost.getContent());
        assertThat(viewLost.getReward()).isEqualTo(createdLost.getReward());
        assertThat(viewLost.getX()).isEqualTo(createdLost.getX());
        assertThat(viewLost.getY()).isEqualTo(createdLost.getY());
        assertThat(viewLost.getAddress()).isEqualTo(createdLost.getAddress());
    }

    // 글 리스트 테스트
    @Test
    public void listLost() {
        createSampleLostList();

        List<LostListResponseDTO> listLost = lostService.listLost();

        assertFalse(listLost.isEmpty());
        assertEquals(2, listLost.size());
    }

    private void createSampleLostList() {
        List<LostWriteRequestDTO> list = new ArrayList<>();
        LostWriteRequestDTO lost1 = new LostWriteRequestDTO("test title1", "test content1", 10000,
            "test x1", "test y1", "test address1");
        list.add(lost1);
        lostService.writeLost(lost1);

        LostWriteRequestDTO lost2 = new LostWriteRequestDTO("test title2", "test content2", 10000,
            "test x2", "test y2", "test address2");
        list.add(lost2);
        lostService.writeLost(lost2);
    }
}