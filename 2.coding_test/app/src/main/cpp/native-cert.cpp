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

string getApiKey(string flavorKey) {
    if (strcmp(flavorKey.c_str(), "prod") == 0) {
        return "e73a4eb98ed84a2e9e5adbdff58e1ccf";
    } else if (strcmp(flavorKey.c_str(), "dev") == 0) {
        return "e73a4eb98ed84a2e9e5adbdff58e1ccf";
    } else if (strcmp(flavorKey.c_str(), "sit") == 0) {
        return "e73a4eb98ed84a2e9e5adbdff58e1ccf";
    } else {
        return "";
    }
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
Java_com_savvy_core_base_BaseViewModel_getApiKey(
        JNIEnv *env,
        jobject ob,
        jstring flavor
) {
    string sFlavor = jstring2string(env, flavor);
    string sNative = getApiKey(sFlavor);

    return env->NewStringUTF(sNative.c_str());
}