package org.vertx.mods.async.strategies;

import org.vertx.mods.async.AsyncResultCallback;

public class ExceptionHandler {
  private final AsyncResultCallback handler;

  public ExceptionHandler(AsyncResultCallback handler) {
    this.handler = handler;
  }

  public void exception(Exception e) {
    handler.result(e.getMessage(), null);
  }

  public void exception(String message) {
    handler.result(message, null);
  }
}
