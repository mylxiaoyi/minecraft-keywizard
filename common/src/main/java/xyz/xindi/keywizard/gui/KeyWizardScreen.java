package xyz.xindi.keywizard.gui;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import xyz.xindi.keywizard.KeyWizardCommon;
import xyz.xindi.keywizard.platform.KeyMappingHelp;

public class KeyWizardScreen extends OptionsSubScreen {
    private final int[] mouseCodes = new int[]{GLFW.GLFW_MOUSE_BUTTON_1, GLFW.GLFW_MOUSE_BUTTON_2, GLFW.GLFW_MOUSE_BUTTON_3, GLFW.GLFW_MOUSE_BUTTON_4, GLFW.GLFW_MOUSE_BUTTON_5, GLFW.GLFW_MOUSE_BUTTON_6, GLFW.GLFW_MOUSE_BUTTON_7, GLFW.GLFW_MOUSE_BUTTON_8};

    private int mouseCodeIndex = 0;

    private KeyboardWidget keyboard;

    private KeyboardWidget mouseButton;

    private Button mousePlus;

    private Button mouseMinus;

    private KeyBindingListWidget bindingList;

    private CategorySelectorWidget categorySelector;

    private ImageButton screenToggleButton;

    private EditBox searchBar;

    private Button resetBinding;

    private Button resetAll;

    private Button clearBinding;

    public KeyWizardScreen(Screen parent) {
        super(parent, Minecraft.getInstance().options, Component.nullToEmpty(KeyWizardCommon.MOD_ID));
    }

    @Override
    protected void init() {
        int mouseButtonX = this.width - 105;
        int mouseButtonY = this.height / 2 - 115;
        int mouseButtonWidth = 80;
        int mouseButtonHeight = 20;
        int maxBindingNameWidth = 0;
        int maxCategoryWidth = 0;
        for (KeyMapping k : this.options.keyMappings) {
            int w = this.font.width(MutableComponent.create(new TranslatableContents(k.getName(), k.getName(), new Object[]{})));
            if (w > maxBindingNameWidth)
                maxBindingNameWidth = w;

            int categoryWidth = this.font.width(k.getCategory());
            if (categoryWidth > maxCategoryWidth)
                maxCategoryWidth = categoryWidth;
        }

        int bindingListWidth = maxBindingNameWidth + 20;

        this.keyboard = KeyboardWidgetBuilder.standardKeyboard(this, (bindingListWidth + 15), (this.height / 2 - 90), (this.width - bindingListWidth - 15), 180.0F);
        this.bindingList = new KeyBindingListWidget(this, 10, 10, bindingListWidth, this.height - 40, 9 * 3 + 10);
        this.categorySelector = new CategorySelectorWidget(this, bindingListWidth + 15, 5, maxCategoryWidth + 50, 20);

        this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, this.mouseCodes[this.mouseCodeIndex], InputConstants.Type.MOUSE);

