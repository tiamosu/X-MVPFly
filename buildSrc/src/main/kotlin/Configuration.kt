@file:Suppress("unused", "SpellCheckingInspection")

object Android {
    const val compileSdkVersion = 29
    const val buildToolsVersion = "29.0.2"
    const val minSdkVersion = 15
    const val targetSdkVersion = 29

    const val versionName = "1.0"
    const val versionCode = 1
}

object Versions {
    const val kotlin = "1.3.61"
    const val butterknife = "10.2.0"
    const val retrofit2 = "2.6.4"
    const val okhttp3 = "3.12.8" //版本3.12.X，兼容Android 4
    const val autodispose = "1.4.0"
    const val glide = "4.11.0"
    const val dagger2 = "2.26"
    const val fragmentation = "1.1.4"
    const val fly = "3.0.8"
}

object Publish {
    const val userOrg = "weixia" //bintray.com用户名
    const val groupId = "me.xia" //jcenter上的路径
    const val publishVersion = "3.1.0" //版本号
    const val desc = "Oh hi, this is a nice description for a project, right?"
    const val website = "https://github.com/tiamosu/X-MVPFly"
    const val gitUrl = "https://github.com/tiamosu/X-MVPFly.git"
    const val email = "djy2009wenbi@gmail.com"
}

object Deps {
    //support
    const val androidx_annotation = "androidx.annotation:annotation:1.1.0"
    const val androidx_appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val androidx_recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
    const val androidx_multidex = "androidx.multidex:multidex:2.0.1"
    const val androidx_constraint_layout = "androidx.constraintlayout:constraintlayout:1.1.3"

    //kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlin_stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlin_ktx = "androidx.core:core-ktx:1.2.0"

    //arouter
    const val arouter_api = "com.alibaba:arouter-api:1.5.0"
    const val arouter_compiler = "com.alibaba:arouter-compiler:1.2.2"

    //butterknife
    const val butterknife = "com.jakewharton:butterknife:${Versions.butterknife}"
    const val butterknife_apt = "com.jakewharton:butterknife-compiler:${Versions.butterknife}"

    //dagger2
    const val dagger2 = "com.google.dagger:dagger:${Versions.dagger2}"
    const val dagger2_apt = "com.google.dagger:dagger-compiler:${Versions.dagger2}"

    //leakcanary
    const val leakcanary_android = "com.squareup.leakcanary:leakcanary-android:2.2"

    //rx
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2}"
    const val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
    const val autodispose = "com.uber.autodispose:autodispose-android:${Versions.autodispose}"
    const val autodispose_archcomponents = "com.uber.autodispose:autodispose-android-archcomponents:${Versions.autodispose}"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:2.2.17"
    const val rxandroid2 = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxcache2 = "com.github.VictorAlbertos.RxCache:runtime:1.8.3-2.x"
    const val rxcache_gson = "com.github.VictorAlbertos.Jolyglot:gson:0.0.4"
    const val rxerrorhandler = "me.jessyan:rxerrorhandler:2.1.1"
    const val x_rxbus = "me.xia:x-flyrxbus:1.0.6"

    //utilcode
    const val utilcode = "com.blankj:utilcodex:1.26.0"

    //okhttp
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttp3_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

    //glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    //fragment
    const val fragmentation_core_androidx = "me.xia:fragmentation-core-androidx:${Versions.fragmentation}"
    const val fragmentation_support_androidx = "me.xia:fragmentation-with-androidx:${Versions.fragmentation}"
    const val fragmentation_swipeback_androidx = "me.xia:fragmentation-swipeback-androidx:${Versions.fragmentation}"
    const val fragmentation_eventbus_activity_scope_androidx = "me.xia:eventbus-activity-scope-androidx:${Versions.fragmentation}"

    //loading各种动画样式
    const val loading_indicator_view = "com.wang.avi:library:2.1.3"

    //fly
    const val fly = "me.xia:fly:${Versions.fly}"
    const val fly_http = "me.xia:fly-http:${Versions.fly}"
    const val fly_imageloader_glide = "me.xia:fly-imageloader-glide:${Versions.fly}"
}