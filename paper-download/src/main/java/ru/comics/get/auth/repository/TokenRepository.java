package ru.comics.get.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.comics.get.auth.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
}
