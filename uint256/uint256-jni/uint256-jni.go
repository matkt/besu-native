package main

/*
#include <stdint.h>
#include <string.h>
*/
import "C"

import (
	"unsafe"

	"github.com/holiman/uint256"
)

//export mod
func mod(a *C.char, aLen C.int, b *C.char, bLen C.int, out *C.char) C.int {
	    if a == nil || b == nil || out == nil {
    		return C.int(-2) // invalid pointers
    	}
    	ab := C.GoBytes(unsafe.Pointer(a), aLen)
    	bb := C.GoBytes(unsafe.Pointer(b), bLen)

    	var x, y uint256.Int
    	x.SetBytes(ab) // big-endian
    	y.SetBytes(bb) // big-endian
    	if y.IsZero() {
    		return C.int(-1) // mod by zero
    	}
    	var r uint256.Int
    	r.Mod(&x, &y)

    	// r.Bytes32() -> [32]byte big-endian
    	res := r.Bytes32()
    	C.memcpy(unsafe.Pointer(out), unsafe.Pointer(&res[0]), 32)
    	return C.int(32) // number of bytes written
}


func main() {}
