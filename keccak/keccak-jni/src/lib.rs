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
use jni::objects::JByteBuffer;

use jni::sys::jint;
use jni::JNIEnv;
use tiny_keccak::{Hasher, Keccak};
#[no_mangle]
pub extern "system" fn Java_org_hyperledger_besu_nativelib_keccak_LibKeccak_compute
(
    env: JNIEnv,
    _class: JClass,
    input: JByteBuffer,
    output: JByteBuffer,
    input_len: jint,
    output_len: jint,
) {
    // Get a pointer to the ByteBuffer's data
    let input_ptr = env.get_direct_buffer_address(input).expect("Cannot get input buffer address").as_ptr();
    let input_slice = unsafe { std::slice::from_raw_parts(input_ptr, input_len as usize) };

    // Get a pointer to the ByteBuffer's data
    let output_ptr = env.get_direct_buffer_address(output).expect("Cannot get output buffer address").as_mut_ptr();
    let output_slice = unsafe { std::slice::from_raw_parts_mut(output_ptr, output_len as usize) };

    let mut keccak = Keccak::v256();

    keccak.update(input_slice);

    keccak.finalize(output_slice);
}
