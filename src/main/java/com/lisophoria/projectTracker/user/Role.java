package com.lisophoria.projectTracker.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lisophoria.projectTracker.user.Permission.ADMIN_CREATE;
import static com.lisophoria.projectTracker.user.Permission.ADMIN_DELETE;
import static com.lisophoria.projectTracker.user.Permission.ADMIN_READ;
import static com.lisophoria.projectTracker.user.Permission.ADMIN_UPDATE;
import static com.lisophoria.projectTracker.user.Permission.MANAGER_CREATE;
import static com.lisophoria.projectTracker.user.Permission.MANAGER_DELETE;
import static com.lisophoria.projectTracker.user.Permission.MANAGER_READ;
import static com.lisophoria.projectTracker.user.Permission.MANAGER_UPDATE;

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet(), "User"),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  MANAGER_READ,
                  MANAGER_UPDATE,
                  MANAGER_DELETE,
                  MANAGER_CREATE
          ), "ADMIN"
  ),
  MANAGER(
          Set.of(
                  MANAGER_READ,
                  MANAGER_UPDATE,
                  MANAGER_DELETE,
                  MANAGER_CREATE
          ), "MANAGER"
  )

  ;

  @Getter
  private final Set<Permission> permissions;
  @Getter
  private final String name;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
