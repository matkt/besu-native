package org.hyperledger.besu.nativelib.keccak;

import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;


public class LibKeccakTest {
        @Test
        public void testCallLibrary() {
            byte[] compute = LibKeccak.compute(Bytes32.fromHexString("0x27e5458b666ef581475a9acddbc3524ca252185cae3936506e65cda9c358222b").toArrayUnsafe());
            AssertionsForClassTypes.assertThat(Bytes.wrap(compute)).isEqualTo(Bytes.fromHexString("0xd83adfb4126ec594651cc9e5442169921d904ded7843287aff5bb1adf02d3f98"));
        }
}
