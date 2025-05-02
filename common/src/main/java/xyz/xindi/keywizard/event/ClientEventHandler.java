package xyz.xindi.keywizard.event;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import xyz.xindi.keywizard.gui.KeyWizardScreen;

public class ClientEventHandler {
    public static final KeyMapping KEY_OPEN_KEYWIZARD = new KeyMapping(
            "key.keywizard.openKeyWizard",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F7,
            "key.categories.keywizard.bindings"
    );

    public static void init() {
        // 注册客户端Tick事件
        ClientTickEvent.CLIENT_POST.register(client -> {
            // 检查按键是否被按下
            while (KEY_OPEN_KEYWIZARD.consumeClick()) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.setScreen(new KeyWizardScreen(minecraft.screen));
            }
        });
    }
}