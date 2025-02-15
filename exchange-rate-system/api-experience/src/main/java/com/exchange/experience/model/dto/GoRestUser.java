package com.exchange.experience.model.dto;

import lombok.Data;

@Data
public class GoRestUser {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String status;
}