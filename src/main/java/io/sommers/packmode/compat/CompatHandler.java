package io.sommers.packmode.compat;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sommers.packmode.PackMode;
import io.sommers.packmode.compat.crafttweaker.CraftTweakerCompat;
import net.minecraftforge.fml.common.Loader;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompatHandler {
    private static final List<Compat> compat = Lists.newArrayList();
    private static final Map<String, String> compatClasses = Maps.newHashMap();

    static {
        compatClasses.put("crafttweaker", "io.sommers.packmode.compat.crafttweaker.CraftTweakerCompat");
        compatClasses.put("gamestages", "io.sommers.packmode.compat.gamestages.GameStagesCompat");
    }

    public static void tryActivate() {
        compat.addAll(compatClasses.entrySet().stream()
                .filter(entry -> Loader.isModLoaded(entry.getKey()))
                .map(Entry::getValue)
                .map(CompatHandler::loadCompat)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    public static Compat loadCompat(String compatClassString) {
        Compat compatInstance = null;
        try {
            Class compatClass = Class.forName(compatClassString);
            Object objectInstance = compatClass.newInstance();
            if (objectInstance instanceof Compat) {
                compatInstance = (Compat) objectInstance;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            PackMode.logger.error(e);
        }
        return compatInstance;
    }

    public static void preInit() {
        compat.forEach(Compat::preInit);
    }
}
