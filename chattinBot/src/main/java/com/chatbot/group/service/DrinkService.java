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

    public String drink(String userId, String userName, int timestamp) {
        User user = drinkRepository.findUser(userId);
        double amount = random.nextInt(-50, 200) / 10;
        double sum = amount;
        boolean showAd = random.nextBoolean();
        int lastDrankAt = user.getTimestamp();
        int elapsed = (int) ((System.currentTimeMillis() / 1000) - lastDrankAt);
        if (user.getUserId() != null) {
            if (elapsed > 14400) {
                sum = amount + user.getAmount();
                user.setAmount(sum);
                user.setTimestamp(timestamp);
                drinkRepository.updateUser(user.getUserId(), user.getAmount(), user.getTimestamp());
            } else {
                return "@" + userName + ", зачекай ще " + ((14400 - elapsed)/60) + " хвилин";
            }

        } else {
            drinkRepository.saveUser(userId, userName, amount, timestamp);
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

    public String getAmount(String userId) {
        User user = drinkRepository.findUser(userId);
        if (user.getUserId() != null) {
            return "Ти випив " + user.getAmount() + " літрів";
        } else {
            return "Ти блять хто?";
        }
    }

    public String getTop() {
        return drinkRepository.getTop();
    }

    public String getPublicTop() {
        return drinkRepository.getPublicTop();
    }
}
