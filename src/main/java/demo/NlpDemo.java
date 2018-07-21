package demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NlpDemo {
    public static void main(String[] args) throws IOException {
        // Utils.replaceWord("/home/beka/Рабочий стол/BD/wiki.txt", "Л.", "легкие", "/home/beka/Рабочий стол/BD/wiki2.txt");
        // Utils.listFilesForFolder(new File("/home/beka/Рабочий стол/project/DemoTexts/"));
        String s = Utils.readFile("/home/beka/IdeaProjects/Ontology/lemmatizator.txt");
        s = s.replaceAll("[a-zA-Z0-9_-]", "");

        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/beka/IdeaProjects/Ontology/lemmatizator2.txt"));
        writer.write(s);
        writer.flush();
        writer.close();

   //     Utils.getFreq2("/home/beka/Рабочий стол/mini3.txt", "/home/beka/Рабочий стол/mini2.txt");

        // Utils.getAttrInBigClaster(Utils.getElements("/home/beka/Документы/words_clusters_k7.txt"), "/home/beka/Рабочий стол/aaa/cluster/a.txt");
         Utils.listFilesForFolder(new File("/home/beka/Рабочий стол/BD/"));
        //  Utils.stripDuplicatesFromFile("/home/beka/Рабочий стол/mini.txt","/home/beka/Рабочий стол/mini2.txt");

        // Utils.getAttrInBigClaster(Utils.getElements("/home/beka/Рабочий стол/names2.txt"),"/home/beka/Рабочий стол/aaa/cluster/FirstData2.txt","/home/beka/Рабочий стол/mini.txt");


        //System.out.println(context);



       /* ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%91%D0%B5%D1%82%D0%B0-%D0%BC%D0%B0%D0%BD%D0%BD%D0%BE%D0%B7%D0%B8%D0%B4%D0%BE%D0%B7#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%93%D0%B5%D0%BC%D0%BE%D0%BB%D0%B8%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F+%D0%B6%D0%B5%D0%BB%D1%82%D1%83%D1%85%D0%B0+%D0%BD%D0%BE%D0%B2%D0%BE%D1%80%D0%BE%D0%B6%D0%B4%D1%91%D0%BD%D0%BD%D1%8B%D1%85#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%94%D0%B8%D1%81%D0%BC%D0%B5%D0%BD%D0%BE%D1%80%D0%B5%D1%8F#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%9A%D0%BE%D1%80%D0%BE%D0%BD%D0%B0%D1%80%D0%BD%D0%B0%D1%8F+%D0%BD%D0%B5%D0%B4%D0%BE%D1%81%D1%82%D0%B0%D1%82%D0%BE%D1%87%D0%BD%D0%BE%D1%81%D1%82%D1%8C#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%9C%D1%8B%D1%88%D0%B8%D0%BD%D1%8B%D0%B9+%D0%BA%D0%BB%D0%B5%D1%89%D0%B5%D0%B2%D0%BE%D0%B9+%D0%B4%D0%B5%D1%80%D0%BC%D0%B0%D1%82%D0%B8%D1%82#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%9F%D0%B0%D1%80%D0%B0%D0%B7%D0%B8%D1%82%D0%B0%D1%80%D0%BD%D1%8B%D0%B5+%D0%B1%D0%BE%D0%BB%D0%B5%D0%B7%D0%BD%D0%B8#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%9F%D1%83%D0%BB%D1%8C%D0%BF%D0%B8%D1%82#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%A1%D0%B8%D0%BD%D0%B4%D1%80%D0%BE%D0%BC+%D1%81%D0%BB%D0%B5%D0%BF%D0%BE%D0%B9+%D0%BA%D0%B8%D1%88%D0%B5%D1%87%D0%BD%D0%BE%D0%B9+%D0%BF%D0%B5%D1%82%D0%BB%D0%B8#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=%D0%A4%D0%B0%D1%81%D1%86%D0%B8%D0%BE%D0%BB%D1%91%D0%B7#mw-pages");
        arrayList.add("https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%97%D0%B0%D0%B1%D0%BE%D0%BB%D0%B5%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83&pagefrom=Ornithonyssosis#mw-pages");


        Map<String, String> map = Utils.getURLArticles(arrayList, "div.mw-category");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String context = Utils.getURLContext(entry.getValue(), "div.mw-parser-output", "div.references-small");
            String name = entry.getKey();
            Utils.writeFile(name, context);
        }*/

    }
}