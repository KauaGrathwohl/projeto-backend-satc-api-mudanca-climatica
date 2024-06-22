package com.satc.projeto.mudancaclimatica.controllers;

import com.satc.projeto.mudancaclimatica.dto.InfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    @GetMapping
    public InfoDto getProjectInfo() {
        return new InfoDto();
    }
}
