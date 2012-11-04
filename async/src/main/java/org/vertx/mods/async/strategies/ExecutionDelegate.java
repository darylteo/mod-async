package org.vertx.mods.async.strategies;

import org.vertx.mods.async.Task;

public interface ExecutionDelegate {
  public abstract void taskComplete(Object value);

  public abstract void allComplete();

  public abstract void exception(String message);

  public abstract boolean shouldContinue();

  public abstract Task next();
}
