package org.hyperledger.besu.nativelib.uint256;

import org.hyperledger.besu.nativelib.common.BesuNativeLibraryLoader;

public class LibUint256 {

    public static final boolean ENABLED;

    static {
        boolean enabled;
        try {
            BesuNativeLibraryLoader.registerJNA(LibUint256.class, "uint256_jni");
            enabled = true;
        } catch (final Throwable t) {
            t.printStackTrace();
            enabled = false;
        }
        ENABLED = enabled;
    }

    public static native void mod(byte[] a, int aLen, byte[] b, int bLen, byte[] out);

}
