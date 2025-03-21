package com.kaushal.API_Gateway_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllComments {
    private String _id;
    private String time;
    private String comment;
    private String postID;
    private String userID;
    private String createdAt;
    private int __v;
}
