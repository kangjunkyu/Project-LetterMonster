package com.lemon.backend.domain.sketchbook.service.impl;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetSimpleDto;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.sketchbook.service.SketchbookService;
import com.lemon.backend.domain.users.entity.Users;
import com.lemon.backend.domain.users.repository.UserRepository;
import com.lemon.backend.global.exception.sketchbook.SketchbookNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class SketchbookServiceImpl implements SketchbookService {

    private final UserRepository userRepository;
    private final SketchbookRepository sketchbookRepository;

    @Override
    public List<SketchbookGetSimpleDto> getSketchList(Integer userId){
        return sketchbookRepository.getSketchList(userId).orElseThrow(SketchbookNotFound::new);
    }

    @Override
    public SketchbookGetDto getSketchSelect(Long sketchId){
        return sketchbookRepository.getSketchSelect(sketchId).orElseThrow(SketchbookNotFound::new);
    }
    @Transactional
    @Override
    public Long createSketchbook(SketchbookCreateDto sketchDto){
        Users user = userRepository.findById(sketchDto.getUserId()).orElseThrow();
        Sketchbook sketch = Sketchbook.builder()
                .name(sketchDto.getName())
                .users(user)
                .build();


        return sketchbookRepository.save(sketch).getId();
    }

    @Transactional
    @Override
    public boolean changePublic(Long sketchbookId){
        Sketchbook sketch = sketchbookRepository.findById(sketchbookId).orElseThrow();
        boolean changePublicStatus = !sketch.getIsPublic();
        sketch.setIsPublic(changePublicStatus);

        return sketch.getIsPublic();
    }

    @Transactional
    @Override
    public Long updateSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto){
        Sketchbook sketch = sketchbookRepository.findById(sketchbookId).orElseThrow();

        sketch.setName(sketchDto.getName());

        return sketch.getId();
    }

    @Transactional
    @Override
    public Long ShareSketchbook(Long sketchbookId, SketchbookUpdateDto sketchDto){
        Sketchbook sketch = sketchbookRepository.findById(sketchbookId).orElseThrow();

        sketch.setShareLink(sketchDto.getShareLink());

        return sketch.getId();
    }

    @Transactional
    @Override
    public void deleteSketchbook(Long sketchbookId){
        sketchbookRepository.deleteById(sketchbookId);
    }
}
