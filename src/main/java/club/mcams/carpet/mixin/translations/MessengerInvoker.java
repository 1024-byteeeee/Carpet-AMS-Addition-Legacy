package club.mcams.carpet.mixin.translations;

import club.mcams.carpet.utils.compat.DummyInterface;

import org.spongepowered.asm.mixin.Mixin;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft < 1.15.2")
@Mixin(DummyInterface.class)
public interface MessengerInvoker {}
