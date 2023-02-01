package com.chatbot.group;

import com.chatbot.group.repository.DrinkRepository;
import com.chatbot.group.service.DrinkService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.log4j.Logger;


/**
 * Задача: написать чатбота для Telegram. Сохранять информацию о пользователях в БД и показывать статистику. Так же
 * сохрянять очки пользователей в БД и по вызову показывать их в лидере топе.
 */

public class Main {
    private final static Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        // Create your bot passing the token received from @BotFather
        TelegramBot bot = new TelegramBot(System.getenv("BOT_KEY"));
        DrinkRepository drinkRepository = new DrinkRepository();
        DrinkService drinkService = new DrinkService(drinkRepository);
        // Register for updates
        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                if(update.message() != null) {
                    long groupId = update.message().chat().id();
                    String userId = String.valueOf(update.message().from().id());
                    log.info(update);
                    SendResponse response = bot.execute(new SendMessage(groupId, drinkService.drink(userId)));
                    log.info(response);
                }

            });
            // return id of last processed update or confirm them all
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}