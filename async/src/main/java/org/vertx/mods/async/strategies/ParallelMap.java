package org.vertx.mods.async.strategies;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.results.ScriptableObjectResult;

public class ParallelMap {
  private final ParallelMap that = this;

  private final Map<Object, Task> tasks;
  private final AsyncResultCallback callback;

  /* Constructors */
  public ParallelMap(Map<Object, Task> tasks) {
    this.tasks = tasks;
    this.callback = null;
  }

  public ParallelMap(Map<Object, Task> tasks, AsyncResultCallback callback) {
    this.tasks = tasks;
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final ExceptionHandler exceptionHandler = new ExceptionHandler(this.callback);

    final Map<Object, Object> results = new ScriptableObjectResult();
    final List<Object> keys = new LinkedList<>(this.tasks.keySet());
    final Set<Integer> done = new HashSet<>(this.tasks.size());

    final class ParallelDelegate implements ExecutionDelegate {
      private int index;

      public ParallelDelegate(int index) {
        this.index = index;
      }

      @Override
      public void taskComplete(Object value) {
        Object key = keys.get(this.index);
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
        Object key = keys.get(this.index);
        return that.tasks.get(key);
      }
    }

    for (int i = 0; i < keys.size(); i++) {
      new ExecutionController(new ParallelDelegate(i));
    }
  }
}
