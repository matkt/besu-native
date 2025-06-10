package org.hyperledger.besu.nativelib.constantine;

import com.google.common.collect.Streams;
import com.google.common.io.CharStreams;
import com.sun.jna.ptr.IntByReference;
import org.apache.tuweni.bytes.Bytes;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class LibConstantineModExpTest {

    @Parameterized.Parameter(0)
    public String inputString;
    @Parameterized.Parameter(1)
    public String outputString;

    @Parameterized.Parameters
    public static Iterable<String[]> modExpParameters() {
        return List.of(
                new String[]{
                        "000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001",
                        "0000000000000000000000000000000000000000000000000000000000000000"
                },
                new String[]{
                        "000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001",
                        "0000000000000000000000000000000000000000000000000000000000000000"
                }
        );
    }

    @Test
    public void testModExp() {
        Bytes input = Bytes.fromHexString(inputString);
        Bytes output = Bytes.fromHexString(outputString);

        byte[] resultArray = new byte[output.size()];
        IntByReference resultSize = new IntByReference(resultArray.length);
        LibConstantineModExp.modexp_precompiled(resultArray, input.toArrayUnsafe());

        Bytes result = Bytes.wrap(resultArray, 0, resultSize.getValue());

        assertThat(result).isEqualTo(output);
    }

}
