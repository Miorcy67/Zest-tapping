package com.zesttapping;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
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
}
