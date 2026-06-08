package org.example.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/user/login", "/user/add", "/css/**", "/js/**", "/webjars/**").permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")

                        .requestMatchers("/book/add/**", "/book/edit/**").hasRole("ADMIN")
                        .requestMatchers("/book/delete/**").hasAuthority("DELETE_PRIVILEGE")
                        .requestMatchers("/author/add/**", "/author/edit/**", "/author/delete/**").hasRole("ADMIN")


                        .requestMatchers("/borrow/add").authenticated()
                        .requestMatchers("/book", "/author", "/borrow", "/borrow/edit/**").hasAnyRole("ADMIN", "LIBRARIAN")
                        .anyRequest()
                        .authenticated()
        )
                .formLogin(login -> login
                        .loginPage("/user/login")
                        .successHandler(authenticationSuccessHandler())
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/user/login")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler  authenticationSuccessHandler(){
        return (request, response, authentication) -> {
            var roles = authentication.getAuthorities();

            String targetUrl = "/book";

            for (GrantedAuthority role : roles) {
                if (role.getAuthority().equals("ROLE_ADMIN")) {
                    targetUrl = "/user";
                    break;
                }else if (role.getAuthority().equals("ROLE_LIBRARIAN")) {
                    targetUrl = "/borrow";
                    break;
                }
            }

            response.sendRedirect(request.getContextPath() + targetUrl);
        };
    }
    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
}
