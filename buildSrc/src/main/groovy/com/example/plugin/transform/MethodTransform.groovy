package com.example.plugin.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils

class MethodTransform extends Transform {

    @Override
    String getName() {
        return "MethodTimeTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        //需要处理的数据类型,这里表示class文件
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        //作用范围
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        //是否支持增量编译
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        //TransformOutputProvider管理输出路径,如果消费型输入为空,则outputProvider也为空
        TransformOutputProvider outputProvider = transformInvocation.outputProvider

        //transformInvocation.inputs的类型是Collection<TransformInput>,可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        transformInvocation.inputs.each { input -> //这里的input是TransformInput

            input.jarInputs.each { jarInput ->
                //处理jar
                processJarInput(jarInput, outputProvider)
            }

            input.directoryInputs.each { directoryInput ->
                //处理源码文件
                processDirectoryInput(directoryInput, outputProvider)
            }
        }
    }

    void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(jarInput.file.absolutePath, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        //将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        println("拷贝文件 $dest -----")
        FileUtils.copyFile(jarInput.file, dest)
    }

    void processDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format
                .DIRECTORY)
        //将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        println("拷贝文件夹 $dest -----")
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

}