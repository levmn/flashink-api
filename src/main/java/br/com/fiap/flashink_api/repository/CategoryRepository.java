package br.com.fiap.flashink_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.flashink_api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
