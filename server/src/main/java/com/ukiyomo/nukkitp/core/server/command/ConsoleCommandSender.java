package com.ukiyomo.nukkitp.core.server.command;


import com.ukiyomo.nukkitp.core.server.CoreApplication;

public class ConsoleCommandSender implements CommandSender {

    @Override
    public void senMessage(String message) {
        System.out.println(message);
    }

    @Override
    public CoreApplication getServer() {
        return CoreApplication.getInstance();
    }

    @Override
    public String getName() {
        return "Console";
    }
}
