package top.fhcy.utils;

import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;

public class TikaUtils {

    /**
     * 获取文件类型
     * @param inputStream inputStream
     * @return 文件类型
     */
    public static String detectFileType(InputStream inputStream) {
        Tika tika = new Tika();
        try {
            return tika.detect(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
