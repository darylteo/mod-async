package org.vertx.mods.async;

public interface TaskResultCallback {
  // public void result(Object e, Byte value);
  //
  // public void result(Object e, Integer value);
  //
  // public void result(Object e, Short value);
  //
  // public void result(Object e, Long value);
  //
  // public void result(Object e, Float value);
  //
  // public void result(Object e, Double value);
  //
  // public void result(Object e, Number value);
  //
  // public void result(Object e, Boolean value);
  //
  // public void result(Object e, Character value);
  //
  // public void result(Object e, String value);

  public void result(Object e, Object value);
}
