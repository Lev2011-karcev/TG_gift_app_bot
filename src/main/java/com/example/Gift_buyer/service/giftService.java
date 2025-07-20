package com.example.Gift_buyer.service;

import com.example.Gift_buyer.parser.parser;
import org.springframework.stereotype.Service;

@Service
public class giftService {
    public void loginAndSaveSession(){
        parser.loginAndSaveSession();
    }
    public void buyGift(int amount, String username){
        parser.buyGiftAndReturnLink(amount, username);
    }
}
