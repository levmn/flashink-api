package br.com.fiap.flashink_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.flashink_api.model.Transaction;
import br.com.fiap.flashink_api.model.TransactionFilter;
import br.com.fiap.flashink_api.repository.TransactionRepository;
import br.com.fiap.flashink_api.specification.TransactionSpecification;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @GetMapping
    public Page<Transaction> index(TransactionFilter filter,
            @PageableDefault(size = 10, sort = "date", direction = Direction.DESC) Pageable pageable) {
        // log.info("Buscando transações com descrição {} e data {} ",
        // filter.description(), filter.date());

        // if (description != null && date != null) return
        // repository.findByDescriptionContainingIgnoringCaseAndDate(description, date);
        // if (description != null) return
        // repository.findByDescriptionContainingIgnoringCase(description);
        // if (date != null) return repository.findByDate(date);

        // var probe = Transaction.builder()
        // .description(filter.description)
        // .date(filter.date())
        // .amount(filter.amount())
        // .build();

        // var matcher = ExampleMatcher.matchingAll()
        // .withIgnoreCase()
        // .withIgnoreNullValues()
        // .withStringMatcher(StringMatcher.CONTAINING);

        // var example = Example.of(probe, matcher);

        // return repository.findAll(example);

        return repository.findAll(TransactionSpecification.withFilters(filter), pageable);
    }
}
