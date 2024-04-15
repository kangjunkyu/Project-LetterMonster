package com.lemon.backend.domain.letter.controller;

import com.lemon.backend.domain.letter.service.LetterService;
import com.lemon.backend.global.format.code.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Letter 컨트롤러", description = "Letter Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/letter")
public class LetterController {

    private final LetterService letterService;
    private final ApiResponse apiResponse;

}
