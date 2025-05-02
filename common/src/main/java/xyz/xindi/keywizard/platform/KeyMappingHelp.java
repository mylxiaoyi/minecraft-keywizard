package xyz.xindi.keywizard.platform;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import dev.architectury.injectables.annotations.ExpectPlatform;

public class KeyMappingHelp {

    /**
     * 获取KeyMapping的按键
     * @param keyMapping 按键映射
     * @return 按键
     */
    @ExpectPlatform
    public static InputConstants.Key getKey(KeyMapping keyMapping) {
        throw new AssertionError("Platform implementation missing");
    }

    /**
     * 将KeyMapping重置为默认按键
     * @param keyMapping 按键映射
     */
    @ExpectPlatform
    public static void setToDefault(KeyMapping keyMapping) {
        throw new AssertionError("Platform implementation missing");
    }
}