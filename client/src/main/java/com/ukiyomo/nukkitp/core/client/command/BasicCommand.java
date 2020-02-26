package com.ukiyomo.nukkitp.core.client.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;

public abstract class BasicCommand extends Command{

    private CommandContext commandContext;

    public BasicCommand(String name, String description, String usageMessage) {
        super(name, description, usageMessage);
        commandContext = new CommandContext(this.getClass());
    }


    @Override
    public boolean execute(CommandSender commandSender, String s, String[] params){

        for (int i = Math.min(commandContext.getMaxMappingValueCount(), params.length); i >= 0; i--) {

            String key = implode(params, i);

            if(commandContext.getMethods().containsKey(key)) {

                try {

                    for (CommandContext.MethodSpace methodSpace : commandContext.getMethods().get(key)) {

                        int parameterCount = methodSpace.methodParams.size() - 1;
                        if(params.length - methodSpace.mappingValueCount == parameterCount) {
                            Object[] newS = new Object[parameterCount + 1];
                            newS[0] = commandSender;
                            System.arraycopy(params, methodSpace.mappingValueCount, newS, 1, parameterCount);
                            methodSpace.method.invoke(this, newS);
                            return true;
                        }
                    }


                    CommandContext.MethodSpace methodSpace = commandContext.getRootMethods().get(key);
                    if(null != methodSpace) {
                        methodSpace.method.invoke(this, commandSender);
                    }

                    return true;

                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private String implode(String[] array, int length) {

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++) {
            s.append(array[i]).append(" ");
        }

        return s.toString().trim();
    }

}
