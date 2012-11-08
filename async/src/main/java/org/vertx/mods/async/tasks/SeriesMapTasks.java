package org.vertx.mods.async.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.vertx.mods.async.Task;

public class SeriesMapTasks {
  private Map<String, Task> tasks;

  public SeriesMapTasks() {
    this.tasks = new HashMap<>();
  }

  public SeriesMapTasks(Map<String, Task> tasks) {
    this.tasks = new HashMap<>(tasks);
  }

  public SeriesMapTasks add(String name, Task task) {
    this.tasks.put(name, task);
    return this;
  }

  public Set<String> getNames(){
    return this.tasks.keySet();
  }
  
  public Task get(String name) {
    return this.tasks.get(name);
  }

  public int size() {
    return this.tasks.size();
  }

}
