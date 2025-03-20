package com.kaushal.API_Gateway_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String imageURL;
    private String userImageURL;
    private String userEmail;
    private String username;
    private String totalLikes;
    private String totalComments;
    private String caption;

    public Post(String caption) {
        this.caption = caption;
    }
}
