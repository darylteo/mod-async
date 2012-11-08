package org.vertx.mods.async.strategies;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.results.ScriptableArrayResult;
import org.vertx.mods.async.tasks.ParallelListTasks;

public class ParallelList {
  private final ParallelList that = this;

  private final ParallelListTasks tasks;
  private final AsyncResultCallback callback;

  /* Constructors */
  public ParallelList(ParallelListTasks tasks) {
    this.tasks = tasks;
    this.callback = null;
  }

  public ParallelList(ParallelListTasks tasks, AsyncResultCallback callback) {
    this.tasks = tasks;
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final ExceptionHandler exceptionHandler = new ExceptionHandler(this.callback);

    final List<Object> results = new ScriptableArrayResult();
    final Set<Integer> done = new HashSet<>(this.tasks.size());

    final class ParallelDelegate implements ExecutionDelegate {
      private int index;

      public ParallelDelegate(int index) {
        this.index = index;
      }

      @Override
      public void taskComplete(Object value) {
        results.add(this.index, value);
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
        return that.tasks.get(this.index);
      }
    }

    for (int i = 0; i < this.tasks.size(); i++) {
      new ExecutionController(new ParallelDelegate(i));
    }
  }
}
