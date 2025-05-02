package xyz.xindi.keywizard.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import xyz.xindi.keywizard.KeyWizardCommon;

public class KeyWizardQuilt implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        KeyWizardCommon.init();
    }
}