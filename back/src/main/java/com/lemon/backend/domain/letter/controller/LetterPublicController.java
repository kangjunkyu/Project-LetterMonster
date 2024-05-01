package com.lemon.backend.domain.letter.controller;

import com.lemon.backend.domain.letter.dto.requestDto.LetterCreateDto;
import com.lemon.backend.domain.letter.service.LetterService;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "Letter Public 컨트롤러", description = "Letter Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/letter/public")
public class LetterPublicController {

    private final LetterService letterService;

    @Operation(summary = "비회원 편지 생성", description = "편지 생성, sketchbookId, characterId 필요")
    @PostMapping
    public ResponseEntity<?> createLetter(@Valid @RequestBody LetterCreateDto letterDto){
        return getResponseEntity(SuccessCode.CREATED, letterService.createAnonymousLetter(letterDto));
    }

}
