/*
 * This file is part of the Carpet AMS Addition project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2024 A Minecraft Server and contributors
 *
 * Carpet AMS Addition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Carpet AMS Addition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Carpet AMS Addition. If not, see <https://www.gnu.org/licenses/>.
 */

package club.mcams.carpet.utils.compat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class MessengerCompatFactory {
    public static BaseText CarpetCompoundText(Object... fields) {
        return carpet.utils.Messenger.c(fields);
    }

    public static LiteralText LiteralText(String text) {
        return new LiteralText(text);
    }

    public static void sendFeedBack(ServerCommandSource source, BaseText text, boolean broadcastToOps) {
        source.sendFeedback(text, broadcastToOps);
    }

    // Send system message to server
    public static void sendSystemMessage(MinecraftServer server, Text text) {
        server.sendMessage(text);
    }

    // Send system message to player
    public static void sendSystemMessage(PlayerEntity player, Text text) {
        player.sendMessage(text);
    }

    public static BaseText TranslatableText(String key, Object... args) {
        return new TranslatableText(key, args);
    }
}
