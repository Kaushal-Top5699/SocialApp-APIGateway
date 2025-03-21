package com.kaushal.API_Gateway_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteLike {
    private String token;
    private String email;
    private String likeID;
}
