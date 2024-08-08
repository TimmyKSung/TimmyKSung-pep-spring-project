package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class used to demonstrate ORM functionality for related entities.
 */
@Service
@Transactional
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * Return an account from the account table that matches the given username
     * @param username a String object that identifies an account
     * @return the Account identified by the given username.
     */
    public Account findByUsername(String username){
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }else{
            return null;
        }
    }

    /**
     * Insert an account into the account table.
     * - The registration will be successful if and only if the username is not blank, 
     * the password is at least 4 characters long, and an Account with that username 
     * does not already exist.
     * @param account an Account object
     * @return the Account added into the database.
     */
    public Account addAccount(Account account){
        if (account == null) {
            return null;
        }
        else if (account.getUsername() == null || account.getPassword() == null) {
            return null;
        }
        else if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            return null;
        }
        else if (findByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountRepository.save(account);
    }

}
