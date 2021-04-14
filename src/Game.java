import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Game {
    private String name;
    boolean isInstalled;

    private static String [] dirsOfGames = new String[]{"src", "res", "savegames", "temp"};
    private static String [] dirsOfSrc = new String[]{"main", "test"};
    private static String [] dirsOfRes = new String[]{"drawables", "vectors", "icons"};
    private static String [] filesOfMain = new String[]{"Main.java", "Utils.java"};

    public Game(String name) {
        this.name = name;
    }

    private String makeDirs(String dirsPath, String[] dirs) {
        StringBuilder log = new StringBuilder();
        String fullPath;
        for (String dir:dirs) {
            fullPath = dirsPath + dir;
            File newDir = new File(fullPath);
            if (newDir.mkdir()) {
                log.append(String.format("Каталог \"%s\" успешно создан.\n", fullPath));
            } else {
                log.append(String.format("Каталог \"%s\" не создан.\n", fullPath));
                if (isInstalled) {
                    isInstalled = false;
                }
            }
        }
        return log.toString();
    }

    private String makeFiles(String filesPath, String[] files){
        StringBuilder log = new StringBuilder();
        String fullPath;
        for (String file:files) {
            fullPath = filesPath + file;
            File newFile = new File(fullPath);
            try {
                if (newFile.createNewFile()) {
                    log.append(String.format("Файл \"%s\" успешно создан.\n", fullPath));
                } else {
                    throw new IOException("File already exists");
                }
            } catch (IOException e) {
                log.append(String.format("Файл \"%s\" не создан: %s.\n", fullPath, e.getMessage()));
                if (isInstalled) {
                    isInstalled = false;
                }
            }
        }
        return log.toString();
    }

    private String makeLog(String logPath, StringBuilder log){
        String fullPath = logPath + "temp.txt";
        File logFile = new File(fullPath);
        try {
            if (logFile.createNewFile()) {
                log.append(String.format("Файл \"%s\" успешно создан.\n", fullPath));
            } else {
                throw new IOException("File already exists");
            }
        } catch (IOException e) {
            log.append(String.format("Файл \"%s\" не создан: %s.\n", fullPath, e.getMessage()));
            if (isInstalled) {
                isInstalled = false;
            }
        }
        String result = String.format("Установка %s\n" ,
                isInstalled ? "успешно завершена :)" : "завершена с ошибками :(");
        log.append(result);

        try (FileWriter logWriter = new FileWriter(logFile)) {
            logWriter.write(log.toString());
            logWriter.flush();
            result = String.format("%sПодробности смотрите в %s" , result, fullPath);
        } catch (IOException e) {
            log.append(String.format("Сбой установки: ошибка при записи лога: %s\n", e.getMessage()));
            result = log.toString();
        }
        return result;
    }

    public String install(String gamesDir){
        isInstalled = true;
        StringBuilder installLog = new StringBuilder();
        installLog.append(String.format("Начата установка игры  \"%s\". . .\n" , name));
        installLog.append(makeDirs(gamesDir, dirsOfGames));
        installLog.append(makeDirs(gamesDir + "src/", dirsOfSrc));
        installLog.append(makeFiles(gamesDir +"src/main/", filesOfMain));
        installLog.append(makeDirs(gamesDir + "res/", dirsOfRes));
        return makeLog(gamesDir +"temp/", installLog);
    }
}

