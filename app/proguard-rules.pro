# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleAnnotations, RuntimeInvisibleParameterAnnotations
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn okio.**

# Gson
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Room
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Kotlin Serialization / Flow / Coroutines
-keepnames class kotlinx.coroutines.** { *; }
-keepnames class kotlinx.serialization.** { *; }

# Google AI (Generative AI)
-keep class com.google.ai.client.generativeai.** { *; }
-dontwarn com.google.ai.client.generativeai.**

# Keep models to avoid Gson issues
-keep class com.simats.orcare.data.api.models.** { *; }
-keep class com.simats.orcare.data.models.** { *; }
-keep class com.simats.orcare.data.entity.** { *; }
-keep class com.simats.orcare.db.entity.** { *; }
-keep class com.simats.orcare.ui.viewmodel.** { *; }