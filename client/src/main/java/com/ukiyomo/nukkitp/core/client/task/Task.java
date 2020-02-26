package com.ukiyomo.nukkitp.core.client.task;


public abstract class Task extends TaskAction {

    public static final short TASK_ID = -1;

    public final long createTime = System.currentTimeMillis();

    protected boolean isUndo = false;

    public abstract String forwardLocation();



}
