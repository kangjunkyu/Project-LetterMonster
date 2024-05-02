package com.lemon.backend.domain.letter.service.impl;

import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.repository.CharacterMotionRepository;
import com.lemon.backend.domain.characters.repository.CharacterRepository;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetRecentListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterCreateDto;
import com.lemon.backend.domain.letter.dto.responseDto.LetterCreateResponse;
import com.lemon.backend.domain.letter.entity.Letter;
import com.lemon.backend.domain.letter.repository.LetterRepository;
import com.lemon.backend.domain.letter.service.LetterService;
import com.lemon.backend.domain.notification.entity.Notification;
import com.lemon.backend.domain.notification.repository.NotificationRepository;
import com.lemon.backend.domain.notification.service.NotificationService;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.entity.SketchbookCharacterMotion;
import com.lemon.backend.domain.sketchbook.repository.SketchCharacterMotionRepository;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.global.badWord.BadWordFilterUtil;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class LetterServiceImpl implements LetterService {

    private final CharacterRepository characterRepository;
    private final SketchbookRepository sketchbookRepository;
    private final CharacterMotionRepository characterMotionRepository;
    private final SketchCharacterMotionRepository sketchCharacterMotionRepository;
    private final LetterRepository letterRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Override
    public List<LetterGetListDto> getLetterList(Long sketchbookId) {
        return letterRepository.getLetterList(sketchbookId).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
    }

    @Override
    public List<LetterGetRecentListDto> getLetterThree(Integer userId){
        return letterRepository.getLetterThree(userId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_ACCESS));
    }

    @Transactional
    @Override
    public Long createLetter(Integer senderId, LetterCreateDto letterDto) {
        SketchbookCharacterMotion sketchbookCharacterMotion = sketchbookRepository.findByCharacterMotionAndSketchbook(letterDto.getSketchbookId(), letterDto.getCharacterMotionId())
                .orElseGet(() -> {
                    Sketchbook sketchbook = sketchbookRepository.findById(letterDto.getSketchbookId()).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
                    CharacterMotion characterMotion = characterMotionRepository.findById(letterDto.getCharacterMotionId()).orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND));
                    SketchbookCharacterMotion newSketchbookCharacterMotion = SketchbookCharacterMotion.builder()
                            .sketchbook(sketchbook)
                            .characterMotion(characterMotion)
                            .build();
                    return sketchCharacterMotionRepository.save(newSketchbookCharacterMotion);
                });

        Users sender = userRepository.findById(senderId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Users receiver = userRepository.findById(sketchbookCharacterMotion.getSketchbook().getUsers().getId()).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));

        BadWordFilterUtil badWordFilterUtil = new BadWordFilterUtil("☆");
        String content = badWordFilterUtil.change(letterDto.getContent());
        Letter letter = Letter.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .sketchbookCharacterMotion(sketchbookCharacterMotion)
                .build();

        Notification notification = Notification.builder()
                .receiver(receiver)
                .type(1)
                .friendName(sender.getNickname())
                .build();

        String body = null;

        if(letter.getSender() != null){
            body = "[ " + letter.getSender().getNickname() + " ] 님에게 편지가 도착했어요";
            notificationRepository.save(notification);
        }

        String title = "LEMON";
        if(!notificationService.sendNotification(receiver.getNotificationToken(), title, body)){
            throw new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION);
        }

        return letterRepository.save(letter).getId();
    }

    @Override
    public LetterCreateResponse createAnonymousLetter(LetterCreateDto letterDto) {
        SketchbookCharacterMotion sketchbookCharacterMotion = sketchbookRepository.findByCharacterMotionAndSketchbook(letterDto.getSketchbookId(), letterDto.getCharacterMotionId())
                .orElseGet(() -> {
                    Sketchbook sketchbook = sketchbookRepository.findById(letterDto.getSketchbookId()).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
                    CharacterMotion characterMotion = characterMotionRepository.findById(letterDto.getCharacterMotionId()).orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND));
                    SketchbookCharacterMotion newSketchbookCharacterMotion = SketchbookCharacterMotion.builder()
                            .sketchbook(sketchbook)
                            .characterMotion(characterMotion)
                            .build();
                    return sketchCharacterMotionRepository.save(newSketchbookCharacterMotion);
                });

        Users receiver = userRepository.findById(sketchbookCharacterMotion.getSketchbook().getUsers().getId()).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));

        BadWordFilterUtil badWordFilterUtil = new BadWordFilterUtil("☆");
        String content = badWordFilterUtil.change(letterDto.getContent());
        Letter letter = Letter.builder()
                .receiver(receiver)
                .content(content)
                .sketchbookCharacterMotion(sketchbookCharacterMotion)
                .build();

        Notification notification = Notification.builder()
                .receiver(receiver)
                .type(1)
                .friendName("비회원")
                .build();

        String body = null;

        if(letter.getSender() != null){
            body = "[ 비회원 ] 으로부터 편지가 도착했어요";
            notificationRepository.save(notification);
        }

        String title = "LEMON";
        if(!notificationService.sendNotification(receiver.getNotificationToken(), title, body)){
            throw new CustomException(ErrorCode.NOT_FOUND_NOTIFICATION);
        }

        LetterCreateResponse response = new LetterCreateResponse();
        response.setLetterId(letterRepository.save(letter).getId());
        return response;
    }


    @Transactional
    @Override
    public void deleteLetter(Long letterId) {
        letterRepository.deleteById(letterId);
    }
}
