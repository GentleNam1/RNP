package Praktikum1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class ThreadReader extends Thread {
    private final String filename;
    private final List<String> filenames;

    public ThreadReader(String filename, List<String> filenames) {
        this.filename = filename;
        this.filenames = filenames;
    }

    @Override
    public void run() {
        try {
            //Debug-Ausgabe
            System.out.println("Thread Reader startet");
            List<ThreadCreator> threadCreators = new ArrayList<>();
            //Einlesen von Thread Anzahl
            Scanner scanner = new Scanner(System.in);
            int count;
            do {
                System.out.println("Enter the Thread Count: ");
                //Wenn Eingabe keine Zahl ist
                while (!scanner.hasNextInt()) {
                    System.out.println("ungültig");
                    scanner.next();
                }
                count = scanner.nextInt();
                //Prüfung ob Eingabe größer 0 ist
            } while (count <= 0);
            System.out.println("Thread Count = " + count);
            //Semaphoren Erzeugung für Thread Creator
            Semaphore semaphore = new Semaphore(count);
            Semaphore semaphore1 = new Semaphore(count);
            //Thread zur Dateierstellung
//            for (int i = 0; i < count; i++) {
//                ThreadCreator threadCreator = new ThreadCreator(filenames, semaphore);
//                threadCreator.setName("ThreadCreator" + i);
//                System.out.println("" + threadCreator.getName() + " wird erzeugt");
//                threadCreator.start();
//                threadCreators.add(threadCreator);
//            }
            //BufferedReader zum Zeilen einlesen
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    //Eintrag nur, wenn er nicht schon in der Liste ist
                    if (!filenames.contains(line)) {
                        filenames.add(line);
                        System.out.println("Eintrag eingeschrieben");
                        if (semaphore1.tryAcquire()){
                            ThreadCreator threadCreator = new ThreadCreator(filenames, semaphore);
                            threadCreator.setName("ThreadCreator" + threadCreators.size());
                            System.out.println("" + threadCreator.getName() + " wird erzeugt");
                            threadCreator.start();
                            threadCreators.add(threadCreator);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
            reader.close();
            //Sobald die Liste leer ist, ist das Programm fertig
            if (filenames.isEmpty()) {
                System.out.println("Keine Dateien mehr zu erstellen, Programm wird beendet.");
                interrupt();
                //damit das Programm erst endet, wenn alle Dateien erstellt werden
                for (ThreadCreator threadCreator : threadCreators) {
                    threadCreator.join();
                }
            }
        } catch (InterruptedException | IOException ignored) {
        }
    }
}


