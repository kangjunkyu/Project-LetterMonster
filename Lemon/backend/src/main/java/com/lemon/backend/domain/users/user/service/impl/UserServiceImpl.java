package com.lemon.backend.domain.users.user.service.impl;

import com.lemon.backend.domain.users.kakao.dto.KakaoProfile;
import com.lemon.backend.domain.users.user.entity.Adjective;
import com.lemon.backend.domain.users.user.entity.Noun;
import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.global.jwt.JwtTokenProvider;
import com.lemon.backend.global.jwt.TokenResponse;
import com.lemon.backend.global.redis.RefreshToken;
import com.lemon.backend.global.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Users createKakaoUser(KakaoProfile profile, Social social) {
        String nickname = makeNickname();
        long sameNicknameCount = userRepository.countByNickname(nickname);

        Users newUser = Users.builder()
                .nickname(nickname)
                .nicknameTag(String.valueOf(sameNicknameCount + 1))
                .kakaoId(profile.getId())
                .provider(Social.KAKAO)
                .build();

        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public TokenResponse recreateToken(String bearerToken) {
        //유효성 검사
        String refreshToken = jwtTokenProvider.resolveToken(bearerToken);
        jwtTokenProvider.validateToken(refreshToken);

        //토큰 재발급
        Integer userId = jwtTokenProvider.getSubject(refreshToken);
        TokenResponse tokenResponse = jwtTokenProvider.createToken(userId);
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
}
