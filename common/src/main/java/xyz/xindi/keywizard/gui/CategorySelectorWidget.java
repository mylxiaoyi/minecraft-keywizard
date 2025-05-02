package xyz.xindi.keywizard.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import xyz.xindi.keywizard.util.KeyBindingUtil;

import java.util.Objects;

public class CategorySelectorWidget
        extends AbstractButton implements TickableElement {
    public KeyWizardScreen keyWizardScreen;

    public boolean extended = false;

    private BindingCategoryListWidget categoryList;

    public CategorySelectorWidget(KeyWizardScreen keyWizardScreen, int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        this.keyWizardScreen = keyWizardScreen;

        int listItemHeight = Minecraft.getInstance().font.lineHeight + 7;
        int listHeight = KeyBindingUtil.getCategoriesWithDynamics().size() * listItemHeight + 10;
        int listBottom = this.getY() + this.height + listHeight;
        if (listBottom > this.keyWizardScreen.height) {
            listHeight = this.keyWizardScreen.height - this.getY() - this.height - 10;
        }
        this.categoryList = new BindingCategoryListWidget(Minecraft.getInstance(), this.getY() + this.height, this.getX(), this.width, listHeight, listItemHeight);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean listClicked = this.categoryList.mouseClicked(mouseX, mouseY, button);
        boolean thisClicked = super.mouseClicked(mouseX, mouseY, button);
        if (!listClicked && !thisClicked)
            this.extended = false;
        return (listClicked || thisClicked);
    }

    @Override
    public void onPress() {
        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.extended = !this.extended;
    }

    public void tick() {
        setMessage(Component.translatable(getSelctedCategory()));
        this.categoryList.setVisible(this.extended);
    }

    public String getSelctedCategory() {
        return Objects.requireNonNull(this.categoryList.getSelected()).category;
    }

    public BindingCategoryListWidget getCategoryList() {
        return this.categoryList;
    }

    private class BindingCategoryListWidget extends AbstractSelectionList<BindingCategoryListWidget.CategoryEntry> {

        public BindingCategoryListWidget(Minecraft client, int top, int left, int width, int height, int itemHeight) {
            super(client, width, height, top, top + height, itemHeight);
            setX(left);
            for (String c : KeyBindingUtil.getCategoriesWithDynamics())
                addEntry(new CategoryEntry(c));
            if (!children().isEmpty()) {
                setSelected(children().get(0));
            }

        }
        private boolean visible = true;

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            if (this.visible) {
                super.render(graphics, mouseX, mouseY, partialTick);
            }
        }

        @Override
        public int getRowWidth() {
            return this.width - 15;
        }

        @Override
        public void updateNarration (NarrationElementOutput output) {

        }

        public class CategoryEntry extends Entry<CategoryEntry> {
            private final String category;

            public CategoryEntry(String category) {
                this.category = category;
            }

            @Override
            public void render(GuiGraphics graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                graphics.drawString(Minecraft.getInstance().font, this.category, x, y + 2, 0xFFFFFFFF);
            }

            @Override
            public boolean mouseClicked(double p_94737_, double p_94738_, int p_94739_) {
                return true;
            }
        }
    }
}

