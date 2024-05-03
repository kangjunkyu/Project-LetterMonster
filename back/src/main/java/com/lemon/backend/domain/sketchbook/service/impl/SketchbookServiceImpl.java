package com.lemon.backend.domain.sketchbook.service.impl;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.*;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.repository.SketchCharacterMotionRepository;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.sketchbook.service.SketchbookService;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.badWord.BadWordFilterUtil;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class SketchbookServiceImpl implements SketchbookService {

    private final UserRepository userRepository;
    private final SketchbookRepository sketchbookRepository;
    private final SketchCharacterMotionRepository sketchbookCharacterMotionRepository;
    BadWordFilterUtil badWordFilterUtil = new BadWordFilterUtil("☆");
//    String baseUrl = System.getenv("BASE_URL");

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public List<SketchbookGetSimpleDto> getSketchList(Integer userId){
        return sketchbookRepository.getSketchList(userId).orElse(Collections.emptyList());
    }

    @Override
    public SketchbookGetDto getSketchSelect(String sketchId){
        return sketchbookRepository.getSketchSelect(sketchId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
    }
    @Override
    public SketchbookGetDetailDto getSketchSelect2(String sketchId){
        return sketchbookRepository.getSketchSelect2(sketchId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
    }

    @Override
    public SketchbookDetailPageDto getSketchSelect3(String sketchId, Pageable pageable){
        return sketchbookRepository.getSketchSelect3(sketchId, pageable);
    }

    private long getSameSketchbookLastNumber(String name){
        Optional<String> highestTagOpt = sketchbookRepository.findHighestSketchbookTagByName(name);
        long sameSketchbookLastNumber = highestTagOpt.map(tag -> Long.parseLong(tag)+1).orElse(1L);
        return sameSketchbookLastNumber;
    }

    @Override
    public Optional<List<SketchbookSearchGetDto>> searchSkechbook(String searchName){
        Optional<List<SketchbookSearchGetDto>> list = sketchbookRepository.searchList(searchName);

        return list;
    }

    @Transactional
    @Override
    public Long createSketchbook(Integer userId, SketchbookCreateDto sketchDto){
        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(badWordFilterUtil.checkBadWord(sketchDto.getName())) throw new CustomException(ErrorCode.CANT_USING_BAD_WORD);
        long sameSketchbookLastNumber = getSameSketchbookLastNumber(sketchDto.getName());
        String uuid = UUID.randomUUID().toString();
        String sharaLink = baseUrl + "/sketchbooks/" + uuid;
        boolean isRepresent = !sketchbookRepository.existsRepresentSketchbook(userId);

        Sketchbook sketch = Sketchbook.builder()
                .name(sketchDto.getName())
                .users(user)
                .shareLink(sharaLink)
                .sketchbookUuid(uuid)
                .tag(String.valueOf(sameSketchbookLastNumber))
                .isRepresent(isRepresent)
                .build();

        return sketchbookRepository.save(sketch).getId();
    }

    @Transactional
    @Override
    public boolean changePublic(Long sketchbookId){
        Sketchbook sketch = sketchbookRepository.findById(sketchbookId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
        boolean changePublicStatus = !sketch.getIsPublic();
        sketch.setIsPublic(changePublicStatus);

        return sketch.getIsPublic();
    }

    @Transactional
    @Override
    public Long updateSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto){
        Sketchbook sketch = sketchbookRepository.findById(sketchbookId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
        if(badWordFilterUtil.checkBadWord(sketchDto.getName())) throw new CustomException(ErrorCode.CANT_USING_BAD_WORD);
        sketch.setName(sketchDto.getName());

        return sketch.getId();
    }

    @Transactional
    @Override
    public Long ShareSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto){
        Sketchbook sketch = sketchbookRepository.findById(sketchbookId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));

        sketch.setShareLink(sketchDto.getShareLink());

        return sketch.getId();
    }

    @Transactional
    @Override
    public void deleteSketchbook(Long sketchbookId){
        sketchbookRepository.deleteById(sketchbookId);
    }

    @Override
    public List<SketchbookGetAllDto> getSketchAll(){
        return sketchbookRepository.getSketchAll().orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
    }

    @Override
    @Transactional
    public void changeRepresent(Integer userId, Long newRepresentId) {
        Optional<Sketchbook> currentRepresent = sketchbookRepository.findRepresentSkechbook(userId);

        if (currentRepresent.isPresent()) {
            Sketchbook currentSketchbook = currentRepresent.get();
            currentSketchbook.changeRepresent(false);
            sketchbookRepository.save(currentSketchbook);
        }

        // 새로운 스케치북을 대표로 설정
        sketchbookRepository.findById(newRepresentId)
                .ifPresent(sketchbook -> {
                    sketchbook.changeRepresent(true);
                    sketchbookRepository.save(sketchbook);
                });
    }

}
