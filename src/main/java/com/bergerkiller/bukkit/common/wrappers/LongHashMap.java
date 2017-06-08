package com.bergerkiller.bukkit.common.wrappers;

import com.bergerkiller.bukkit.common.utils.MathUtil;
import com.bergerkiller.mountiplex.reflection.declarations.Template;

import gnu.trove.map.hash.TLongObjectHashMap;

import java.util.Collection;

/**
 * A wrapper around the internal LongHashMap implementation. This type of
 * HashMap allows storing values at long keys. Ideally, two int values are
 * merged into one long to map against 2D-coordinates. This type of class is
 * used by, for example, the internal chunk storage.
 *
 * @param <V> - Value type
 */
@SuppressWarnings("unchecked")
public class LongHashMap<V> extends BasicWrapper<Template.Handle> {

    public LongHashMap() {
        this(new TLongObjectHashMap<V>());
    }

    /**
     * Constructs a new LongHashMap with an initial capacity as specified
     *
     * @param initialCapacity for the new LongHashMap
     */
    public LongHashMap(int initialCapacity) {
        this(new TLongObjectHashMap<V>(initialCapacity));
    }

    public LongHashMap(Object handle) {
        this.setHandle(Template.Handle.createHandle(handle));
    }

    /**
     * Gets the amount of Long:Value pairs stored in this LongHashMap
     *
     * @return size
     */
    public int size() {
        return ((TLongObjectHashMap<V>) getRawHandle()).size();
    }

    /**
     * Checks whether this Long HashMap contains the coordinates specified.<br>
     * <b>Warning: this method was added in v1.54 and is not compatible with MC
     * 1.5.2.</b>
     *
     * @param msw - most significant part of the key
     * @param lsw - least signfificant part of the key
     * @return True if contained, False if not
     */
    public boolean contains(int msw, int lsw) {
        return contains(MathUtil.longHashToLong(msw, lsw));
    }

    public boolean contains(long key) {
        return ((TLongObjectHashMap<V>) getRawHandle()).containsKey(key);
    }

    /**
     * Gets the value stored at the coordinates specified.<br>
     * <b>Warning: this method was added in v1.54 and is not compatible with MC
     * 1.5.2.</b>
     *
     * @param msw - most significant part of the key
     * @param lsw - least signfificant part of the key
     * @return The value stored at the key, or null if none stored
     */
    public V get(int msw, int lsw) {
        return get(MathUtil.longHashToLong(msw, lsw));
    }

    public V get(long key) {
        return ((TLongObjectHashMap<V>) getRawHandle()).get(key);
    }

    /**
     * Removes and obtains the value stored at the coordinates specified.<br>
     * <b>Warning: this method was added in v1.54 and is not compatible with MC
     * 1.5.2.</b>
     *
     * @param msw - most significant part of the key
     * @param lsw - least signfificant part of the key
     * @return The removed value previously stored at the key, or null if none
     * was stored
     */
    public V remove(int msw, int lsw) {
        return remove(MathUtil.longHashToLong(msw, lsw));
    }

    public V remove(long key) {
        return ((TLongObjectHashMap<V>) getRawHandle()).remove(key);
    }

    /**
     * Puts a value at the coordinates specified.<br>
     * <b>Warning: this method was added in v1.54 and is not compatible with MC
     * 1.5.2.</b>
     *
     * @param msw - most significant part of the key
     * @param lsw - least signfificant part of the key
     * @param value to put at the coordinates
     */
    public void put(int msw, int lsw, V value) {
        put(MathUtil.longHashToLong(msw, lsw), value);
    }

    public void put(long key, V value) {
        ((TLongObjectHashMap<V>) getRawHandle()).put(key, value);
    }

    public Collection<V> getValues() {
        return ((TLongObjectHashMap<V>) getRawHandle()).valueCollection();
    }

    public long[] getKeys() {
        return ((TLongObjectHashMap<V>) getRawHandle()).keySet().toArray(new long[0]);
    }
}
