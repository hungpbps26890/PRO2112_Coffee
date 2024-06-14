package com.poly.coffee.config;

import com.poly.coffee.entity.Role;
import com.poly.coffee.entity.User;
import com.poly.coffee.enums.RoleEnum;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.repository.RoleRepository;
import com.poly.coffee.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Configuration
public class ApplicationInitConfig {

   PasswordEncoder passwordEncoder;

   RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
//                Set<Role> roles = new HashSet<>();
//
//                Role roleAdmin = roleRepository.findById(RoleEnum.ADMIN.name())
//                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
//
//                roles.add(roleAdmin);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("12345678"))
//                        .roles(roles)
                        .build();

                userRepository.save(user);

                log.warn("Admin user was created with default password: 12345678");
            }
        };
    }
}
