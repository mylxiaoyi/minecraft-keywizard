package xyz.xindi.keywizard.platform.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import xyz.xindi.keywizard.fabriclike.platform.fabric.KeyMappingHelperImpl;


/**
 * Fabric平台的KeyMappingHelp实现
 * 委托给fabriclike模块中的实现
 */
public class KeyMappingHelpImpl {

    public static InputConstants.Key getKey(KeyMapping keyMapping) {
        return KeyMappingHelperImpl.getKey(keyMapping);
    }

    public static void setToDefault(KeyMapping keyMapping) {
        KeyMappingHelperImpl.setToDefault(keyMapping);
    }
}