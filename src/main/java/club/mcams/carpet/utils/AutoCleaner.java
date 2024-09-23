package club.mcams.carpet.utils;

import club.mcams.carpet.AmsServer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class AutoCleaner {
    @SuppressWarnings("Convert2Diamond")
    public static void removeAmsDataFolder(MinecraftServer server) {
        if (Files.exists(getAmsDataFolderPath(server))) {
            try {
                Files.walkFileTree(getAmsDataFolderPath(server), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                AmsServer.LOGGER.warn("Failed to delete AmsData folder.");
            }
        }
    }

    public static Path getAmsDataFolderPath(MinecraftServer server) {
        Path worldDirectory = server.getWorld(DimensionType.OVERWORLD).getSaveHandler().getWorldDir().toPath();
        return worldDirectory.resolve("datapacks").resolve("AmsData");
    }
}
