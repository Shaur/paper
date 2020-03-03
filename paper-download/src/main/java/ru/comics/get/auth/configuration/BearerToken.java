package ru.comics.get.auth.configuration;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class BearerToken extends AbstractAuthenticationToken {

    private String value;

    public BearerToken(String value) {
        super(null);

        this.value = value;
    }

    @Override
    public String getCredentials() {
        return value;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        value = null;
    }
}
