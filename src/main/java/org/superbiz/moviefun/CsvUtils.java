package org.superbiz.moviefun;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvUtils {

    public static String readFile(String path) {

        try {

            // returns the Class object associated with this class
            Class cls = Class.forName("org.superbiz.moviefun.CsvUtils");

            // returns the ClassLoader object associated with this Class.
            ClassLoader cLoader = cls.getClassLoader();

            InputStream is = cLoader.getResourceAsStream(".");
            
//            Scanner scanner = new Scanner(new File(path)).useDelimiter("\\A");
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            if (scanner.hasNext()) {
                String str = scanner.next();
                System.out.println("" + str );
                return str;//scanner.next();
            } else {
                return "";
            }

//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }



    public static <T> List<T> readFromCsv(ObjectReader objectReader, String path) {
        try {
            List<T> results = new ArrayList<>();

            MappingIterator<T> iterator = objectReader.readValues(readFile(path));

            while (iterator.hasNext()) {
                results.add(iterator.nextValue());
            }

            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
