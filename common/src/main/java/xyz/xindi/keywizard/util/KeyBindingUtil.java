package xyz.xindi.keywizard.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import xyz.xindi.keywizard.platform.KeyMappingHelp;

import java.util.*;

public class KeyBindingUtil {
    public static final String DYNAMIC_CATEGORY_ALL = "key.categories.keywizard.all";

    public static final String DYNAMIC_CATEGORY_CONFLICTS = "key.categories.keywizard.conflicts";

    public static final String DYNAMIC_CATEGORY_UNBOUND = "key.categories.keywizard.unbound";

    public static ArrayList<String> getCategories() {
        Set<String> categories = new HashSet<>();
        for(KeyMapping k : Minecraft.getInstance().options.keyMappings) {
            categories.add(k.getCategory());
        }
        return new ArrayList<>(categories);
    }

    public static ArrayList<String> getCategoriesWithDynamics() {
        ArrayList<String> categories = getCategories();
        categories.add(0, "key.categories.keywizard.unbound");
        categories.add(0, "key.categories.keywizard.conflicts");
        categories.add(0, "key.categories.keywizard.all");
        return categories;
    }

    public static Map<InputConstants.Key, Integer> getBindingCountsByKey() {
        HashMap<InputConstants.Key, Integer> map = new HashMap<>();
        for (KeyMapping b : (Minecraft.getInstance()).options.keyMappings)
            map.merge(KeyMappingHelp.getKey(b), 1, Integer::sum);
        return Collections.unmodifiableMap(map);
    }

}
