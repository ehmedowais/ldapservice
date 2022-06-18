package com.lambdainfo.ldapservice.domain;

import lombok.Data;

@Data
public class UserDetails{
    public User user;
    public String token;

    @Data
    private static class User{
        public int age;
        public String _id;
        public String name;
        public String email;
        public String createdAt;
        public String updatedAt;
        public int __v;
    }
}
