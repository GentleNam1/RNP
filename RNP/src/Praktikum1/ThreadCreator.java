package Praktikum1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ThreadCreator extends Thread {

    // Liste der Dateinamen
    private final List<String> filenames;
    private final Semaphore semaphore;

    public ThreadCreator(List<String> filenames, Semaphore semaphore) {
        this.filenames = filenames;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.println("" + this.getName() + " startet");
                //Lock aktivieren und den ersten Namen aus der Liste herausnehmen
                String filename = null;
                semaphore.acquire();
                //Erste Dateinamen aus der Liste entfernen
                if (!filenames.isEmpty()) {
                    filename = filenames.remove(0);
                }
                semaphore.release();
                //falls Datei nicht null ist und nicht schon existiert wird dieser erstellt
                if (filename != null) {
                    //Pfad name
                    String path = "C:\\Users\\namng\\Downloads\\RNP\\RNP\\src\\Output\\" + filename;
                    File file = new File(path);
                    //Dateierzeugung nur, wenn es die Datei nicht schon gibt
                    if (!file.exists()) {
                        file.createNewFile();
                        System.out.println("Datei " + filename + " wurde erzeugt");
                    }
                } else {
                    if (filenames.isEmpty()){
                        // Wenn es keine Arbeit mehr gibt, beende den Thread
                        interrupt();
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            interrupt();
        }
    }
}
