package com.ukiyomo.nukkitp.core.client.task;

import com.ukiyomo.nukkitp.core.client.CorePlugin;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import java.io.IOException;

public class TaskManager {

    public void pullTask(Task task, TaskCallback taskCallback) {

        task.action();

        CorePlugin.getInstance().getCorePluginContext().getHttpManager().doPost(task.forwardLocation(), toRequestBody(task), (response) -> {
            ResponseBody body = response.body();
            if(null != body) {
                try {
                    taskCallback.callback(true, body.string());
                } catch (IOException ignore) {
                    taskCallback.callback(true, null);
                }
            } else {
                taskCallback.callback(true, null);
            }
        }, () -> taskCallback.callback(false, null));

    }

    private RequestBody toRequestBody(Task task) {

        FormBody.Builder builder = new FormBody.Builder();
        task.paramIterator().forEach(v -> builder.add(v.getKey(), v.getValue()));
        return builder.build();
    }
}
