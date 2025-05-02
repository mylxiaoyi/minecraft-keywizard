package xyz.xindi.keywizard.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class DrawingUtil {
    public static void fill(PoseStack matrices, float x1, float y1, float x2, float y2, int color) {
        Matrix4f matrix = matrices.last().pose();
        float j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }

        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }

        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, y2, 0.0F).color(g, h, k, f).endVertex();
        bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(g, h, k, f).endVertex();
        bufferBuilder.vertex(matrix, x2, y1, 0.0F).color(g, h, k, f).endVertex();
        bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(g, h, k, f).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    public static void drawHorizontalLine(PoseStack matrices, float x1, float x2, float y, int color) {
        if (x2 < x1) {
            float i = x1;
            x1 = x2;
            x2 = i;
        }

        fill(matrices, x1, y, x2 + 1, y + 1, color);
    }

    public static void drawVerticalLine(PoseStack matrices, float x, float y1, float y2, int color) {
        if (y2 < y1) {
            float i = y1;
            y1 = y2;
            y2 = i;
        }

        fill(matrices, x, y1 + 1, x + 1, y2, color);
    }

    public static void drawNoFillRect(PoseStack matrices, float left, float top, float right, float bottom, int color) {
        drawHorizontalLine(matrices, left, right, top, color);
        drawHorizontalLine(matrices, left, right, bottom, color);
        drawVerticalLine(matrices, left, top, bottom, color);
        drawVerticalLine(matrices, right, top, bottom, color);
    }
}
