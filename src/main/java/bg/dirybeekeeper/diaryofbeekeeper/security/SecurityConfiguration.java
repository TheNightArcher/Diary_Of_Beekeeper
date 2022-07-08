package bg.dirybeekeeper.diaryofbeekeeper.security;

import bg.dirybeekeeper.diaryofbeekeeper.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .antMatchers("/", "/login").permitAll()
                // all other pages are available for logger in users
                .anyRequest()
                .authenticated()
                .and()
                // configuration of form login
                .formLogin()
                // the custom login form
                .loginPage("/login")
                // the name of the username form field
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                // the name of the password form field
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                // where to go in case that the login is successful
                .defaultSuccessUrl("/")
                // where to go in case that the login failed
                .failureForwardUrl("/login-error")
                .and()
                // configure logout
                .logout()
                // which is the logout url, must be POST request
                .logoutUrl("/logout")
                // on logout go to the home page
                .logoutSuccessUrl("/")
                // invalidate the session and delete the cookies
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository, ModelMapper modelMapper) {
        return new UserDetailsImp(userRepository,modelMapper);
    }
}
