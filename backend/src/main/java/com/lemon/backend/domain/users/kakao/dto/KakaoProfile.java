package com.lemon.backend.domain.users.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {

    public String id;
    @JsonProperty("connected_at")
    public String connectedAt;
}