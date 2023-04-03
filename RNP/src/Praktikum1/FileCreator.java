
package Praktikum1;

import java.util.ArrayList;
import java.util.List;
public class FileCreator {

    //Dateiname
    private static final String filename = "C:\\Users\\namng\\IdeaProjects\\RNP\\RNP\\src\\Input\\File1.txt";

    public void startFileCreation() {
        //Debug-Ausgaben
        System.out.println("Main Thread startet");
        //Dateinamen-Liste zum übergeben
        List<String> filenames = new ArrayList<>();
        //Thread Reader wird erzeugt
        ThreadReader threadReader = new ThreadReader(filename, filenames);
        System.out.println("Thread Reader wird erzeugt");
        threadReader.setName("ThreadReader");
        threadReader.start();
        //main endet erst wenn Reader endet
        try {
            threadReader.join();
        } catch (InterruptedException ignored) {
        }
    }
    //start
    public static void main(String[] args) {
        new FileCreator().startFileCreation();
    }
}
