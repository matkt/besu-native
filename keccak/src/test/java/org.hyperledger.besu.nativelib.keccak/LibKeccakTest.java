package org.hyperledger.besu.nativelib.keccak;

import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import java.nio.ByteBuffer;


public class LibKeccakTest {
        @Test
        public void testCallLibrary() {

            byte[] input = Bytes32.fromHexString("0x27e5458b666ef581475a9acddbc3524ca252185cae3936506e65cda9c358222b").toArrayUnsafe();
            ByteBuffer inputBuffer = ByteBuffer.allocateDirect(input.length);
            inputBuffer.put(input);

            byte[] output = new byte[32];
            ByteBuffer outputBuffer = ByteBuffer.allocateDirect(output.length);
            LibKeccak.compute(inputBuffer, outputBuffer, inputBuffer.capacity(), outputBuffer.capacity());
            outputBuffer.get(output);
            
            AssertionsForClassTypes.assertThat(Bytes.wrap(output)).isEqualTo(Bytes.fromHexString("0xd83adfb4126ec594651cc9e5442169921d904ded7843287aff5bb1adf02d3f98"));
        }
}
