package org.vertx.mods.async;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;

public class AsyncControllerJS {
  public static Object getJSModule() {
    return new AsyncControllerJS();
  }

  /**
   * Run each task one after another in isolation. If one of the tasks results
   * in an error, all subsequent tasks are not attempted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public void series(NativeArray tasks, AsyncResultCallback callback) {
    AsyncController.series(convertNativeArrayToTaskArray(tasks), callback);
  }

  /**
   * Run each task one after another in isolation. If one of the tasks results
   * in an error, all subsequent tasks are not attempted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public void series(NativeObject tasks, AsyncResultCallback callback) {
    AsyncController.series(convertNativeObjectToTaskMap(tasks), callback);
  }

  /**
   * Performs each task in parallel at the same time. If one of the tasks
   * results in an error, the process is immediately aborted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public void parallel(NativeArray tasks, AsyncResultCallback callback) {
    AsyncController.parallel(convertNativeArrayToTaskArray(tasks), callback);
  }

  /**
   * Performs each task in parallel at the same time. If one of the tasks
   * results in an error, the process is immediately aborted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public void parallel(NativeObject tasks, AsyncResultCallback callback) {
    AsyncController.parallel(convertNativeObjectToTaskMap(tasks), callback);
  }

  private Task[] convertNativeArrayToTaskArray(NativeArray arr) {
    Task[] copiedTasks = new Task[(int) arr.getLength()];
    for (int i = 0; i < arr.getLength(); i++) {
      Task t = (Task) Context.jsToJava(arr.get(i), Task.class);
      copiedTasks[i] = t;
    }

    return copiedTasks;
  }

  private Map<Object, Task> convertNativeObjectToTaskMap(NativeObject obj) {
    Object[] properties = ScriptableObject.getPropertyIds(obj);
    Map<Object, Task> tasks = new HashMap<>();

    for (Object property : properties) {
      Task t = (Task) Context.jsToJava(obj.get(property), Task.class);
      tasks.put(property, t);
    }

    return tasks;
  }

}
