package com.poly.coffee.auth.oauthuser;

import com.poly.coffee.auth.UserRoot;
import com.poly.coffee.entity.Cart;
import com.poly.coffee.entity.Role;
import com.poly.coffee.entity.User;
import com.poly.coffee.enums.AuthTypeEnum;
import com.poly.coffee.enums.RoleEnum;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.repository.CartRepository;
import com.poly.coffee.repository.RoleRepository;
import com.poly.coffee.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    CartRepository cartRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String typeOAuth = userRequest.getClientRegistration().getClientName();

        OAuth2UserInfo oAuth2UserInfo = switch (typeOAuth) {
            case "Google" -> new GoogleUserInfo(oAuth2User.getAttributes());
            case "Facebook" -> new FacebookUserInfo(oAuth2User.getAttributes());
            default -> throw new IllegalArgumentException("Unexpected type auth: " + typeOAuth);
        };

        User user;
        Optional<User> optionalUser = userRepository.findByEmail(oAuth2UserInfo.getEmail());

        if (optionalUser.isPresent()) {
            // Update
            user = optionalUser.get();

            user.setFirstName(oAuth2UserInfo.getFirstname());
            user.setLastName(oAuth2UserInfo.getLastname());
            user.setAuthType(typeOAuth.equals("Google") ? AuthTypeEnum.GOOGLE : AuthTypeEnum.FACEBOOK);

            user = userRepository.save(user);
        } else {
            // Create new user
            Set<Role> roles = new HashSet<>();

            Role roleUser = roleRepository.findById(RoleEnum.USER.name())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

            roles.add(roleUser);

            User savedUser = userRepository.save(User.builder()
                    .firstName(oAuth2UserInfo.getFirstname())
                    .lastName(oAuth2UserInfo.getLastname())
                    .email(oAuth2UserInfo.getEmail())
                    .roles(roles)
                    .authType(typeOAuth.equals("Google") ? AuthTypeEnum.GOOGLE : AuthTypeEnum.FACEBOOK)
                    .build());

            Cart cart = new Cart();
            cart.setUser(savedUser);

            cartRepository.save(cart);

            user = savedUser;
        }
        return UserRoot.create(user, oAuth2UserInfo.getAttributes());
    }
}
