package com.eronalves.genericsexercises;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PluginRegistry {

  private Map<Class<?>, Object> register = new HashMap<>();

  public <T> void registerPlugin(Class<T> clazz, T plugin) {
    register.put((Class<?>) clazz, plugin);
  }

  // Plugins are registered through lambda functions, it's impossible to have
  // circular references
  public <T> void registerPluginWithDeps(Class<T> clazz, Function<PluginRegistry, T> plugin) {
    registerPlugin(clazz, plugin.apply(this));
  }

  public <T> Optional<T> getPlugin(Class<T> type) {
    return Optional.ofNullable(register.get(type))
        .map(type::cast);
  }

}
