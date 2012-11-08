package org.vertx.mods.async.strategies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.results.ScriptableObjectResult;
import org.vertx.mods.async.tasks.AutoMapTasks;

public class Auto {

  private final Auto that = this;

  private final AutoMapTasks tasks;
  private final AsyncResultCallback callback;

  private final ScriptableObjectResult results;
  private final ExceptionHandler exceptionHandler;

  public Auto(AutoMapTasks tasks) {
    this(tasks, null);
  }

  public Auto(AutoMapTasks tasks, AsyncResultCallback callback) {
    this.tasks = tasks;
    this.callback = callback;

    this.results = new ScriptableObjectResult();
    this.exceptionHandler = new ExceptionHandler(this.callback);
  }

  /* Perform */
  public void perform() {

    final List<String> keys = new LinkedList<>(this.tasks.getNames());

    /* Begin */

    final List<SeriesDelegate> delegates = new LinkedList<>();

    for (String key : keys) {
      delegates.add(new SeriesDelegate(key, that.tasks.getDependentTasks(key)));
    }

  }

  private final class SeriesDelegate implements ExecutionDelegate {
    private String key;
    private Set<String> deps;

    public SeriesDelegate(String key, Set<String> deps) {
      this.key = key;
      this.deps = deps;
    }

    @Override
    public void taskComplete(Object value) {
      that.results.put(key, value);
    }

    @Override
    public boolean isAllComplete() {
      return false;
    }

    @Override
    public void allComplete() {
      that.callback.result(null, results);
    }

    @Override
    public void exception(String error) {
      that.exceptionHandler.exception(error);
    }

    @Override
    public boolean shouldContinue() {
      return false;
    }

    @Override
    public Task next() {
      return that.tasks.get(this.key);
    }
  }
}
