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
        String step = getCurrentStep();
        String guidance = "Hold W for hits 1-2 • Release W for hit 3 • Strafe A/D";
        String tip = "Use clean burst clicks and keep your spacing tight";
        String controls = "P toggles overlay • /zesttap help for commands";

        mc.fontRendererObj.drawStringWithShadow(title, centerX - 60, centerY - 50, 0x55FFFF);
        mc.fontRendererObj.drawStringWithShadow(step, centerX - 90, centerY - 28, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow(guidance, centerX - 155, centerY - 8, 0xAAAAAA);
        mc.fontRendererObj.drawStringWithShadow(tip, centerX - 125, centerY + 12, 0xFFD166);
        mc.fontRendererObj.drawStringWithShadow(controls, centerX - 120, centerY + 32, 0x8BE9FD);
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
