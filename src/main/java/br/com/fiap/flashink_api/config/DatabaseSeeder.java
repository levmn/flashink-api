package br.com.fiap.flashink_api.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.fiap.flashink_api.model.Category;
import br.com.fiap.flashink_api.model.Transaction;
import br.com.fiap.flashink_api.model.TransactionType;
import br.com.fiap.flashink_api.model.User;
import br.com.fiap.flashink_api.model.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.flashink_api.repository.CategoryRepository;
import br.com.fiap.flashink_api.repository.TransactionRepository;
import br.com.fiap.flashink_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Configuration
@Profile("dev")
public class DatabaseSeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        var allan = User.builder()
                        .email("rm98276@fiap.com.br")
                        .password(passwordEncoder.encode("senha1234"))
                        .role(UserRole.ADMIN)
                        .build();

        var teste = User.builder()
                        .email("email@email.com.br")
                        .password(passwordEncoder.encode("senha1234"))
                        .role(UserRole.USER)
                        .build();

        userRepository.saveAll(List.of(allan, teste));

        var categories = List.of(
                Category.builder().name("Old School").icon("Anchor").user(allan).build(),
                Category.builder().name("Neo Tradicional").icon("Feather").user(allan).build(),
                Category.builder().name("Minimalista").icon("Dot").user(allan).build(),
                Category.builder().name("Blackwork").icon("Triangle").user(teste).build());

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
