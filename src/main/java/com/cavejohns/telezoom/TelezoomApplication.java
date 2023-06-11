package com.cavejohns.telezoom;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import com.cavejohns.telezoom.utils.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TelezoomApplication {

	private static final String TAG = "TelezoomApplication";

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TelezoomApplication.class, args);
		TelegramZoomBot bot = context.getBean(TelegramZoomBot.class);
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e) {
			Log.e(TAG, e.getMessage());
		}
	}
}
