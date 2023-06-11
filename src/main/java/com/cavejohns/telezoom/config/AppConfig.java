package com.cavejohns.telezoom.config;

import com.cavejohns.telezoom.bot.TelegramZoomBot;
import com.cavejohns.telezoom.bot.commands_handler.BotHandler;
import com.cavejohns.telezoom.bot.commands_handler.CommandsHandler;
import com.cavejohns.telezoom.data.data_base.dao.UserDao;
import com.cavejohns.telezoom.data.data_base.dao.UserDaoImpl;
import com.cavejohns.telezoom.domain.repository.AuthRepository;
import com.cavejohns.telezoom.domain.repository.AuthRepositoryImpl;
import com.cavejohns.telezoom.domain.repository.ZoomRepository;
import com.cavejohns.telezoom.domain.repository.ZoomRepositoryImpl;
import com.cavejohns.telezoom.domain.use_case.MeetingUseCase;
import com.cavejohns.telezoom.domain.use_case.MeetingUseCaseImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret}")
    private String apiSecret;

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    @Bean
    public BotConfig botConfig() {
        return new BotConfig(token, name);
    }

    @Bean
    @Primary
    public ZoomRepository zoomRepository() {
        return new ZoomRepositoryImpl(jwtTokenGenerator());
    }

    @Bean
    @Primary
    public AuthRepository authRepository() {
        return new AuthRepositoryImpl(userDao());
    }

    @Bean
    @Primary
    public MeetingUseCase meetingUseCase() {
        return new MeetingUseCaseImpl(zoomRepository(), userDao());
    }

    @Bean
    @Primary
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean TelegramZoomBot bot() {
        return new TelegramZoomBot(botConfig(), handler());
    }

    @Bean
    public BotHandler handler() {
        return new CommandsHandler(meetingUseCase(), authRepository());
    }

    @Bean
    public JwtConfig jwtTokenGenerator() {
        return new JwtConfig(apiKey, apiSecret);
    }
}
