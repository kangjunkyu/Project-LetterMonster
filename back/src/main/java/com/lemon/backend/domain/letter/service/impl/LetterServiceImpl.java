package com.lemon.backend.domain.letter.service.impl;

import com.lemon.backend.domain.characters.entity.CharacterMotion;
import com.lemon.backend.domain.characters.repository.CharacterMotionRepository;
import com.lemon.backend.domain.characters.repository.CharacterRepository;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterGetRecentListDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterCreateDto;
import com.lemon.backend.domain.letter.dto.requestDto.LetterReplyResponse;
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
    public List<LetterGetRecentListDto> getLetterThree(Integer userId) {
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

        Sketchbook sketchbook = sketchbookRepository.findById(sketchbookCharacterMotion.getSketchbook().getId()).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));

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

        letterRepository.save(letter);

        if (receiver.getNotificationToken() != null && !receiver.getNotificationToken().equals("null")) {

            Notification notification = Notification.builder()
                    .receiver(receiver)
                    .type(1)
                    .friendName(sender.getNickname())
                    .friendTag(sender.getNicknameTag())
                    .sketchbookName(sketchbook.getName())
                    .sketchbookTag(sketchbook.getTag())
                    .sketchbookUuid(sketchbook.getSketchbookUuid())
                    .build();

            String body = null;

            if (letter.getSender() != null) {
                body = "[ " + letter.getSender().getNickname() + " ] 님에게 편지가 도착했어요";
                notificationRepository.save(notification);
            }

            String title = "LEMON";

            try {
                if (!notificationService.sendNotification(receiver.getNotificationToken(), title, body, sketchbook.getSketchbookUuid())) {
                    System.out.println("Notification failed to send, but letter was created.");
                }
            } catch (Exception e) {
                System.out.println("Failed to send notification due to: " + e.getMessage());
            }
        }

        return letter.getId();
    }

    @Override
    public Long replyLetter(Integer userId, LetterReplyResponse letterDto){

        Sketchbook findsketchbook = sketchbookRepository.findRepresentSkechbook(letterDto.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));

        System.out.println(findsketchbook);
        SketchbookCharacterMotion sketchbookCharacterMotion = sketchbookRepository.findByCharacterMotionAndSketchbook(findsketchbook.getId(), letterDto.getCharacterMotionId())
                .orElseGet(() -> {
                    Sketchbook sketchbook = sketchbookRepository.findById(findsketchbook.getId()).orElseThrow(() -> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
                    CharacterMotion characterMotion = characterMotionRepository.findById(letterDto.getCharacterMotionId()).orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND));
                    SketchbookCharacterMotion newSketchbookCharacterMotion = SketchbookCharacterMotion.builder()
                            .sketchbook(sketchbook)
                            .characterMotion(characterMotion)
                            .build();
                    return sketchCharacterMotionRepository.save(newSketchbookCharacterMotion);
                });

        Users sender = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Users receiver = userRepository.findById(sketchbookCharacterMotion.getSketchbook().getUsers().getId()).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));

        BadWordFilterUtil badWordFilterUtil = new BadWordFilterUtil("☆");
        String content = badWordFilterUtil.change(letterDto.getContent());
        Letter letter = Letter.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .sketchbookCharacterMotion(sketchbookCharacterMotion)
                .build();

        letterRepository.save(letter);

        if (receiver.getNotificationToken() != null && !receiver.getNotificationToken().equals("null")) {

            Notification notification = Notification.builder()
                    .receiver(receiver)
                    .type(1)
                    .friendName(sender.getNickname())
                    .friendTag(sender.getNicknameTag())
                    .sketchbookName(findsketchbook.getName())
                    .sketchbookTag(findsketchbook.getTag())
                    .sketchbookUuid(findsketchbook.getSketchbookUuid())
                    .build();

            String body = null;

            if (letter.getSender() != null) {
                body = "[ " + letter.getSender().getNickname() + " ] 님에게 답장이 도착했어요";
                notificationRepository.save(notification);
            }

            String title = "LEMON";

            try {
                if (!notificationService.sendNotification(receiver.getNotificationToken(), title, body, findsketchbook.getSketchbookUuid())) {
                    System.out.println("Notification failed to send, but letter was created.");
                }
            } catch (Exception e) {
                System.out.println("Failed to send notification due to: " + e.getMessage());
            }
        }

        return letter.getId();

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

        Sketchbook sketchbook = sketchbookRepository.findById(sketchbookCharacterMotion.getSketchbook().getId()).orElseThrow(()-> new CustomException(ErrorCode.SKETCHBOOK_NOT_FOUND));
        
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
                .friendTag("비회원")
                .sketchbookName(sketchbook.getName())
                .sketchbookTag(sketchbook.getTag())
                .sketchbookUuid(sketchbook.getSketchbookUuid())
                .build();

        String body = null;

        if (letter.getSender() == null) {
            body = "[ 비회원 ] 으로부터 편지가 도착했어요";
            notificationRepository.save(notification); // 알림 저장

            String title = "LEMON";
            try {
                if (!notificationService.sendNotification(receiver.getNotificationToken(), title, body, sketchbook.getSketchbookUuid())) {
                    // 로그 기록 또는 알림 실패 처리
                    System.out.println("Notification sending failed: User notification token might be missing or invalid.");
                }
            } catch (Exception e) {
                // 예외 로깅
                System.out.println("Error sending notification: " + e.getMessage());
            }
        }

        LetterCreateResponse response = new LetterCreateResponse();
        response.setLetterId(letterRepository.save(letter).getId());
        return response;
    }


    @Transactional
    @Override
    public void deleteLetter(Integer userId, Long letterId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));

        Letter letter = letterRepository.findById(letterId).orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND));

        if (!letter.getReceiver().getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        } else {
            letterRepository.delete(letter);
        }
    }

    @Transactional
    @Override
    public Boolean changePublicStatus(Integer userId, Long letterId) {
        Letter letter = letterRepository.findById(letterId).orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND));
        if (!letter.getReceiver().getId().equals(userId)) {
            new CustomException(ErrorCode.INVALID_ACCESS);
        }
        letter.changePublic();
        letterRepository.save(letter);
        return letter.isPublic();
    }
}
