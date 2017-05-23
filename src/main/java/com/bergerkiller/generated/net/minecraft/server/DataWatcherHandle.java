package com.bergerkiller.generated.net.minecraft.server;

import com.bergerkiller.bukkit.common.wrappers.DataWatcher.Item;
import com.bergerkiller.mountiplex.reflection.declarations.Template;
import java.util.List;
import com.bergerkiller.mountiplex.reflection.util.StaticInitHelper;
import com.bergerkiller.bukkit.common.wrappers.DataWatcher.Key;

public class DataWatcherHandle extends Template.Handle {
    public static final DataWatcherClass T = new DataWatcherClass();
    static final StaticInitHelper _init_helper = new StaticInitHelper(DataWatcherHandle.class, "net.minecraft.server.DataWatcher");


    /* ============================================================================== */

    public static DataWatcherHandle createHandle(Object handleInstance) {
        if (handleInstance == null) return null;
        DataWatcherHandle handle = new DataWatcherHandle();
        handle.instance = handleInstance;
        return handle;
    }

    public static final DataWatcherHandle createNew(EntityHandle entity) {
        return T.constr_entity.newInstance(entity);
    }

    /* ============================================================================== */

    public List<Item<?>> unwatchAndReturnAllWatched() {
        return T.unwatchAndReturnAllWatched.invoke(instance);
    }

    public List<Item<?>> returnAllWatched() {
        return T.returnAllWatched.invoke(instance);
    }

    public void watch(Key<?> key, Object defaultValue) {
        T.watch.invoke(instance, key, defaultValue);
    }

    public Item<Object> read(Key<?> key) {
        return T.read.invoke(instance, key);
    }

    public Object get(Key<?> key) {
        return T.get.invoke(instance, key);
    }

    public void set(Key<?> key, Object value) {
        T.set.invoke(instance, key, value);
    }

    public boolean isChanged() {
        return T.isChanged.invoke(instance);
    }

    public boolean isEmpty() {
        return T.isEmpty.invoke(instance);
    }

    public EntityHandle getOwner() {
        return T.owner.get(instance);
    }

    public void setOwner(EntityHandle value) {
        T.owner.set(instance, value);
    }

    public static final class DataWatcherClass extends Template.Class<DataWatcherHandle> {
        public final Template.Constructor.Converted<DataWatcherHandle> constr_entity = new Template.Constructor.Converted<DataWatcherHandle>();

        public final Template.Field.Converted<EntityHandle> owner = new Template.Field.Converted<EntityHandle>();

        public final Template.Method.Converted<List<Item<?>>> unwatchAndReturnAllWatched = new Template.Method.Converted<List<Item<?>>>();
        public final Template.Method.Converted<List<Item<?>>> returnAllWatched = new Template.Method.Converted<List<Item<?>>>();
        public final Template.Method.Converted<Void> watch = new Template.Method.Converted<Void>();
        public final Template.Method.Converted<Item<Object>> read = new Template.Method.Converted<Item<Object>>();
        public final Template.Method.Converted<Object> get = new Template.Method.Converted<Object>();
        public final Template.Method.Converted<Void> set = new Template.Method.Converted<Void>();
        public final Template.Method<Boolean> isChanged = new Template.Method<Boolean>();
        public final Template.Method<Boolean> isEmpty = new Template.Method<Boolean>();

    }

    public static class ItemHandle extends Template.Handle {
        public static final ItemClass T = new ItemClass();
        static final StaticInitHelper _init_helper = new StaticInitHelper(ItemHandle.class, "net.minecraft.server.DataWatcher.Item");


        /* ============================================================================== */

        public static ItemHandle createHandle(Object handleInstance) {
            if (handleInstance == null) return null;
            ItemHandle handle = new ItemHandle();
            handle.instance = handleInstance;
            return handle;
        }

        /* ============================================================================== */

        public Key<?> getKey() {
            return T.key.get(instance);
        }

        public void setKey(Key<?> value) {
            T.key.set(instance, value);
        }

        public Object getValue() {
            return T.value.get(instance);
        }

        public void setValue(Object value) {
            T.value.set(instance, value);
        }

        public boolean isChanged() {
            return T.changed.getBoolean(instance);
        }

        public void setChanged(boolean value) {
            T.changed.setBoolean(instance, value);
        }

        public static final class ItemClass extends Template.Class<ItemHandle> {
            public final Template.Field.Converted<Key<?>> key = new Template.Field.Converted<Key<?>>();
            public final Template.Field<Object> value = new Template.Field<Object>();
            public final Template.Field.Boolean changed = new Template.Field.Boolean();

        }
    }
}
