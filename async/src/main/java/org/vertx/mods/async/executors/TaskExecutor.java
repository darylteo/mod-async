package org.vertx.mods.async.executors;

import org.vertx.mods.async.TaskResultCallback;

public abstract class TaskExecutor implements TaskResultCallback {

  private boolean completed = false;

  public abstract void complete();

  public abstract void exception(String exception);

  void beforeComplete(){
    if(completed){
      exception("Task already completed");
      return;
    }

    completed = true;
  }
}
