import com.google.devtools.ksp.gradle.KspExtension
import com.app.demo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            // Define the schema output directory
            val schemaOutputDir = "$projectDir/schemas"

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
                arg("room.schemaLocation", schemaOutputDir)
            }

            dependencies {
                add("implementation", libs.findLibrary("room-runtime").get())
                add("implementation", libs.findLibrary("room-ktx").get())
                add("ksp", libs.findLibrary("room-compiler").get())
            }
        }
    }
}