package com.lemon.backend.domain.users;

import com.lemon.backend.domain.users.user.dto.request.ChangeNicknameRequest;
import com.lemon.backend.domain.users.user.dto.response.ChangeNicknameResponse;
import com.lemon.backend.domain.users.user.dto.response.UserGetDto;
import com.lemon.backend.domain.users.user.dto.response.UserSearchGetDto;
import com.lemon.backend.domain.users.user.entity.Social;
import com.lemon.backend.domain.users.user.entity.Users;
import com.lemon.backend.domain.users.user.repository.UserRepository;
import com.lemon.backend.domain.users.user.service.UserService;
import com.lemon.backend.domain.users.user.service.impl.UserServiceImpl;
import com.lemon.backend.global.auth.userinfo.KakaoOAuth2UserInfo;
import com.lemon.backend.global.auth.userinfo.OAuth2UserInfo;
import com.lemon.backend.global.jwt.JwtTokenProvider;
import com.lemon.backend.global.redis.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Integer USER_ID = 1;
    private static final String NICKNAME = "안녕";
    private static final String NICKNAME_TAG = "1";
    private OAuth2UserInfo mockedUserInfo;
    private Users expectedUser;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", USER_ID);
        mockedUserInfo = new KakaoOAuth2UserInfo(attributes);

        expectedUser = Users.builder()
                .id(USER_ID)
                .nickname(NICKNAME)
                .nicknameTag(NICKNAME_TAG)
                .provider(Social.KAKAO)
                .build();

        userService = new UserServiceImpl(userRepository, jwtTokenProvider, refreshTokenRepository);
        lenient().when(userService.createUser(mockedUserInfo, Social.KAKAO)).thenReturn(expectedUser);
    }

    @Test
    public void makeUser_test(){
        Users createdUser = userService.createUser(mockedUserInfo, Social.KAKAO);
        assertThat(createdUser).isNotNull();
    }

    @Test
    public void withdrawUser_test(){
        Users createdUser = userService.createUser(mockedUserInfo, Social.KAKAO);
        userService.withdrawUser(createdUser.getId());

        Optional<Users> deleteUser = userRepository.findById(createdUser.getId());
        assertFalse(deleteUser.isPresent());
    }

    @Test
    public void getUserInfoTest() {
        // given
        Users user = Users.builder()
                .id(USER_ID)
                .nickname(NICKNAME)
                .nicknameTag(NICKNAME_TAG)
                .provider(Social.KAKAO)
                .build();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        // when
        UserGetDto userDto = userService.getUserInfo(USER_ID);

        // then
        assertNotNull(userDto);
        assertEquals(NICKNAME, userDto.getNickname());
        assertEquals(NICKNAME_TAG, userDto.getNicknameTag());
    }
    @Test
    public void changeNicknameTest(){
        // given
        Users user = Users.builder()
                .id(USER_ID)
                .nickname(NICKNAME)
                .nicknameTag(NICKNAME_TAG)
                .provider(Social.KAKAO)
                .build();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        String change = "하이";
        ChangeNicknameRequest request = new ChangeNicknameRequest();
        request.setNickname(change);

        ChangeNicknameResponse response = userService.changeNickname(user.getId(), request);

        assertEquals(change, response.getNickname());
    }

}

