package com.ukiyomo.nukkitp.core.server.command;

import com.ukiyomo.nukkitp.core.server.command.defaults.QuitCommand;
import com.ukiyomo.nukkitp.core.server.command.space.CommandType;
import com.ukiyomo.nukkitp.core.server.command.space.CommandTypeSpace;
import com.ukiyomo.nukkitp.core.server.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager {

    private Map<String, CommandTypeSpace> spaceMap = new HashMap<>();

    public CommandManager() {
        this.init();
    }

    public void register(Class<? extends Command> commandClass) {
        CommandType ann = commandClass.getAnnotation(CommandType.class);
        if (ann == null) {
            // TODO: throw;
            return;
        }
        String name;
        if ("".equals(ann.name())) {
            name = commandClass.getSimpleName();
        } else {
            name = ann.name();
        }
        if (spaceMap.containsKey(name)) {
            // TODO: throw ;
            return;
        }
        spaceMap.put(name, new CommandTypeSpace(commandClass, name));
    }

    private void registerCoreCommand() {
        register(QuitCommand.class);
    }

    private void init() {

        registerCoreCommand();

        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {

            one : while(true) {
                String oldStr = scanner.nextLine();
                String newStr;

                // 清理多余空格
                do {
                    newStr = oldStr.replace("  ", " ");
                } while (!newStr.equals(oldStr));

                String[] args = newStr.split(" ");

                if (args.length == 0 || "".equals(newStr.replace(" ", ""))) {
                    // TODO: log
                    System.out.println("别瞎JB乱搞");
                    continue;
                }
                if (!spaceMap.containsKey(args[0])) {
                    // TODO: log
                    System.out.println("指令 " + args[0] + " 不存在");
                    continue;
                }

                CommandTypeSpace space = spaceMap.get(args[0]);
                try {
                    String[] newArgs = Utils.shearArray(args, 1, args.length);
                    //System.out.println(space.getInstance() == null);
                    if (space.getInstance().execute(new ConsoleCommandSender(), newArgs)) {
                        // 执行成功
                        continue;
                    }

                    StringBuilder sb = new StringBuilder();

                    for (int i = 1; i < args.length; i++) {
                        sb.append(newArgs[0]);
                        newArgs = Utils.shearArray(args, 1, args.length);
                        if (space.containsKey(sb.toString())) {
                            if (space.run(args[0], newArgs)) {
                                // 执行成功
                                continue one;
                            }
                        }
                        sb.append(" ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // TODO: log
                System.out.println("指令 " + args[0] + " 执行失败");
            }

        }).start();
    }

}
