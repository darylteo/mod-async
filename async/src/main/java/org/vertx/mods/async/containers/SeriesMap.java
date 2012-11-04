package org.vertx.mods.async.containers;

import java.util.HashMap;
import java.util.Map;

import org.vertx.mods.async.ResultHandler;
import org.vertx.mods.async.Task;

public class SeriesMap {

  private final Map<Object, Task> tasks;
  private final ResultHandler callback;

  public SeriesMap(Map<Object, Task> tasks) {
    this.tasks = tasks;
    this.callback = null;
  }

  public SeriesMap(Map<Object, Task> tasks, ResultHandler callback) {
    this.tasks = tasks;
    this.callback = callback;
  }

  /* Perform */
  public void perform() {
    final Map<Object, Object> results = new HashMap<>();

    try {
      for (Map.Entry<Object, Task> t : this.tasks.entrySet()) {
        Object result = t.getValue().perform();

        System.out.printf("%s: %s\n", t.getKey(), result);
        results.put(t.getKey(), result);
      }

      if (this.callback != null) {
        this.callback.result(results, null);
      }
    } catch (Exception e) {
      if (this.callback != null) {
        this.callback.result((Map<String, Object>) null, e.getMessage());
      }
    }
  }
}
