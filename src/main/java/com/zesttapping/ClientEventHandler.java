package com.zesttapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (ClientProxy.toggleBinding != null && ClientProxy.toggleBinding.isPressed()) {
            ZestTappingMod.overlayEnabled = !ZestTappingMod.overlayEnabled;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) {
            return;
        }

        GameSettings settings = mc.gameSettings;
        if (settings == null) {
            return;
        }

        EntityPlayer closest = null;
        double closestDistanceSq = Double.MAX_VALUE;
        for (Object obj : mc.theWorld.playerEntities) {
            if (!(obj instanceof EntityPlayer)) {
                continue;
            }
            EntityPlayer other = (EntityPlayer) obj;
            if (other == mc.thePlayer || other.isDead) {
                continue;
            }

            double distSq = mc.thePlayer.getDistanceSqToEntity(other);
            if (distSq < closestDistanceSq) {
                closestDistanceSq = distSq;
                closest = other;
            }
        }

        if (closest != null) {
            ZestTappingMod.currentTargetName = closest.getName();
            ZestTappingMod.currentTargetDistance = Math.sqrt(closestDistanceSq);
        } else {
            ZestTappingMod.currentTargetName = "None";
            ZestTappingMod.currentTargetDistance = 0.0D;
        }

        if (!ZestTappingMod.autoMovementEnabled) {
            return;
        }

        ZestTappingMod.sequenceTickCounter++;
        if (ZestTappingMod.sequenceTickCounter >= 20) {
            ZestTappingMod.sequenceTickCounter = 0;
            ZestTappingMod.sequencePhase = (ZestTappingMod.sequencePhase + 1) % 3;
            ZestTappingMod.attackTickCounter = 0;
            if (ZestTappingMod.sequencePhase == 2) {
                ZestTappingMod.strafeRight = !ZestTappingMod.strafeRight;
            }
        }

        float forward = 0.0F;
        float strafe = 0.0F;
        if (ZestTappingMod.sequencePhase != 2) {
            forward = 1.0F;
        }

        if (closest != null) {
            double dx = closest.posX - mc.thePlayer.posX;
            double dz = closest.posZ - mc.thePlayer.posZ;
            float targetYaw = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) - 90.0F;
            float yawDelta = MathHelper.wrapAngleTo180_float(targetYaw - mc.thePlayer.rotationYaw);
            if (Math.abs(yawDelta) > 10.0F) {
                strafe = yawDelta > 0.0F ? 1.0F : -1.0F;
            }

            if (ZestTappingMod.sequencePhase == 2) {
                forward = 0.0F;
                strafe = ZestTappingMod.strafeRight ? 1.0F : -1.0F;
            }

            if (ZestTappingMod.currentTargetDistance > 4.0D && ZestTappingMod.sequencePhase != 2) {
                forward = 1.0F;
            } else if (ZestTappingMod.sequencePhase == 2) {
                forward = 0.0F;
            } else {
                forward = 1.0F;
            }
        }

        mc.thePlayer.movementInput.moveForward = forward;
        mc.thePlayer.movementInput.moveStrafe = strafe;

        if (ZestTappingMod.autoTapEnabled && settings.keyBindAttack != null) {
            boolean attackNow = ZestTappingMod.attackTickCounter == 0;
            KeyBinding.setKeyBindState(settings.keyBindAttack.getKeyCode(), attackNow);
            ZestTappingMod.attackTickCounter++;
        }
    }
}
