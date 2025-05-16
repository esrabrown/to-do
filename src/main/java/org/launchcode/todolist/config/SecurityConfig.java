//package org.launchcode.todolist.config;
//
//import org.launchcode.todolist.models.data.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//
//    public SecurityConfig(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests()
//                .requestMatchers("/authentication/**", "/css/**", "/images/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .csrf().disable()
//                .formLogin()
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .loginPage("/authentication/login")
//                .failureUrl("/authentication/login?failed")
//                .loginProcessingUrl("/authentication/login/process")
//                .and()
//                .logout()
//                .logoutUrl("/authentication/logout")
//                .logoutSuccessUrl("/authentication/login")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true);
//        http.authenticationProvider(authenticationProvider());
//
//        return http.build();
//    }
//
//
////
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(auth.userDetailsService())
////                .passwordEncoder(passwordEncoder());
////    }
//}

package org.launchcode.todolist.config;

import org.launchcode.todolist.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//               // .requestMatchers("/api/users/authentication/register", "/api/users/authentication/login", "/css/**", "/images/**").permitAll()
//                // .requestMatchers("/api/users/**").permitAll()
//                .requestMatchers("/api/users/authentication/login/**", "/api/users/authentication/register/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/authentication/login")
//                .loginProcessingUrl("/api/users/authentication/login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/authentication/login?error")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/api/users/authentication/logout")
//                .logoutSuccessUrl("/authentication/login")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .permitAll()
//                .and()
//                .cors();  // Enable CORS
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .requestMatchers("/api/users/authentication/register", "/api/users/authentication/login", "/api/todos", "/api/create", "/error").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .cors()  // Enable CORS
//                .and()
//                .formLogin().disable();  // Disable default form login
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
              //  .requestMatchers("/api/users/authentication/register", "/api/users/authentication/login", "/api/todos", "/api/create", "/error").permitAll()
                .requestMatchers("/api/users/authentication/register", "/api/users/authentication/login","/error").permitAll()
                .requestMatchers("/api/create", "/api/delete", "/api/todos").authenticated()
//                .requestMatchers("/admin_only/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .cors()  // Enable CORS
                .and()
                .formLogin().disable();  // Disable default form login

        return http.build();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

        };
    }
}
