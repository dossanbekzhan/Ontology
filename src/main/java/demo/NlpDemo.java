package demo;

import java.io.*;
import java.util.Map;

public class NlpDemo {
    public static void main(String[] args) throws IOException {
        //Utils.replaceWord("/home/beka/Рабочий стол/katava/data.txt", "Ж.", "желудок", "/home/beka/Рабочий стол/katava/data2.txt");
        // Utils.listFilesForFolder(new File("/home/beka/Рабочий стол/project/DemoTexts/"));
      /*  String s = Utils.readFile("/home/beka/IdeaProjects/Ontology/lemmatizator.txt");
        s = s.replaceAll("[a-zA-Z0-9_-]", "");

        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/beka/IdeaProjects/Ontology/lemmatizator2.txt"));
        writer.write(s);
        writer.flush();
        writer.close();*/

        Map<String, String> maps = Utils.getURLArticles();
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            String context = Utils.getURLContext(entry.getValue());
            String nameArticle = entry.getKey();
            Utils.writeFile(nameArticle, context);
        }

    }
}