package org.vertx.mods.async;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;
import org.vertx.mods.async.tasks.AutoMapTasks;
import org.vertx.mods.async.tasks.ParallelListTasks;
import org.vertx.mods.async.tasks.ParallelMapTasks;
import org.vertx.mods.async.tasks.SeriesListTasks;
import org.vertx.mods.async.tasks.SeriesMapTasks;

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
  public void series(NativeArray obj, AsyncResultCallback callback) {
    List<Object> converted = convertNativeArray(obj);
    SeriesListTasks tasks = new SeriesListTasks();

    for (Object task : converted) {
      tasks.add((Task) task);
    }

    Async.series(tasks, callback);
  }

  /**
   * Run each task one after another in isolation. If one of the tasks results
   * in an error, all subsequent tasks are not attempted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public void series(NativeObject obj, AsyncResultCallback callback) {
    Map<String, Object> converted = convertNativeObject(obj);
    SeriesMapTasks tasks = new SeriesMapTasks();

    for (Map.Entry<String, Object> task : converted.entrySet()) {
      tasks.add(task.getKey(), (Task) task.getValue());
    }

    Async.series(tasks, callback);
  }

  /**
   * Performs each task in parallel at the same time. If one of the tasks
   * results in an error, the process is immediately aborted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public void parallel(NativeArray obj, AsyncResultCallback callback) {
    List<Object> converted = convertNativeArray(obj);
    ParallelListTasks tasks = new ParallelListTasks();

    for (Object task : converted) {
      tasks.add((Task) task);
    }

    Async.parallel(tasks, callback);
  }

  /**
   * Performs each task in parallel at the same time. If one of the tasks
   * results in an error, the process is immediately aborted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public void parallel(NativeObject obj, AsyncResultCallback callback) {
    Map<String, Object> converted = convertNativeObject(obj);
    ParallelMapTasks tasks = new ParallelMapTasks();

    for (Map.Entry<String, Object> task : converted.entrySet()) {
      tasks.add(task.getKey(), (Task) task.getValue());
    }

    Async.parallel(tasks, callback);
  }

  /**
   * Performs the given tasks in an order based on the dependencies specified
   * for each task. This is useful when tasks depend on the completion of other
   * tasks, to a complex degree.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs
   */
  public void auto(NativeObject obj, AsyncResultCallback callback) {
    Map<String, Object> converted = convertNativeObject(obj);
    AutoMapTasks tasks = new AutoMapTasks();

    for (Map.Entry<String, Object> task : converted.entrySet()) {
      String name = task.getKey();
      Object value = task.getValue();

      if (value instanceof List<?>) {
        /* Dependencies Included */
        List<Object> params = (List<Object>) value;
        List<String> deps = new LinkedList<>();

        for (int i = 0; i < params.size() - 2; i++) {
          deps.add((String) params.get(i));
        }

        tasks.add(name, (Task) params.get(params.size() - 1), deps.toArray(new String[0]));
      } else {
        tasks.add(name, (Task) value);
      }
    }

    Async.auto(tasks, callback);
  }

  private List<Object> convertNativeArray(NativeArray arr) {
    List<Object> list = new LinkedList<>();

    for (int i = 0; i < arr.size(); i++) {
      Object value = arr.get(i);

      if (value instanceof NativeObject) {
        value = convertNativeObject((NativeObject) value);
      } else if (value instanceof NativeArray) {
        value = convertNativeArray((NativeArray) value);
      } else if (value instanceof NativeFunction) {
        value = Context.jsToJava(value, Task.class);
      }

      list.add(value);
    }

    return list;
  }

  private Map<String, Object> convertNativeObject(NativeObject obj) {
    Object[] properties = ScriptableObject.getPropertyIds(obj);
    Map<String, Object> map = new HashMap<>();

    for (Object property : properties) {
      Object value = obj.get(property);

      if (value instanceof NativeObject) {
        value = convertNativeObject((NativeObject) value);
      } else if (value instanceof NativeArray) {
        value = convertNativeArray((NativeArray) value);
      } else if (value instanceof NativeFunction) {
        value = Context.jsToJava(value, Task.class);
      }

      map.put((String) property, value);
    }

    return map;
  }
}
