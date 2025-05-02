package xyz.xindi.keywizard.fabric.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import xyz.xindi.keywizard.event.ClientEventHandler;
import xyz.xindi.keywizard.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public void registerClientEvents() {
        if (isClientEnvironment()) {
            ClientEventHandler.init();
        }
    }

    @Override
    public boolean isClientEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}