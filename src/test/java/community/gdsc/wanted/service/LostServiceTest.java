package community.gdsc.wanted.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import community.gdsc.wanted.domain.Lost;
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
        Lost lost = new Lost();
        lost.setAuthorIdx(1234);
        lost.setTitle("test title");
        lost.setContent("test content");
        lost.setReward(10000);
        lost.setX("test x");
        lost.setY("test y");
        lost.setAddress("test address");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        lost.setCreatedAt(currentTimestamp);
        lost.setIsDeleted(0);

        lostService.writeLost(lost);
        createdLost = lost;
        Lost savedLost = lostRepository.findById(lost.getId()).orElse(null);

        assertNotNull(savedLost);
        assertThat(lost.getAuthorIdx()).isEqualTo(savedLost.getAuthorIdx());
        assertThat(lost.getTitle()).isEqualTo(savedLost.getTitle());
        assertThat(lost.getContent()).isEqualTo(savedLost.getContent());
        assertThat(lost.getReward()).isEqualTo(savedLost.getReward());
        assertThat(lost.getX()).isEqualTo(savedLost.getX());
        assertThat(lost.getY()).isEqualTo(savedLost.getY());
        assertThat(lost.getAddress()).isEqualTo(savedLost.getAddress());
        assertThat(lost.getCreatedAt()).isEqualTo(savedLost.getCreatedAt());
        assertThat(lost.getIsDeleted()).isEqualTo(savedLost.getIsDeleted());
    }

    // 글 수정 테스트
    @Test
    public void modifyLost() {
        writeLost();
        Lost modifingLost = lostRepository.findById(createdLost.getId()).orElse(null);

        modifingLost.setAuthorIdx(1234);
        modifingLost.setTitle("modify title");
        modifingLost.setContent("modify content");
        modifingLost.setReward(10000);
        modifingLost.setX("modify x");
        modifingLost.setY("modify y");
        modifingLost.setAddress("modify address");
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        modifingLost.setCreatedAt(currentTimestamp);
        modifingLost.setIsDeleted(0);

        lostService.modifyLost(modifingLost);
        Lost savedLost = lostRepository.findById(createdLost.getId()).orElse(null);

        assertNotNull(savedLost);
        assertThat(modifingLost.getAuthorIdx()).isEqualTo(savedLost.getAuthorIdx());
        assertThat(modifingLost.getTitle()).isEqualTo(savedLost.getTitle());
        assertThat(modifingLost.getContent()).isEqualTo(savedLost.getContent());
        assertThat(modifingLost.getReward()).isEqualTo(savedLost.getReward());
        assertThat(modifingLost.getX()).isEqualTo(savedLost.getX());
        assertThat(modifingLost.getY()).isEqualTo(savedLost.getY());
        assertThat(modifingLost.getAddress()).isEqualTo(savedLost.getAddress());
        assertThat(modifingLost.getCreatedAt()).isEqualTo(savedLost.getCreatedAt());
        assertThat(modifingLost.getIsDeleted()).isEqualTo(savedLost.getIsDeleted());
    }

    // 글 삭제 테스트
    @Test
    public void deleteLost() {
        writeLost();
        Integer lostId = createdLost.getId();

        lostService.deleteLost(lostId);

        Lost deletedLost = lostRepository.findById(lostId).orElse(null);
        assertNull(deletedLost);
    }

    // 글 조회 테스트
    @Test
    public void viewLost() {
        writeLost();
        Integer viewId = createdLost.getId();

        Lost viewLost = lostService.viewLost(viewId);

        assertNotNull(viewLost);
        assertThat(viewLost.getAuthorIdx()).isEqualTo(createdLost.getAuthorIdx());
        assertThat(viewLost.getTitle()).isEqualTo(createdLost.getTitle());
        assertThat(viewLost.getContent()).isEqualTo(createdLost.getContent());
        assertThat(viewLost.getReward()).isEqualTo(createdLost.getReward());
        assertThat(viewLost.getX()).isEqualTo(createdLost.getX());
        assertThat(viewLost.getY()).isEqualTo(createdLost.getY());
        assertThat(viewLost.getAddress()).isEqualTo(createdLost.getAddress());
        assertThat(viewLost.getCreatedAt()).isEqualTo(createdLost.getCreatedAt());
        assertThat(viewLost.getIsDeleted()).isEqualTo(createdLost.getIsDeleted());
    }
}