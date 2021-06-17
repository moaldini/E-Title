package miu.edu.etitle.config;

import lombok.Getter;
import miu.edu.etitle.domain.Role;
import miu.edu.etitle.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class JPAUserDetails implements UserDetails {
    String firstName;
    String lastName;
    String email;
    String password;
    String ssn;
    String blockchainToken;
    boolean isActive;
    List<Role> roles;

    public JPAUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.ssn = user.getSsn();
        this.blockchainToken = user.getBlockchainToken();

        this.isActive = user.isActive();
        this.roles = List.of(user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::getName).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
