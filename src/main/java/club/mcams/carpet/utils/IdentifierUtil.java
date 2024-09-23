package club.mcams.carpet.utils;

import net.minecraft.util.Identifier;

public class IdentifierUtil {
	public static Identifier of(String namespace, String path) {
		return new Identifier(namespace, path);
	}

	public static Identifier ofId(String id) {
		return new Identifier(id);
	}
}
