package com.lemon.backend.domain.sketchbook;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookUpdateDto;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.sketchbook.service.impl.SketchbookServiceImpl;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreateSketchbookTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SketchbookRepository sketchbookRepository;

    @InjectMocks
    private SketchbookServiceImpl sketchbookService;

    @Test
    public void testCreateSketchbook() {
        // Arrange
        Integer userId = 1;
        SketchbookCreateDto sketchDto = SketchbookCreateDto.builder()
                .name("Test Sketchbook")
                .build();
        Users user = Users.builder().id(userId).build();
        Sketchbook sketchbook = Sketchbook.builder()
                .id(1L)  // Explicitly setting an ID for the sketchbook
                .name("Test Sketchbook")
                .users(user)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(sketchbookRepository.save(any(Sketchbook.class))).thenReturn(sketchbook);

        // Act
        Long sketchbookId = sketchbookService.createSketchbook(userId, sketchDto);

        // Assert
        assertNotNull(sketchbookId);  // Changed to assertNotNull to explicitly check for non-null value
        verify(userRepository, times(1)).findById(userId);
        verify(sketchbookRepository, times(1)).save(any(Sketchbook.class));
    }

    @Test
    public void testCreateSketchbookUserNotFound() {
        // Arrange
        Integer userId = 1;
        SketchbookCreateDto sketchDto = SketchbookCreateDto.builder()
                .name("Test Sketchbook")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomException.class, () -> sketchbookService.createSketchbook(userId, sketchDto));
    }

    @Test
    public void testUpdateSketchbookSuccess() {
        // Arrange
        Long sketchbookId = 1L;
        Sketchbook existingSketchbook = Sketchbook.builder()
                .id(sketchbookId)
                .name("Original Sketchbook")
                .build();
        SketchbookUpdateDto updateDto = SketchbookUpdateDto.builder()
                .name("Updated Sketchbook")
                .build();

        when(sketchbookRepository.findById(sketchbookId)).thenReturn(Optional.of(existingSketchbook));

        // Act
        sketchbookService.updateSketchbook(sketchbookId, updateDto);

        // Assert
        verify(sketchbookRepository, times(1)).findById(sketchbookId);
        verify(sketchbookRepository, times(1)).save(existingSketchbook); // Ensure the existing sketchbook is saved with updated data
        assertEquals("Updated Sketchbook", existingSketchbook.getName());
    }


    @Test
    public void testUpdateSketchbookNotFound() {
        Long sketchbookId = 1L;
        SketchbookUpdateDto sketchDto = SketchbookUpdateDto.builder()
                .name("test")
                .build();

        when(sketchbookRepository.findById(sketchbookId)).thenReturn(Optional.empty());

        // CustomException을 기대
        assertThrows(CustomException.class, () -> sketchbookService.updateSketchbook(sketchbookId, sketchDto));
    }

    @Test
    public void testDeleteSketchbookSuccess() {
        // Arrange
        Long sketchbookId = 1L;
        Sketchbook sketchbook = Sketchbook.builder()
                .id(sketchbookId)
                .name("Test Sketchbook")
                .build();

        when(sketchbookRepository.findById(sketchbookId)).thenReturn(Optional.of(sketchbook));
        doNothing().when(sketchbookRepository).delete(sketchbook);

        // Act
        sketchbookService.deleteSketchbook(sketchbookId);

        // Assert
        verify(sketchbookRepository).delete(sketchbook);
    }

    @Test
    public void testDeleteSketchbookNotFound() {
        // Arrange
        Long sketchbookId = 1L;

        when(sketchbookRepository.findById(sketchbookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomException.class, () -> sketchbookService.deleteSketchbook(sketchbookId));
    }

}
