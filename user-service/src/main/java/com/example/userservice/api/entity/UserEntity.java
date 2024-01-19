package com.example.userservice.api.entity;

import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString(exclude = "userAuthorities")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false, unique = true)
    private String encryptedPwd;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<UserAuthority> userAuthorities = new ArrayList<>();
    public void addUserAuthority(UserAuthority userAuthority) {
        userAuthorities.add(userAuthority);
        userAuthority.setUserEntity(this);
    }

}
