package com.zesttapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ZestTapRenderer {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderOverlay(RenderGameOverlayEvent.Text event) {
        if (!ZestTappingMod.overlayEnabled || Minecraft.getMinecraft().theWorld == null) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        String title = "Zest Tap Trainer";
        String mode = "Mode: " + ZestTappingMod.practiceMode.getDisplayName();
        String target = "Target: " + ZestTappingMod.currentTargetName + String.format(" (%.1fm)", ZestTappingMod.currentTargetDistance);
        String step = getCurrentStep();
        String guidance = ZestTappingMod.practiceMode.getGuidance();
        String tip = ZestTappingMod.practiceMode.getTimingHint();
        String controls = "P toggles overlay • /zesttap help • /zesttap mode <low|medium|high>";

        int boxX = centerX - 170;
        int boxY = centerY - 60;
        int boxWidth = 340;
        int boxHeight = 95;

        drawPanel(mc, boxX, boxY, boxWidth, boxHeight);

        mc.fontRendererObj.drawStringWithShadow(title, centerX - 55, centerY - 48, 0x55FFFF);
        mc.fontRendererObj.drawStringWithShadow(mode, centerX - 70, centerY - 28, 0xFFD166);
        mc.fontRendererObj.drawStringWithShadow(step, centerX - 125, centerY - 8, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow(guidance, centerX - 155, centerY + 12, 0xAAAAAA);
        mc.fontRendererObj.drawStringWithShadow(tip, centerX - 115, centerY + 32, 0x8BE9FD);
        mc.fontRendererObj.drawStringWithShadow(controls, centerX - 150, centerY + 52, 0xC0C0C0);
    }

    private void drawPanel(Minecraft mc, int x, int y, int width, int height) {
        int borderColor = 0x66000000;
        int fillColor = 0xAA111111;
        drawRect(mc, x, y, x + width, y + height, fillColor);
        drawRect(mc, x, y, x + width, y + 1, borderColor);
        drawRect(mc, x, y + height - 1, x + width, y + height, borderColor);
        drawRect(mc, x, y, x + 1, y + height, borderColor);
        drawRect(mc, x + width - 1, y, x + width, y + height, borderColor);
    }

    private void drawRect(Minecraft mc, int left, int top, int right, int bottom, int color) {
        mc.ingameGUI.drawRect(left, top, right, bottom, color);
    }

    private String getCurrentStep() {
        ZestTappingMod.tickCounter++;
        if (ZestTappingMod.tickCounter % 20 == 0) {
            ZestTappingMod.pulsePhase = (ZestTappingMod.pulsePhase + 1) % 3;
        }

        switch (ZestTappingMod.pulsePhase) {
            case 0:
                return "Phase 1/3: Hold W during the first two hits";
            case 1:
                return "Phase 2/3: Release W cleanly for the third hit";
            default:
                return "Phase 3/3: Strafe A/D and re-enter the loop";
        }
    }
}
