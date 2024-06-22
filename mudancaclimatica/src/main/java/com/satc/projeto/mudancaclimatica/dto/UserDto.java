package com.satc.projeto.mudancaclimatica.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {

    private String id;
    private String username;
    private String role;
}