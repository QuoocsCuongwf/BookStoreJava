package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private String username;
    private String password;
    private String chucvu;


}