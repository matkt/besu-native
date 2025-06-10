package org.hyperledger.besu.nativelib.constantine;

import com.sun.jna.Native;
import org.hyperledger.besu.nativelib.common.BesuNativeLibraryLoader;

public class LibConstantineModExp {
    public static final boolean ENABLED;

    static {
        boolean enabled;
        try {
            BesuNativeLibraryLoader.registerJNA(LibConstantineModExp.class, "constantinebindings");
            enabled = true;
        } catch (final Throwable t) {
            t.printStackTrace();
            enabled = false;
        }
        ENABLED = enabled;
    }

    public static native int modexp(byte[] r, int r_len, byte[] inputs, int inputs_len);

    public static int modexp_precompiled(byte[] result, byte[] inputs) {
        return modexp(result, result.length, inputs, inputs.length);
    }

}
