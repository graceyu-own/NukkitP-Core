package com.ukiyomo.nukkitp.core.client.task;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class TaskAction {

    private Map<String, String> params = new HashMap<>();
    private Queue<Task> undo = new LinkedBlockingQueue<>();

    public abstract void action();

    public void addParams(String key, String value) {
        if(null != key) {
            this.params.put(key, value);
        }
    }

    public Set<Map.Entry<String, String>> paramIterator() {
        return this.params.entrySet();
    }

    public void addUndo(Task task) {

        if(!(this instanceof TaskUndo)) {
            throw new RuntimeException("This task cannot undo.");
        } else {
            if(null != task) {
                task.isUndo = true;
                this.undo.offer(task);
            }
        }
    }

    public Iterator<Task> undoIterator() {
        return this.undo.iterator();
    }

}
