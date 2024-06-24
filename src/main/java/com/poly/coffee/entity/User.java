package com.poly.coffee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.coffee.enums.AuthTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Column(columnDefinition = "nvarchar(255)")
    private String firstName;

    @Column(columnDefinition = "nvarchar(255)")
    private String lastName;

    private String phoneNumber;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private AuthTypeEnum authType = AuthTypeEnum.LOCAL;

    private LocalDate dob;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}