package com.github.unldenis.corpse.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.github.unldenis.corpse.CorpseP;
import com.github.unldenis.corpse.logic.Corpse;
import com.github.unldenis.corpse.manager.CorpsePool;

import java.util.List;
import java.util.Optional;

public class InteractListener extends PacketAdapter {
    public InteractListener(CorpseP plugin) {
        super(plugin, ListenerPriority.NORMAL, List.of(PacketType.Play.Client.USE_ENTITY), ListenerOptions.SYNC);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        int id = packet.getIntegers().readSafely(0);
        Optional<Corpse> corpse = CorpsePool.getInstance().getCorpse(id);
        if (corpse.isPresent()) {
            if (event.getPlayer().getLocation().distanceSquared(corpse.get().getLocation()) < 25) {
                event.getPlayer().openInventory(corpse.get().getDeathInventory());
                event.setCancelled(true);
            }
        }
    }
}
