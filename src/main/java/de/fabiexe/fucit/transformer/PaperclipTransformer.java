package de.fabiexe.fucit.transformer;

import de.fabiexe.fucit.Fucit;
import io.papermc.paperclip.Paperclip;
import java.net.URL;
import java.net.URLClassLoader;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CModifyExpressionValue;

@CTransformer(Paperclip.class)
public class PaperclipTransformer {
    @CModifyExpressionValue(
            method = "main",
            target = @CTarget(
                    value = "NEW",
                    target = "Ljava/net/URLClassLoader;<init>([Ljava/net/URL;Ljava/lang/ClassLoader;)V"))
    public static URLClassLoader classLoader(URLClassLoader classLoader) throws Exception {
        URL[] urls = classLoader.getURLs();
        classLoader.close();
        return new URLClassLoader(urls, Fucit.class.getClassLoader());
    }
}
