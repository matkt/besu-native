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
use jni::objects::ReleaseMode;
#[no_mangle]
pub extern "system" fn Java_org_hyperledger_besu_nativelib_keccak_LibKeccak_compute
(
    env: JNIEnv,
    _class: JClass,
    input: jbyteArray,
    output: jbyteArray,
) {

     // Get a pointer to the byte array's elements
     let input_elements = env.get_primitive_array_critical(input, ReleaseMode::NoCopyBack).expect("Cannot get input array elements");
     // Convert the AutoPrimitiveArrayCritical to a slice
     let input_len = env.get_array_length(input).expect("Cannot get input array length") as usize;
     let input_slice = unsafe { std::slice::from_raw_parts(input_elements.as_ptr() as *const u8, input_len) };

     // Get a pointer to the byte array's elements
     let output_elements = env.get_primitive_array_critical(output, ReleaseMode::NoCopyBack).expect("Cannot get output array elements");
     // Convert the AutoPrimitiveArrayCritical to a slice
     let output_len = env.get_array_length(output).expect("Cannot get output array length") as usize;
     let output_slice = unsafe { std::slice::from_raw_parts_mut(output_elements.as_ptr() as *mut u8, output_len) };

     let mut keccak = Keccak::v256();

     keccak.update(input_slice);

     keccak.finalize(output_slice);

}
