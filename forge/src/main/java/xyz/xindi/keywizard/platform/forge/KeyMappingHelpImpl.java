package xyz.xindi.keywizard.platform.forge;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class KeyMappingHelpImpl {
    public static InputConstants.Key getKey (KeyMapping keyMapping) {
        return keyMapping.getKey();
    }

    public static void setToDefault(KeyMapping keyMapping) {
        keyMapping.setToDefault();
    }
}