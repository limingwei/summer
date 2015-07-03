package cn.limw.summer.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import cn.limw.summer.util.Files;
import cn.limw.summer.util.ListUtil;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年8月18日 下午5:14:01)
 * @since Java7
 */
public class Code {
    private static String suffix;

    private Code() {}

    /**
     * 代码分析结果。
     * @author pangwu86(pangwu86@gmail.com)
     */
    public static class CodeAnalysisResult {
        // 代码行
        protected long normalLines;

        // 注释行
        protected long commentLines;

        // 空行
        protected long whiteLines;

        // 导入行
        protected long importLines;

        public CodeAnalysisResult() {}

        public CodeAnalysisResult(long normalLines, long commentLines, long whiteLines, long importLines) {
            this.normalLines = normalLines;
            this.commentLines = commentLines;
            this.whiteLines = whiteLines;
            this.importLines = importLines;
        }

        public long getNormalLines() {
            return normalLines;
        }

        public long getCommentLines() {
            return commentLines;
        }

        public long getWhiteLines() {
            return whiteLines;
        }

        public long getImportLines() {
            return importLines;
        }

        public long getTotalLines() {
            return normalLines + commentLines + whiteLines + importLines;
        }
    }

    /**
     * 代码统计结果。
     * @author pangwu86(pangwu86@gmail.com)
     */
    public static class CodeStatisticsResult extends CodeAnalysisResult {
        private File src;

        private List<File> files;

        private int fileCount;

        public CodeStatisticsResult(File src) {
            this.src = src;
        }

        public void addCodeAnalysisResult(CodeAnalysisResult analysisResult) {
            this.normalLines += analysisResult.getNormalLines();
            this.commentLines += analysisResult.getCommentLines();
            this.whiteLines += analysisResult.getWhiteLines();
            this.importLines += analysisResult.getImportLines();
            fileCount++;
        }

        public void addCodeAnalysisResult(CodeStatisticsResult statisticsResult) {
            this.files = ListUtil.add(this.files, statisticsResult.getSrc());

            this.normalLines += statisticsResult.getNormalLines();
            this.commentLines += statisticsResult.getCommentLines();
            this.whiteLines += statisticsResult.getWhiteLines();
            this.importLines += statisticsResult.getImportLines();
            this.fileCount += statisticsResult.getFileCount();
        }

        public File getSrc() {
            return src;
        }

        public int getFileCount() {
            return fileCount;
        }

        public String toString() {
            Double fileCount = new Double(getFileCount());
            Double totalLines = new Double(getTotalLines());

            String path;
            if (ListUtil.isEmpty(files)) {
                path = Files.getCanonicalPath(src);
            } else {
                path = "";
                for (File file : files) {
                    path += ", " + Files.getCanonicalPath(file);
                }
                path = path.substring(2);
            }

            String string = "统计路径\t" + path + "\n文件后缀\t" + Code.suffix + "\t\t\t\t文件个数\t\t" + getFileCount() + "\n";
            string += "    \t\t" + fmt("行数") + "\t\t" + fmt("平均") + "\t\t" + fmt("占比") + "\n";
            string += "导入\t\t" + fmt(getImportLines()) + "\t\t" + fmt(getImportLines() / fileCount) + "\t\t" + fmt(getImportLines() * 100 / totalLines) + "%\n";
            string += "空行\t\t" + fmt(getWhiteLines()) + "\t\t" + fmt(getWhiteLines() / fileCount) + "\t\t" + fmt(getWhiteLines() * 100 / totalLines) + "%\n";
            string += "注释\t\t" + fmt(getCommentLines()) + "\t\t" + fmt(getCommentLines() / fileCount) + "\t\t" + fmt(getCommentLines() * 100 / totalLines) + "%\n";
            string += "代码\t\t" + fmt(getNormalLines()) + "\t\t" + fmt(getNormalLines() / fileCount) + "\t\t" + fmt(getNormalLines() * 100 / totalLines) + "%\n";
            string += "总计\t\t" + fmt(getTotalLines()) + "\t\t" + fmt(getTotalLines() / fileCount) + "\t\t" + fmt(getTotalLines() * 100 / totalLines) + "%\n";
            return string;
        }

        private String fmt(Object object) {
            String str = "";
            if (object instanceof String || object instanceof Integer || object instanceof Long) {
                str = object + "";
            } else {
                str = new DecimalFormat("0.000").format(object);
            }
            return StringUtil.alignRight(str, 9, " ");
        }
    }

    /**
     * 代码分析配置信息。
     * @author pangwu86(pangwu86@gmail.com)
     */
    public static class CodeAnalysisConf {
        /** 包名行开头 */
        public String pakStart;

        /** 导入行开头 */
        public String impStart;

        /** 单行注解开头 */
        public String singleLineCommentStart;

        /** 多行注解开头 */
        public String multiLineCommentStart;

