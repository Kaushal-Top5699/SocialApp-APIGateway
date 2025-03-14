package com.kaushal.API_Gateway_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String phoneNum;
    private String userImage;
}
