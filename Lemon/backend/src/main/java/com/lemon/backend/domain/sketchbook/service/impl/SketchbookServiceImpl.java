package com.lemon.backend.domain.sketchbook.service.impl;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDetailDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetSimpleDto;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import com.lemon.backend.domain.sketchbook.repository.SketchCharacterMotionRepository;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.sketchbook.service.SketchbookService;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    private long getSameSketchbookLastNumber(String name){
        Optional<String> highestTagOpt = sketchbookRepository.findHighestSketchbookTagByName(name);
        long sameSketchbookLastNumber = highestTagOpt.map(tag -> Long.parseLong(tag)+1).orElse(1L);
        return sameSketchbookLastNumber;
    }

    @Transactional
    @Override
    public Long createSketchbook(Integer userId, SketchbookCreateDto sketchDto){
        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        long sameSketchbookLastNumber = getSameSketchbookLastNumber(sketchDto.getName());
        String uuid = UUID.randomUUID().toString();
        String sharaLink = "http://localhost:8080/api/sketchbooks/detail/" + uuid;

        Sketchbook sketch = Sketchbook.builder()
                .name(sketchDto.getName())
                .users(user)
                .shareLink(sharaLink)
                .sketchbookUuid(uuid)
                .tag(String.valueOf(sameSketchbookLastNumber))
                .build();

        SketchbookCharacterMotion sketchbookCharacterMotion = SketchbookCharacterMotion.builder()
                .sketchbook(sketch)
                .build();
        sketchbookCharacterMotionRepository.save(sketchbookCharacterMotion);

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
}
