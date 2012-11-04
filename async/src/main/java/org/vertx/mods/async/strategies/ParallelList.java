package org.vertx.mods.async.strategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.results.ScriptableArrayResult;

public class ParallelList {
  private final ParallelList that = this;

  private final List<Task> tasks;
  private final AsyncResultCallback callback;

  /* Constructors */
  public ParallelList(Task[] tasks) {
    this.tasks = Arrays.asList(tasks);
    this.callback = null;
  }

  public ParallelList(Task[] tasks, AsyncResultCallback callback) {
    this.tasks = Arrays.asList(tasks);
    this.callback = callback;
  }

  public ParallelList(Collection<Task> tasks) {
    this.tasks = new LinkedList<>(tasks);
    this.callback = null;
  }

  public ParallelList(Collection<Task> tasks, AsyncResultCallback callback) {
    this.tasks = new LinkedList<>(tasks);
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
