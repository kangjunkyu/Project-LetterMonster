package com.lemon.backend.domain.sketchbook.controller;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDetailDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.SketchbookGetSimpleDto;
import com.lemon.backend.domain.sketchbook.service.SketchbookService;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "Sketchbook 컨트롤러", description = "Sketchbook Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sketchbooks")
public class SketchbookController {

    private final SketchbookService sketchbookService;

    @Operation(summary = "스케치북 목록 조회", description = "스케치북 목록 조회 / userId 필요")
    @GetMapping("/list")
    public ResponseEntity<?> getSketchList(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        List<SketchbookGetSimpleDto> sketchList = sketchbookService.getSketchList(userId);
        return getResponseEntity(SuccessCode.OK, sketchList);
    }

    @Operation(summary = "스케치북 선택 조회", description = "스케치북 선택 조회 / sketchbookId 필요")
    @GetMapping("/simple/{sketchbookId}")
    public ResponseEntity<?> getSketchSelect(@PathVariable(value = "sketchbookId") Long sketchId){
        SketchbookGetDto sketchSelect = sketchbookService.getSketchSelect(sketchId);
        return getResponseEntity(SuccessCode.OK, sketchSelect);
    }

    @Operation(summary = "스케치북 선택 조회", description = "스케치북 선택 조회 / sketchbookId 필요")
    @GetMapping("/detail/{sketchbookId}")
    public ResponseEntity<?> getSketchSelect2(@PathVariable(value = "sketchbookId") Long sketchId){
        SketchbookGetDetailDto sketchSelect = sketchbookService.getSketchSelect2(sketchId);
        return getResponseEntity(SuccessCode.OK, sketchSelect);
    }


    @Operation(summary = "스케치북 생성", description = "스케치북 생성 / userId 필요")
    @PostMapping
    public ResponseEntity<?> createSketch(HttpServletRequest request, @Valid @RequestBody SketchbookCreateDto sketchDto){
        Integer userId = (Integer) request.getAttribute("userId");
        Long createSketchId = sketchbookService.createSketchbook(userId,sketchDto);
        return getResponseEntity(SuccessCode.CREATED, createSketchId);
    }

    @Operation(summary = "스케치북 수정", description = "스케치북 수정 / sketchbookId 필요")
    @PutMapping("/{sketchbookId}")
    public ResponseEntity<?> updateSketch(@PathVariable(value = "sketchbookId")Long sketchbookId, @Valid @RequestBody SketchbookUpdateDto sketchDto){
        Long updateSketchId = sketchbookService.updateSketchbook(sketchbookId, sketchDto);
        return getResponseEntity(SuccessCode.OK, updateSketchId);
    }

    @Operation(summary = "스케치북 삭제", description = "스케치북 삭제 / sketchbookId 필요")
    @DeleteMapping("/{sketchbookId}")
    public ResponseEntity<?> deleteSketchBook(@PathVariable(value = "sketchbookId")Long sketchbookId){
        sketchbookService.deleteSketchbook(sketchbookId);
        return getResponseEntity(SuccessCode.OK);
    }
}
