package xyz.xindi.keywizard.event;

import org.lwjgl.glfw.GLFW;
import xyz.xindi.keywizard.gui.KeyWizardScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {
    public static final KeyMapping KEY_OPEN_KEYWIZARD = new KeyMapping("key.keywizard.openKeyWizard", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F7, "key.categories.keywizard.bindings");

    @SubscribeEvent
    public void onKeyRegister(RegisterKeyMappingsEvent e) {
        e.register(KEY_OPEN_KEYWIZARD);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key e) {
        if (KEY_OPEN_KEYWIZARD.isDown()) {
            Minecraft client = Minecraft.getInstance();
            client.setScreen((Screen)new KeyWizardScreen(client.screen));
        }
    }
}
