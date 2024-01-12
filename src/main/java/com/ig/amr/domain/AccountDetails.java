package com.ig.amr.domain;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class AccountDetails extends User {

    private final Account account;

    public AccountDetails(Account account) {
        super(account.getEmail(), account.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        this.account = account;
    }
}