package edu.fpt.yellowmoon.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long id;
    private String email;
    private String password;
    private String address;
    private String fullName;
    private int roleId;
}