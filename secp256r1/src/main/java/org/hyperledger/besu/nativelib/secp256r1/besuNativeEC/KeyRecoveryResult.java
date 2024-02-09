/*
 * Copyright ConsenSys AG.
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
 */

package org.hyperledger.besu.nativelib.secp256r1.besuNativeEC;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class KeyRecoveryResult extends Structure {
	public byte[] public_key = new byte[131];
	public byte[] error_message = new byte[256];

	public KeyRecoveryResult() {
		super();
	}

  @Override
  protected List<String> getFieldOrder() {
		return Arrays.asList("public_key", "error_message");
	}

	public static class KeyRecoveryResultByValue extends KeyRecoveryResult implements Structure.ByValue {}
}
