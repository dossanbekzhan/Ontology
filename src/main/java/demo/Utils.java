package demo;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by beka on 6/21/18.
 */
public class Utils {

    public static void stripDuplicatesFromFile(String filename, String newFilename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Set<String> lines = new HashSet<>(); // maybe should be bigger
        String line = reader.readLine();
        while ((line) != null) {
            System.out.println(line);
            lines.add(line);
            line = reader.readLine();
        }
        System.out.println(lines.size());
        reader.close();

        // List<String> personsSorted = lines.stream().collect(Collectors.toList());
        List<String> personsSorted = new ArrayList<>(lines);
        Collections.sort(personsSorted);

        BufferedWriter writer = new BufferedWriter(new FileWriter(newFilename));
        for (String unique : personsSorted) {
            writer.write(unique);
            writer.newLine();
        }
        writer.close();
    }


    public static void getAttrInBigClaster(List<String> attr, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/beka/Рабочий стол/aaaaaa.txt", true));
        String line;

        while ((line = reader.readLine()) != null) {
            for (String s : attr) {
                String[] lines = line.split(";");
                if (lines[0].equals(s)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        writer.close();
    }


    public static List<String> getElements(String filename) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();

        while ((line) != null) {
            lines.add(line);
            line = reader.readLine();
        }

        System.out.println(lines);
        return lines;
    }


    public static void writeFile(List<String> object, List<String> attr, List<String> fullCollocation, String newFilename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFilename));

        for (String o : object) {
            for (String a : attr) {
                String collocation = o + "; " + a;
                for (String fC : fullCollocation) {
                    if (fC.contains(collocation)) {
                        writer.write(collocation);
                        writer.newLine();
                    }
                }
            }
        }
        writer.close();
    }


    public static void getFreq(String fileName, String newFilename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFilename));
        String line = reader.readLine();

        while ((line) != null) {
            String[] lines = line.split(";");
            if (line.contains("1")) {
                writer.write(lines[0] + "; " + lines[1]);
                writer.newLine();
            }
            line = reader.readLine();
        }

        writer.close();

    }


    public static void main() throws IOException {
        List<String> keywords = new LinkedList<>();
        FileWriter writer = new FileWriter("/home/beka/Рабочий стол/BCSort.txt");

        BufferedReader br = new BufferedReader(new FileReader("/home/beka/Рабочий стол/Bigclaster.txt"));
        String line = br.readLine();

        while ((line != null)) {
            keywords.add(line);
            line = br.readLine();
            System.out.println(line);
        }

        System.out.println(keywords.size());

        Collections.sort(keywords);

        for (String i : keywords) {
            writer.write(i);
            writer.write("\n");
        }

        System.out.println(keywords.size());
        writer.close();

        br.close();
    }

    public static void replaceWord(String fileName, String target, String replacement, String fileWrite) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileWrite, true));
        String line;

        while ((line = reader.readLine()) != null) {

            String newline = line.replace(target, replacement);
            writer.write(newline);
        }


    }
}
