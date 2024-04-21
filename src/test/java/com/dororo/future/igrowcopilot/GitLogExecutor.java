package com.dororo.future.igrowcopilot;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class GitLogExecutor {
    public static void main(String[] args) {
        try {
            // 指定目录
            File workingDirectory = new File("C:\\dororovm\\dev\\projects\\github\\igrow-copilot");

            // 用户输入
            String userInput = "3"; // 示例用户输入

            // 构建命令
            String command = String.format("git log -%s --pretty=format:\"%%ad %%s\" --date=short", userInput);

            // 创建 ProcessBuilder
            // ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command); // 在 Linux/macOS
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command); // 在 Windows
            processBuilder.directory(workingDirectory); // 设置工作目录

            // 启动进程
            Process process = processBuilder.start();

            // 读取输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // 打印输出
            }

            // 检查进程状态
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Command execution failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 处理异常
        }
    }
}
