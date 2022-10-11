package com.ll.exam.app10.app.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static String GET_FILE_DIR_PATH; // static으로 하면 @Value가 안먹힌다. 따라서 아래처럼 setter를 만든다.

    @Value("${custom.genFileDirPath}")
    public void setFileDirPath(String genFileDirPath) { // @Value를 setter에 붙이면 자동으로 한번 실행된다.
        GET_FILE_DIR_PATH = genFileDirPath;
    }
}