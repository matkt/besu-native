/*
 * Copyright Besu Contributors
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

extern crate jni;
extern crate tiny_keccak;
use jni::objects::JClass;
use jni::sys::jbyteArray;
use jni::JNIEnv;
use tiny_keccak::{Hasher, Keccak};

#[no_mangle]
pub extern "system" fn Java_org_hyperledger_besu_nativelib_keccak_LibKeccak_compute
(
    env: JNIEnv,
    _class: JClass,
    input: jbyteArray,
) -> jbyteArray {

                let input = env
                        .convert_byte_array(input)
                        .expect("Cannot convert jbyteArray to rust array");

                let mut raw_out: [u8; 32] = [0; 32];
                let mut keccak = Keccak::v256();
                keccak.update(&input);
                keccak.finalize(&mut raw_out);

                env.byte_array_from_slice(&raw_out)
                        .expect("Couldn't convert to byte array")
}
