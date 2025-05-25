package de.fabiexe.fucit.transformer;

import com.google.common.reflect.ClassPath;
import de.fabiexe.fucit.Fucit;
import io.papermc.paper.plugin.provider.PluginProvider;
import io.papermc.paper.plugin.storage.ServerPluginProviderStorage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.zip.ZipEntry;
import net.lenni0451.classtransform.annotations.CInline;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@CTransformer(ServerPluginProviderStorage.class)
public class ServerPluginProviderStorageTransformer {
    @CInline
    @CInject(method = "processProvided", target = @CTarget("HEAD"))
    public void processProvided(PluginProvider<JavaPlugin> provider, JavaPlugin provided) {
        try {
            provided.getSLF4JLogger().info("Loading class transformers of server plugin {}", provided.getPluginMeta().getDisplayName());

            ZipEntry entry = provider.file().getEntry("paper-plugin.yml");
            if (entry == null) {
                return;
            }

            InputStream in = provider.file().getInputStream(entry);
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new InputStreamReader(in));
            in.close();

            if (yaml.isList("class-transformers")) {
                Fucit.getClassLoader().getChildren().add(provided.getClass().getClassLoader());
                for (String transformer : yaml.getStringList("class-transformers")) {
                    try {
                        Class<?> clazz = Class.forName(transformer, false, provided.getClass().getClassLoader());
                        provided.getSLF4JLogger().info("Applying class transformer {}", clazz.getName());
                        Fucit.getTransformerManager().addTransformer(clazz.getName());
                    } catch (ClassNotFoundException e) {
                        ClassPath classPath = ClassPath.from(provided.getClass().getClassLoader());
                        Set<ClassPath.ClassInfo> classInfos = classPath.getTopLevelClassesRecursive(transformer);
                        for (ClassPath.ClassInfo classInfo : classInfos) {
                            provided.getSLF4JLogger().info("Applying class transformer {}", classInfo.getName());
                            Fucit.getTransformerManager().addTransformer(classInfo.getName());
                        }
                    }
                }
            }
        } catch (Throwable ex) {
            provided.getSLF4JLogger().error("Error while loading class transformers of plugin '%s' in folder '%s' (Is it up to date?)".formatted(provider.getFileName(), provider.getParentSource()), ex);
        }
    }
}
