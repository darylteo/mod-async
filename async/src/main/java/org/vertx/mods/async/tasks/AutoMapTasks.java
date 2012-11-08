package org.vertx.mods.async.tasks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.vertx.mods.async.Task;

public class AutoMapTasks {
  private Map<String, Task> tasks;
  private Map<String, Set<String>> dependentTasks;

  public AutoMapTasks() {
    this.tasks = new HashMap<>();
    this.dependentTasks = new HashMap<>();
  }

  public AutoMapTasks(Map<String, Task> tasks) {
    this.tasks = tasks;
    this.dependentTasks = new HashMap<>();
  }

  public AutoMapTasks(Map<String, Task> tasks, Map<String, Set<String>> dependentTasks) {
    this.tasks = tasks;
    this.dependentTasks = dependentTasks;
  }

  public AutoMapTasks add(String name, Task task) {
    this.tasks.put(name, task);
    return this;
  }

  public AutoMapTasks add(String name, Task task, String... dependencies) {
    this.tasks.put(name, task);

    for (String dep : dependencies) {
      Set<String> tasks = dependentTasks.get(dep);

      if (tasks == null) {
        tasks = new HashSet<String>();
        dependentTasks.put(dep, tasks);
      }

      tasks.add(name);
    }

    return this;
  }

  public Set<String> getNames() {
    return this.tasks.keySet();
  }

  public Set<String> getDependentTasks(String name) {
    return this.dependentTasks.get(name);
  }

  public Task get(String name) {
    return this.tasks.get(name);
  }

  public int size() {
    return this.tasks.size();
  }

}
