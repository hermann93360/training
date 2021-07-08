package com.java.training.dto;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class TokenDto {

    private String access_token;
    private String token_type ;
    private String refresh_token ;
    private int expires_in;
    private String scope ;
    private int iat;
    private String sub;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public int getIat() {
        return iat;
    }

    public void setIat(int iat) {
        this.iat = iat;
    }


    public static TokenDto fromClaims(Map<String, Object > map) throws JSONException {
        if (map == null)
            return null;
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccess_token((String) map.get("access_token"));
        tokenDto.setRefresh_token((String) map.get("access_token"));
        tokenDto.setExpires_in((Integer) map.get("expires_in"));
        //tokenDto.setIat((Integer) map.get("iat"));
        byte[] decodedBytes = new byte[0];
        decodedBytes = Base64.getDecoder().decode(tokenDto.getAccess_token().split("\\.")[1]);
        String payload = new String(decodedBytes, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(payload);
        tokenDto.setSub(jsonObject.getString("sub"));
        return tokenDto;
    }
}
