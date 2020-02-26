package com.ukiyomo.nukkitp.core.client.command;

import cn.nukkit.command.CommandSender;
import com.ukiyomo.nukkitp.core.client.command.annotation.CommandMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class CommandContext {

    private int maxMappingValueCount = 0;
    private Map<String, List<MethodSpace>> methods = new HashMap<>();
    private Map<String, MethodSpace> rootMethods = new HashMap<>();

    public CommandContext(Class<? extends BasicCommand> clazz) {
        init(clazz);
    }

    public Map<String, List<MethodSpace>> getMethods() {
        return methods;
    }

    public Map<String, MethodSpace> getRootMethods() {
        return rootMethods;
    }

    public int getMaxMappingValueCount() {
        return maxMappingValueCount;
    }

    private void init(Class<? extends BasicCommand> clazz) {

        for (Method method : clazz.getDeclaredMethods()) {

            Annotation found = this.getCommandMapping(method);

            if(null != found) {

                CommandMapping commandMapping = (CommandMapping) found;

                MethodSpace methodSpace = new MethodSpace();
                methodSpace.method = method;
                methodSpace.mapping = commandMapping;
                methodSpace.mappingValueCount = valueSplit(commandMapping.value()).length;

                if(method.getParameters().length == 1) {
                    rootMethods.put(commandMapping.value(), methodSpace);
                }

                if(methodSpace.mappingValueCount > maxMappingValueCount)
                    maxMappingValueCount = methodSpace.mappingValueCount;

                for (Parameter parameter : method.getParameters()) {
                    methodSpace.methodParams.put(parameter.getName(), parameter.getType());
                }

                if(methods.containsKey(commandMapping.value())) {
                    List<MethodSpace> methodSpaces = methods.get(commandMapping.value());
                    methodSpaces.add(methodSpace);
                    methods.put(commandMapping.value(), methodSpaces);
                } else {
                    List<MethodSpace> methodSpaces = new ArrayList<>();
                    methodSpaces.add(methodSpace);
                    methods.put(commandMapping.value(), methodSpaces);
                }
            }
        }
    }

    private String[] valueSplit(String value) {
        if(value.equals("")) return new String[]{};
        return value.split(" ");
    }

    private Annotation getCommandMapping(Method method) {
        Annotation found = null;

        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if(annotation instanceof CommandMapping) {
                found = annotation;
                break;
            }
        }

        return found;
    }

    public static class MethodSpace {

        public Method method;
        public Map<String, Class<?>> methodParams = new LinkedHashMap<>();
        public CommandMapping mapping;
        public int mappingValueCount;

    }

    public static class ExecSpace {

        public CommandSender commandSender;
        public boolean except;

        public ExecSpace(CommandSender commandSender, boolean except) {
            this.commandSender = commandSender;
            this.except = except;
        }
    }

}
