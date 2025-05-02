package xyz.xindi.keywizard.forge.platform;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import xyz.xindi.keywizard.event.ClientEventHandler;
import xyz.xindi.keywizard.platform.services.IPlatformHelper;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public void registerClientEvents() {
        if (isClientEnvironment()) {
            ClientEventHandler.init();
        }
    }

    @Override
    public boolean isClientEnvironment() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }
}