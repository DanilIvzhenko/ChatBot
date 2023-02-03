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

    public String drink(String userId, String userName) {
        User user = drinkRepository.findUser(userId);
        double amount = random.nextInt(-50, 200) / 10;
        double sum = amount;
        boolean showAd = random.nextBoolean();
        if (user.getUserId() != null) {
            sum = amount + user.getAmount();
            user.setAmount(sum);
            drinkRepository.updateUser(user.getUserId(), user.getAmount());
        } else {
            drinkRepository.saveUser(userId, userName, amount);
        }

        if (showAd) {
            return "@" + userName + ", ти випив " + amount + " літрів коли 🥤.\nЗагалом ти випив " + sum + " літрів.\nПідпишись на @geekpartylab";
        } else {
            return "@" + userName + ", ти випив " + amount + " літрів коли 🥤.\nЗагалом ти випив " + sum + " літрів.";
        }

    }

    public String reset(String userId) {
        User user = drinkRepository.findUser(userId);
        if (user.getUserId() != null) {
            drinkRepository.resetUser(user.getUserId());
            return "Скидаю рахунок до 0 для " + user.getUserName();

        } else {
            return "Ти блять хто?";
        }
    }
}
