#include <jni.h>
#include <string>
#include <iostream>
#include <sstream>
#include <fstream>
#include <cstdint>
#include <iomanip>
#include <stdio.h>
#include <string.h>
#include <android/log.h>

using namespace std;

string get_public_key_scg() {
   return "IMBIjINAgBqkkhGiw9B0QAFEAACOQAA8IMBIgCCKQAAEnzmSCOp1SawWsOifBAhrRv"s +
           "r0RH/dFehD9SMtXKmOv+U3K879ESwCdwuAx8NhLLMnC7vM8bAodohf0L6ADA3icHw"s +
           "oSgPgUJyQ81LJ7Rh6FFGbgVlrxNtzbruc2J1PglMUQIvey55k+DKOmsUpqwi2xvXP"s +
           "iA5et6F7NqXy42cGLEZKLCCaFIrGu/SQNNn3P1Ue4UdbfmAkiUb0vjSobRYfptzrp"s +
           "OJVEPzzdE7phYJKE3uyOVIGIgqz5lhAIsFMo5IXsqkYXtIzWkLji9Gco26/K0ha9w"s +
           "/8UdJ8EOR1gzlnnJgf8/XTdaUA0G+fr1PRo9wpwRmWTeC0xriVk5s1b9jcwpDIQABA";
}

string get_public_key_wrap_api() {
    return "IMBIjINAgBqkkhGiw9B0QAFEAACOQAA8IMBIgCCKQAAESu2f6rvoOBJaqet0eNCwy"s +
            "GC2X7NUxc/vRxvsfaU2nC7fY40qxkmomyWyyf8JI4APP6n6/3HMCLdBLahZtxNFd"s +
            "J4cG/DU/BrJljwJwBG/zN71jcB7ipOVgYk0FQKnGbi6w0jpP4zhT0b5freBtIucZ"s +
            "WAq6UlvGNxy/BtAw3D4NidII0/01AAPs12Ph/y0An1Xxud4RLKtJVUW1tX4n+Itr"s +
            "OeZcTlsYmaoYSQEoexfmh4MMy7nR/YbhnmicbA3lTmai774psqVAZZTPq6hCWLv6"s +
            "cLxNr9YsdBgvrARnc4CH2euM4OtEiZ7O0ueUIgLFX7evMF1PcRBgMaeGcfmgVhpw"s +
            "GDIQABA";
}

string get_public_key_prod_wrap_api() {
    return "IMBIjINAgBqkkhGiw9B0QAFEAACOQAA8IMBIgCCKQAAEtjQdFjM2P+HdeWBiC/y8P"s +
            "DWsE31SdQCzuQ2GIjq/bLKvTVzxCOUhpWNvDuN7qxfG6kfwx6AVj57UVvw0ASBvY"s +
            "MbTn0Z8unTHJH0t/gYwgQ68eVg57wY8Y2yXKAL0ibblJtOrncsSwrIy7m+WCrGTS"s +
            "suuFsqKGKrjwOzFXAbynKrKFtN1ogf9PZSaYOPaEa68CW3sk8r+3jfGMYMfjjv4y"s +
            "vnBcXVFwkiGNLajsNl+tuCT0ucIr4+4rShHSKvgy3KhHosNhGnuIPcve5ZZgyJq2"s +
            "hPIvCMECotpzEgDEHMqCpETj5HmQKQiURwNXi05IdWy6bn3Bj4O2xiKKZgP3jVYw"s +
            "SDIQABA";
}

string get_public_key_q_chang() {
    return "IMBIjINAgBqkkhGiw9B0QAFEAACOQAA8IMBIgCCKQAAEa0j3YT+JATdoonGo/k5SP"s +
            "09jmG+sQi+HAas2OvH6FVaAc1LRD2N/OU5LhrCp0ep06QQ3NyL2Z0WVjzo7gesRn"s +
            "XjiGmwPLxE0bMX+y10va+rRtYWZAmW675Wk+zVOVyZwUrbyMmttDdMjpNcuO9Pzd"s +
            "OqGSmXZ3kx3iIFZiMfiHglxkc4ZsHSz/14lxj5M0b9pJqP4npSOhYg0WG86SDWnpf"s +
            "xUPOd8bmEQGVFLwyRJxoBOO6HQZABmABDu7X0Aw7ah/OrJ4BoJCa/H4saHBpOUkQ"s +
            "sD9SoB9JHWQLivvOVhwVqM/qvs5upHDuugfRz8abHnowWGkZ1HNNnGQiZdcW+Fwg"s +
            "DIQABA";
}

