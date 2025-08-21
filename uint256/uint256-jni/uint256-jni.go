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
		return C.int(-2)
	}

	ab := unsafe.Slice((*byte)(unsafe.Pointer(a)), int(aLen))
	bb := unsafe.Slice((*byte)(unsafe.Pointer(b)), int(bLen))

	var x, y uint256.Int
	x.SetBytes(ab)
	y.SetBytes(bb)
	if y.IsZero() {
		return C.int(-1)
	}

	var r uint256.Int
	r.Mod(&x, &y)

	*(*[32]byte)(unsafe.Pointer(out)) = r.Bytes32()
	return C.int(32)
}


func main() {}
