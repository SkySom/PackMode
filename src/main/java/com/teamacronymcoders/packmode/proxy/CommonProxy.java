package com.teamacronymcoders.packmode.proxy;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class CommonProxy<T extends EntityPlayer> {
    public List<T> getPlayers() {
        return Lists.newArrayList();
    }
}
