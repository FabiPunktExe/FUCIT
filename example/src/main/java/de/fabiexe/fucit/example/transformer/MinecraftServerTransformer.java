package de.fabiexe.fucit.example.transformer;

import java.util.function.BooleanSupplier;
import net.lenni0451.classtransform.InjectionCallback;
import net.lenni0451.classtransform.annotations.CInline;
import net.lenni0451.classtransform.annotations.CTarget;
import net.lenni0451.classtransform.annotations.CTransformer;
import net.lenni0451.classtransform.annotations.injection.CInject;
import net.minecraft.server.MinecraftServer;

@CTransformer(MinecraftServer.class)
public class MinecraftServerTransformer {
    @CInline
    @CInject(method = "tickServer", target = @CTarget("HEAD"), cancellable = true)
    public void tickServer(BooleanSupplier hasTimeLeft, InjectionCallback callback) {
        System.out.println("Hello from MinecraftServerTransformer!");
    }
}
