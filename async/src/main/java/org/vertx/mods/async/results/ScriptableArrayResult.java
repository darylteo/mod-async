package org.vertx.mods.async.results;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ScriptableArrayResult extends ScriptableObject implements List<Object> {

  private static final long serialVersionUID = -6587770766655043278L;

  private List<Object> list;

  public ScriptableArrayResult() {
    this.list = new LinkedList<>();
  }

  /* Scriptable Object Overrides */
  @Override
  public Object getDefaultValue(Class<?> typeHint) {
    return this.toString();
  }

  @Override
  public String getClassName() {
    return this.getClass().getName();
  }

  @Override
  public Object get(int index, Scriptable start) {
    return this.list.get(index);
  }

  @Override
  public boolean add(Object e) {
    return this.list.add(e);
  }

  @Override
  public void add(int index, Object element) {
    this.list.add(index, element);
  }

  @Override
  public boolean addAll(Collection<? extends Object> c) {
    return this.list.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends Object> c) {
    return this.list.addAll(index, c);
  }

  @Override
  public void clear() {
    this.list.clear();
  }

  @Override
  public boolean contains(Object o) {
    return this.list.contains(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return this.list.containsAll(c);
  }

  @Override
  public Object get(int index) {
    return this.list.get(index);
  }

  @Override
  public int indexOf(Object o) {
    return this.list.indexOf(o);
  }

  @Override
  public Iterator<Object> iterator() {
    return this.list.iterator();
  }

  @Override
  public int lastIndexOf(Object o) {
    return this.list.lastIndexOf(o);
  }

  @Override
  public ListIterator<Object> listIterator() {
    return this.list.listIterator();
  }

  @Override
  public ListIterator<Object> listIterator(int index) {
    return this.list.listIterator(index);
  }

  @Override
  public boolean remove(Object o) {
    return this.list.remove(o);
  }

  @Override
  public Object remove(int index) {
    return this.list.remove(index);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return this.list.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return this.list.retainAll(c);
  }

  @Override
  public Object set(int index, Object element) {
    return this.list.set(index, element);
  }

  @Override
  public List<Object> subList(int fromIndex, int toIndex) {
    return this.list.subList(fromIndex, toIndex);
  }

  @Override
  public Object[] toArray() {
    return this.list.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return this.list.toArray(a);
  }
}
