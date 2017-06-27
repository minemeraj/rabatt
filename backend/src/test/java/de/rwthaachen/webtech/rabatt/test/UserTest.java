package de.rwthaachen.webtech.rabatt.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.rwthaachen.webtech.rabatt.helper.*;
import de.rwthaachen.webtech.rabatt.model.*;

public class UserTest {
  @Test
  public void classHierarchy() {
    User user = new User();
    Assert.assertEquals(user instanceof AbstractEntityId, true);
  }

  @Test
  public void typeAnnotations() {
    // assert
    AssertAnnotations.assertType(User.class, Entity.class, Table.class);
  }

  @Test
  public void fieldAnnotations() {
    // assert
    AssertAnnotations.assertField(User.class, "firstName");
    AssertAnnotations.assertField(User.class, "lastName");
    AssertAnnotations.assertField(User.class, "username", Column.class);
    AssertAnnotations.assertField(User.class, "email", Column.class);
    AssertAnnotations.assertField(User.class, "password", Column.class);
    AssertAnnotations.assertField(User.class, "address");
    AssertAnnotations.assertField(User.class, "discounts", OneToMany.class, JsonIgnore.class);
  }

  @Test
  public void methodAnnotations() {
    // assert
    AssertAnnotations.assertMethod(User.class, "getPassword", JsonIgnore.class);
  }

  @Test
  public void entity() {
    // setup
    Entity a = ReflectTool.getClassAnnotation(User.class, Entity.class);
    // assert
    Assert.assertEquals("", a.name());
  }

  @Test
  public void table() {
    // setup
    Table t = ReflectTool.getClassAnnotation(User.class, Table.class);
    // assert
    Assert.assertEquals("users", t.name());
  }

  @Test
  public void username() {
    // setup
    Column c = ReflectTool.getFieldAnnotation(User.class, "username", Column.class);
    // assert
    Assert.assertEquals("user_name", c.name());
  }

  @Test
  public void email() {
    // setup
    Column c = ReflectTool.getFieldAnnotation(User.class, "email", Column.class);
    // assert
    Assert.assertEquals(true, c.unique());
  }

  @Test
  public void password() {
    // setup
    Column c = ReflectTool.getFieldAnnotation(User.class, "password", Column.class);
    // assert
    Assert.assertEquals(false, c.nullable());
  }

  @Test
  public void discounts() {
    // setup
    OneToMany a = ReflectTool.getFieldAnnotation(User.class, "discounts", OneToMany.class);
    // assert
    Assert.assertEquals("creator", a.mappedBy());
    Assert.assertEquals(FetchType.LAZY, a.fetch());
  }
}
