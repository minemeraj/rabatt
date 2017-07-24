package de.rwthaachen.webtech.rabatt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.rwthaachen.webtech.rabatt.model.User;
import de.rwthaachen.webtech.rabatt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserRepository userRepository;

  @Overre
  public UserDetai loadUserByUsername(String arg0) throws UsernameNotFoundException {
    List<User> users = userRepository.findByUsername(arg0);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException("Username " + arg0 + " not found");
    }
    User user = users.get(0);
    if (!user.getUsername().equalsIgnoreCase(arg0)) {
      throw new UsernameNotFoundException("Username " + arg0 + " not found");
    }

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    Permission p = new Permission(user.getRole());
    authorities.add(p);

    UserInformations userDetails =
        new UserInformations(user.getUsername(), user.getPassword(), authorities);
    userDetails.setFirstName(user.getFirstName());
    userDetails.setLastName(user.getLastName());

    return userDetails;
  }

  public class UserInformations implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private boolean accountLocked;

    public boolean isAccountLocked() {
      return accountLocked;
    }

    private List<GrantedAuthority> permissions;

    public UserInformations(String userName, String password, List<GrantedAuthority> permissions) {
      this.userName = userName;
      this.password = password;
      this.permissions = permissions;
    }

    public void setAccountLocked(boolean locked) {
      this.accountLocked = locked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return permissions;
    }

    @Override
    public String getPassword() {
      return password;
    }

    @Override
    public String getUsername() {
      return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

  }

  public class Permission implements GrantedAuthority {


    private static final long serialVersionUID = 1L;
    private String permission = null;

    public Permission(String permission) {
      this.permission = permission;
    }

    @Override
    public String getAuthority() {
      return permission;
    }

  }

  @Override
  public void saveUser(User user, String password) {
    // TODO Auto-generated method stub

  }

  @Override
  public void saveUser(User user) {
    // TODO Auto-generated method stub

  }
}
