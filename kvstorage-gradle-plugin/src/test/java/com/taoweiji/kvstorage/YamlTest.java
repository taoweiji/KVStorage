package com.taoweiji.kvstorage;

import org.junit.Test;

import java.io.IOException;

public class YamlTest {
    @Test
    public void parse() throws IOException {
        BuildJavaFile buildJavaFile = new BuildJavaFile();
        String className = buildJavaFile.build("/Users/wiki/Documents/source/KVStorage/example/storage.yaml",
                "com.taoweiji.kvstorage.example",
                "/Users/wiki/Documents/source/KVStorage/example/src/main/java"
        );
        System.out.println("创建 " + className);
    }
}
