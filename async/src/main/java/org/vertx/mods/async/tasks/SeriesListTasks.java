package org.vertx.mods.async.tasks;

import java.util.LinkedList;
import java.util.List;

import org.vertx.mods.async.Task;

public class SeriesListTasks {
  private List<Task> tasks;

  public SeriesListTasks() {
    this.tasks = new LinkedList<>();
  }

  public SeriesListTasks(Task[] tasks) {
    this.tasks = new LinkedList<>();
    for (Task t : tasks) {
      this.tasks.add(t);
    }
  }

  public SeriesListTasks(List<?> tasks) {
    this.tasks = new LinkedList<>();
    for (Object t : tasks) {
      this.tasks.add((Task) t);
    }
  }

  public SeriesListTasks add(Task task) {
    this.tasks.add(task);
    return this;
  }

  public Task get(int index) {
    return this.tasks.get(index);
  }

  public int size() {
    return this.tasks.size();
  }

}
