package de.fabiexe.fucit.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class FucitClassLoader extends URLClassLoader {
    private final Set<ClassLoader> children = new HashSet<>();

    public FucitClassLoader() {
        super(new URL[0]);
        children.add(this);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException exception) {
            for (ClassLoader classLoader : children) {
                if (classLoader == this || classLoader == getParent() || classLoader.getParent() == this) {
                    continue;
                }
                try {
                    return classLoader.loadClass(name);
                } catch (Throwable ignored) {
                }
            }
            throw exception;
        }
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream stream = super.getResourceAsStream(name);
        if (stream != null) {
            return stream;
        }
        for (ClassLoader classLoader : children) {
            if (classLoader == this || classLoader == getParent() || classLoader.getParent() == this) {
                continue;
            }
            stream = classLoader.getResourceAsStream(name);
            if (stream != null) {
                return stream;
            }
        }
        return null;
    }

    public @NotNull Set<ClassLoader> getChildren() {
        return children;
    }
}
