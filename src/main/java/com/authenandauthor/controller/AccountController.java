package com.authenandauthor.controller;

import com.authenandauthor.model.Account;
import com.authenandauthor.model.AccountToken;
import com.authenandauthor.model.Product;
import com.authenandauthor.model.Role;
import com.authenandauthor.repository.AccountRepo;
import com.authenandauthor.service.AccountService;
import com.authenandauthor.service.JwtService;
import com.authenandauthor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public AccountToken login(@RequestBody Account account) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.createToken(authentication);
        Account account1 = accountService.findAccountByUsername(account.getUsername());
        if (account1.getStatus() == 2) {
            return null;
        } else {
            AccountToken accountToken = new AccountToken(account1.getId(), account1.getUsername(), token, account1.getRoles());
            return accountToken;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(1);
        roles.add(role);
        account.setRoles(roles);
        account.setStatus(1);
        accountService.createAccount(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/user/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }




}
