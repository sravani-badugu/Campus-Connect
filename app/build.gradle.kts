plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // ✅ Required for Firebase
}

android {
    namespace = "com.example.campusconnect"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.campusconnect"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {

    implementation("com.google.android.material:material:1.11.0")
    implementation("com.cloudinary:cloudinary-android:2.2.0")


    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))

    // Firebase
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // Material Design
    implementation("com.google.android.material:material:1.11.0")

    // RecyclerView (for EventsAdapter list)
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Glide (for image loading)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
}

