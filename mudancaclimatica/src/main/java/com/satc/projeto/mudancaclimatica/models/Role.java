package com.satc.projeto.mudancaclimatica.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN(0);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String getAuthority() {
        return ADMIN.name();
    }
}
