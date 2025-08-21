package org.hyperledger.besu.nativelib.uint256;

import org.hyperledger.besu.nativelib.common.BesuNativeLibraryLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LibUint256 {

    public static final boolean ENABLED;

    static {
        boolean enabled;
        try {
            BesuNativeLibraryLoader.loadJNI(LibUint256.class,"uint256_jni");
            enabled = true;
        } catch (final UnsatisfiedLinkError e) {
            e.printStackTrace();
            enabled = false;
        }
        ENABLED = enabled;
    }

    // Méthodes natives JNI
    private static native int modNative(byte[] a, int aLen,
                                        byte[] b, int bLen,
                                        byte[] out);

    private static final ThreadLocal<byte[]> TEMP_A = ThreadLocal.withInitial(() -> new byte[32]);
    private static final ThreadLocal<byte[]> TEMP_B = ThreadLocal.withInitial(() -> new byte[32]);
    private static final ThreadLocal<byte[]> TEMP_OUT = ThreadLocal.withInitial(() -> new byte[32]);

    public static byte[] mod256(byte[] a, byte[] b) {
        byte[] tempA = TEMP_A.get();
        byte[] tempB = TEMP_B.get();
        byte[] out = TEMP_OUT.get();
        System.arraycopy(a, 0, tempA, 32 - a.length, a.length);
        System.arraycopy(b, 0, tempB, 32 - b.length, b.length);

        int n = modNative(tempA, 32, tempB, 32, out);

        if (n < 0) {
            throw new ArithmeticException("mod error: " + n);
        }

        return out.clone(); // Retourner une copie
    }

}
