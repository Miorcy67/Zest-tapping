package com.zesttapping;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {
    public static KeyBinding toggleBinding;

    @Override
    public void registerKeyBindings() {
        toggleBinding = new KeyBinding("key.zesttapping.toggle", Keyboard.KEY_P, "key.category.zesttapping");
        ClientRegistry.registerKeyBinding(toggleBinding);
    }
}
