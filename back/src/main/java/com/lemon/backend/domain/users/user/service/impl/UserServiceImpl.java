package com.lemon.backend.domain.users.user.service.impl;

import com.lemon.backend.domain.users.user.dto.request.ChangeNicknameRequest;
import com.lemon.backend.domain.users.user.dto.response.ChangeNicknameResponse;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.domain.users.user.dto.response.UserSearchGetDto;
import com.lemon.backend.domain.users.user.entity.*;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.global.exception.CustomException;
import com.lemon.backend.global.exception.ErrorCode;
import com.lemon.backend.global.jwt.JwtTokenProvider;
import com.lemon.backend.global.jwt.TokenResponse;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfo;
import com.lemon.backend.global.redis.entity.RefreshToken;
import com.lemon.backend.global.redis.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Random random = new Random();
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String makeNickname() {
        Adjective randomAdjective = Adjective.values()[random.nextInt(Adjective.values().length)];
        Noun randomNoun = Noun.values()[random.nextInt(Noun.values().length)];

        return randomAdjective.toString() + " " + randomNoun.toString();
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
                new CustomException(ErrorCode.INVALID_AUTH_CODE)
        );

        //레디스에서 가져온 리프레시 토큰과 비교
        if (redisRefreshToken == null || !redisRefreshToken.getToken().equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
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
}
