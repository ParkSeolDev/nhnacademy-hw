package com.nhnacademy;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDate;

public class SettingCollector implements Runnable {
    private String settingFilePath;

    public String getSettingFilePath() {
        return this.settingFilePath;
    }

    @Override
    public void run() {
        LocalDate date = LocalDate.now();
        this.settingFilePath = "./setting/" + date + ".json";
        if (!Files.exists(Paths.get(settingFilePath))) {
            try {
                createFileWithFolder(settingFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path directory = Paths.get(settingFilePath).getParent();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    return;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    Path changedFile = (Path) event.context();
                    System.out.println("변경된 파일: " + changedFile);

                    FileAccessor.saveFile(directory.resolve(changedFile));
                }
                key.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void createFileWithFolder(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();
        
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }
}
