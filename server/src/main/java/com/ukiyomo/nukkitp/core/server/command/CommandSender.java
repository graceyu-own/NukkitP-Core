package com.ukiyomo.nukkitp.core.server.command;


import com.ukiyomo.nukkitp.core.server.CoreApplication;

public interface CommandSender {

    void senMessage(String message);

    CoreApplication getServer();

    String getName();

}
