package com.training.springjwt.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApiUserRole {
  STUDENT(Sets.newHashSet()),
  ADMIN(
      Sets.newHashSet(
          ApiUserPermission.COURSE_READ,
          ApiUserPermission.COURSE_WRITE,
          ApiUserPermission.STUDENT_READ,
          ApiUserPermission.STUDENT_WRITE)),
  ADMINTRAINEE(Sets.newHashSet(ApiUserPermission.COURSE_READ, ApiUserPermission.STUDENT_READ));

  ApiUserRole(Set<ApiUserPermission> permissions) {
    this.permissions = permissions;
  }

  private final Set<ApiUserPermission> permissions;

  /**
   * Fonction permettant de récupérer les permissions associées à un role
   *
   * @return Une collection de permissions associées à un role
   */
  public Set<ApiUserPermission> getPermissions() {
    return permissions;
  }

  /**
   * Fonction permettant de récupérer les autorités Spring à partir des informations du role
   * concernée par le get (Role courant et ses permissions lors de l'utilisation de l'enum)
   *
   * <p>ROLE_XXX => Prefix utilisé par Spring pour la définition d'un role voir {@link
   * org.springframework.security.core.userdetails.User.UserBuilder#roles(String...)}
   *
   * @return Une collection de {@link SimpleGrantedAuthority} (role + permissions associées)
   *     permettant gérer les autorités Spring
   */
  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    Set<SimpleGrantedAuthority> permissions =
        this.permissions.stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
            .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
