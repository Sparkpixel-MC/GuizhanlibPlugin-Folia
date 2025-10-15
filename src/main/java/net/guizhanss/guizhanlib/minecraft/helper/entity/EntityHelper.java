package net.guizhanss.guizhanlib.minecraft.helper.entity;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

/**
 * @deprecated 请使用 {@link net.guizhanss.minecraft.guizhanlib.gugu.minecraft.helpers.entity.EntityHelper} 代替
 */

@UtilityClass
@SuppressWarnings("unused")
public final class EntityHelper {

    @Nonnull
    public static String getDisplayName(@Nonnull Entity entity) {
        return net.guizhanss.minecraft.guizhanlib.gugu.minecraft.helpers.entity.EntityHelper.getDisplayName(entity);
    }

    @Nonnull
    public static String getName(@Nonnull Entity entity) {
        return net.guizhanss.minecraft.guizhanlib.gugu.minecraft.helpers.entity.EntityHelper.getName(entity);
    }
}
