package xyz.xindi.keywizard.gui;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.jetbrains.annotations.Nullable;
import xyz.xindi.keywizard.platform.KeyMappingHelp;
import xyz.xindi.keywizard.util.KeyBindingUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyBindingListWidget extends AbstractSelectionList<KeyBindingListWidget.BindingEntry> implements TickableElement
{
    public KeyWizardScreen keyWizardScreen;

    private String currentFilterText = "";

    private String currentCategory = "key.categories.keywizard.all";

    public KeyBindingListWidget(KeyWizardScreen keyWizardScreen, int top, int left, int width, int height, int itemHeight) {
        super(Minecraft.getInstance(), width, height, top, top + height, itemHeight);
        this.keyWizardScreen = keyWizardScreen;
        for (KeyMapping k : Minecraft.getInstance().options.keyMappings)
            addEntry(new BindingEntry(k));
        if (!children().isEmpty()) {
            setSelected(children().get(0));
        }
    }


    @Override
    public int getRowWidth() {
        return this.width - 15;
    }

    @Nullable
    public KeyMapping getSelectedKeyBinding() {
        if (this.getSelected() == null)
            return null;
        return this.getSelected().keyBinding;
    }

    private void updateList() {
        boolean filterUpdate = !this.currentFilterText.equals(this.keyWizardScreen.getFilterText());
        boolean categoryUpdate = !this.currentCategory.equals(this.keyWizardScreen.getSelectedCategory());

        if (categoryUpdate || filterUpdate) {
            if (categoryUpdate) {
                this.currentCategory = this.keyWizardScreen.getSelectedCategory();
            }

            KeyMapping[] bindings = getBindingsByCategory(this.currentCategory);

            if (filterUpdate) {
                this.currentFilterText = this.keyWizardScreen.getFilterText();
                if (!this.currentFilterText.equals("")) {
                    bindings = filterBindings(bindings, this.currentFilterText);
                }
            }

            this.children().clear();
            if (bindings.length > 0) {
                for (KeyMapping k : bindings) {
                    this.addEntry(new BindingEntry(k));
                }
                setSelected(children().get(0));
            } else {
                this.setSelected(null);
            }
            this.setScrollAmount(0);
        }
    }

    private KeyMapping[] filterBindings(KeyMapping[] bindings, String filterText) {
        KeyMapping[] bindingsFiltered = bindings;
        String keyNameRegex = "<.*>";
        Matcher keyNameMatcher = Pattern.compile(keyNameRegex).matcher(filterText);
        if (keyNameMatcher.find()) {
            String keyNameWithBrackets = keyNameMatcher.group();
            String keyName = keyNameWithBrackets.replace("<", "").replace(">", "");
            filterText = filterText.replace(keyNameWithBrackets, "");
            bindingsFiltered = filterBindingsByKey(bindingsFiltered, keyName);
        }
        if (!filterText.equals(""))
            bindingsFiltered = filterBindingsByName(bindingsFiltered, filterText);
        return bindingsFiltered;
    }

    private KeyMapping[] filterBindingsByName(KeyMapping[] bindings, String bindingName) {
        String[] words = bindingName.split("\\s+");
        KeyMapping[] bindingsFiltered = Arrays.stream(bindings).filter(binding -> {
            boolean flag = true;
            for (String w:words) {
                flag = flag && ((TranslatableContents)binding.getTranslatedKeyMessage()).getKey().toLowerCase().contains(w.toLowerCase());
            }
            return flag;
        }).toArray(KeyMapping[]::new);
        return bindingsFiltered;
    }

    private KeyMapping[] filterBindingsByKey(KeyMapping[] bindings, String keyName) {
        return Arrays.stream(bindings).filter(b -> {
            Component t = b.getTranslatedKeyMessage();
            if (t instanceof TranslatableContents) {
                return ((TranslatableContents) t).getKey().equalsIgnoreCase(keyName);
            }
            else {
                return t.getString().equalsIgnoreCase(keyName);
            }
        }).toArray(KeyMapping[]::new);
    }

    private KeyMapping[] getBindingsByCategory(String category) {
        KeyMapping[] bindings = Arrays.copyOf(Minecraft.getInstance().options.keyMappings, Minecraft.getInstance().options.keyMappings.length);
        switch (category) {
            case KeyBindingUtil.DYNAMIC_CATEGORY_ALL:
                return bindings;
            case KeyBindingUtil.DYNAMIC_CATEGORY_CONFLICTS:
                Map<InputConstants.Key, Integer> bindingCounts = KeyBindingUtil.getBindingCountsByKey();
                return Arrays.stream(bindings).filter(b -> bindingCounts.get(KeyMappingHelp.getKey(b)) > 1  && KeyMappingHelp.getKey(b).getValue() != -1).toArray(KeyMapping[]::new) ;
            case KeyBindingUtil.DYNAMIC_CATEGORY_UNBOUND:
                return Arrays.stream(bindings).filter(KeyMapping::isUnbound).toArray(KeyMapping[]::new);
            default:
                return Arrays.stream(bindings).filter(b -> b.getCategory().equals(category)).toArray(KeyMapping[]::new);
        }
    }

    public void tick() {
        updateList();
    }


    @Override
    public void updateNarration (NarrationElementOutput output) {

    }

    public class BindingEntry extends Entry<BindingEntry> {
        private final KeyMapping keyBinding;

        public BindingEntry(KeyMapping keyBinding) {
            this.keyBinding = keyBinding;
        }

        @Override
        public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            String keyName = this.keyBinding.getName().substring(4);
            graphics.drawString(Minecraft.getInstance().font, keyName.substring(0, 1).toUpperCase() + keyName.substring(1), x, y, 0xFFFFFFFF);
            graphics.drawString(Minecraft.getInstance().font, this.keyBinding.getTranslatedKeyMessage(), x, (y + Minecraft.getInstance().font.lineHeight + 5), 0xFF999999);
        }

        @Override
        public boolean mouseClicked(double p_94737_, double p_94738_, int p_94739_) {
            return true;
        }
    }
}

