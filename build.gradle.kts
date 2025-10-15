group = "net.guizhanss"
val mainPackage = "net.guizhanss.minecraft.guizhanlib"

plugins {
    java
    `java-library`
    `maven-publish`
    signing
    id("io.freefair.lombok") version "8.11"
    id("com.gradleup.shadow") version "8.3.5"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

repositories {
    flatDir {
        dirs ("libs")
    }
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Sparkpixel-MC/Guizhanlib-Folia")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/Sparkpixel-MC/Slimefun4-CN-Folia")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/Sparkpixel-MC/Folia-Adapter")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
    maven("https://jitpack.io/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/groups/public/")
    mavenLocal()
}

dependencies {
    fun compileOnlyAndTestImplementation(dependencyNotation: Any) {
        compileOnly(dependencyNotation)
        testImplementation(dependencyNotation)
    }

    implementation("net.guizhanss:guizhanlib:2.4.0-Folia")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
    api("com.github.houbb:pinyin:0.4.0")

    compileOnlyAndTestImplementation("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    compileOnlyAndTestImplementation("com.github.slimefun:slimefun:4.1-SNAPSHOT")

    // mockbukkit
    testImplementation("com.github.MockBukkit:MockBukkit:v1.20-SNAPSHOT")
    // junit
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    disableAutoTargetJvm()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()
    withSourcesJar()
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    fun doRelocate(from: String, to: String? = null) {
        val last = to ?: from.split(".").last()
        relocate(from, "$mainPackage.libs.$last")
    }

    doRelocate("io.papermc.lib", "paperlib")
    doRelocate("com.github.houbb")
    doRelocate("org.bstats")
    archiveClassifier = ""
}

bukkit {
    main = "$mainPackage.GuizhanLib"
    apiVersion = "1.18"
    authors = listOf("ybw0014")
    description = "A library plugin for Simplified Chinese Slimefun addons."
    website = "https://github.com/ybw0014/GuizhanLibPlugin"
    foliaSupported = true
    depend = listOf("Slimefun")
}

tasks.runServer {
    downloadPlugins {
        // Slimefun
        url("https://builds.guizhanss.com/api/download/SlimefunGuguProject/Slimefun4/master/latest")
        // GuizhanCraft for testing convenient
        url("https://builds.guizhanss.com/api/download/ybw0014/GuizhanCraft/master/latest")
    }
    jvmArgs("-Dcom.mojang.eula.agree=true")
    minecraftVersion("1.20.6")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "net.guizhanss"
            artifactId = "guizhanlibplugin"
            version = "2.2.0-Folia"
            artifact(tasks.named("shadowJar").get().outputs.files.singleFile)
            pom {
                name.set("GuizhanlibPlugin")
                description.set("Guizhanlib Plugin")
                url.set("https://github.com/Sparkpixel-MC/GuizhanlibPlugin-Folia")
            }
        }
    }
    repositories {
        maven {
            name = "GitHub-Packages"
            url = uri("https://maven.pkg.github.com/Sparkpixel-MC/GuizhanlibPlugin-Folia")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("com.molean:FoliaAdapter")).using(module("com.molean:foliaadapter:1.0-SNAPSHOT"))
    }
}
