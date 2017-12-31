package com.bergerkiller.bukkit.common.utils;

import com.bergerkiller.bukkit.common.conversion.type.HandleConversion;
import com.bergerkiller.bukkit.common.internal.CommonNMS;
import com.bergerkiller.bukkit.common.internal.CommonPlugin;
import com.bergerkiller.bukkit.common.protocol.CommonPacket;
import com.bergerkiller.bukkit.common.protocol.PacketListener;
import com.bergerkiller.bukkit.common.protocol.PacketMonitor;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.bergerkiller.bukkit.common.wrappers.EntityTracker;
import com.bergerkiller.generated.net.minecraft.server.ChunkHandle;
import com.bergerkiller.generated.net.minecraft.server.EntityTrackerEntryHandle;
import com.bergerkiller.generated.net.minecraft.server.PacketHandle;
import com.bergerkiller.generated.net.minecraft.server.TileEntityHandle;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;

public class PacketUtil {

    /**
     * Sends all the packets required to properly display a chunk to a player
     *
     * @param player to send to
     * @param chunk to send the information of
     */
    public static void sendChunk(Player player, org.bukkit.Chunk chunk) {
        sendChunk(player, chunk, true);
    }

    /**
     * Sends all the packets required to properly display a chunk to a player.
     * To only send (Tile)Entity related information, use a 'sendPayload' of
     * False.
     *
     * @param player to send to
     * @param chunk to send the information of
     * @param sendPayload - whether the block data is sent
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public static void sendChunk(final Player player, final org.bukkit.Chunk chunk, boolean sendPayload) {
        final Object chunkHandle = HandleConversion.toChunkHandle(chunk);
        final Map<Object, Object> tileEntities = (Map<Object, Object>) ChunkHandle.T.tileEntities.raw.get(chunkHandle);

        // Send payload
        if (sendPayload) {
            sendPacket(player, PacketType.OUT_MAP_CHUNK.newInstance(chunk));
            //sendPacket(player, PacketType.OUT_MAP_CHUNK_BULK.newInstance(Arrays.asList(chunk)));
        }
        // Tile entities
        CommonPacket packet;
        for (Object tile : tileEntities.values()) {
            if (tile == null) {
                continue;
            }
            if ((packet = TileEntityHandle.T.getUpdatePacket.invoke(tile)) != null) {
                PacketUtil.sendPacket(player, packet);
            }
        }

        // Entity spawn messages
        CommonUtil.nextTick(new Runnable() {
            public void run() {
                WorldUtil.getTracker(player.getWorld()).spawnEntities(player, chunk);
            }
        });
    }

    /**
     * Fakes a packet sent from the Client to the Server for a certain Player.
     *
     * @param player to receive a packet for
     * @param packet to receive
     */
    public static void receivePacket(Player player, CommonPacket packet) {
        receivePacket(player, (Object) packet);
    }

    /**
     * Fakes a packet sent from the Client to the Server for a certain Player.
     *
     * @param player to receive a packet for
     * @param packet to receive
     */
    public static void receivePacket(Player player, Object packet) {
        if (packet instanceof CommonPacket) {
            packet = ((CommonPacket) packet).getHandle();
        }
        if (packet == null) {
            return;
        }
        CommonPlugin.getInstance().getPacketHandler().receivePacket(player, packet);
    }

    public static void sendPacket(Player player, CommonPacket packet) {
        sendPacket(player, packet, true);
    }

    public static void sendPacket(Player player, CommonPacket packet, boolean throughListeners) {
        if (packet != null) {
            Object rawPacket = packet.getHandle();
            if (rawPacket != null) {
                CommonPlugin.getInstance().getPacketHandler().sendPacket(player, rawPacket, throughListeners);
            }
        }
    }

    public static void sendPacket(Player player, PacketHandle packet) {
        sendPacket(player, packet, true);
    }

    public static void sendPacket(Player player, PacketHandle packet, boolean throughListeners) {
        if (packet != null) {
            CommonPlugin.getInstance().getPacketHandler().sendPacket(player, packet.getRaw(), throughListeners);
        }
    }

    /**
     * Sends a raw packet to a player. All wrapper types for Packet are supported as well.<br>
     * <b>Deprecated: Please avoid using raw packet types</b>
     * 
     * @param player to send to
     * @param packet to send
     */
    @Deprecated
    public static void sendPacket(Player player, Object packet) {
        sendPacket(player, packet, true);
    }

    /**
     * Sends a raw packet to a player. All wrapper types for Packet are supported as well.<br>
     * <b>Deprecated: Please avoid using raw packet types</b>
     * 
     * @param player to send to
     * @param packet to send
     * @param throughListeners whether to let packet listeners see this packet
     */
    @Deprecated
    public static void sendPacket(Player player, Object packet, boolean throughListeners) {
        if (packet instanceof CommonPacket) {
            packet = ((CommonPacket) packet).getHandle();
        } else if (packet instanceof PacketHandle) {
            packet = ((PacketHandle) packet).getRaw();
        }
        if (packet == null) {
            return;
        }
        CommonPlugin.getInstance().getPacketHandler().sendPacket(player, packet, throughListeners);
    }

    public static void broadcastBlockPacket(Block block, Object packet, boolean throughListeners) {
        broadcastBlockPacket(block.getWorld(), block.getX(), block.getZ(), packet, throughListeners);
    }

