package ru.comics.get.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.comics.get.security.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
}
