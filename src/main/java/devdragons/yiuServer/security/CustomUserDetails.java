package devdragons.yiuServer.security;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {
    private final User user;

    @Setter
    @Getter
    private UserRoleCategory role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // 사용자의 역할(role)에 따라 권한을 설정합니다.
        if (user.getRole() == UserRoleCategory.SUPER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
        } else if (user.getRole() == UserRoleCategory.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }
        return authorities;
    }

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public final User getUser() {
        return user;
    }

    public String getUserId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    //이하 4개의 메소드는 jwt를 사용하기에 true로 설정
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
        return true;
    }
}
