package de.rwthaachen.webtech.rabatt.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "users")
public class User extends AbstractCommonEntity {

  @Column(name = "user_name", nullable = false, unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private String firstName;

  private String lastName;

  @Column(nullable = false)
  private String password;

  private String address;

  @Column(columnDefinition = "boolean default true")
  @JsonIgnore
  private boolean enabled;

  @JsonIgnore
  private String role;

  @OneToMany(mappedBy = "creator")
  private List<Discount> discounts;

  public boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonSetter
  public void setPassword(String password) {
    String salt = this.getId() + password;
    this.password = DigestUtils.sha256Hex(salt);
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(this.email).append(this.username)
        .append(this.firstName).append(this.lastName).toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof User))
      return false;
    User other = (User) obj;
    if (getId() != other.getId())
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "User [id=" + getId() + ", username=" + username + ", address=" + address + ", email="
        + email + "]";
  }
}
