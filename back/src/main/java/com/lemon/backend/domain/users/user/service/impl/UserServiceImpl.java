package com.lemon.backend.domain.users.user.service.impl;

import com.lemon.backend.domain.sketchbook.dto.requestDto.SketchbookCreateDto;
import com.lemon.backend.domain.sketchbook.entity.Sketchbook;
import com.lemon.backend.domain.sketchbook.repository.SketchbookRepository;
import com.lemon.backend.domain.users.user.dto.request.ChangeNicknameRequest;
import com.lemon.backend.domain.users.user.dto.response.ChangeNicknameResponse;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.domain.users.user.dto.response.UserSearchAndFriendResponse;
import com.lemon.backend.domain.users.user.dto.response.UserSearchGetDto;
import com.lemon.backend.domain.users.user.entity.*;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.global.badWord.BadWordFilterUtil;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.lemon.backend.global.jwt.JwtTokenProvider;
import com.lemon.backend.global.jwt.TokenResponse;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfo;
import com.lemon.backend.global.redis.entity.RefreshToken;
import com.lemon.backend.global.redis.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Random random = new Random();
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final SketchbookRepository sketchbookRepository;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public String makeNickname() {
        Adjective randomAdjective = Adjective.values()[random.nextInt(Adjective.values().length)];
        Noun randomNoun = Noun.values()[random.nextInt(Noun.values().length)];

        return randomAdjective.toString() + " " + randomNoun.toString();
    }

    private long getSameSketchbookLastNumber(String name) {
        Optional<String> highestTagOpt = sketchbookRepository.findHighestSketchbookTagByName(name);
        long sameSketchbookLastNumber = highestTagOpt.map(tag -> Long.parseLong(tag) + 1).orElse(1L);
        return sameSketchbookLastNumber;
    }

    public Users createUser(OAuth2UserInfo userInfo, Social social) {
        String nickname = makeNickname();
        long sameNicknameLastNumber = getSameNicknameLastNumber(nickname);

        Users newUser = Users.builder()
                .nickname(nickname)
                .nicknameTag(String.valueOf(sameNicknameLastNumber))
                .providerId(userInfo.getId())
                .provider(social)
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(newUser);

        long sameSketchbookLastNumber = getSameSketchbookLastNumber(nickname);
        String uuid = UUID.randomUUID().toString();
        String sharaLink = baseUrl + "/sketchbook/" + uuid;
        boolean isRepresent = !sketchbookRepository.existsRepresentSketchbook(newUser.getId());

        Sketchbook sketchbook = Sketchbook.builder()
                .name(nickname)
                .users(newUser)
                .shareLink(sharaLink)
                .sketchbookUuid(uuid)
                .tag(String.valueOf(sameSketchbookLastNumber))
                .isRepresent(isRepresent)
                .build();

        sketchbookRepository.save(sketchbook);

        return newUser;
    }

    private long getSameNicknameLastNumber(String nickname) {
        Optional<String> highestTagOpt = userRepository.findHighestNicknameTagByNickname(nickname);
        long sameNicknameLastNumber = highestTagOpt.map(tag -> Long.parseLong(tag) + 1).orElse(1L);
        return sameNicknameLastNumber;
    }

    @Override
    public TokenResponse recreateToken(String bearerToken) {
        //유효성 검사
        String refreshToken = jwtTokenProvider.resolveToken(bearerToken);
        jwtTokenProvider.validateToken(refreshToken);

        Integer userId = jwtTokenProvider.getSubject(refreshToken);

        //레디스에서 리프레시토큰 가져오기
        RefreshToken redisRefreshToken = refreshTokenRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.INVALID_AUTH_TOKEN)
        );

        //레디스에서 가져온 리프레시 토큰과 비교
        if (redisRefreshToken == null || !redisRefreshToken.getToken().equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }

        //헤더에 들어온 리프레시 토큰을 블랙리스트에 추가
        jwtTokenProvider.addTokenIntoBlackList(refreshToken);

        TokenResponse tokenResponse = jwtTokenProvider.createToken(userId, Role.ROLE_USER.name());
        saveRefreshTokenIntoRedis(userId, tokenResponse.getRefreshToken());

        return tokenResponse;
    }

    @Override
    public void saveRefreshTokenIntoRedis(Integer userId, String token){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(userId);
        refreshToken.setToken(token);
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void logout(Integer userId) {
        deleteRefreshToken(userId);
    }

    @Override
    @Transactional
    public ChangeNicknameResponse changeNickname(Integer userId, ChangeNicknameRequest request) {
        BadWordFilterUtil badWordFilterUtil = new BadWordFilterUtil("☆");
        if(badWordFilterUtil.checkBadWord(request.getNickname())) throw new CustomException(ErrorCode.CANT_USING_BAD_WORD);
        long sameNicknameLastNumber = getSameNicknameLastNumber(request.getNickname());
        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        userRepository.changeNickname(user, request.getNickname(), String.valueOf(sameNicknameLastNumber));
        return ChangeNicknameResponse.builder()
                .nickname(request.getNickname())
                .nicknameTag(sameNicknameLastNumber).build();
    }

    @Override
    @Transactional
    public void withdrawUser(Integer userId) {
        userRepository.deleteById(userId);
        deleteRefreshToken(userId);
    }

    @Override
    public UserGetDto getUserInfo(Integer userId) {
        Users user = userRepository.findById(userId).get();
        return UserGetDto.builder().nickname(user.getNickname()).nicknameTag(user.getNicknameTag())
                .isLanguageSet(user.getIsLanguage()).build();
    }

    @Override
    public List<UserSearchGetDto> searchNickname(String nickName){
        List<UserSearchGetDto> list =  userRepository.findUsersByNickName(nickName);
        return list;
    }

    private void deleteRefreshToken(Integer userId) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(userId);

        refreshTokenOptional.ifPresent(refreshToken -> {
            //리프레시 토큰 삭제
            refreshTokenRepository.delete(refreshToken);

            //리프레시 토큰을 블랙리스트에 추가
            jwtTokenProvider.addTokenIntoBlackList(refreshToken.getToken());
        });
    }

    @Override
    public List<UserSearchAndFriendResponse> userSearchUserByNickname(Integer userId, String nickname){
        List<UserSearchAndFriendResponse> list = userRepository.findUserAndFriend(userId, nickname);

        return list;
    }
}
