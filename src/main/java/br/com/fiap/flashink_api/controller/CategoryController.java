package br.com.fiap.flashink_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.flashink_api.model.Category;
import br.com.fiap.flashink_api.model.User;
import br.com.fiap.flashink_api.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
// @CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@Cacheable(value = "categories")
public class CategoryController {

    @Autowired // Injeção de dependência
    private CategoryRepository repository;

    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Lista todas as categorias salvas para um determinado usuário.", tags = "Category")
    public List<Category> index(@AuthenticationPrincipal User user) {
        return repository.findByUser(user);
    }

    @PostMapping
    @Operation(responses = @ApiResponse(responseCode = "400"))
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody @Valid Category category, @AuthenticationPrincipal User user) {
        log.info("Cadastrando categoria " + category.getName());
        category.setUser(user);
        return repository.save(category);
    }

    @GetMapping("{id}")
    public Category get(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Buscando categoria " + id);
        var category = getCategory(id);
        if (!category.getUser().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return category;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Apagando categoria " + id);
        var category = getCategory(id);
        if (!category.getUser().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        repository.delete(category);
    }

    @PutMapping("{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        log.info("Atualizando categoria " + category.toString());
        getCategory(id);
        category.setId(id);
        repository.save(category);

        return category;
    }

    private Category getCategory(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada, id=" + id));
    }
}
