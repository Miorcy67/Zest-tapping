package com.zesttapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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

        String rhythm = "Zest Tap Rhythm";
        String step = getCurrentStep();
        String guidance = "W: hold | W release: hit 3 | A/D: strafe";
        String ping = "Ping: adjust timing to your opponent";

        mc.fontRendererObj.drawStringWithShadow(rhythm, centerX - 60, centerY - 40, 0x55FFFF);
        mc.fontRendererObj.drawStringWithShadow(step, centerX - 70, centerY - 20, 0xFFFFFF);
        mc.fontRendererObj.drawStringWithShadow(guidance, centerX - 100, centerY, 0xAAAAAA);
        mc.fontRendererObj.drawStringWithShadow(ping, centerX - 95, centerY + 20, 0xAAAAAA);
    }

    private String getCurrentStep() {
        ZestTappingMod.tickCounter++;
        if (ZestTappingMod.tickCounter % 20 == 0) {
            ZestTappingMod.pulsePhase = (ZestTappingMod.pulsePhase + 1) % 3;
        }

        switch (ZestTappingMod.pulsePhase) {
            case 0:
                return "Step 1/3: Hold W on hits 1-2";
            case 1:
                return "Step 2/3: Release W for hit 3";
            default:
                return "Step 3/3: Strafe A/D and re-enter";
        }
    }
}
