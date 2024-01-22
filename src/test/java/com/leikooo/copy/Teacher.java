package com.leikooo.copy;

import lombok.Builder;
import lombok.Data;

import java.io.*;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
@Data
@Builder
public class Teacher implements Serializable {
    private String teacherName;

    private Student student;

    /**
     * 深拷贝，建议使用实现 Serializable 接口的方式实现
     *
     * @return
     */
    public Teacher copy() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Teacher teacher = null;
        try {
            // 先写出然后在读取
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(this);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            inputStream = new ObjectInputStream(byteArrayInputStream);
            teacher = (Teacher) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return teacher;
    }
}
