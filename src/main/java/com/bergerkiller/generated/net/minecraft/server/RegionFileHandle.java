package com.bergerkiller.generated.net.minecraft.server;

import java.io.File;
import java.io.RandomAccessFile;
import com.bergerkiller.mountiplex.reflection.declarations.Template;
import com.bergerkiller.mountiplex.reflection.util.StaticInitHelper;

public class RegionFileHandle extends Template.Handle {
    public static final RegionFileClass T = new RegionFileClass();
    static final StaticInitHelper _init_helper = new StaticInitHelper(RegionFileHandle.class, "net.minecraft.server.RegionFile");

    /* ============================================================================== */

    public static RegionFileHandle createHandle(Object handleInstance) {
        if (handleInstance == null) return null;
        RegionFileHandle handle = new RegionFileHandle();
        handle.instance = handleInstance;
        return handle;
    }

    public static final RegionFileHandle createNew(File file) {
        return T.constr_file.newInstance(file);
    }

    /* ============================================================================== */

    public void close() {
        T.close.invoke(instance);
    }

    public boolean chunkExists(int cx, int cz) {
        return T.chunkExists.invoke(instance, cx, cz);
    }

    public File getFile() {
        return T.file.get(instance);
    }

    public void setFile(File value) {
        T.file.set(instance, value);
    }

    public RandomAccessFile getStream() {
        return T.stream.get(instance);
    }

    public void setStream(RandomAccessFile value) {
        T.stream.set(instance, value);
    }

    public static final class RegionFileClass extends Template.Class<RegionFileHandle> {
        public final Template.Constructor.Converted<RegionFileHandle> constr_file = new Template.Constructor.Converted<RegionFileHandle>();

        public final Template.Field<File> file = new Template.Field<File>();
        public final Template.Field<RandomAccessFile> stream = new Template.Field<RandomAccessFile>();

        public final Template.Method<Void> close = new Template.Method<Void>();
        public final Template.Method<Boolean> chunkExists = new Template.Method<Boolean>();

    }

}

