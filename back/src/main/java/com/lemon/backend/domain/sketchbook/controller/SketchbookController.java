package com.lemon.backend.domain.sketchbook.controller;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.dto.responseDto.*;
import com.lemon.backend.domain.sketchbook.service.SketchbookService;
import com.lemon.backend.domain.users.user.dto.response.UserSearchGetDto;
import com.lemon.backend.global.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.lemon.backend.global.response.CommonResponseEntity.getResponseEntity;

@Tag(name = "Sketchbook 컨트롤러", description = "Sketchbook Controller API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sketchbooks")
public class SketchbookController {

    private final SketchbookService sketchbookService;

    @Operation(summary = "스케치북 목록 조회", description = "스케치북 목록 조회 / userId 필요")
    @GetMapping("/list")
    public ResponseEntity<?> getSketchList(Authentication authentication){
        Integer userId = (Integer) authentication.getPrincipal();
        List<SketchbookGetSimpleDto> sketchList = sketchbookService.getSketchList(userId);
        return getResponseEntity(SuccessCode.OK, sketchList);
    }

    @Operation(summary = "스케치북 선택 조회", description = "스케치북 선택 조회 / sketchbookId 필요")
    @GetMapping("/public/simple/{sketchbookUuid}")
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
    @GetMapping("/public/{sketchbookUuid}/pagination")
    public ResponseEntity<SketchbookDetailPageDto> getSketchSelect3(
            @Parameter(description = "스케치북 ID", required = true) @PathVariable(value = "sketchbookUuid") String sketchId,
            @Parameter(description = "페이지 정보", required = true) Pageable pageable) {
        SketchbookDetailPageDto detailPageDto = sketchbookService.getSketchSelect3(sketchId, pageable);
        return ResponseEntity.ok(detailPageDto);
    }

    @Operation(summary = "스케치북 전체 조회", description = "스케치북 전체 목록 조회")
    @GetMapping("/public/all")
    public ResponseEntity<?> getAllSketchList(){
        List<SketchbookGetAllDto> sketchAll = sketchbookService.getSketchAll();
        return getResponseEntity(SuccessCode.OK, sketchAll);
    }

    @Operation(summary = "스케치북 생성", description = "스케치북 생성 / userId 필요")
    @PostMapping
    public ResponseEntity<?> createSketch(Authentication authentication, @Valid @RequestBody SketchbookCreateDto sketchDto){
        Integer userId = (Integer) authentication.getPrincipal();
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

    @Operation(summary = "스케치북 검색", description = "스케치북 이름으로 검색합니다.")
    @GetMapping("/public/search/{sketchbookName}")
    public ResponseEntity<?> searchNickname(@PathVariable("sketchbookName") String nickname){
        Optional<List<SketchbookSearchGetDto>> searchList = sketchbookService.searchSkechbook(nickname);
        return getResponseEntity(SuccessCode.OK, searchList);
    }

    @Operation(summary = "대표 스케치북 수정", description = "대표 스케치북 수정 / sketchbookId 필요")
    @PutMapping("/representative/{changeRepresentSketchbookId}")
    public ResponseEntity<?> updateSketch(Authentication authentication, @PathVariable(value = "changeRepresentSketchbookId")Long sketchbookId){
        Integer userId = (Integer) authentication.getPrincipal();
        sketchbookService.changeRepresent(userId, sketchbookId);
        return getResponseEntity(SuccessCode.OK);
    }

    @GetMapping("/friend")
    public ResponseEntity<?> getFriendSketchbook(@RequestParam Integer userId){
        List<SketchbookGetSimpleDto> list = sketchbookService.getFriendSketchList(userId);
        return getResponseEntity(SuccessCode.OK, list);
    }

    @PutMapping("/changepublic")
    public ResponseEntity<?> changePublicSketchbook(@RequestParam Long skechbookId){
        Boolean changePublic = sketchbookService.changePublic(skechbookId);
        return getResponseEntity(SuccessCode.OK, changePublic);
    }
}
