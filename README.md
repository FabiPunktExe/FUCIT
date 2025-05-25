# FUCIT (Fabi's utility can inject transformers)

## Installation
Download `FUCIT.jar` from the [releases page](https://github.com/FabiPunktExe/FUCIT/releases) and place it in your server directory.
Add the JVM flag `-javaagent:FUCIT.jar` before `-jar` to your server startup script:
```bash
java -javaagent:FUCIT.jar -jar server.jar
```

## Development
⚠ You need to use the `paper-plugin.yml` (not `plugin.yml`) to use FUCIT.
Read the [introduction into paper-plugin.yml](https://docs.papermc.io/paper/dev/getting-started/paper-plugins) if you don't know how to use it.

1. Add the [ClassTransform](https://github.com/Lenni0451/ClassTransform) library as a Maven / Gradle dependency to your project:<br><br>

    `build.gradle.kts`:
    ```kotlin
    compileOnly("net.lenni0451.classtransform:core:1.14.1")
    ```
     <br>

    `pom.xml`:
    ```xml
    <dependency>
        <groupId>net.lenni0451.classtransform</groupId>
        <artifactId>core</artifactId>
        <version>1.14.1</version>
    </dependency>
    ```

2. Write your class transformers.
   Read the [ClassTransform documentation](https://github.com/Lenni0451/ClassTransform/wiki) if you don't know how to write them.
    ```java
    @CTransformer(MinecraftServer.class)
    public class MinecraftServerTransformer {
        @CInline
        @CInject(method = "tickServer", target = @CTarget("HEAD"), cancellable = true)
        public void tickServer(BooleanSupplier hasTimeLeft, InjectionCallback callback) {
            System.out.println("Hello from MinecraftServerTransformer!");
        }
    }
    ```

3. Add class names or package names of your class transformers to the `class-transformers` list in your `paper-plugin.yml`:
```yaml
name: FucitExample
main: de.fabiexe.fucit.example.FucitExample
version: '${version}'
api-version: '1.21.5'
class-transformers:
- de.fabiexe.fucit.example.transformer
```
