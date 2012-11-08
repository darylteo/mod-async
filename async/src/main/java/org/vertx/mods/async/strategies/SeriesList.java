package org.vertx.mods.async.strategies;

import java.util.List;

import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.results.ScriptableArrayResult;
import org.vertx.mods.async.tasks.SeriesListTasks;

public class SeriesList {
  private final SeriesList that = this;

  private final SeriesListTasks tasks;
  private final AsyncResultCallback callback;

  /* Constructors */
  public SeriesList(SeriesListTasks tasks) {
    this.tasks = tasks;
    this.callback = null;
  }

  public SeriesList(SeriesListTasks tasks, AsyncResultCallback callback) {
    this.tasks = tasks;
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final ExceptionHandler exceptionHandler = new ExceptionHandler(this.callback);

    final List<Object> results = new ScriptableArrayResult();

    final class SeriesDelegate implements ExecutionDelegate {
      private int index;

      public SeriesDelegate() {
        this.index = -1;
      }

      @Override
      public void taskComplete(Object value) {
        results.add(this.index, value);
      }

      @Override
      public boolean isAllComplete() {
        return this.index < that.tasks.size() - 1;
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
        return that.tasks.get(index);
      }
    }

    new ExecutionController(new SeriesDelegate());
  }
}
