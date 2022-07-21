package bg.dirybeekeeper.diaryofbeekeeper.security;

import bg.dirybeekeeper.diaryofbeekeeper.model.user.BeekeeperUserDetails;
import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

// It's not a @Service
public class BeekeeperUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public BeekeeperUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(u -> new BeekeeperUserDetails(
                        u.getUsername(),
                        u.getPassword(),
                        u.isEnabled(),
                        u.getRoles()
                                .stream()
                                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                                .collect(Collectors.toList())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User with that username:  " + username + " not found!"));
    }
}
