package com.ukiyomo.nukkitp.core.server.command.defaults;


import com.ukiyomo.nukkitp.core.server.command.Command;
import com.ukiyomo.nukkitp.core.server.command.CommandSender;
import com.ukiyomo.nukkitp.core.server.command.space.CommandType;

@CommandType(name = "quit")
public class QuitCommand extends Command {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        // TODO: log
        sender.senMessage("正在关闭服务");
        sender.getServer().shutdown();
        return false;
    }
}
