package org.vertx.mods.async.results;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ScriptableObjectResult extends ScriptableObject implements Map<Object, Object> {

  private static final long serialVersionUID = 6453347887198780640L;

  private Map<Object, Object> map;

  public ScriptableObjectResult() {
    this.map = new HashMap<>();
  }

  /* Scriptable Object Overrides */
  @Override
  public Object getDefaultValue(Class<?> typeHint) {
    return this.toString();
  }

  @Override
  public String getClassName() {
    return this.getClass().getName();
  }

  @Override
  public Object get(String name, Scriptable start) {
    if (this.map.containsKey(name)) {
      return this.map.get(name);
    }

    return NOT_FOUND;
  }

  @Override
  public void clear() {
    this.map.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  @Override
  public Set<java.util.Map.Entry<Object, Object>> entrySet() {
    return this.map.entrySet();
  }

  @Override
  public Set<Object> keySet() {
    return this.map.keySet();
  }

  @Override
  public Object put(Object key, Object value) {
    return this.map.put(key, value);
  }

  @Override
  public void putAll(Map<? extends Object, ? extends Object> m) {
    this.map.putAll(m);
  }

  @Override
  public Object remove(Object key) {
    return this.map.remove(key);
  }

  @Override
  public Collection<Object> values() {
    return this.map.values();
  }

}
