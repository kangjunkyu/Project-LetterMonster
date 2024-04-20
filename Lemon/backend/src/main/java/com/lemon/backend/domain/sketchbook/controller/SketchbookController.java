package com.lemon.backend.domain.sketchbook.controller;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.*;
import com.lemon.backend.domain.sketchbook.service.SketchbookService;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @GetMapping("/simple/{sketchbookUuid}")
    public ResponseEntity<?> getSketchSelect(@PathVariable(value = "sketchbookUuid") String sketchId){
        SketchbookGetDto sketchSelect = sketchbookService.getSketchSelect(sketchId);
        return getResponseEntity(SuccessCode.OK, sketchSelect);
    }

    @Operation(summary = "스케치북 선택 조회", description = "스케치북 선택 조회 / sketchbookId 필요")
    @GetMapping("/detail/{sketchbookUuid}")
    public ResponseEntity<?> getSketchSelect2(@PathVariable(value = "sketchbookUuid") String sketchId){
        SketchbookGetDetailDto sketchSelect = sketchbookService.getSketchSelect2(sketchId);
        return getResponseEntity(SuccessCode.OK, sketchSelect);
    }

    @Operation(summary = "스케치북 캐릭터 모션 페이지네이션", description = "스케치북 ID에 따른 캐릭터 모션의 페이지네이션된 리스트를 조회")
    @GetMapping("/sketchbooks/{sketchbookUuid}/pagination")
    public ResponseEntity<SketchbookDetailPageDto> getSketchSelect3(
            @Parameter(description = "스케치북 ID", required = true) @PathVariable(value = "sketchbookUuid") String sketchId,
            @Parameter(description = "페이지 정보", required = true) Pageable pageable) {
        SketchbookDetailPageDto detailPageDto = sketchbookService.getSketchSelect3(sketchId, pageable);
        return ResponseEntity.ok(detailPageDto);
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
