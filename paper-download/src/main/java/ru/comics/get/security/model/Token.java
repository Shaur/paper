package ru.comics.get.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long expirationTime;
}
