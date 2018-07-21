package demo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;


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


    public static void getAttrInBigClaster(List<String> attr, String filename, String newFileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName, true));
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

    public static void listFilesForFolder(final File folder) {
        StringBuilder stringBuilder = new StringBuilder();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                //   System.out.println(fileEntry.getName());
                stringBuilder.append(readFile(folder.toPath() + "/" + fileEntry.getName()));
                stringBuilder.append("\n");

            }
            fileWrite(stringBuilder.toString(), folder.toPath() + "/" + "fullData.txt");

        }
    }

    private static void fileWrite(String text, String fileName) {
        File file = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName) {
        String fullText = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fullText = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullText;
    }


    public static Map<String, String> getURLArticles(ArrayList<String> urls, String select) throws IOException {
        Map<String, String> articles = new HashMap<>();

        for (int i = 0; i < urls.size(); i++) {


            String tempURL = urls.get(i);
            Connection connection = Jsoup.connect(tempURL).userAgent("Mozilla/5.0").timeout(10000).followRedirects(true);
            Document document = connection.timeout(10000).get();

            Elements main = document.select(select);

            Elements links = main.select("a[href]");

            for (Element link : links) {
                if (!link.text().isEmpty()) {
                    System.out.println("Name Article: " + link.text());
                    System.out.println("Href: " + Constants.URLWIKI + link.attr("href"));
                    articles.put(link.text(), Constants.URLWIKI + link.attr("href"));
                }
            }
        }

        return articles;
    }

    /**
     * @param url    is article's url
     * @param select is html context
     *               <p>
     *               div id is div#
     *               div class is div.
     *               <p>
     *               for remove use method document.select(select).remove()
     * @return Article's context
     * @throws IOException
     */
    public static String getURLContext(String url, String select, String removeselect) throws IOException {
        StringBuilder builder = new StringBuilder();

        Connection connection = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(10000).followRedirects(true);
        Document document = connection.timeout(10000).get();

        document.select(removeselect).remove();
        document.select("table.infobox").remove();
        document.select("div#toctitle").remove();
        document.select("div.toctitle").remove();
        document.select("div#toc").remove();
        document.select("div.toc").remove();

        Element context = document.select(select).first();

        builder.append(context.text());

        return builder.toString();
    }

    /**
     * @param nameFile
     * @param context
     * @throws IOException
     */
    public static void writeFile(String nameFile, String context) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/beka/Рабочий стол/BD/" + nameFile + ".txt"));

        writer.write(context);
        writer.flush();
        writer.close();

    }

    public static void getFreq2(String fileName, String newFilename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFilename));
        String line = reader.readLine();

        while ((line) != null) {
            String[] lines = line.split(";");

            if ((!line.contains("мать") &&
                    !line.contains("мягкое небо") &&
                    !line.contains("одежда") &&
                    !line.contains("птица") &&
                    !line.contains("жидкий стул") &&
                    !line.contains("клеточная стенка") &&
                    !line.contains("животное") &&
                    !line.contains("ребенок") &&
                    !line.contains("свинья") &&
                    !line.contains("собака") &&
                    !line.contains("стол") &&
                    !line.contains("страна") &&
                    !line.contains("сутки") &&
                    !line.contains("ткань") &&
                    !line.contains("тяжелая форма") &&
                    !line.contains("хозяин") &&
                    !line.contains("белка") &&
                    !line.contains("белка") &&
                    !line.contains("белая мышь") &&
                    !line.contains("важная роль") &&
                    !line.contains("белка") &&
                    !line.contains("животное") &&
                    !line.contains("лекция") &&
                    !line.contains("население") &&
                    !line.contains("катаральное явление") &&
                    !line.contains("клеточная стенка") &&
                    !line.contains("кошка") &&
                    !line.contains("лошадь"))) {
                line.replaceAll("больной", "боль");
                line.replaceAll("беременная", "беременность");
                line.replaceAll("беременная", "беременность");

                writer.write(line);
                writer.newLine();
            }
            line = reader.readLine();
        }

        writer.close();

    }


/*    public static void script(ArrayList<String> relations) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("a.txt"));
        for (String s : relations) {

            String[] lines = s.split(";");
            String word1 = lines[0];
            String word2 = lines[0];

            double cos = model.similarity(word1, word2);
            writer.write(word1 + ";" + word2 + ";" + cos);
            writer.newLine();

        }
        writer.flush();
        writer.close();
    }*/


}
