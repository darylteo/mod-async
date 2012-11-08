package org.vertx.mods.async.strategies;

import org.vertx.mods.async.AsyncResultCallback;

public class ExceptionHandler {
  private final AsyncResultCallback handler;

  public ExceptionHandler(AsyncResultCallback handler) {
    this.handler = handler;
  }

  public void exception(Exception e) {
    if (handler == null) {
      return;
    }

    handler.result(e.getMessage(), null);
  }

  public void exception(String message) {
    if (handler == null) {
      return;
    }

    handler.result(message, null);
  }
}
