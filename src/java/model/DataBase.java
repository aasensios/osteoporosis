package model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumne
 */
public class DataBase {

    //atributs
    private String fileName;

    //constructor
    public DataBase(String fileName) {
        this.fileName = fileName;

    }

    //accesores
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    //metode per llegir de fitxer
    /**
     * Reads all the content of the file.
     *
     * @return a List of strings, each string is a line of the file
     */
    public List listAllLines() {
        List all = new ArrayList();
        try {
            FileInputStream fstream = new FileInputStream(this.fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                all.add(strLine);
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return all;
    }

    //metode per escriure a fitxer
    /**
     * Writes into the file.
     *
     * @param inputText String to be written
     * @return 1 if success, 0 otherwise
     */
    public int insertToFile(String inputText) {
        //DINS DE DATABASE
        File outputFile = null;
        FileWriter fout = null;
        int i = 0;
        try {
            outputFile = new File(this.fileName);
            fout = new FileWriter(outputFile, true);
            fout.write(inputText);
            i = 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
        return i;
    }

    //metode per esborrar els continguts del fitxer
    /**
     * Deletes all the content in the file.
     *
     * @throws java.io.IOException
     */
    public void deleteContentFile() throws IOException {
        try {
            new FileWriter(this.fileName, false).close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

}
