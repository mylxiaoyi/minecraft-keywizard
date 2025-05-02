package xyz.xindi.keywizard;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.xindi.keywizard.event.ClientEventHandler;
import xyz.xindi.keywizard.platform.Services;

public class KeyWizardCommon {
    public static final String MOD_ID = "keywizard";
    public static final Logger LOGGER = LoggerFactory.getLogger("KeyWizard");

    public static final ResourceLocation SCREEN_TOGGLE_WIDGETS = new ResourceLocation(MOD_ID, "textures/gui/screen_toggle_widgets.png");

    public static void init() {
        LOGGER.info("Initializing KeyWizard");

        ClientEventHandler.init();

        KeyMappingRegistry.register(ClientEventHandler.KEY_OPEN_KEYWIZARD);

        Config.init();

        Services.PLATFORM.registerClientEvents();
    }
}