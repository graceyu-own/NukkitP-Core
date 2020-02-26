package com.ukiyomo.nukkitp.core.server.command.space;

import com.ukiyomo.nukkitp.core.server.command.Command;
import com.ukiyomo.nukkitp.core.server.utils.Utils;
import com.ukiyomo.nukkitp.core.server.utils.space.TypeSpace;

import java.lang.reflect.Method;

public class CommandTypeSpace extends TypeSpace<CommandMethodSpace, CommandMethod> {

    public CommandTypeSpace(Class<? extends Command> aClass) {
        this(aClass, null);
    }

    public CommandTypeSpace(Class<? extends Command> aClass, String name) {
        super(aClass, CommandMethod.class, name);
    }

    @Override
    public void init() {
        super.init();

        Method[] methods = this.aClass.getMethods();
        for (Method method : methods) {
            CommandMethod func = method.getAnnotation(CommandMethod.class);
            if (func == null) {
                continue;
            }

            CommandMethodSpace space = new CommandMethodSpace(method);

            if (this.containsKey(space.getName())) {
                // TODO: throw;
                return;
            } else {
                this.put(space.getName(), space);
            }

        }
    }

    @Override
    public Command getInstance() {
        return (Command) super.getInstance();
    }

    public boolean run(String Command, String... args) throws Exception {

        if (Command == null || !Command.equals(this.getName())) {
            return false;
        }
        if (args == null || args.length < 1) {
            return false;
        }

        StringBuilder sb = new StringBuilder(args[0]);
        for (int i = 1; i < args.length; i++) {
            if (this.containsKey(sb.toString())) {
                CommandMethodSpace space = this.get(sb.toString());
                if (space.getLength() > -1) {
                    if (space.getLength() != args.length - i) {
                        continue;
                    }
                }

                String[] param = Utils.shearArray(args, i, args.length);
                if (space.run(this.getInstance(), param)) {
                    return true;
                }
            }
            sb.append(" ");
        }

        return false;
    }
}
