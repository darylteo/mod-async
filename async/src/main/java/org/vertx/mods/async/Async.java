package org.vertx.mods.async;

import org.vertx.mods.async.strategies.Auto;
import org.vertx.mods.async.strategies.ParallelList;
import org.vertx.mods.async.strategies.ParallelMap;
import org.vertx.mods.async.strategies.SeriesList;
import org.vertx.mods.async.strategies.SeriesMap;
import org.vertx.mods.async.tasks.AutoMapTasks;
import org.vertx.mods.async.tasks.ParallelListTasks;
import org.vertx.mods.async.tasks.ParallelMapTasks;
import org.vertx.mods.async.tasks.SeriesListTasks;
import org.vertx.mods.async.tasks.SeriesMapTasks;

public class Async {

  /* Async Flow Controllers */

  /**
   * Run each task one after another in isolation. If one of the tasks results
   * in an error, all subsequent tasks are not attempted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public static void series(SeriesListTasks tasks, AsyncResultCallback callback) {
    new SeriesList(tasks, callback).perform();
  }

  /**
   * Run each task one after another in isolation. If one of the tasks results
   * in an error, all subsequent tasks are not attempted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public static void series(SeriesMapTasks tasks, AsyncResultCallback callback) {
    new SeriesMap(tasks, callback).perform();
  }

  /**
   * Performs each task one after another, with the result of a task passed to
   * the subsequent task. If one of the tasks results in an error, all
   * subsequent tasks are not attempted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public static void waterfall(Object tasks, AsyncResultCallback callback) {

  }

  /**
   * Performs each task in parallel at the same time. If one of the tasks
   * results in an error, the process is immediately aborted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public static void parallel(ParallelListTasks tasks, AsyncResultCallback callback) {
    new ParallelList(tasks, callback).perform();
  }

  /**
   * Performs each task in parallel at the same time. If one of the tasks
   * results in an error, the process is immediately aborted.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs.
   */
  public static void parallel(ParallelMapTasks tasks, AsyncResultCallback callback) {
    new ParallelMap(tasks, callback).perform();
  }

  /**
   * Repeatedly performs the given task, while test() returns true.
   * 
   * The callback is invoked when test() returns false, or immediately when an
   * error occurs.
   */
  public static void whilst(Object task, Object test, AsyncResultCallback callback) {

  }

  /**
   * Repeatedly performs the given task, until test() returns true.
   * 
   * The callback is invoked when test() returns true, or immediately when an
   * error occurs.
   * 
   * This is the inverse operation of whilst.
   */
  public static void until(Object task, Object test, AsyncResultCallback callback) {

  }

  /**
   * Creates and returns a Queue that processes items based on the given task.
   * Each item that is added will be passed to the worker to process. Multiple
   * instances of the worker can also be specified, allowing items to be
   * processed in parallel by each worker.
   * 
   * The worker callback is invoked after each item is processed, or immediately
   * when an error occurs.
   */
  public static Object queue(Object worker, int workers) {
    return null;
  }

  /**
   * Performs the given tasks in an order based on the dependencies specified
   * for each task. This is useful when tasks depend on the completion of other
   * tasks, to a complex degree.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs
   */
  public static void auto(AutoMapTasks tasks, AsyncResultCallback callback) {
    new Auto(tasks, callback);
  }

  /**
   * Provides an iterator that performs the next task in an array of tasks.
   * 
   * The callback is invoked when all tasks are completed, or immediately when
   * an error occurs
   */
  public static void iterator(Object tasks) {

  }

  /**
   * Provides an iterator that performs a task repeatedly for each argument
   * provided.
   */
  public static void apply(Object task, Object arguments) {

  }

  /*
   * public static void nextTick() {
   * 
   * }
   */
}
