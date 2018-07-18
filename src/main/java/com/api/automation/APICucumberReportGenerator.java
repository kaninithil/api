package com.api.automation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class APICucumberReportGenerator {

    public static void main(String[] args) {
        List<String> jsonFiles = new ArrayList<String>();
        String jsonDirectoryPath = "target/reports";
        File jsonsonDirectory = new File(jsonDirectoryPath);
        if (jsonsonDirectory.exists() && jsonsonDirectory.isDirectory()) {
            File[] files = jsonsonDirectory.listFiles();
            for (File file : files) {
                String filepath = file.getAbsolutePath();
                System.out.println("filePath:" + filepath);
                jsonFiles.add(filepath);
            }

        }

        File reportOutputDirectory = new File("target");
        String jenkinsBasePath = "";
        String buildNumber = "1";
        String projectName = "PAO-API-Automation";
        boolean skippedFails = true;
        boolean pendingFails = false;
        boolean undefinedFails = true;
        boolean missingFails = true;
        boolean runWithJenkins = false;
        boolean parallelTesting = false;

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        // optionally only if you need
        configuration.setStatusFlags(skippedFails, pendingFails, undefinedFails, missingFails);
        configuration.setParallelTesting(parallelTesting);
        configuration.setJenkinsBasePath(jenkinsBasePath);
        configuration.setRunWithJenkins(runWithJenkins);
        configuration.setBuildNumber(buildNumber);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }

}
