package org.vertx.mods.async.executors;

import java.util.Map;

public abstract class TaskMapExecutor extends TaskExecutor {
  private final Map<Object, Object> results;
  private final Object key;

  public TaskMapExecutor(Object key, Map<Object, Object> results) {
    this.key = key;
    this.results = results;
  }

  //
  // @Override
  // public void result(Byte value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(Integer value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(Short value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(Long value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(Float value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(Double value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(Boolean value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(Character value) {
  // this.results.put(key, value);
  // completed();
  // }
  //
  // @Override
  // public void result(String value) {
  // this.results.put(key, value);
  // completed();
  // }

  @Override
  public void result(Object e, Object value) {
    if (e != null) {
      this.exception(e.toString());
      return;
    }

    this.results.put(key, value);
    this.complete();
  }

}