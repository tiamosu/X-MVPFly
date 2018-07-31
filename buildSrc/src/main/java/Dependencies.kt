@file:Suppress("unused")

object Android {
    const val compileSdkVersion = 27
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
}

object Deps {
    //support
    const val appcompat_v7 = "com.android.support:appcompat-v7:${Versions.support}"
    const val multidex = "com.android.support:multidex:1.0.3"
    const val constraint_layout = "com.android.support.constraint:constraint-layout:1.1.2"
    //butterknife
    const val butterknife = "com.jakewharton:butterknife:${Versions.butterknife}"
    const val butterknife_apt = "com.jakewharton:butterknife-compiler:${Versions.butterknife}"
    //rx
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttp3_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.1.17"
    const val rxandroid2 = "io.reactivex.rxjava2:rxandroid:2.0.2"
    const val rxlifecycle2_components = "com.trello.rxlifecycle2:rxlifecycle-components:2.2.2"
    //other
    const val utilcode = "com.blankj:utilcode:1.17.4"
    const val rxbus = "com.blankj:rxbus:1.1"
    const val fragmentation_core = "me.yokeyword:fragmentation-core:1.3.5"
    const val loading_indicator_view = "com.wang.avi:library:2.1.3"
}