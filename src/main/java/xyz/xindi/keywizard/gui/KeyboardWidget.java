package xyz.xindi.keywizard.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyboardWidget
        extends AbstractContainerEventHandler implements Renderable, TickableElement, NarratableEntry {
    public KeyWizardScreen keyWizardScreen;

    private HashMap<Integer, KeyboardKeyWidget> keys = new HashMap<>();

    private float anchorX;

    private float anchorY;

    protected KeyboardWidget(KeyWizardScreen keyWizardScreen, float anchorX, float anchorY) {
        this.keyWizardScreen = keyWizardScreen;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
    }

    public float addKey(float relativeX, float relativeY, float width, float height, float keySpacing, int keyCode) {
        this.keys.put(Integer.valueOf(keyCode), new KeyboardKeyWidget(keyCode, this.anchorX + relativeX, this.anchorY + relativeY, width, height, InputConstants.Type.KEYSYM));
        return relativeX + width + keySpacing;
    }

    public float addKey(float relativeX, float relativeY, float width, float height, float keySpacing, int keyCode, InputConstants.Type keyType) {
        this.keys.put(Integer.valueOf(keyCode), new KeyboardKeyWidget(keyCode, this.anchorX + relativeX, this.anchorY + relativeY, width, height, keyType));
        return relativeX + width + keySpacing;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        List<? extends KeyboardKeyWidget> keys = children();
        for (KeyboardKeyWidget k : keys)
            k.render(graphics, mouseX, mouseY, partialTick);
        if (!this.keyWizardScreen.getCategorySelectorExtended())
            for (KeyboardKeyWidget k : keys) {
                if (k.active && k.isHovered()) {
                    List<FormattedCharSequence> textList = new ArrayList<>();
                    textList.add(Component.translatable(k.getTooltipText()).getVisualOrderText());
                    this.keyWizardScreen.setTooltipForNextRenderPass(textList);
                }
            }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.keyWizardScreen.getCategorySelectorExtended())
            for (KeyboardKeyWidget k : children()) {
                if (k.mouseClicked(mouseX, mouseY, button))
                    return true;
            }
        return false;
    }

    @Override
    public List<? extends KeyboardKeyWidget> children() {
        return new ArrayList<>(this.keys.values());
    }

    public void tick() {
        for (KeyboardKeyWidget k : children())
            k.tick();
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_) {

    }

    public float getAnchorX() {
        return this.anchorX;
    }

    public float getAnchorY() {
        return this.anchorY;
    }

    public class KeyboardKeyWidget extends AbstractButton implements TickableElement {
        private InputConstants.Key key;

        private List<String> tooltipText = new ArrayList<>();

        protected KeyboardKeyWidget(int keyCode, float x, float y, float width, float height, InputConstants.Type keyType) {
            super((int) x, (int) y, (int) width, (int) height, Component.empty());
            this.key = keyType.getOrCreate(keyCode);
            this.setMessage(MutableComponent.create(this.key.getDisplayName().getContents()));
        }

        public InputConstants.Key getKey() {
            return this.key;
        }

        public String getTooltipText() {
            return String.join("/", this.tooltipText);
        }

        @Override
        public int getFGColor() {
            int bindingCount = this.tooltipText.size();
            int color = 0;
            if (this.visible) {
                if (isHovered() && !KeyboardWidget.this.keyWizardScreen.getCategorySelectorExtended()) {
                    color = 0xFFAAAAAA;
                    if (bindingCount == 1) {
                        color = 0xFF00AA00;
                    } else if (bindingCount > 1) {
                        color = 0xFFAA0000;
                    }
                } else {
                    color = 0xFFFFFFFF;
                    if (bindingCount == 1) {
                        color = 0xFF00FF00;
                    } else if (bindingCount > 1) {
                        color = 0xFFFF0000;
                    }
                }
            } else {
                color = 0xFF555555;
            }

            return color;
        }

        private void updateTooltip() {
            ArrayList<String> tooltipText = new ArrayList<>();
            for (KeyMapping b : (Minecraft.getInstance()).options.keyMappings) {
                if (b.getKey().equals(this.key)) {
                    String keyName = b.getName().substring(4);
                    tooltipText.add(keyName.substring(0, 1).toUpperCase() + keyName.substring(1));
                }
            }

            this.tooltipText = tooltipText;
        }

        public void tick() {
            updateTooltip();
        }

        @Override
        public void onPress() {
            this.playDownSound(Minecraft.getInstance().getSoundManager());
            if (Screen.hasShiftDown()) {
                Component t = this.getMessage();
                String keyName;
                if (t instanceof TranslatableContents) {
                    keyName = ((TranslatableContents) t).getKey();
                } else {
                    keyName = t.getString();
                }
                keyWizardScreen.setSearchText("<" + keyName + ">");
            } else {
                KeyMapping selectedKeyBinding = keyWizardScreen.getSelectedKeyBinding();
                if (selectedKeyBinding != null) {
                    selectedKeyBinding.setKey(this.key);
                }
            }
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

        }
    }
}
