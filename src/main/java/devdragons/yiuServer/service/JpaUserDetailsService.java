package devdragons.yiuServer.service;

import devdragons.yiuServer.domain.User;
import devdragons.yiuServer.domain.state.UserRoleCategory;
import devdragons.yiuServer.repository.UserRepository;
import devdragons.yiuServer.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name).orElseThrow(
                () -> new UsernameNotFoundException("사용자가 존재하지 않습니다.")
        );
        return new CustomUserDetails(user);
    }

    @Transactional
    public UserDetails loadUserById(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("사용자가 존재하지 않습니다.")
        );
        CustomUserDetails userDetails = new CustomUserDetails(user);

        if (user.getRole() == UserRoleCategory.SUPER) {
            userDetails.setRole(UserRoleCategory.valueOf("SUPER"));
        } else if(user.getRole() == UserRoleCategory.ADMIN){
            userDetails.setRole(UserRoleCategory.valueOf("ADMIN"));
        } else {
            userDetails.setRole(UserRoleCategory.valueOf("STUDENT"));
        }
        return userDetails;
    }
}