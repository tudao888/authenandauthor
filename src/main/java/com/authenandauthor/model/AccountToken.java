package com.authenandauthor.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountToken {
    private Integer id;
    private String username;
    private String token;

    private List<Role> roles;

}
