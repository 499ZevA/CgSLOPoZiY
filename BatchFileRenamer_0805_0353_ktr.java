// 代码生成时间: 2025-08-05 03:53:25
import io.micronaut.core.annotation.Introspected;
    import java.nio.file.*;
    import java.io.IOException;
    import java.nio.file.attribute.BasicFileAttributes;
    import java.util.function.Consumer;

    /**
     * 批量文件重命名工具
     * 
     * 该工具能够将指定目录下的所有文件进行重命名，按照一定的规则。
     * 可以自定义重命名的规则，通过传入一个重命名规则的函数。
     */
    @Introspected
    public class BatchFileRenamer {

        /**
         * 执行批量文件重命名操作
         * 
         * @param directoryPath 需要重命名文件的目录路径
         * @param renameRule    文件重命名规则
         * @throws IOException 如果在遍历文件或重命名文件时发生IO异常
         */
        public void renameFiles(String directoryPath, Consumer<String> renameRule) throws IOException {
            Path directory = Paths.get(directoryPath);
            if (!Files.isDirectory(directory)) {
                throw new IOException(