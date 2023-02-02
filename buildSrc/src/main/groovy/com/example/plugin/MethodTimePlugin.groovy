package com.example.plugin

import com.android.build.gradle.AppExtension
import com.example.plugin.transform.MethodTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class MethodTimePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def app = project.extensions.getByType(AppExtension.class)
        app.registerTransform(new MethodTransform())
    }
}