package com.example.plugin

import com.example.plugin.task.ParseDebugTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class ManifestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            def mergeResourcesTask = project.tasks.findByName("mergeDebugResources")
            if(mergeResourcesTask != null) {
                def parseDebugTask = project.tasks.create("ParseDebugTask", ParseDebugTask.class)
                //mergeResourcesTask执行结束后再执行parseDebugTask
                mergeResourcesTask.finalizedBy(parseDebugTask)
            }
        }
    }
}