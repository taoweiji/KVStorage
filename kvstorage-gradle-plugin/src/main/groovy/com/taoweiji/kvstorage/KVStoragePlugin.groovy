package com.taoweiji.kvstorage

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class KVStoragePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("kvstorage", KVStorageExtension)
        Task task = project.tasks.create("kvstorage") {
            println("Creating configuration kvstorage")
            doLast {
                execute(project)
            }
        }
        // gradle 同步后执行
        project.afterEvaluate {
            println("kvstorage afterEvaluate")
            execute(project)
        }

//        project.dependencies {
//            api "io.github.taoweiji.kvstorage:kvstorage:1.0.0"
//        }
//
        // 检查文件的md5是否
    }

    private static void execute(Project project) {
        if (project.kvstorage == null) {
            return
        }
        println("kvstorage start")
        if (project.kvstorage.yamlFiles == null || project.kvstorage.yamlFiles == "") {
            new Exception("kvstorage.yamlFiles is empty").printStackTrace()
            return
        }
        if (project.kvstorage.packageName == null || project.kvstorage.packageName == "") {
            new Exception("kvstorage.packageName is empty").printStackTrace()
            return
        }
        def yamlFiles = project.kvstorage.yamlFiles.split(",")
        def outputDir = project.kvstorage.outputDir
        if (outputDir == null) {
            outputDir = "src/main/java"
        }
        def outFile = project.file(outputDir)
        def buildClassFile = new BuildJavaFile()
        for (def yamlFile : yamlFiles) {
            if (yamlFile == "") {
                continue
            }
            def file = project.file(yamlFile)
            println("kvstorage execute ${file.getAbsolutePath()}")
            def className = buildClassFile.build(file.getAbsolutePath(), project.kvstorage.packageName, outFile.getAbsolutePath())
            println("kvstorage create ${className}")
        }
    }
}
