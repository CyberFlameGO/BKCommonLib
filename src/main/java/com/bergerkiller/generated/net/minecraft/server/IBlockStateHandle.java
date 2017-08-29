package com.bergerkiller.generated.net.minecraft.server;

import com.bergerkiller.mountiplex.reflection.util.StaticInitHelper;
import com.bergerkiller.mountiplex.reflection.declarations.Template;

/**
 * Instance wrapper handle for type <b>net.minecraft.server.IBlockState</b>.
 * To access members without creating a handle type, use the static {@link #T} member.
 * New handles can be created from raw instances using {@link #createHandle(Object)}.
 */
public class IBlockStateHandle extends Template.Handle {
    /** @See {@link IBlockStateClass} */
    public static final IBlockStateClass T = new IBlockStateClass();
    static final StaticInitHelper _init_helper = new StaticInitHelper(IBlockStateHandle.class, "net.minecraft.server.IBlockState");

    /* ============================================================================== */

    public static IBlockStateHandle createHandle(Object handleInstance) {
        if (handleInstance == null) return null;
        IBlockStateHandle handle = new IBlockStateHandle();
        handle.instance = handleInstance;
        return handle;
    }

    /* ============================================================================== */

    public String getKeyToken() {
        return T.getKeyToken.invoke(instance);
    }

    public String getValueToken(Object value) {
        return T.getValueToken.invoke(instance, value);
    }

    /**
     * Stores class members for <b>net.minecraft.server.IBlockState</b>.
     * Methods, fields, and constructors can be used without using Handle Objects.
     */
    public static final class IBlockStateClass extends Template.Class<IBlockStateHandle> {
        public final Template.Method<String> getKeyToken = new Template.Method<String>();
        public final Template.Method<String> getValueToken = new Template.Method<String>();

    }

}

