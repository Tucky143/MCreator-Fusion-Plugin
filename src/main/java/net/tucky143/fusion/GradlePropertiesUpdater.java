package net.tucky143.fusion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GradlePropertiesUpdater {

    public static void main(String args) {
        update(args, false);
    }

    public static void SetTrue(String args) {
        update(args, true);
    }

    private static void update(String args, boolean enable) {
        File projectRoot = new File(args);
        if (!projectRoot.exists() || !projectRoot.isDirectory()) {
            System.err.println("Invalid project root directory: " + args);
            System.exit(1);
        }

        File gradleProperties = new File(projectRoot, "gradle.properties");
        String targetLine = "org.gradle.configuration-cache=" + (enable ? "true" : "false");

        try {
            boolean modified = false;
            List<String> lines;

            if (gradleProperties.exists()) {
                lines = Files.readAllLines(gradleProperties.toPath());
                boolean found = false;

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (line.startsWith("org.gradle.configuration-cache=")) {
                        found = true;
                        if (!line.equals(targetLine)) {
                            lines.set(i, targetLine);
                            modified = true;
                        }
                        break;
                    }
                }

                if (!found) {
                    lines.add(targetLine);
                    modified = true;
                }
            } else {
                lines = List.of(targetLine);
                modified = true;
            }

            if (modified) {
                Files.write(gradleProperties.toPath(), lines);
                System.out.println("Updated gradle.properties: " + targetLine);
            } else {
                System.out.println("No changes needed, already set: " + targetLine);
            }

        } catch (IOException e) {
            System.err.println("Error updating gradle.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
