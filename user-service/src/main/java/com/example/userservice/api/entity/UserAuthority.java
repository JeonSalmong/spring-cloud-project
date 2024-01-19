package com.example.userservice.api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USER_AUTHORITY")
@Data
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "USER_AUTH_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTH_SEQ")
    private Authority authority;

    public static UserAuthority createUserAuthority(Authority authority) {
        UserAuthority userAuthority = new UserAuthority();
        userAuthority.setAuthority(authority);
        return userAuthority;
    }
}
