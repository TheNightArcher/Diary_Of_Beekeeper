package bg.dirybeekeeper.diaryofbeekeeper.config;

import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import bg.dirybeekeeper.diaryofbeekeeper.security.BeekeeperUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // define which requests are allowed and which not
                .authorizeHttpRequests()
                // everyone can download static resources (css, js, images)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // everyone can login and register
                .antMatchers("/", "/users/login", "/users/register").permitAll()
                .antMatchers("/users/add-beehives").permitAll()
                // all other pages are available for logger in users
                .anyRequest()
                .authenticated()
                .and()
                // configuration of form login
                .formLogin()
                // the custom login form
                .loginPage("/users/login")
                // the name of the username form field
                .usernameParameter("username")
                // the name of the password form field
                .passwordParameter("password")
                // where to go in case that the login is successful
                .defaultSuccessUrl("/")
                // where to go in case that the login failed
                .failureForwardUrl("/users/login-error")
                .and()
                // configure logout
                .logout()
                // which is the logout url, must be POST request
                .logoutUrl("/users/logout")
                // on logout go to the home page
                .logoutSuccessUrl("/")
                // invalidate the session and delete the cookies
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository, ModelMapper modelMapper) {
        return new BeekeeperUserDetailsService(userRepository);
    }
}
