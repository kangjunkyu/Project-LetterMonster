package com.lemon.backend.domain.letter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.repository.CharacterMotionRepository;
import com.lemon.backend.domain.letter.dto.requestDto.LetterCreateDto;
import com.lemon.backend.domain.letter.entity.Letter;
import com.lemon.backend.domain.letter.repository.LetterRepository;
import com.lemon.backend.domain.letter.service.impl.LetterServiceImpl;
import com.lemon.backend.domain.notification.entity.Notification;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.NotificationService;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
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
public class LetterServiceTest {

    @Mock
    private LetterRepository letterRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SketchbookRepository sketchbookRepository;

    @Mock
    private CharacterMotionRepository characterMotionRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private LetterServiceImpl letterService;

//    @Mock
//    private NotificationServiceImpl notificationService;
    @Mock
    private NotificationService notificationService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);  // 목 객체 초기화
//    }


    @Test
    public void testCreateLetter() {
        // Arrange
        Integer senderId = 1;
        Integer receiverId = 2;
        Long sketchId = 2L;
        Long characterMotionId = 3L;
        String notificationToken = "notificationToken";

        Users sender = Users.builder().id(senderId).build();
        Users receiver = Users.builder().id(receiverId).notificationToken(notificationToken).build();

        Sketchbook sketchbook = Sketchbook.builder()
                .id(sketchId)
                .name("Test Sketchbook")
                .users(receiver)
                .build();

        CharacterMotion characterMotion = CharacterMotion.builder()
                .id(characterMotionId)
                .build();

        SketchbookCharacterMotion sketchbookCharacterMotion = SketchbookCharacterMotion.builder()
                .characterMotion(characterMotion)
                .sketchbook(sketchbook)
                .build();

        LetterCreateDto letterCreateDto = LetterCreateDto.builder()
                .content("test")
                .characterMotionId(characterMotionId)
                .sketchbookId(sketchId)
                .build();

        Letter letter = Letter.builder()
                .id(1L)
                .sketchbookCharacterMotion(sketchbookCharacterMotion)
                .sender(sender)
                .receiver(receiver)
                .content("test")
                .build();

        Notification notification = Notification.builder()
                .id(1L)
                .type(1)
                .receiver(receiver)
                .friendName("test")
                .isCheck(false)
                .build();

        when(userRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
//        when(sketchbookRepository.findById(sketchId)).thenReturn(Optional.of(sketchbook));
//        when(characterMotionRepository.findById(characterMotionId)).thenReturn(Optional.of(characterMotion));
        when(letterRepository.save(any(Letter.class))).thenReturn(letter);
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        when(sketchbookRepository.findByCharacterMotionAndSketchbook(sketchId, characterMotionId))
                .thenReturn(Optional.of(sketchbookCharacterMotion));
        when(notificationService.sendNotification(anyString(), anyString(), anyString())).thenReturn(true);
        Long createdLetterId = letterService.createLetter(senderId, letterCreateDto);


        assertNotNull(createdLetterId, "Created letter ID should not be null");
        verify(letterRepository).save(any(Letter.class));
        verify(notificationService).sendNotification(anyString(), anyString(), anyString());
    }



//    @Test
//    public void testCreateLetterNotFound() {
//        Integer senderId = 1;
//        Long sketchId = 2L;
//        Long characterMotionId = 3L;
//        Long createLetterId = 2L;
//
//        Users sender = Users.builder().id(senderId).build();
//
////        when(userRepository.findById(senderId)).thenReturn(java.util.Optional.of(sender));
//        when(sketchbookRepository.findById(sketchId)).thenReturn(Optional.empty());
////        when(characterMotionRepository.findById(characterMotionId)).thenReturn(Optional.empty());
//
//        LetterCreateDto letterCreateDto = LetterCreateDto.builder()
//                .id(createLetterId)
//                .characterMotionId(characterMotionId)
//                .sketchbookId(sketchId)
//                .content("test")
//                .build();
//
//        assertThrows(CustomException.class, () -> letterService.createLetter(senderId, letterCreateDto));
//    }


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
}
