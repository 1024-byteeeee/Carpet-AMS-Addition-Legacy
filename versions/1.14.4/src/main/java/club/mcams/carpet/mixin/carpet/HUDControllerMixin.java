package club.mcams.carpet.mixin.carpet;

import club.mcams.carpet.utils.compat.DummyClass;

import org.spongepowered.asm.mixin.Mixin;

import top.byteeeee.annotationtoolbox.annotation.GameVersion;

@GameVersion(version = "Minecraft >= 1.15.2")
@Mixin(DummyClass.class)
public abstract class HUDControllerMixin {}
