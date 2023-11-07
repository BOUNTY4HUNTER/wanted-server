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
import community.gdsc.wanted.dto.ListResponseDTO;
import community.gdsc.wanted.dto.ModifyRequestDTO;
import community.gdsc.wanted.dto.ViewResponseDTO;
import community.gdsc.wanted.dto.WriteRequestDTO;
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
        WriteRequestDTO writeRequestDTO = new WriteRequestDTO();
        writeRequestDTO.setTitle("test title");
        writeRequestDTO.setContent("test content");
        writeRequestDTO.setReward(10000);
        writeRequestDTO.setX("test x");
        writeRequestDTO.setY("test y");
        writeRequestDTO.setAddress("test address");

        lostService.writeLost(writeRequestDTO);
        List<Lost> lostList = lostRepository.findAll();
        Lost savedLost = lostList.get(0);
        createdLost = savedLost;

        assertNotNull(savedLost);
        assertThat(writeRequestDTO.getTitle()).isEqualTo(savedLost.getTitle());
        assertThat(writeRequestDTO.getContent()).isEqualTo(savedLost.getContent());
        assertThat(writeRequestDTO.getReward()).isEqualTo(savedLost.getReward());
        assertThat(writeRequestDTO.getX()).isEqualTo(savedLost.getX());
        assertThat(writeRequestDTO.getY()).isEqualTo(savedLost.getY());
        assertThat(writeRequestDTO.getAddress()).isEqualTo(savedLost.getAddress());
    }

    // 글 수정 테스트
    @Test
    public void modifyLost() {
        writeLost();
        Integer modifyId = createdLost.getId();
        ModifyRequestDTO modifyLost = new ModifyRequestDTO("modify title", "modify content", 10000,
            "modify x", "modify y", "modify address");
        //Lost modifingLost = lostRepository.findById(modifyId).get();

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

        ViewResponseDTO viewLost = lostService.viewLost(viewId);

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

        List<ListResponseDTO> listLost = lostService.listLost();

        assertFalse(listLost.isEmpty());
        assertEquals(2, listLost.size());
    }

    private void createSampleLostList() {
        List<WriteRequestDTO> list = new ArrayList<>();
        WriteRequestDTO lost1 = new WriteRequestDTO("test title1", "test content1", 10000,
            "test x1", "test y1", "test address1", 0);
        list.add(lost1);
        lostService.writeLost(lost1);

        WriteRequestDTO lost2 = new WriteRequestDTO("test title2", "test content2", 10000,
            "test x2", "test y2", "test address2", 0);
        list.add(lost2);
        lostService.writeLost(lost2);
    }
}