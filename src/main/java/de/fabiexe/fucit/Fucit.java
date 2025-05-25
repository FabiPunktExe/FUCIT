package de.fabiexe.fucit;

import de.fabiexe.fucit.util.FucitClassLoader;
import java.lang.instrument.Instrumentation;
import net.lenni0451.classtransform.TransformerManager;
import net.lenni0451.classtransform.utils.FailStrategy;
import net.lenni0451.classtransform.utils.tree.BasicClassProvider;
import org.jetbrains.annotations.NotNull;

public class Fucit {
    private static FucitClassLoader classLoader;
    private static TransformerManager transformerManager;

    public static void premain(String args, Instrumentation instrumentation) {
        classLoader = new FucitClassLoader();
        transformerManager = new TransformerManager(new BasicClassProvider(classLoader));
        transformerManager.setFailStrategy(FailStrategy.CONTINUE);
        transformerManager.addTransformer("de.fabiexe.fucit.transformer.PaperclipTransformer");
        transformerManager.addTransformer("de.fabiexe.fucit.transformer.ServerPluginProviderStorageTransformer");
        transformerManager.hookInstrumentation(instrumentation);
    }

    public static @NotNull FucitClassLoader getClassLoader() {
        return classLoader;
    }

    public static @NotNull TransformerManager getTransformerManager() {
        return transformerManager;
    }
}
