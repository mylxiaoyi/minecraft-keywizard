package xyz.xindi.keywizard.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.xindi.keywizard.KeyWizardCommon;

@Mod(KeyWizardCommon.MOD_ID)
public class KeyWizardForge {
    public KeyWizardForge(ModLoadingContext context) {
        EventBuses.registerModEventBus(KeyWizardCommon.MOD_ID, ((FMLJavaModLoadingContext) context.extension()).getModEventBus());
        KeyWizardCommon.init();
    }
}