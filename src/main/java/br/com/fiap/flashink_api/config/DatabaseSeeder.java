package br.com.fiap.flashink_api.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.fiap.flashink_api.model.Category;
import br.com.fiap.flashink_api.model.Transaction;
import br.com.fiap.flashink_api.model.TransactionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.flashink_api.repository.CategoryRepository;
import br.com.fiap.flashink_api.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostConstruct
    public void init() {
        var categories = List.of(
                Category.builder().name("Old School").icon("Anchor").build(),
                Category.builder().name("Neo Tradicional").icon("Feather").build(),
                Category.builder().name("Minimalista").icon("Dot").build(),
                Category.builder().name("Blackwork").icon("Triangle").build());

        categoryRepository.saveAll(categories);

        var descriptions = List.of(
                "Tattoo de dragão",
                "Tattoo de rosa",
                "Tattoo de caveira",
                "Tattoo de lobo",
                "Tattoo de leão",
                "Tattoo de águia",
                "Tattoo de flor",
                "Tattoo de coração",
                "Tattoo de estrela",
                "Tattoo de sol");

        var transaction = new ArrayList<Transaction>();
        for (int i = 0; i < 10; i++) {
            transaction.add(
                    Transaction.builder()
                            .description(descriptions.get(new Random().nextInt(descriptions.size())))
                            .amount(BigDecimal.valueOf(10 + new Random().nextDouble() * 500))
                            .date(LocalDate.now().minusDays(new Random().nextInt(30)))
                            .type(TransactionType.EXPENSE)
                            .category(categories.get(new Random().nextInt(categories.size())))
                            .build());
        }
        transactionRepository.saveAll(transaction);
    }
}
