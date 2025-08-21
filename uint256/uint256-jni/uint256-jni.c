#include <jni.h>
#include <string.h>
#include <stdint.h>

extern int mod(char* a, int aLen, char* b, int bLen, char* out);

JNIEXPORT jint JNICALL Java_org_hyperledger_besu_nativelib_uint256_LibUint256_modNative
  (JNIEnv *env, jclass clazz, jbyteArray a, jint aLen, jbyteArray b, jint bLen, jbyteArray out) {

    // GetPrimitiveArrayCritical évite la copie et désactive temporairement le GC
    jbyte* aPtr = (*env)->GetPrimitiveArrayCritical(env, a, NULL);
    jbyte* bPtr = (*env)->GetPrimitiveArrayCritical(env, b, NULL);
    jbyte* outPtr = (*env)->GetPrimitiveArrayCritical(env, out, NULL);

    if (!aPtr || !bPtr || !outPtr) {
        // Libération en cas d'erreur
        if (aPtr) (*env)->ReleasePrimitiveArrayCritical(env, a, aPtr, JNI_ABORT);
        if (bPtr) (*env)->ReleasePrimitiveArrayCritical(env, b, bPtr, JNI_ABORT);
        if (outPtr) (*env)->ReleasePrimitiveArrayCritical(env, out, outPtr, JNI_ABORT);
        return -3;
    }

    // Appel de la fonction Go
    int result = mod((char*)aPtr, aLen, (char*)bPtr, bLen, (char*)outPtr);

    // Libération des pointeurs
    // JNI_ABORT = ne pas copier les modifications (pour a et b)
    // 0 = copier les modifications (pour out)
    (*env)->ReleasePrimitiveArrayCritical(env, a, aPtr, JNI_ABORT);
    (*env)->ReleasePrimitiveArrayCritical(env, b, bPtr, JNI_ABORT);
    (*env)->ReleasePrimitiveArrayCritical(env, out, outPtr, 0);

    return result;
}