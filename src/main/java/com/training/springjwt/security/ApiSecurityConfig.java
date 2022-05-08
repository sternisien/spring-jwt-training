package com.training.springjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

  private PasswordEncoder passwordEncoder;

  @Autowired
  public ApiSecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Cette fonction permet la sécurité que l'on souhaite pour notre application. La configuration
   * actuelle permet d'autoriser toutes les requetes lorqu'on est authentifié. Le type de sécurité
   * utilisé pour notre application, ici, est "Basic Auth"
   *
   * @param http - Objet Spring permettant de définir notre sécurité
   * @throws Exception - Exception pouvant etre levé en case d'erreur
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable() // TODO TEACH LATER
        .authorizeRequests()
        .antMatchers("/", "index", "/css/*", "/js/*")
        .permitAll()
        .antMatchers("/api/**")
        .hasRole(ApiUserRole.STUDENT.name())
        .antMatchers(HttpMethod.DELETE, "/management/api/**")
        .hasAuthority(ApiUserPermission.COURSE_WRITE.getValue())
        .antMatchers(HttpMethod.PUT, "/management/api/**")
        .hasAuthority(ApiUserPermission.COURSE_WRITE.getValue())
        .antMatchers("/management/api/**")
        .hasAnyRole(ApiUserRole.ADMIN.name(), ApiUserRole.ADMINTRAINEE.name())
        .antMatchers(HttpMethod.POST, "/management/api/**")
        .hasAuthority(ApiUserPermission.COURSE_WRITE.getValue())
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic();
  }

  /**
   * Ce service permet de récuperer les utilisateurs dans la base de données. Actuellement la
   * gestion des utilistaurs n'est pas implémenter via la base de données. Nous allons gérer les
   * utilisateurs via l'objet {@link InMemoryUserDetailsManager} qui permet de charger des
   * utilisateurs en mémoire de la JVM
   *
   * @return Service Spring permettant de gérer des utilisateurs
   */
  @Override
  @Bean
  protected UserDetailsService userDetailsService() {
    UserDetails sebStudent =
        User.builder()
            .username("sebastient")
            .password(passwordEncoder.encode("toto"))
            //            .roles(ApiUserRole.STUDENT.name()) // ROLE_STUDENT format spring
            .authorities(ApiUserRole.STUDENT.getGrantedAuthorities())
            .build();

    List<String> permiss =
        ApiUserRole.ADMIN.getPermissions().stream()
            .map(perm -> perm.getValue())
            .collect(Collectors.toList());
    String[] permissionArray = new String[permiss.size()];
    UserDetails floAdmin =
        User.builder()
            .username("flo")
            .password(passwordEncoder.encode("pwd1234")) // ROLE_ADMIN
            .authorities(ApiUserRole.ADMIN.getGrantedAuthorities())
            .build();

    UserDetails adminTrainee =
        User.builder()
            .username("admintrainee")
            .password(passwordEncoder.encode("pwd1234"))
            //            .roles(ApiUserRole.ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
            .authorities(ApiUserRole.ADMINTRAINEE.getGrantedAuthorities())
            .build();

    return new InMemoryUserDetailsManager(sebStudent, floAdmin, adminTrainee);
  }
}
