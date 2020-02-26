package com.ukiyomo.nukkitp.core.server.command.space;


import com.ukiyomo.nukkitp.core.server.utils.space.MethodSpace;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandMethodSpace extends MethodSpace {

    private CommandMethod mapping;

    protected String value;
    protected int length;

    public CommandMethodSpace(Method method) {
        super(method);
    }

    @Override
    public void init() {
        super.init();

        CommandMethod mapping = method.getAnnotation(CommandMethod.class);
        if (mapping == null) {
            // TODO: throw
            return;
        }
        this.name = mapping.value();
        this.mapping = mapping;
        this.value = this.getMapping().value();

        if (this.getMapping().length() == 0) {
            this.length = this.paramTypeArray.size();
        } else {
            this.length = this.getMapping().length();
        }
    }

    public CommandMethod getMapping() {
        return mapping;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public boolean run(Object instance, String... args)
            throws InvocationTargetException, IllegalAccessException {
        return (boolean) super.run(instance, (Object[]) args);
    }
}
