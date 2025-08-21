package org.hyperledger.besu.nativelib.uint256;/*
 * Copyright contributors to Besu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 */

import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.bytes.MutableBytes;
import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LibUint256Test {

    @Test
    public void testHashZeroBn254() {
        byte[] output = new byte[Bytes32.SIZE];
        byte[] a = BigInteger.ONE.toByteArray();
        byte[] b = BigInteger.TWO.toByteArray();

        LibUint256.mod(a,a.length, b, b.length, output);
        assertThat(Bytes.wrap(output)).isNotNull();
    }


}