package xyz.xindi.keywizard.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;

public class KeyboardWidgetBuilder {
    public static KeyboardWidget standardKeyboard(KeyWizardScreen keyWizardScreen, float anchorX, float anchorY, float width, float height) {
        KeyboardWidget kb = new KeyboardWidget(keyWizardScreen, anchorX, anchorY);
        float currentX = 0.0F;
        float currentY = 0.0F;
        float keySpacing = 5.0F;
        float keyWidth = width / 12.0F - keySpacing;
        float keyHeight = height / 6.0F - keySpacing;
        currentX = addHorizontalRow(kb, new int[] {
                290, 291, 292, 293, 294, 295, 296, 297, 298, 299,
                300, 301 }, 0.0F, currentY, keyWidth, keyHeight, keySpacing);
        currentY += keyHeight + keySpacing;
        keyWidth = width / 15.0F - keySpacing;
        currentX = addHorizontalRow(kb, new int[] {
                96, 49, 50, 51, 52, 53, 54, 55, 56, 57,
                48, 45, 61 }, 0.0F, currentY, keyWidth, keyHeight, keySpacing);
        currentX = kb.addKey(currentX, currentY, keyWidth * 2.0F + keySpacing, keyHeight, keySpacing, 259);
        currentY += keyHeight + keySpacing;
        currentX = kb.addKey(0.0F, currentY, keyWidth * 2.0F + keySpacing, keyHeight, keySpacing, 258);
        currentX = addHorizontalRow(kb, new int[] {
                81, 87, 69, 82, 84, 89, 85, 73, 79, 80,
                91, 93 }, currentX, currentY, keyWidth, keyHeight, keySpacing);
        currentX = kb.addKey(currentX, currentY, keyWidth, keyHeight, keySpacing, 92);
        currentY += keyHeight + keySpacing;
        currentX = kb.addKey(0.0F, currentY, keyWidth * 2.0F + keySpacing, keyHeight, keySpacing, 280);
        currentX = addHorizontalRow(kb, new int[] {
                65, 83, 68, 70, 71, 72, 74, 75, 76, 59,
                39 }, currentX, currentY, keyWidth, keyHeight, keySpacing);
        kb.addKey(currentX, currentY, keyWidth * 2.0F + keySpacing, keyHeight, keySpacing, 257);
        currentY += keyHeight + keySpacing;
        currentX = kb.addKey(0.0F, currentY, keyWidth * 2.0F + keySpacing, keyHeight, keySpacing, 340);
        currentX = addHorizontalRow(kb, new int[] { 90, 88, 67, 86, 66, 78, 77, 44, 46, 47 }, currentX, currentY, keyWidth, keyHeight, keySpacing);
        currentX = kb.addKey(currentX, currentY, keyWidth * 3.0F + keySpacing * 2.0F, keyHeight, keySpacing, 344);
        currentY += keyHeight + keySpacing;
        keyWidth = width / 7.0F - keySpacing;
        currentX = addHorizontalRow(kb, new int[] { 341, 343, 342, 32, 346, 347, 345 }, 0.0F, currentY, keyWidth, keyHeight, keySpacing);
        return kb;
    }

    public static KeyboardWidget singleKeyKeyboard(KeyWizardScreen keyWizardScreen, float anchorX, float anchorY, float width, float height, int keyCode, InputConstants.Type keyType) {
        KeyboardWidget kb = new KeyboardWidget(keyWizardScreen, anchorX, anchorY);
        kb.addKey(0.0F, 0.0F, width, height, 0.0F, keyCode, keyType);
        return kb;
    }

    private static float addHorizontalRow(KeyboardWidget kb, int[] keys, float startX, float y, float width, float height, float spacing) {
        float currentX = startX;
        for (int k : keys)
            currentX = kb.addKey(currentX, y, width, height, spacing, k);
        return currentX;
    }
}

