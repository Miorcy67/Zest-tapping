package com.zesttapping;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ZestTapCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "zesttap";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "command.zesttapping.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0 || "help".equals(args[0])) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Zest Tapping help"));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Use /zesttap toggle to switch the overlay."));
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Use /zesttap reset to restart the rhythm."));
            return;
        }

        if ("toggle".equals(args[0])) {
            ZestTappingMod.overlayEnabled = !ZestTappingMod.overlayEnabled;
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Zest tap overlay " + (ZestTappingMod.overlayEnabled ? "enabled" : "disabled")));
            return;
        }

        if ("reset".equals(args[0])) {
            ZestTappingMod.pulsePhase = 0;
            ZestTappingMod.tickCounter = 0;
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Zest tap rhythm reset."));
            return;
        }

        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Unknown subcommand. Use /zesttap help."));
    }
}
