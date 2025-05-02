package xyz.xindi.keywizard.fabriclike.platform.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import java.lang.reflect.Field;

public class KeyMappingHelperImpl {
    private static Field keyField;

    static {
        try {
            keyField = KeyMapping.class.getDeclaredField("key");
            keyField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("无法访问KeyMapping.key字段", e);
        }
    }

    public static InputConstants.Key getKey(KeyMapping keyMapping) {
        try {
            return (InputConstants.Key) keyField.get(keyMapping);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("无法访问KeyMapping.key字段", e);
        }
    }

    public static void setToDefault(KeyMapping keyMapping) {
        keyMapping.setKey(keyMapping.getDefaultKey());
    }
}