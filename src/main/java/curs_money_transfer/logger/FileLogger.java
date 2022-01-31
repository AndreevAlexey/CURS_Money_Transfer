package curs_money_transfer.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class FileLogger implements ILogger {
    private static FileLogger instance = null;
    private File logFile;

    private static final Calendar calendar = new GregorianCalendar();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    // конструктор
    private FileLogger() {
        try {
            Files.createDirectories(Paths.get(Settings.dir));
            this.logFile = new File(Settings.dir, Settings.fileName);
            logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // получить логгер
    public static FileLogger get() {
        if(instance == null) instance = new FileLogger();
        return instance;
    }

    // добавить в лог
    @Override
    public void log(String msg) {
        try(FileWriter fw = new FileWriter(logFile, true);
            BufferedWriter bw = new BufferedWriter(fw))
        {
            bw.write(dateFormat.format(calendar.getTime()) + ": " + msg + "\n");
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

}
