package com.eronalves.genericsexercises;

import java.beans.BeanInfo;
import java.beans.Beans;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CSVParser<T> {

  private Class<T> target;
  private BeanInfo beanInfo;

  public CSVParser(Class<T> target) {
    this.target = target;
  }

  private BeanInfo targetInfo() {
    if (this.beanInfo == null) {
      synchronized (this) {
        if (this.beanInfo == null) {
          try {
            this.beanInfo = Introspector.getBeanInfo(target);
          } catch (IntrospectionException e) {
            throw new IllegalStateException("Failed to generate introspection for bean", e);
          }
        }
      }
    }

    return this.beanInfo;
  }

  /**
   * Parses a line into the specified type
   */
  public Optional<T> parseOne(List<String> header, int lineNumber, String line) {
    if (line.isBlank()) {
      return Optional.empty();
    }

    var splited = Arrays.asList(line.split(","));

    if (splited.size() != header.size()) {
      throw new UnmatchedModelSpecException(header.size(), splited.size(), lineNumber);
    }

    BeanInfo info = targetInfo();
    PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
    T instance = null;
    try {
      instance = target
          .cast(Beans.instantiate(Thread.currentThread().getContextClassLoader(),
              info.getBeanDescriptor().getBeanClass().getName()));
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    for (final var p : propertyDescriptors) {
      var name = p.getName();
      var nameIndex = header.indexOf(name);
      if (nameIndex != -1) {
        var setter = p.getWriteMethod();
        try {
          setter.invoke(instance, splited.get(nameIndex));
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

    return Optional.of(instance);
  }

}
