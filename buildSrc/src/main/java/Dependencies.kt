@file:Suppress("unused")

object Android {
    const val compileSdkVersion = 28
    const val minSdkVersion = 15
    const val targetSdkVersion = 25

    const val versionName = "1.0"
    const val versionCode = 1
}

object Versions {
    const val support = "27.1.1"
    const val butterknife = "8.8.1"
    const val retrofit2 = "2.4.0"
    const val okhttp3 = "3.11.0"
    const val autodispose = "1.0.0-RC1"
    const val leakcanary = "1.6.1"
    const val glide = "4.8.0"
}

object Deps {
    //support
    const val support_annotations = "com.android.support:support-annotations:${Versions.support}"
    const val support_v4 = "com.android.support:support-v4:${Versions.support}"
    const val appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support}"
    const val recyclerview_v7 = "com.android.support:recyclerview-v7:${Versions.support}"
    const val multidex = "com.android.support:multidex:1.0.3"
    const val constraint_layout = "com.android.support.constraint:constraint-layout:1.1.2"

    //butterknife
    const val butterknife = "com.jakewharton:butterknife:${Versions.butterknife}"
    const val butterknife_apt = "com.jakewharton:butterknife-compiler:${Versions.butterknife}"

    //leakcanary
    const val leakcanary_android = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
    const val leakcanary_android_no_op = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakcanary}"
    const val leakcanary_support_fragment = "com.squareup.leakcanary:leakcanary-support-fragment:${Versions.leakcanary}"

    //rx
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    const val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val autodispose = "com.uber.autodispose:autodispose-android:${Versions.autodispose}"
    const val autodispose_archcomponents = "com.uber.autodispose:autodispose-android-archcomponents:${Versions.autodispose}"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.2.1"
    const val rxandroid2 = "io.reactivex.rxjava2:rxandroid:2.1.0"
    const val x_rxbus = "com.xia:x-rxbus:1.0.2"

    //okhttp
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttp3_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

    //glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    //util
    const val utilcode = "com.blankj:utilcode:1.19.3"
    //fragment
    const val fragmentation_core = "me.yokeyword:fragmentation-core:1.3.6"
    //loading各种动画样式
    const val loading_indicator_view = "com.wang.avi:library:2.1.3"
}