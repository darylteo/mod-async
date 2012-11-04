package org.vertx.mods.async.strategies;

import org.vertx.java.core.Handler;
import org.vertx.java.deploy.impl.VertxLocator;
import org.vertx.mods.async.Task;
import org.vertx.mods.async.TaskResultCallback;

public class ExecutionController {

  private final ExecutionController that = this;
  private final ExecutionDelegate delegate;
  private final TaskResultCallback callback = new Callback();

  public ExecutionController(ExecutionDelegate delegate) {
    this.delegate = delegate;

    this.nextTask();
  }

  private void nextTask() {
    VertxLocator.vertx.runOnLoop(new Handler<Void>() {
      @Override
      public void handle(Void event) {
        Task t = delegate.next();
        if (t == null) {
          delegate.exception("Task should not be null");
          return;
        }

        t.perform(callback);
      }
    });
  }

  private class Callback implements TaskResultCallback {
    @Override
    public void result(Object e, Object value) {
      /* Check for Exceptions */
      if (e != null) {
        delegate.exception(e.toString());
        return;
      }

      /* Signal that the currently processing task is completed */
      delegate.taskComplete(value);

      /* Check that all tasks are completed */
      if (delegate.isAllComplete()) {
        delegate.allComplete();
        return;
      }

      /* Check for subsequent tasks in this chain */
      if (delegate.shouldContinue()) {
        that.nextTask();
        return;
      }

      /*
       * If code reaches here, this means that this particular set of tasks has
       * been completed, but there are still other tasks executing in parallel
       */
    }

  }
}
