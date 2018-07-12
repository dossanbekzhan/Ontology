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


       // Utils.getAttrInBigClaster(Utils.getElements("/home/beka/Документы/words_clusters_k7.txt"), "/home/beka/Рабочий стол/aaa/cluster/a.txt");
        //Utils.listFilesForFolder(new File("/home/beka/Рабочий стол/DB/"));
        Utils.stripDuplicatesFromFile("/home/beka/Рабочий стол/mini.txt","/home/beka/Рабочий стол/mini2.txt");

       // Utils.getAttrInBigClaster(Utils.getElements("/home/beka/Рабочий стол/names2.txt"),"/home/beka/Рабочий стол/aaa/cluster/FirstData2.txt","/home/beka/Рабочий стол/mini.txt");

    }
}