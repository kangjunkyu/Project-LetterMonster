package com.lemon.backend.domain.users.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoProfile {

    public String id;
    @JsonProperty("connected_at")
    public String connectedAt;
    public Properties properties;
    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;

    @Data
    public class Properties {
        public String nickname;
        @JsonProperty("profile_image")
        public String profileImage; //이미지 경로 필드1
        @JsonProperty("thumbnail_image")
        public String thumbnailImage;
    }

    @Data
    public class KakaoAccount {
        @JsonProperty("profile_nickname_needs_agreement")
        public Boolean profileNicknameNeedsAgreement;
        @JsonProperty("profile_image_needs_agreement")
        public Boolean profileImageNeedsAgreement;
        public Profile profile;
        @JsonProperty("has_email")
        public Boolean hasEmail;
        @JsonProperty("email_needs_agreement")
        public Boolean emailNeedsAgreement;
        @JsonProperty("is_email_valid")
        public Boolean isEmailValid;
        @JsonProperty("is_email_verified")
        public Boolean isEmailVerified;
        public String email;

        @Data
        public class Profile {
            public String nickname;
            @JsonProperty("thumbnail_image_url")
            public String thumbnailImageUrl;
            @JsonProperty("profile_image_url")
            public String profileImageUrl; //이미지 경로 필드2
            @JsonProperty("is_default_image")
            public Boolean isDefaultImage;
        }
    }
}