package org.vertx.mods.async.containers;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.vertx.mods.async.ResultHandler;
import org.vertx.mods.async.Task;

public class SeriesList {

  private final List<Task> tasks;
  private final ResultHandler callback;

  /* Constructors */
  public SeriesList(Task[] tasks) {
    this.tasks = Arrays.asList(tasks);
    this.callback = null;
  }

  public SeriesList(Task[] tasks, ResultHandler callback) {
    this.tasks = Arrays.asList(tasks);
    this.callback = callback;
  }

  public SeriesList(Collection<Task> tasks) {
    this.tasks = new LinkedList<>(tasks);
    this.callback = null;
  }

  public SeriesList(Collection<Task> tasks, ResultHandler callback) {
    this.tasks = new LinkedList<>(tasks);
    this.callback = callback;
  }



  /* Perform */
  public void perform() {
    final LinkedList<Object> results = new LinkedList<>();

    try {
      for (Task t : this.tasks) {
        Object result = t.perform();

        System.out.println(result);
        results.add(result);
      }

      if (this.callback != null) {
        this.callback.result(results, null);
      }
    } catch (Exception e) {
      if (this.callback != null) {
        this.callback.result((List) null, e.getMessage());
      }
    }
  }
}
