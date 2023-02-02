package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("showCustomPlugin") {
            doLast {
                println "Hello world plugin"
            }
        }
    }
}