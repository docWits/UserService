package com.romanyuta.UserService.config;

import com.romanyuta.UserService.model.role.Role;
import com.romanyuta.UserService.model.user.User;
import com.romanyuta.UserService.repos.RoleRepo;
import com.romanyuta.UserService.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoleRepo roleRepo;

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login").permitAll().defaultSuccessUrl("/auth/success")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout","POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login")
                .and()
                .oauth2Login()
                .and().httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
         daoAuthenticationProvider.setUserDetailsService(userDetailsService);
         return daoAuthenticationProvider;
    }



//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//                org.springframework.security.core.userdetails.User.builder()
//                        .username("admin")
//                        .roles("ADMIN")
//                        .password(passwordEncoder().encode("admin"))
//                        .build(),
//                User.builder()
//                        .username("user")
//                        .roles("USER")
//                        .password(passwordEncoder().encode("user"))
//                        .build()
//
//        );
//    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }



//    @Bean
//    public PrincipalExtractor principalExtractor(UserRepo userRepo){
//        return map -> {
//            Role role = roleRepo.findByName("USER");
//            Long id = Long.valueOf((Integer) map.get("id"));
//            User user = userRepo.findById(id).orElseGet(() ->{
//              User newUser = new User();
//              newUser.setId(id);
//              newUser.setName((String) map.get("name"));
//              newUser.setLogin((String) map.get("login"));
//              Set<Role> roles = new HashSet<>();
//              roles.add(role);
//              newUser.setRoles(roles);
//              return newUser;
//          });
//            user.setLastVisit(LocalDateTime.now());
//           return userRepo.save(user);
//        };
//    }

}
