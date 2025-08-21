package org.hyperledger.besu.nativelib.uint256;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.hyperledger.besu.nativelib.common.BesuNativeLibraryLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LibUint256 {

    public static final boolean ENABLED;

    static {
        boolean enabled;
        try {
            BesuNativeLibraryLoader.registerJNA(LibUint256.class,"uint256_jni");
            enabled = true;
        } catch (final Throwable t) {
            t.printStackTrace();
            enabled = false;
        }
        ENABLED = enabled;
    }

    public static native int mod(com.sun.jna.Pointer a, int aLen,
                                 com.sun.jna.Pointer b, int bLen,
                                 com.sun.jna.Pointer out);

    public static byte[] mod256(byte[] a, byte[] b) {
        ByteBuffer aBuf = ByteBuffer.allocateDirect(a.length).order(ByteOrder.BIG_ENDIAN);
        aBuf.put(a).flip();

        ByteBuffer bBuf = ByteBuffer.allocateDirect(b.length).order(ByteOrder.BIG_ENDIAN); // ← fix ici
        bBuf.put(b).flip();

        ByteBuffer outBuf = ByteBuffer.allocateDirect(32);

        Pointer aPtr = Native.getDirectBufferPointer(aBuf);
        Pointer bPtr = Native.getDirectBufferPointer(bBuf);
        Pointer outPtr = Native.getDirectBufferPointer(outBuf);

        // 3) Appel natif
        int n = LibUint256.mod(aPtr, aBuf.remaining(), bPtr, bBuf.remaining(), outPtr);
        if (n < 0) {
            throw new IllegalArgumentException("mod() native error code: " + n);
        }
        if (n != 32) {
            throw new IllegalStateException("Unexpected output length: " + n);
        }

        // 4) Lire la sortie
        outBuf.position(0);
        byte[] out = new byte[32];
        outBuf.get(out);
        return out;
    }
}
