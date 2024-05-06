package com.lemon.backend.domain.letter.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.repository.CharacterMotionRepository;
import com.lemon.backend.domain.letter.dto.requestDto.LetterCreateDto;
import com.lemon.backend.domain.letter.entity.Letter;
import com.lemon.backend.domain.letter.repository.LetterRepository;
import com.lemon.backend.domain.letter.service.impl.LetterServiceImpl;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.sketchbook.repository.SketchCharacterMotionRepository;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreateLetterTest {

    @Mock
    private LetterRepository letterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SketchbookRepository sketchbookRepository;

    @Mock
    private CharacterMotionRepository characterMotionRepository;

    @Mock
    private SketchCharacterMotionRepository sketchCharacterMotionRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private LetterServiceImpl letterService;

    @Test
    public void testCreateLetter() {
        // Arrange
        Integer senderId = 1;
        Integer receiverId = 2;
        Long letterId = 1L;
        Long createLetterId = 2L;
        Long sketchId = 2L;
        Long characterMotionId = 3L;
        Long sketchCharacterMotionId = 4L;

        Users sender = Users.builder().id(senderId).build();
        Users receiver = Users.builder().id(receiverId).build();

        Sketchbook sketchbook = Sketchbook.builder()
                .id(sketchId)
                .name("Test Sketchbook")
                .users(receiver)
                .build();

        CharacterMotion characterMotion = CharacterMotion.builder()
                .id(characterMotionId)
                .build();

        SketchbookCharacterMotion sketchbookCharacterMotion = SketchbookCharacterMotion.builder()
                .id(sketchCharacterMotionId)
                .characterMotion(characterMotion)
                .sketchbook(sketchbook)
                .build();

        Letter letter = Letter.builder()
                .id(letterId)
                .content("test")
                .sender(sender)
                .receiver(receiver)
                .sketchbookCharacterMotion(sketchbookCharacterMotion)
                .build();

        LetterCreateDto letterCreateDto = LetterCreateDto.builder()
                .id(createLetterId)
                .characterMotionId(characterMotionId)
                .sketchbookId(sketchId)
                .content("test")
                .build();

        when(userRepository.findById(senderId)).thenReturn(java.util.Optional.of(sender));
        when(sketchbookRepository.findById(sketchId)).thenReturn(java.util.Optional.of(sketchbook));
        when(characterMotionRepository.findById(characterMotionId)).thenReturn(java.util.Optional.of(characterMotion));
        when(sketchCharacterMotionRepository.save(any(SketchbookCharacterMotion.class))).thenReturn(sketchbookCharacterMotion);
        when(letterRepository.save(any(Letter.class))).thenReturn(letter);

        Long createdLetterId = letterService.createLetter(senderId, letterCreateDto);

        assertNotNull(createdLetterId);
        verify(letterRepository).save(any(Letter.class));
    }

    @Test
    public void testCreateLetterNotFound() {
        // Arrange
        Integer senderId = 1;
        Long sketchId = 2L;
        Long characterMotionId = 3L;
        Long createLetterId = 2L;

        Users sender = Users.builder().id(senderId).build();

        // 이 테스트에서는 스케치북이나 캐릭터 모션을 찾을 수 없도록 설정합니다.
//        when(userRepository.findById(senderId)).thenReturn(java.util.Optional.of(sender));
        when(sketchbookRepository.findById(sketchId)).thenReturn(Optional.empty());
//        when(characterMotionRepository.findById(characterMotionId)).thenReturn(Optional.empty());

        LetterCreateDto letterCreateDto = LetterCreateDto.builder()
                .id(createLetterId)
                .characterMotionId(characterMotionId)
                .sketchbookId(sketchId)
                .content("test")
                .build();

        // Act & Assert
        assertThrows(CustomException.class, () -> letterService.createLetter(senderId, letterCreateDto));
    }


    @Test
    public void testDeleteLetter(){
        Long letterId = 1L;
        Letter letter = Letter.builder().id(letterId).content("test").build();

        when(letterRepository.findById(letterId)).thenReturn(java.util.Optional.of(letter));
        doNothing().when(letterRepository).delete(letter);

        letterService.deleteLetter(letterId);

        verify(letterRepository).delete(letter);
    }
    @Test
    public void testDeleteLetterNotFound() {
        Long letterId = 1L;

        when(letterRepository.findById(letterId)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> letterService.deleteLetter(letterId));
    }

    // 추가적인 테스트들은 비슷한 방식으로 진행될 수 있습니다.
}
