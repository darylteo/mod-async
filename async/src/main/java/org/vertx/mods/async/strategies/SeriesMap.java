package org.vertx.mods.async.strategies;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.results.ScriptableObjectResult;
import org.vertx.mods.async.tasks.SeriesMapTasks;

public class SeriesMap {

  private final SeriesMap that = this;

  private final SeriesMapTasks tasks;
  private final AsyncResultCallback callback;

  /* Constructors */
  public SeriesMap(SeriesMapTasks tasks) {
    this.tasks = tasks;
    this.callback = null;
  }

  public SeriesMap(SeriesMapTasks tasks, AsyncResultCallback callback) {
    this.tasks = tasks;
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final ExceptionHandler exceptionHandler = new ExceptionHandler(this.callback);

    final Map<String, Object> results = new ScriptableObjectResult();
    final List<String> keys = new LinkedList<>(this.tasks.getNames());
    final Set<Integer> done = new HashSet<>(this.tasks.size());

    final class SeriesDelegate implements ExecutionDelegate {
      private int index = 0;

      public SeriesDelegate() {
      }

      @Override
      public void taskComplete(Object value) {
        String key = keys.get(this.index);
        results.put(key, value);
        done.add(this.index);

        this.index++;
      }

      @Override
      public boolean isAllComplete() {
        return done.size() == that.tasks.size();
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
        return done.size() != that.tasks.size();
      }

      @Override
      public Task next() {
        String key = keys.get(this.index);
        return that.tasks.get(key);
      }
    }

    /* Begin */
    new ExecutionController(new SeriesDelegate());
  }

}