string get_public_key_uat_scg_id() {
	return "FMwkwEHYoKIZjzC0QAIYoKIZjzD0QADcgQEAiBozfXT/oSfUfTSmEgIYeQY5q200P"s +
	        "TXHWAy6koKp+AGb414WEg34sfAU5M4bwGNfZpQAlyKjMkvk15gVG4aew0==";
}

string get_public_key_dev_scg_id() {
    return "FMwkwEHYoKIZjzC0QAIYoKIZjzD0QADcgQEAsrlBNuRcZjcZekL8ZlSSS3tlPIBYl"s +
            "xH+LIvVuE9LL9lCnuwEBQJC+AsQSsIMLuYwU74eMEMcZSMP8iwgb8T0Qp==";
}

string get_public_key_prod_scg_id() {
    return "FMwkwEHYoKIZjzC0QAIYoKIZjzD0QADcgQEAs1pov2/DfHYrnpd7xdcyBgi9Vaxpp"s +
            "3eL1rpAQWO93NOwH+S8Wik47NAAJMiGZQKu7QC0uyVHn2MvelWYXVHng2==";
}

string swap_string(string key) {
    unsigned long walk = 0;
    unsigned long textCount = key.length();
    stringstream keySS;
    if (textCount > 1) {
        while (walk < textCount - 1) {
            keySS << key[walk + 1] << key[walk];
            walk += 2;
        }
    }
    string client = keySS.str();

    return client;
}

// Converts jstring to string object
string jstring2string(JNIEnv *env, jstring js)
{
    string result;
    long len = env->GetStringUTFLength(js);
    char* p = (char*)malloc(len + 1);
    memset(p, 0, len + 1);
    if(len > 0)
        env->GetStringUTFRegion(js, 0, len, p);
    result = p;
    free(p);
    return result;
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_scghome_app_base_security_DomainSecurity_getPublicKey(
    JNIEnv *env,
    jobject ob,
    jobject scgChangeDomainType
) {
    jobject java_array_list_object;
    // Get enum class
    jclass jclassEnum = env->GetObjectClass(scgChangeDomainType);
    if (jclassEnum != 0) {
        jmethodID toString_ID = env->GetMethodID(jclassEnum, "toString", "()Ljava/lang/String;");
        jstring jstrEnum = (jstring)env->CallObjectMethod(scgChangeDomainType, toString_ID);
        string enumName = jstring2string(env, jstrEnum);

        jclass java_array_list_class = env->FindClass("java/util/ArrayList");
        jmethodID java_array_list_method_constructor = env->GetMethodID(java_array_list_class, "<init>", "()V");
        jmethodID java_add_method = env->GetMethodID(java_array_list_class, "add", "(Ljava/lang/Object;)Z");
        java_array_list_object = env->NewObject(java_array_list_class, java_array_list_method_constructor, "");

        if (strcmp(enumName.c_str(), "SCG") == 0
            || strcmp(enumName.c_str(), "CMS") == 0) {
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_scg()).c_str())
            );
        } else if (strcmp(enumName.c_str(), "WRAP_API") == 0) {

            // uat environment
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_wrap_api()).c_str())
            );

            // production environment
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_prod_wrap_api()).c_str())
            );
        } else if (strcmp(enumName.c_str(), "Q_CHANG") == 0) {
            // development environment
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_q_chang()).c_str())
            );

            // production environment
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_scg()).c_str())
            );
        } else if (strcmp(enumName.c_str(), "SCG_ID") == 0) {
            // uat
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_uat_scg_id()).c_str())
            );

            // dev
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_dev_scg_id()).c_str())
            );

            // production
            env->CallBooleanMethod(
                java_array_list_object,
                java_add_method,
                env->NewStringUTF(swap_string(get_public_key_prod_scg_id()).c_str())
            );
        }
    } else {
        env->ExceptionClear();
    }

    env->DeleteLocalRef(jclassEnum);

    return java_array_list_object;
}