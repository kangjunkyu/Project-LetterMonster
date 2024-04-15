package com.lemon.backend.domain.sketchbook.controller;

import com.lemon.backend.domain.sketchbook.service.SketchbookService;
import com.lemon.backend.global.format.code.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Sketchbook 컨트롤러", description = "Sketchbook Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sketchbook")
public class SketchbookController {

    private final SketchbookService sketchbookService;
    private final ApiResponse apiResponse;

}
