package com.aeroguard.BackgroundStuff;

import javafx.concurrent.Task;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class ReportGenerator extends Task<Void> {

    private final File templateFile;
    private final File outputFile;
    private final Map<String, String> reportData;

    public ReportGenerator(File templateFile, File outputFile, Map<String, String> reportData) {
        this.templateFile = templateFile;
        this.outputFile = outputFile;
        this.reportData = reportData;
    }

    @Override
    protected Void call() {
        try {
            modifyLatexFile();
            compileLatexFile();
        } catch (IOException | InterruptedException e) {
            updateMessage("Error generating report: " + e.getMessage());
        }
        return null;
    }

    private void modifyLatexFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(templateFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                for (Map.Entry<String, String> entry : reportData.entrySet()) {
                    line = line.replace(entry.getKey(), entry.getValue());
                }
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private void compileLatexFile() throws IOException, InterruptedException {
        int maxAttempts = 3;
        boolean success = false;
        List<String> errorMessages = new ArrayList<>();

        for (int attemptCount = 1; attemptCount <= maxAttempts; attemptCount++) {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "pdflatex", "-interaction=nonstopmode", "-output-directory", outputFile.getParent(),
                    outputFile.getName());

            processBuilder.directory(outputFile.getParentFile());
            Process process = processBuilder.start();

            try (BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                outputReader.lines().forEach(System.out::println); 
                errorReader.lines().forEach(errorMessages::add);

                if (process.waitFor() == 0) {
                    success = true;
                    break; 
                }
            }
        }

        if (!success) {
            updateMessage(
                    "PDF generation failed after " + maxAttempts + " attempts:\n" + String.join("\n", errorMessages));
        }
    }
}
