package de.rwthaachen.webtech.rabatt.test;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Id;

import org.junit.Assert;
import org.junit.Test;

import de.rwthaachen.webtech.rabatt.helper.AssertAnnotations;
import de.rwthaachen.webtech.rabatt.helper.ReflectTool;
import de.rwthaachen.webtech.rabatt.model.AbstractEntityId;

public class IdTest {
  @Test
  public void typeAnnotations() {
    // assert
    AssertAnnotations.assertType(AbstractEntityId.class, MappedSuperclass.class);
  }

  @Test
  public void fieldAnnotations() {
    // assert
    AssertAnnotations.assertField(AbstractEntityId.class, "id", GeneratedValue.class, Id.class);
  }

  @Test
  public void id() {
    GeneratedValue a =
        ReflectTool.getFieldAnnotation(AbstractEntityId.class, "id", GeneratedValue.class);
    // assert
    Assert.assertEquals("", a.generator());
    Assert.assertEquals(GenerationType.AUTO, a.strategy());
  }
}
