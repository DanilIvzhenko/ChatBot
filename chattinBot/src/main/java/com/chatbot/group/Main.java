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
                if (update.message() != null) {
                    long groupId = update.message().chat().id();
                    String userId = String.valueOf(update.message().from().id());
                    String userName = String.valueOf(update.message().from().username());
                    log.info(update);
                    if (update.message().text() != null) {
                        switch (update.message().text()) {
                            case "/drink", "/drink@alldrink_bot" -> {
                                int timestamp = update.message().date();
                                SendResponse response = bot.execute(new SendMessage(groupId, drinkService.drink(userId, userName, timestamp)));
                                log.info(response);
                            }
                            case "/list", "/list@alldrink_bot" -> {
                                SendResponse response = bot.execute(new SendMessage(groupId, drinkService.getTop()));
                                log.info(response);
                            }
                            case "/public", "/public@alldrink_bot" -> {
                                SendResponse response = bot.execute(new SendMessage(groupId, drinkService.getPublicTop()));
                                log.info(response);
                            }
                            case "/reset", "/reset@alldrink_bot" -> {
                                SendResponse response = bot.execute(new SendMessage(groupId, drinkService.reset(userId)));
                                log.info(response);
                            }
                            case "/help", "/help@alldrink_bot" -> {
                                SendResponse response = bot.execute(new SendMessage(groupId, "Доступні команди:\n" +
                                        "/drink - випити колу\n" +
                                        "/list - демонструвати топ10\n" +
                                        "/public - демонструвати топ100\n" +
                                        "/me - показати сколіки випито літрів коли у вас\n" +
                                        "/reset - зригнути"));
                                log.info(response);
                            }
                            case "/me", "/me@alldrink_bot" -> {
                                SendResponse response = bot.execute(new SendMessage(groupId, drinkService.getAmount(userId)));
                                log.info(response);
                            }
                            case "/start", "/start@alldrink_bot" -> {
                                SendResponse response = bot.execute(new SendMessage(groupId, "Привіт, я бот, " +
                                        "який відстежує кількість випитої коли. Для того щоб почати використовуй " +
                                        "команду /drink , щоб дізнатися більше використовуй команду /help"));
                                log.info(response);
                            }
                        }
                    }
                }
            });

            // return id of last processed update or confirm them all
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}