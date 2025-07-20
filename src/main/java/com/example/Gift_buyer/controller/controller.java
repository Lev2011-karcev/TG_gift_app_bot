package com.example.Gift_buyer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.Gift_buyer.service.giftService;

@RestController
public class controller {
    private final giftService giftService;

    public controller(giftService giftService) {
        this.giftService = giftService;
    }
    @GetMapping("/auth")
    public String saveAuthSession(){
        giftService.loginAndSaveSession();
        return "Сессия сохранена";
    }
    @PostMapping("/buy")
    public void buyGift(@RequestParam int amount, String username){
        giftService.buyGift(amount, username);
    }
}
