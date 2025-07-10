package com.eronalves.genericsexercises;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GenericCache<K, V> extends LinkedHashMap<K, V> {

  public static class Pair<K, V> {
    private K k;
    private V v;

    public Pair(K k, V v) {
      this.k = k;
      this.v = v;
    }

  }

  private int maxEntries;

  public GenericCache(int maxEntries) {
    super();
    this.maxEntries = 0;
  }

  public void putAll(Collection<Pair<? extends K, ? extends V>> pairs) {
    for (var p : pairs) {
      put(p.k, p.v);
    }
  }

  public Map<K, V> getAll(Collection<? extends K> keys) {
    return entrySet()
        .stream()
        .filter(es -> keys.isEmpty() || keys.contains(es.getKey()))
        .collect(Collectors.toMap(k -> k.getKey(), v -> v.getValue()));
  }

  public Map<K, V> getAll() {
    return getAll(Collections.emptyList());
  }

  @Override
  protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
    return size() > maxEntries;
  }

}
