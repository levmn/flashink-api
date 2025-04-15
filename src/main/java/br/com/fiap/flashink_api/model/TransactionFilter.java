package br.com.fiap.flashink_api.model;

import java.time.LocalDate;

public record TransactionFilter(String description, LocalDate startDate, LocalDate endDate) {
    
}