    public static void broadcastBlockPacket(org.bukkit.World world, final int x, final int z, Object packet, boolean throughListeners) {
        if (packet instanceof CommonPacket) {
            packet = ((CommonPacket) packet).getHandle();
        }
        if (world == null || packet == null) {
            return;
        }
        for (Player player : WorldUtil.getPlayers(world)) {
            if (EntityUtil.isNearBlock(player, x, z, CommonUtil.BLOCKVIEW)) {
                sendPacket(player, packet, throughListeners);
            }
        }
    }

    public static void broadcastChunkPacket(org.bukkit.Chunk chunk, Object packet, boolean throughListeners) {
        if (packet instanceof CommonPacket) {
            packet = ((CommonPacket) packet).getHandle();
        }
        if (chunk == null || packet == null) {
            return;
        }

        for (Player player : WorldUtil.getPlayers(chunk.getWorld())) {
            if (EntityUtil.isNearChunk(player, chunk.getX(), chunk.getZ(), CommonUtil.VIEW)) {
                sendPacket(player, packet, throughListeners);
            }
        }
    }

    public static void broadcastPacket(Object packet, boolean throughListeners) {
        if (packet instanceof CommonPacket) {
            packet = ((CommonPacket) packet).getHandle();
        }
        for (Player player : CommonUtil.getOnlinePlayers()) {
            sendPacket(player, packet, throughListeners);
        }
    }

    /**
     * Sends a packet relating a certain entity to all players that can see it.
     * If the entity is a player itself, he also receives the packet.
     * 
     * @param entity the packet is about
     * @param packet to send
     */
    public static void broadcastEntityPacket(Entity entity, CommonPacket packet) {
        broadcastEntityPacket(entity, packet, true);
    }

    /**
     * Sends a packet relating a certain entity to all players that can see it
     * 
     * @param entity the packet is about
     * @param packet to send
     * @param sendToSelf whether to also send to the player itself, if the entity is a player
     */
    public static void broadcastEntityPacket(Entity entity, CommonPacket packet, boolean sendToSelf) {
        if (entity == null || packet == null) return;

        EntityTracker tracker = WorldUtil.getTracker(entity.getWorld());
        EntityTrackerEntryHandle entry = tracker.getEntry(entity);
        if (entry != null) {
            for (Player viewer : entry.getViewers()) {
                sendPacket(viewer, packet);
            }
        }
        if (sendToSelf && entity instanceof Player) {
            sendPacket((Player) entity, packet);
        }
    }

    /**
     * Adds a single packet monitor. Packet monitors only monitor (not change)
     * packets.
     *
     * @param plugin to register for
     * @param monitor to register
     * @param packets to register for
     */
    public static void addPacketMonitor(Plugin plugin, PacketMonitor monitor, PacketType... packets) {
        if (monitor == null || LogicUtil.nullOrEmpty(packets)) {
            return;
        }
        CommonPlugin.getInstance().getPacketHandler().addPacketMonitor(plugin, monitor, packets);
    }

    /**
     * Adds a single packet listener. Packet listeners can modify packets.
     *
     * @param plugin to register for
     * @param listener to register
     * @param packets to register for
     */
    public static void addPacketListener(Plugin plugin, PacketListener listener, PacketType... packets) {
        if (listener == null || LogicUtil.nullOrEmpty(packets)) {
            return;
        }
        CommonPlugin.getInstance().getPacketHandler().addPacketListener(plugin, listener, packets);
    }

    /**
     * Removes all packet listeners AND monitors of a plugin
     *
     * @param plugin to remove the registered monitors and listeners of
     */
    public static void removePacketListeners(Plugin plugin) {
        CommonPlugin.getInstance().getPacketHandler().removePacketListeners(plugin);
    }

    /**
     * Removes a single registered packet listener
     *
     * @param listener to remove
     */
    public static void removePacketListener(PacketListener listener) {
        CommonPlugin.getInstance().getPacketHandler().removePacketListener(listener);
    }

    /**
     * Removes a single registered packet monitor
     *
     * @param monitor to remove
     */
    public static void removePacketMonitor(PacketMonitor monitor) {
        CommonPlugin.getInstance().getPacketHandler().removePacketMonitor(monitor);
    }

    public static void broadcastPacketNearby(Location location, double radius, Object packet) {
        broadcastPacketNearby(location.getWorld(), location.getX(), location.getY(), location.getZ(), radius, packet);
    }

    public static void broadcastPacketNearby(org.bukkit.World world, double x, double y, double z, double radius, Object packet) {
        CommonPacket packetWrap;
        if (packet instanceof CommonPacket) {
            packetWrap = (CommonPacket) packet;
        } else {
            packetWrap = new CommonPacket(packet);
        }
        CommonNMS.getPlayerList().sendPacketNearby(null, x, y, z, radius, WorldUtil.getDimension(world), packetWrap);
    }

    /**
     * Obtains a collection of all plugins currently listening for the Packet
     * type specified. Packets of this type can be expected to be handled by
     * these plugins when sending it.
     *
     * @param packetType to get the listening plugins for
     * @return collection of listening plugins
     */
    public static Collection<Plugin> getListenerPlugins(PacketType packetType) {
        return CommonPlugin.getInstance().getPacketHandler().getListening(packetType);
    }
}
