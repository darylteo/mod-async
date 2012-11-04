package org.vertx.mods.async.strategies;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;

public class SeriesMap {

  private final SeriesMap that = this;

  private final Map<Object, Task> tasks;
  private final AsyncResultCallback callback;

  public SeriesMap(Map<Object, Task> tasks) {
    this.tasks = tasks;
    this.callback = null;
  }

  public SeriesMap(Map<Object, Task> tasks, AsyncResultCallback callback) {
    this.tasks = tasks;
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final ExceptionHandler exceptionHandler = new ExceptionHandler(this.callback);

    final Map<Object, Object> results = new HashMap<>(this.tasks.size());
    final List<Object> keys = new LinkedList<>(this.tasks.keySet());

    final class SeriesDelegate implements ExecutionDelegate {
      private int index;

      public SeriesDelegate() {
        this.index = -1;
      }

      @Override
      public void taskComplete(Object value) {
        Object key = keys.get(this.index);
        results.put(key, value);
      }

      @Override
      public void allComplete() {
        that.callback.result(null, results);
      }

      @Override
      public void exception(String error) {
        exceptionHandler.exception(error);
      }

      @Override
      public boolean shouldContinue() {
        return this.index < that.tasks.size() - 1;
      }

      @Override
      public Task next() {
        this.index++;
        Object key = keys.get(this.index);
        return that.tasks.get(key);
      }
    }

    new ExecutionController(new SeriesDelegate());
  }

}
