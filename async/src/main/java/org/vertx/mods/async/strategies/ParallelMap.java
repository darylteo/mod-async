package org.vertx.mods.async.strategies;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.results.ScriptableObjectResult;
import org.vertx.mods.async.tasks.ParallelMapTasks;

public class ParallelMap {
  private final ParallelMap that = this;

  private final ParallelMapTasks tasks;
  private final AsyncResultCallback callback;

  /* Constructors */
  public ParallelMap(ParallelMapTasks tasks) {
    this.tasks = tasks;
    this.callback = null;
  }

  public ParallelMap(ParallelMapTasks tasks, AsyncResultCallback callback) {
    this.tasks = tasks;
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final ExceptionHandler exceptionHandler = new ExceptionHandler(this.callback);

    final Map<String, Object> results = new ScriptableObjectResult();
    final List<String> keys = new LinkedList<>(this.tasks.getNames());
    final Set<Integer> done = new HashSet<>(this.tasks.size());

    final class ParallelDelegate implements ExecutionDelegate {
      private int index;

      public ParallelDelegate(int index) {
        this.index = index;
      }

      @Override
      public void taskComplete(Object value) {
        String key = keys.get(this.index);
        results.put(key, value);
        done.add(this.index);
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
        return false;
      }

      @Override
      public Task next() {
        String key = keys.get(this.index);
        return (Task) that.tasks.get(key);
      }
    }

    for (int i = 0; i < keys.size(); i++) {
      new ExecutionController(new ParallelDelegate(i));
    }
  }
}
