package curs_money_transfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import curs_money_transfer.repository.TransferRepository;
import curs_money_transfer.service.TransferService;

@Configuration
public class JavaConfig {


    @Bean
    public TransferService transferService(TransferRepository transferRepository) {
        return new TransferService(transferRepository);
    }

    @Bean
    public TransferRepository transferRepository() {
        return new TransferRepository();
    }

}
