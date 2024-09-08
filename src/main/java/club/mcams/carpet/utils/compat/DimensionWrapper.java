package club.mcams.carpet.utils.compat;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Objects;

public class DimensionWrapper {

    private final DimensionType dimensionType;

    public DimensionWrapper(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public static DimensionWrapper of(DimensionType dimensionType) {
        return new DimensionWrapper(dimensionType);
    }

    public static DimensionWrapper of(World world) {
        return new DimensionWrapper(world.getDimension().getType());
    }

    public static DimensionWrapper of(Entity entity) {
        return of(entity.getEntityWorld());
    }

    public DimensionType getValue() {
        return this.dimensionType;
    }

    public Identifier getIdentifier() {
        return DimensionType.getId(this.dimensionType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DimensionWrapper that = (DimensionWrapper) o;
        return Objects.equals(dimensionType, that.dimensionType);
    }

    @Override
    public int hashCode() {
        return this.dimensionType.hashCode();
    }

    public String getIdentifierString() {
        return this.dimensionType.toString();
    }

    @Deprecated
    @Override
    public String toString() {
        return this.getIdentifierString();
    }
}
