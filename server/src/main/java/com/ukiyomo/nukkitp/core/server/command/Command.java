package com.ukiyomo.nukkitp.core.server.command;

public abstract class Command {

    /**
     * 执行命令的方法
     *
     * @param commandSender 指令发送者
     * @param args 命令的参数
     * @return 是否结束调用，false为结束，true不结束
     * 注意：返回true后将不在调起其他command func
     */
    abstract public boolean execute(CommandSender commandSender, String[] args);

}
