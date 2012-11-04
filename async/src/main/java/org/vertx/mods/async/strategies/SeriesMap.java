package org.vertx.mods.async.strategies;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.deploy.impl.VertxLocator;
import org.vertx.mods.async.AsyncResultCallback;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.executors.TaskExecutor;
import org.vertx.mods.async.executors.TaskMapExecutor;

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

    final class SeriesExecutor extends TaskMapExecutor {
      public SeriesExecutor(Object key, Map<Object, Object> results) {
        super(key, results);
      }

      @Override
      public void complete() {
        if (keys.size() == 0) {
          that.callback.result(null, results);
          return;
        }

        Object key = keys.remove(0);
        that.executeTask(key, new SeriesExecutor(key, results));
      }

      @Override
      public void exception(String error) {
        System.out.println("Error!" + error);
      }
    }

    Object key = keys.remove(0);
    this.executeTask(key, new SeriesExecutor(key, results));
  }

  private void executeTask(final Object key, final TaskExecutor executor) {
    final Vertx vertx = VertxLocator.vertx;
    vertx.runOnLoop(new Handler<Void>() {
      @Override
      public void handle(Void event) {
        Task t = that.tasks.get(key);

        t.perform(executor);
      }
    });
  }
}
