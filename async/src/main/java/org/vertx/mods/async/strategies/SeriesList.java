package org.vertx.mods.async.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.deploy.impl.VertxLocator;
import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.executors.TaskExecutor;
import org.vertx.mods.async.executors.TaskListExecutor;

public class SeriesList {
  private final SeriesList that = this;

  private final List<Task> tasks;
  private final AsyncResultCallback callback;

  /* Constructors */
  public SeriesList(Task[] tasks) {
    this.tasks = Arrays.asList(tasks);
    this.callback = null;
  }

  public SeriesList(Task[] tasks, AsyncResultCallback callback) {
    this.tasks = Arrays.asList(tasks);
    this.callback = callback;
  }

  public SeriesList(Collection<Task> tasks) {
    this.tasks = new LinkedList<>(tasks);
    this.callback = null;
  }

  public SeriesList(Collection<Task> tasks, AsyncResultCallback callback) {
    this.tasks = new LinkedList<>(tasks);
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final ExceptionHandler exceptionHandler = new ExceptionHandler(this.callback);

    final List<Object> results = new ArrayList<>(this.tasks.size());

    final class SeriesExecutor extends TaskListExecutor {
      public SeriesExecutor(int index, List<Object> results) {
        super(index, results);
      }

      @Override
      public void complete() {
        if (super.getIndex() == that.tasks.size() - 1) {
          that.callback.result(null, results);
          return;
        }

        that.executeTask(super.getIndex() + 1, new SeriesExecutor(super.getIndex() + 1, results));
      }

      @Override
      public void exception(String error) {
        System.out.println("Error!" + error);
      }
    }

    this.executeTask(0, new SeriesExecutor(0, results));
  }

  private void executeTask(final int index, final TaskExecutor executor) {
    final Vertx vertx = VertxLocator.vertx;
    vertx.runOnLoop(new Handler<Void>() {
      @Override
      public void handle(Void event) {
        Task t = that.tasks.get(index);

        t.perform(executor);
      }
    });
  }
}
