# GlowUI
[![](https://jitpack.io/v/evanobenauer/GlowUI.svg)](https://jitpack.io/#evanobenauer/GlowUI)
GlowUI is a User Interface library coded in Java using LWJGL3 and GlowLib.

The library contains easy-to-use drawing tools, modification tools, and more.

A larger description will be added once the project is developed further and becomes more polished

To add GlowUI as a dependency, you must add this chunk of code to your gradle java project:
This includes GlowLib and LWJGL3

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

project.ext.lwjglVersion = "3.3.1"
project.ext.lwjglNatives = "natives-windows"

dependencies {
    implementation 'com.github.evanobenauer:GlowUI:1.0.0'
    implementation 'com.github.evanobenauer:GlowLib:1.1.3'

    implementation platform("org.lwjgl:lwjgl-bom:$lwjglVersion")

    implementation "org.lwjgl:lwjgl"
    implementation "org.lwjgl:lwjgl-assimp"
    implementation "org.lwjgl:lwjgl-bgfx"
    implementation "org.lwjgl:lwjgl-cuda"
    implementation "org.lwjgl:lwjgl-egl"
    implementation "org.lwjgl:lwjgl-glfw"
    implementation "org.lwjgl:lwjgl-jawt"
    implementation "org.lwjgl:lwjgl-jemalloc"
    implementation "org.lwjgl:lwjgl-libdivide"
    implementation "org.lwjgl:lwjgl-llvm"
    implementation "org.lwjgl:lwjgl-lmdb"
    implementation "org.lwjgl:lwjgl-lz4"
    implementation "org.lwjgl:lwjgl-meow"
    implementation "org.lwjgl:lwjgl-meshoptimizer"
    implementation "org.lwjgl:lwjgl-nanovg"
    implementation "org.lwjgl:lwjgl-nfd"
    implementation "org.lwjgl:lwjgl-nuklear"
    implementation "org.lwjgl:lwjgl-odbc"
    implementation "org.lwjgl:lwjgl-openal"
    implementation "org.lwjgl:lwjgl-opencl"
    implementation "org.lwjgl:lwjgl-opengl"
    implementation "org.lwjgl:lwjgl-opengles"
    implementation "org.lwjgl:lwjgl-openvr"
    implementation "org.lwjgl:lwjgl-openxr"
    implementation "org.lwjgl:lwjgl-opus"
    implementation "org.lwjgl:lwjgl-ovr"
    implementation "org.lwjgl:lwjgl-par"
    implementation "org.lwjgl:lwjgl-remotery"
    implementation "org.lwjgl:lwjgl-rpmalloc"
    implementation "org.lwjgl:lwjgl-shaderc"
    implementation "org.lwjgl:lwjgl-spvc"
    implementation "org.lwjgl:lwjgl-sse"
    implementation "org.lwjgl:lwjgl-stb"
    implementation "org.lwjgl:lwjgl-tinyexr"
    implementation "org.lwjgl:lwjgl-tinyfd"
    implementation "org.lwjgl:lwjgl-tootle"
    implementation "org.lwjgl:lwjgl-vma"
    implementation "org.lwjgl:lwjgl-vulkan"
    implementation "org.lwjgl:lwjgl-xxhash"
    implementation "org.lwjgl:lwjgl-yoga"
    implementation "org.lwjgl:lwjgl-zstd"
    runtimeOnly "org.lwjgl:lwjgl::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-assimp::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-bgfx::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-glfw::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-jemalloc::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-libdivide::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-llvm::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-lmdb::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-lz4::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-meow::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-meshoptimizer::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-nanovg::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-nfd::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-nuklear::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-openal::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-opengl::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-opengles::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-openvr::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-openxr::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-opus::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-ovr::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-par::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-remotery::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-rpmalloc::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-shaderc::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-spvc::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-sse::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-stb::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-tinyexr::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-tinyfd::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-tootle::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-vma::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-xxhash::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-yoga::$lwjglNatives"
    runtimeOnly "org.lwjgl:lwjgl-zstd::$lwjglNatives"
}
```

For the map of all UI components and their relations, please refer to this google drawing: https://docs.google.com/drawings/d/1jRKvok91ZnKRq6-y74jP-qjGx_Xyvf6g2CQniBRaHv8/edit?usp=sharing 
