package com.ll.exam.app10.util;

import org.apache.tika.Tika;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Util {
    public static class date {

        public static String getCurrentDateFormatted(String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date());
        }
    }

    public static class file {

        public static String getExt(String filename) {
            return Optional.ofNullable(filename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                    .orElse("");
        }

        public static String downloadImg(String url, String filePath) {
            new File(filePath).getParentFile().mkdirs();

            byte[] imageBytes = new RestTemplate().getForObject(url, byte[].class);
            try {
                Files.write(Paths.get(filePath), imageBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String mimeType = null;
            try {
                mimeType = new Tika().detect(new File(filePath)); // Tika는  다양한 형태의, 파일의 메타 데이터와 텍스트를 감지하고 추출하는 외부 라이브러리이다.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String ext = mimeType.replaceAll("image/", "");
            ext = ext.replaceAll("jpeg", "jpg");

            String newFilePath = filePath + "." + ext;

            new File(filePath).renameTo(new File(newFilePath));

            return newFilePath;
        }
    }
}