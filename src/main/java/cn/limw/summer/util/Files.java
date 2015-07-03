package cn.limw.summer.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * @author li
 * @version 1 (2014年6月16日 下午3:52:10)
 * @since Java7
 */
public class Files {
    private static final Logger log = Logs.slf4j();

    public static void write(byte[] from, OutputStream to) {
        write(new ByteArrayInputStream(from), to);
    }

    public static void write(InputStream from, OutputStream to) {
        if (null == from) {
            return;
        }
        try {
            byte[] buf = new byte[1024];
            int flag = 0;
            while ((flag = from.read(buf)) != -1) {
                to.write(buf, 0, flag);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(from);
            close(to);
        }
    }

    public static FileOutputStream fileOutputStream(String fileName) {
        try {
            Asserts.noEmpty(fileName, "fileName为空");
            fileName = fileName.replace("\\", File.separator).replace("/", File.separator); // 不同操作系统
            new File(fileName).getParentFile().mkdirs();
            return new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static OutputStream nullOutputStream() {
        return new OutputStream() {
            public void write(int b) throws IOException {}
        };
    }

    public static byte[] urlToBytes(String path) {
        try {
            return toBytes(new URL(Asserts.noNull(path, "传入路径不可以为空")).openStream());
        } catch (Exception e) {
            throw new RuntimeException("read file error " + path + " " + e.getMessage(), e);
        }
    }

    public static byte[] toBytes(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            write(inputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String toString(InputStream inputStream) {
        return new String(toBytes(inputStream));
    }

    public static String toString(InputStream inputStream, String charset) {
        try {
            return new String(toBytes(inputStream), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(BufferedReader reader) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Boolean isEmpty(InputStream inputStream) {
        return StringUtil.isEmpty(toString(inputStream, "UTF-8"));
    }

    public static FileInputStream fileInputStream(String fileName) {
        try {
            Asserts.noEmpty(fileName, "fileName为空");
            fileName = fileName.replace("\\", File.separator).replace("/", File.separator); // 不同操作系统

            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文件名正则表达式搜索一个路径下的文件,返回文件路径的List
     * @param file 要搜索的目录
     * @param regex 要求文件路径要符合的正则表达式
     * @param increase 是否递进搜索
     * @param fileOrFloder 1 文件 2 文件夹
     * @return 文件绝对路径列表
     */
    public static List<String> listFiles(File file, String regex, Boolean increase, Integer fileOrFloder) {
        List<String> list = new ArrayList<String>();
        if (((1 == fileOrFloder && file.isFile()) || (2 == fileOrFloder && file.isDirectory())) && VerifyUtil.regex(file.getPath(), regex)) {
            list.add(file.getPath());
        } else if (increase && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                list.addAll(listFiles(f, regex, increase, fileOrFloder)); // 递归调用本方法
            }
        }
        return list;
    }

    public static List<String> listFiles(File file, String regex, Boolean increase) {
        return listFiles(file, regex, increase, 1);
    }

    public static String getCanonicalPath(File file) {
        if (null == file) {
            return null;
        }
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSuffix(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf('.'));
        } catch (Exception e) {
            throw new RuntimeException(fileName);
        }
    }

    public static void write(InputStream inputStream, File file) {
        write(inputStream, fileOutputStream(file));
    }

    public static FileOutputStream fileOutputStream(File file) {
        try {
            file.getParentFile().mkdirs();
            return new FileOutputStream(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(File file, OutputStream outputStream) {
        write(fileInputStream(file), outputStream);
    }

    public static FileInputStream fileInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("fileInputStream error, canonicalPath=" + getCanonicalPath(file), e);
        }
    }

    /**
     * 读取一个文本文件,返回每一行文本的集合
     */
    public static Set<String> readFileToLines(InputStream inputStream) {
        try {
            Set<String> lines = new HashSet<String>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            Files.close(bufferedReader);
            return lines;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(File file, String charset) {
        return toString(fileInputStream(file), charset);
    }

    public static void write(byte[] from, File to) {
        write(new ByteArrayInputStream(from), to);
    }

    public static String classPathRead(String path) {
        try {
            return toString(new ClassPathResource(path).getInputStream(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream classPathRead(Class<?> type, String fileName) {
        try {
            return new ClassPathResource(fileName).getInputStream();
        } catch (Exception e1) {
            try {
                return new FileInputStream(currentPath(type) + fileName);
            } catch (Exception e2) {
                try {
                    return new FileInputStream(currentPath(type).replace("/lib/", "/") + fileName); // 权宜之计, 获取的路径是 support.jar 的位置 , 期望得到的是 account-service.jar的位置
                } catch (Exception e3) {
                    throw new RuntimeException(e1.getMessage() + ", " + e2.getMessage() + ", " + e3.getMessage(), e1);
                }
            }
        }
    }

    public static String currentPath(Class<?> type) {
        String url = type.getProtectionDomain().getCodeSource().getLocation().toString();
        return url.substring(0, url.lastIndexOf('/') + 1).replace("file:", "");
    }

    public static void copyDir(File from, File to, FileFilter filter) {
        File[] files = from.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isFile()) {
                    if (filter.accept(file)) {
                        write(file, new File(getCanonicalPath(file).replace(getCanonicalPath(from), getCanonicalPath(to))));
                    } else {
                        //
                    }
                } else if (file.isDirectory()) {
                    copyDir(file, new File(getCanonicalPath(file).replace(getCanonicalPath(from), getCanonicalPath(to))), filter);
                } else {
                    throw new RuntimeException();
                }
            }
        }
    }

    public static void write(File from, File to) {
        write(fileInputStream(from), fileOutputStream(to));
    }

    public static void delete(File dir, FileFilter filter) {
        File[] files = dir.listFiles();
        if (null != files) {
            for (File file : files) {
                if (file.isFile()) {
                    if (filter.accept(file)) {
                        file.delete();
                    } else {
                        //
                    }
                } else if (file.isDirectory()) {
                    delete(file, filter);
                } else {
                    throw new RuntimeException();
                }
            }
        }
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        write(inputStream, out);
        return out.toByteArray();
    }

    public static InputStream getResourceAsStream(Class<?> type, String path) {
        if (null == type || null == path) {
            return null;
        }
        InputStream in = type.getResourceAsStream(path);
        if (null != in) {
            return in;
        }
        in = type.getClassLoader().getResourceAsStream(path);
        if (null != in) {
            return in;
        }
        log.info("getResourceAsStream type={}, path={} return null", type, path);
        return null;
    }

    public static Boolean exists(String path) {
        return new File(path).exists();
    }

    public static String read(File file) {
        return read(file, "UTF-8");
    }

    public static void createNewFile(File file) {
        try {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File newFile(String dir, String fileName) {
        File _dir = new File(dir);
        if (!_dir.exists()) {
            _dir.mkdirs();
        }

        return new File(dir, fileName);
    }

    public static FileOutputStream fileOutputStream(String dir, String fileName) {
        return fileOutputStream(newFile(dir, fileName));
    }

    public static Reader reader(String fileName, String charSet) {
        try {
            return new InputStreamReader(fileInputStream(fileName), charSet);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Reader reader) {
        try {
            String result = "";
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) {
                result += line + "\r\n";
            }
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}