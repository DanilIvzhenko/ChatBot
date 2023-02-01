package com.chatbot.group.service;

import com.chatbot.group.repository.DrinkRepository;
import com.chatbot.group.repository.User;

import java.util.Random;

public class DrinkService {
    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
        random = new Random();
    }

    private Random random;
    private DrinkRepository drinkRepository;

    public String drink(String userId) {
        User user = drinkRepository.findUser(userId);
        double amount = random.nextInt(-50, 200)/10;
        double sum = amount;
        if (user.getUserId() != null) {
            sum = amount + user.getAmount();
            user.setAmount(sum);
            drinkRepository.updateUser(user.getUserId(), user.getAmount());
        } else {
            drinkRepository.saveUser(userId, amount);
        }
        return "Ти випив " + amount + " літрів коли 🥤. \nЗагалом ти випив " + sum + " літрів.\nПідпишись @geekpartylab";
    }
}
