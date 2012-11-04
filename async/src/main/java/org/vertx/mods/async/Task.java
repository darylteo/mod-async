package org.vertx.mods.async;

public interface Task {
  public void perform(TaskResultCallback callback);
}
