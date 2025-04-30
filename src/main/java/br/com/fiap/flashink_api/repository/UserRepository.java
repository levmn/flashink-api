package br.com.fiap.flashink_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.flashink_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Optional<> - query method, quando há apenas um critério de busca
    Optional<User> findByEmail(String username);

}