        Button.Builder mousePlusBuilder = new Button.Builder(Component.translatable("+"), btn -> {
            this.mouseCodeIndex ++;
            if (this.mouseCodeIndex >= this.mouseCodes.length ) {
                this.mouseCodeIndex = 0;
            }
            removeWidget(this.mouseButton);
            this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], InputConstants.Type.MOUSE);
            addRenderableWidget(this.mouseButton);
        });
        mousePlusBuilder.bounds((int) this.mouseButton.getAnchorX() + 83, (int) this.mouseButton.getAnchorY(), 25, 20);
        this.mousePlus = mousePlusBuilder.build();

        Button.Builder mouseMinusBuilder = new Button.Builder(Component.translatable("-"), btn -> {
            this.mouseCodeIndex --;
            if (this.mouseCodeIndex < 0) {
                this.mouseCodeIndex = this.mouseCodes.length - 1;
            }
            removeWidget(this.mouseButton);
            this.mouseButton = KeyboardWidgetBuilder.singleKeyKeyboard(this, mouseButtonX, mouseButtonY, mouseButtonWidth, mouseButtonHeight, mouseCodes[mouseCodeIndex], InputConstants.Type.MOUSE);
            addRenderableWidget(this.mouseButton);
        });
        mouseMinusBuilder.bounds((int) this.mouseButton.getAnchorX() - 26, (int) this.mouseButton.getAnchorY(), 25, 20);
        this.mouseMinus = mouseMinusBuilder.build();

        this.searchBar = new EditBox(this.font, 10, this.height - 20, bindingListWidth, 14, Component.empty());

        Button.Builder resetBindingBuilder = new Button.Builder(MutableComponent.create(new TranslatableContents("controls.reset", "controls.reset", new Object[]{})),
                btn -> {
                    KeyMapping selectedBinding = this.getSelectedKeyBinding();
                    KeyMappingHelp.setToDefault(selectedBinding);
                });
        resetBindingBuilder.bounds(bindingListWidth + 15, this.height - 23, 50, 20);
        this.resetBinding = resetBindingBuilder.build();

        Button.Builder clearBindingBuilder = new Button.Builder(MutableComponent.create(new TranslatableContents("gui.clear", "gui.clear", new Object[]{})),
                btn -> {
                    KeyMapping selectedBinding = this.getSelectedKeyBinding();
                    selectedBinding.setKey(InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_UNKNOWN));
                });
        clearBindingBuilder.bounds(bindingListWidth + 66, this.height - 23, 50, 20);
        this.clearBinding = clearBindingBuilder.build();

        Button.Builder resetAllBuilder = new Button.Builder(MutableComponent.create(new TranslatableContents("controls.resetAll", "controls.resetAll", new Object[]{})),
                btn -> {
                    for (KeyMapping b : this.options.keyMappings) {
                        KeyMappingHelp.setToDefault(b);
                    }
                });
        resetAllBuilder.bounds(bindingListWidth + 117, this.height - 23, 70, 20);
        this.resetAll = resetAllBuilder.build();

        int xTexStart = 0;
        int yTexStart = 0;
        int yDiffTex = 20;

        this.screenToggleButton = new ImageButton(
                this.width - 22, this.height - 22,
                20, 20,
                xTexStart, yTexStart, yDiffTex,
                KeyWizardCommon.SCREEN_TOGGLE_WIDGETS,
                btn -> this.minecraft.setScreen(new ControlsScreen(this.lastScreen, this.options))
        );

        addRenderableWidget(this.bindingList);
        addRenderableWidget(this.keyboard);
        addRenderableWidget(this.categorySelector);
        addRenderableWidget(this.categorySelector.getCategoryList());

        addRenderableWidget(this.mouseButton);
        addRenderableWidget(this.mousePlus);
        addRenderableWidget(this.mouseMinus);

        addRenderableWidget(this.searchBar);
        addRenderableWidget(this.resetBinding);
        addRenderableWidget(this.clearBinding);
        addRenderableWidget(this.resetAll);
        addRenderableWidget(this.screenToggleButton);
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    private void renderTransparentBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
    }

    @Override
    public void tick() {
        for (GuiEventListener e : this.children()) {
            if (e instanceof TickableElement)
                ((TickableElement) e).tick();
        }
    }

    public void showTooltip(Component text) {
        this.setTooltipForNextRenderPass(Tooltip.splitTooltip(this.minecraft, text));
    }


    @Nullable
    public KeyMapping getSelectedKeyBinding() {
        return this.bindingList.getSelectedKeyBinding();
    }

    public boolean getCategorySelectorExtended() {
        return this.categorySelector.extended;
    }

    public String getSelectedCategory() {
        return this.categorySelector.getSelctedCategory();
    }

    public String getFilterText() {
        return this.searchBar.getValue();
    }

    public void setSearchText(String s) {
        this.searchBar.setValue(s);
    }
}
