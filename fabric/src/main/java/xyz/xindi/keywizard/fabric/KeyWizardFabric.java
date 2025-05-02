package xyz.xindi.keywizard.fabric;

import net.fabricmc.api.ModInitializer;
import xyz.xindi.keywizard.KeyWizardCommon;

public class KeyWizardFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        KeyWizardCommon.init();
    }
}