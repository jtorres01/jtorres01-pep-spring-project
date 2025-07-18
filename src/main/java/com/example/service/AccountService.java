package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().build(); 
        }

        Optional<Account> existing = accountRepository.findByUsername(account.getUsername());
        if (existing.isPresent()) {
            return ResponseEntity.status(409).build(); 
        }

        Account saved = accountRepository.save(account);
        return ResponseEntity.ok(saved); 
    }

    public ResponseEntity<?> login(Account account) {
        Optional<Account> existing = accountRepository.findByUsername(account.getUsername());
        if (existing.isPresent() && existing.get().getPassword().equals(account.getPassword())) {
            return ResponseEntity.ok(existing.get());
        }
        return ResponseEntity.status(401).build();
    }
}
