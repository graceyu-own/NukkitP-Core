package com.ukiyomo.nukkitp.core.client;


import com.ukiyomo.nukkitp.core.client.command.CommandManager;
import com.ukiyomo.nukkitp.core.client.form.FormManager;
import com.ukiyomo.nukkitp.core.client.http.HttpManager;
import com.ukiyomo.nukkitp.core.client.network.NetworkManager;
import com.ukiyomo.nukkitp.core.client.task.TaskManager;

public class CorePluginContext {

    private FormManager formManager;
    private TaskManager taskManager;
    private CommandManager commandManager;
    private HttpManager httpManager;
    private NetworkManager networkManager;

    public FormManager getFormManager() {
        return formManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public HttpManager getHttpManager() {
        return httpManager;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {

        private CorePluginContext context = new CorePluginContext();

        public Builder commandManager(CommandManager val) {
            context.commandManager = val;
            return this;
        }

        public Builder formManager(FormManager val) {
            context.formManager = val;
            return this;
        }

        public Builder httpManager(HttpManager val) {
            context.httpManager = val;
            return this;
        }

        public Builder networkManager(NetworkManager val) {
            context.networkManager = val;
            return this;
        }

        public Builder taskManager(TaskManager val) {
            context.taskManager = val;
            return this;
        }

        public CorePluginContext build() {
            return context;
        }

    }

}
