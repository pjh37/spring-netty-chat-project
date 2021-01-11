package com.netty.practice.config.auth;

public enum OAuthRegistration {
    GOOGLE("google"),
    GITHUB("github");

    String registrationId;

    OAuthRegistration(String registrationId){
        this.registrationId=registrationId;
    }
}
