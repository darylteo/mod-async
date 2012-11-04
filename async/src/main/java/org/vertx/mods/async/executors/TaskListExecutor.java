package org.vertx.mods.async.executors;

import java.util.List;

import org.mozilla.javascript.annotations.JSFunction;

public abstract class TaskListExecutor extends TaskExecutor {

  private final List<Object> results;
  private final int index;

  public TaskListExecutor(int index, List<Object> results) {
    this.index = index;
    this.results = results;
  }

  public int getIndex() {
    return this.index;
  }

  //
  // @Override
  // public void result(Object e, Byte value) {
  // if (e != null) {
  // this.exception();
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, Integer value) {
  // if (e != null) {
  // this.exception();
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, Short value) {
  // if (e != null) {
  // this.exception();
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, Long value) {
  // if (e != null) {
  // this.exception();
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, Float value) {
  // if (e != null) {
  // this.exception();
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, Double value) {
  // if (e != null) {
  // this.exception();
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }

  // @Override
  // public void result(Object e, Number value) {
  // if (e != null) {
  // this.exception(e.toString());
  // return;
  // }
  //
  // if(value.)
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, Boolean value) {
  // if (e != null) {
  // this.exception(e.toString());
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, Character value) {
  // if (e != null) {
  // this.exception(e.toString());
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }
  //
  // @Override
  // public void result(Object e, String value) {
  // if (e != null) {
  // this.exception(e.toString());
  // return;
  // }
  //
  // this.results.add(index, value);
  // this.complete();
  // }

  @Override
  @JSFunction()
  public void result(Object e, Object value) {
    if (e != null) {
      this.exception(e.toString());
      return;
    }

    this.results.add(index, value);
    this.complete();
  }

}