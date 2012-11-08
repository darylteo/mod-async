package org.vertx.mods.async.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.vertx.mods.async.Task;

public class ParallelMapTasks {
  private Map<String, Task> tasks;

  public ParallelMapTasks() {
    this.tasks = new HashMap<>();
  }

  public ParallelMapTasks(Map<String, Task> tasks) {
    this.tasks = new HashMap<>(tasks);
  }

  public ParallelMapTasks add(String name, Task task) {
    this.tasks.put(name, task);
    return this;
  }

  public Set<String> getNames() {
    return this.tasks.keySet();
  }

  public Task get(String name) {
    return this.tasks.get(name);
  }

  public int size() {
    return this.tasks.size();
  }

}