        /** 多行注解结尾 */
        public String multiLineCommentEnd;

        /** 空行 */
        public String emptyLinePattern;
    }

    /** 分析JAVA代码的配置项 */
    private static CodeAnalysisConf CODE_INFO_JAVA = new CodeAnalysisConf();
    static {
        CODE_INFO_JAVA.pakStart = "package ";
        CODE_INFO_JAVA.impStart = "import ";
        CODE_INFO_JAVA.singleLineCommentStart = "//";
        CODE_INFO_JAVA.multiLineCommentStart = "/*";
        CODE_INFO_JAVA.multiLineCommentEnd = "*/";
        CODE_INFO_JAVA.emptyLinePattern = "^[\\s&&[^\\n]]*$";
    }

    public static boolean isFile(File f) {
        return null != f && f.exists() && f.isFile();
    }

    /**
     * 统计某个文件的信息。
     * @param file 被分析的文件
     * @param conf 代码分析配置项(为空的话，则按照JAVA代码来进行分析统计)
     * @return 分析结果
     */
    public static CodeAnalysisResult countingCode(File file, CodeAnalysisConf conf) {
        if (!isFile(file)) {
            throw new RuntimeException("file is not a File, can't analysis it.");
        }
        if (null == conf) {
            conf = CODE_INFO_JAVA;
        }
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boolean comment = false;
        long whiteLines = 0;
        long commentLines = 0;
        long normalLines = 0;
        long importLines = 0;
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(conf.multiLineCommentStart) && !line.endsWith(conf.multiLineCommentEnd)) {
                    // 多行注释开始
                    commentLines++;
                    comment = true;
                } else if (true == comment) {
                    // 多行注释结束
                    commentLines++;
                    if (line.endsWith(conf.multiLineCommentEnd)) {
                        comment = false;
                    }
                } else if (line.matches(conf.emptyLinePattern)) {
                    // 空白行(多行注解内的空白行不算在内)
                    whiteLines++;
                } else if (line.startsWith(conf.singleLineCommentStart) || (line.startsWith(conf.multiLineCommentStart) && line.endsWith(conf.multiLineCommentEnd))) {
                    // 单行注释
                    commentLines++;
                } else if (line.startsWith(conf.pakStart) || line.startsWith(conf.impStart)) {
                    // package与import
                    importLines++;
                } else {
                    // 代码行
                    normalLines++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 记录并返回统计结果
        return new CodeAnalysisResult(normalLines, commentLines, whiteLines, importLines);
    }

    public static boolean isDirectory(File f) {
        if (null == f)
            return false;
        if (!f.exists())
            return false;
        if (!f.isDirectory())
            return false;
        return true;
    }

    public static boolean isBlank(CharSequence cs) {
        if (null == cs)
            return true;
        int length = cs.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isWhitespace(cs.charAt(i))))
                return false;
        }
        return true;
    }

    /**
     * 统计某个目录下，以特定后缀名结尾的源码信息。
     * @param src 源代码目录
     * @param suffix 文件后缀（为空的话，则统计所有类型文件）
     * @param countSubFolder 是否统计子文件夹(true的话，将递归统计所有子文件夹)
     * @param conf 代码分析配置项(为空的话，则按照JAVA代码来进行分析统计)
     */
    public static CodeStatisticsResult countingCode(File src, String suffix, boolean countSubFolder, CodeAnalysisConf conf) {
        Code.suffix = suffix;
        if (!isDirectory(src)) {
            System.err.println("src is not a File, can't analysis it.");
            return null;
        }
        if (null == conf) {
            conf = CODE_INFO_JAVA;
        }
        CodeStatisticsResult statisticsResult = new CodeStatisticsResult(src);
        boolean useParticularType = !isBlank(suffix);
        folderAnalysis(src, useParticularType, suffix, countSubFolder, conf, statisticsResult);
        return statisticsResult;
    }

    private static void folderAnalysis(File src, boolean useParticularType, String suffix, boolean countSubFolder, CodeAnalysisConf conf, CodeStatisticsResult statisticsResult) {
        for (File f : src.listFiles()) {
            if (countSubFolder && isDirectory(f)) {
                folderAnalysis(f, useParticularType, suffix, countSubFolder, conf, statisticsResult);
            } else {
                if (useParticularType && !suffix.equalsIgnoreCase(f.getName().substring(f.getName().lastIndexOf('.') + 1))) {
                    continue;
                }
                statisticsResult.addCodeAnalysisResult(countingCode(f, conf));
            }
        }
    }

    public static CodeStatisticsResult merge(List<CodeStatisticsResult> codeStatisticsResults) {
        CodeStatisticsResult total = new CodeStatisticsResult(null);
        for (CodeStatisticsResult codeStatisticsResult : codeStatisticsResults) {
            total.addCodeAnalysisResult(codeStatisticsResult);
        }
        return total;
    }
}
