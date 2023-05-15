#include <jni.h>
#include <stdio.h>
#include <immintrin.h>
#include <stdlib.h>
#include "Utils.h"

JNIEXPORT void JNICALL
Java_com_oracle_truffle_vec_utils_Utils_nativePackMatrices (JNIEnv *env, jclass java_class, 
jobjectArray a, jobjectArray b, jint m_length, jint k_length, jint n_length, jint kc, jint kernel_height, jint kernel_width, jint a_alignment_offset, 
jint b_alignment_offset, jdoubleArray packed_a, jdoubleArray packed_b, jint matrix_form) {
    //printf("Hello World!!!! from C\n");
    jdouble* packed_a_c = (*env)->GetPrimitiveArrayCritical(env, packed_a, NULL);
    jdouble* packed_b_c = (*env)->GetPrimitiveArrayCritical(env, packed_b, NULL);

    /*
    for(int i = 0; i < 4; i++) {
        dst_array[i] = (double) i;
    }
    */

    //int m_length = (*env)->GetArrayLength(env, a);

    jdouble** a_array = (jdouble**) malloc(m_length * sizeof(jdouble*));

    //jdoubleArray row0 = (jdoubleArray)(*env)->GetObjectArrayElement(env, a, 0);
    //int k_length = (*env)->GetArrayLength(env, row0);

    for(int i = 0; i < m_length; i++) {
        jdoubleArray row = (jdoubleArray)(*env)->GetObjectArrayElement(env, a, i);
        a_array[i] = (*env)->GetPrimitiveArrayCritical(env, row, NULL);
    }

    unsigned dst_index = a_alignment_offset;

    if(matrix_form == 0 || matrix_form == 2) {
        for(int k_pos = 0; k_pos < k_length; k_pos+=kc) {
            for(int i_slice = 0; i_slice < m_length; i_slice+=kernel_height) {
                for(int k = k_pos; k < k_pos + kc; k++) {
                    int i_limit = i_slice + kernel_height < m_length ? i_slice + kernel_height : m_length;
                    for(int i = i_slice; i < i_limit; i++) {
                        packed_a_c[dst_index++] = a_array[i][k];
                    }
                }
            }
        }
    } else {
        for(int k_pos = 0; k_pos < k_length; k_pos+=kc) {
            for(int i_slice = 0; i_slice < m_length; i_slice+=kernel_height) {
                for(int k = k_pos; k < k_pos + kc; k++) {
                    int i_limit = i_slice + kernel_height < m_length ? i_slice + kernel_height : m_length;
                    for(int i = i_slice; i < i_limit; i++) {
                        packed_a_c[dst_index++] = a_array[k][i];
                    }
                }
            }
        }
    }


    jdouble** b_array = (jdouble**) malloc(k_length * sizeof(jdouble*));

    //jdoubleArray b_row0 = (jdoubleArray)(*env)->GetObjectArrayElement(env, b, 0);
    //int n_length = (*env)->GetArrayLength(env, b_row0);

    for(int i = 0; i < k_length; i++) {
        jdoubleArray row = (jdoubleArray)(*env)->GetObjectArrayElement(env, b, i);
        b_array[i] = (*env)->GetPrimitiveArrayCritical(env, row, NULL);
    }

    dst_index = b_alignment_offset;
    if (matrix_form == 0 || matrix_form == 1) {
        for(int k_pos = 0; k_pos < k_length; k_pos+=kc) {
            for(int j_slice = 0; j_slice < n_length; j_slice+=kernel_width) {
                for(int k = k_pos; k < k_pos + kc; k++) {
                    /*
                    for(int j = 0; j < kernel_width; j++) {
                        packed_b_c[dst_index++] = b_array[k][j_slice+j];
                    }
                    */
                    //int j_limit = j_slice + kernel_width < n_length ? j_slice + kernel_width : n_length;
                    int j_limit = j_slice + kernel_width;
                    for(int j = j_slice; j < j_limit; j+=4) {
                        __m256d temp = _mm256_loadu_pd(&b_array[k][j]);
                        _mm256_storeu_pd(&packed_b_c[dst_index], temp);
                        //printf("%d %d %d %d\n", dst_index - b_alignment_offset, dst_index- b_alignment_offset+1, dst_index- b_alignment_offset+2, dst_index- b_alignment_offset+3);
                        //printf("%f %f %f %f\n", packed_b_c[dst_index], packed_b_c[dst_index+1], packed_b_c[dst_index+2], packed_b_c[dst_index+3]);
                        dst_index += 4;
                    }
                    
                }
            }
        }
    } else {
        for(int k_pos = 0; k_pos < k_length; k_pos+=kc) {
            for(int j_slice = 0; j_slice < n_length; j_slice+=kernel_width) {
                for(int k = k_pos; k < k_pos + kc; k++) {
                    for(int j = 0; j < kernel_width; j++) {
                        packed_b_c[dst_index++] = b_array[j_slice+j][k];
                    }
                }
            }
        }
    }

    (*env)->ReleasePrimitiveArrayCritical(env, packed_a, packed_a_c, 0);
    (*env)->ReleasePrimitiveArrayCritical(env, packed_b, packed_b_c, 0);

    for(int i = 0; i < m_length; i++) {
        jdoubleArray row = (jdoubleArray)(*env)->GetObjectArrayElement(env, a, i);
        //src_array[i] = (*env)->GetDoubleArrayElements(env, row, NULL);
        (*env)->ReleasePrimitiveArrayCritical(env, row, a_array[i], 0);
    }

    for(int i = 0; i < k_length; i++) {
        jdoubleArray row = (jdoubleArray)(*env)->GetObjectArrayElement(env, b, i);
        //src_array[i] = (*env)->GetDoubleArrayElements(env, row, NULL);
        (*env)->ReleasePrimitiveArrayCritical(env, row, b_array[i], 0);
    }

    free(a_array);
    free(b_array);

}
