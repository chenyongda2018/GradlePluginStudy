package com.example.plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ParseDebugTask extends DefaultTask {

    @TaskAction
    void doAction() {
        //1.拿到合并后的manifest.xml文件
        def file  = new File(project.buildDir,"/intermediates/merged_manifests/debug/AndroidManifest.xml")
        if(!file.exists()) {
            println "文件不存在"
            return
        }

        def fileContent = file.getText()
        //移除权限
        removePermission(file,fileContent)
    }

    void removePermission(File file,String fileContent) {
        fileContent = fileContent.replace("android.permission.READ_PHONE_STATE","")
        println fileContent
        file.write(fileContent)
    }
}