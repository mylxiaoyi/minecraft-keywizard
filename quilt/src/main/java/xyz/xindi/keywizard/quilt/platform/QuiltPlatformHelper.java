package xyz.xindi.keywizard.quilt.platform;

import net.fabricmc.api.EnvType;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;
import xyz.xindi.keywizard.event.ClientEventHandler;
import xyz.xindi.keywizard.platform.services.IPlatformHelper;

public class QuiltPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Quilt";
    }

    @Override
    public void registerClientEvents() {
        if (isClientEnvironment()) {
            ClientEventHandler.init();
        }
    }

    @Override
    public boolean isClientEnvironment() {
        return MinecraftQuiltLoader.getEnvironmentType() == EnvType.CLIENT;
    }
}