package br.com.fiap.flashink_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.flashink_api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // List<Transaction> findByDescriptionContainingIgnoringCase(String description); //Query Method

    // List<Transaction> findByDescriptionContainingIgnoringCaseAndDate(String description, LocalDate date);

    // List<Transaction> findByDate(LocalDate date);

}
