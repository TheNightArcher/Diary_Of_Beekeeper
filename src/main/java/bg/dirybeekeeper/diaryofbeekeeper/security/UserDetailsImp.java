package bg.dirybeekeeper.diaryofbeekeeper.security;

import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


// It's not a @Service
public class UserDetailsImp implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDetailsImp(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserDetails.class))
                .orElseThrow(() -> new UsernameNotFoundException("User with that username:  " + username + " not found!"));
    }
}
