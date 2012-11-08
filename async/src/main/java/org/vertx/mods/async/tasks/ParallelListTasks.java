package org.vertx.mods.async.tasks;

import java.util.LinkedList;
import java.util.List;

import org.vertx.mods.async.Task;

public class ParallelListTasks {
  private List<Task> tasks;

  public ParallelListTasks() {
    this.tasks = new LinkedList<>();
  }

  public ParallelListTasks(Task[] tasks) {
    this.tasks = new LinkedList<>();
    for (Task t : tasks) {
      this.tasks.add(t);
    }
  }

  public ParallelListTasks(List<?> tasks) {
    this.tasks = new LinkedList<>();
    for (Object t : tasks) {
      this.tasks.add((Task) t);
    }
  }

  public ParallelListTasks add(Task task) {
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
