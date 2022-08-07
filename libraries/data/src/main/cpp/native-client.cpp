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

string get_sms_client_id(string flavorKey) {
    if (strcmp(flavorKey.c_str(), "prod") == 0) {
        return "Lm3MfIvucHi4ke7caPHi0OywUXwcIYx4";
    } else if (strcmp(flavorKey.c_str(), "dev") == 0) {
        return "M5J2ulffyvVYg66gdma8OZhl9l8akZ2K";
    } else if (strcmp(flavorKey.c_str(), "sit") == 0) {
        return "0ThW823Gx3hGoBJ8AzZ5om3taLR6mKuT";
    } else {
        return "";
    }
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

extern "C" JNIEXPORT jstring JNICALL
Java_com_scghome_data_base_model_otp_request_CallMobileOtpRequest_00024Companion_getClientId(
    JNIEnv *env,
    jobject ob,
    jstring flavor
) {
    string sFlavor = jstring2string(env, flavor);
    string sNative = swap_string(get_sms_client_id(sFlavor));

    return env->NewStringUTF(sNative.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_scghome_data_base_model_otp_request_VerifyOtpRequest_00024Companion_getClientId(
    JNIEnv *env,
    jobject ob,
    jstring flavor
) {
    string sFlavor = jstring2string(env, flavor);
    string sNative = swap_string(get_sms_client_id(sFlavor));

    return env->NewStringUTF(sNative.c_str());
}