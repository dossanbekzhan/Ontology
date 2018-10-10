package demo;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NlpDemo {
    public static void main(String[] args) throws IOException {
        // Utils.replaceWord("/home/beka/Рабочий стол/BD/wiki.txt", "Л.", "легкие", "/home/beka/Рабочий стол/BD/wiki2.txt");
        // Utils.listFilesForFolder(new File("/home/beka/Рабочий стол/project/DemoTexts/"));
      /*  String s = Utils.readFile("/home/beka/Рабочий стол/lemma.txt");
        //s = s.replaceAll("[a-zA-Z0-9_-]", "");
        s = s.replaceAll("[^А-яа-я\\s+]", "");

        //String s2 = removePunct2(s);


        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/beka/Рабочий стол/lemma2.txt"));
        writer.write(s);
        writer.flush();
        writer.close();
*/
        //     Utils.getFreq2("/home/beka/Рабочий стол/mini3.txt", "/home/beka/Рабочий стол/mini2.txt");


        //Utils.stripDuplicatesFromFile("D:\\DataWithGenitive.txt", "D:\\DataWithGenitive2.txt");
        Utils.getFreq("D:\\DataWithGenitive.txt", "D:\\ForCheck.txt");


        /*BufferedReader reader = new BufferedReader(new FileReader("D:\\attr.txt"));
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();

        while ((line) != null) {
            String[] split = line.split(" : ");
            lines.add(split[0].trim());
            line = reader.readLine();
        }

        System.out.println(lines);
        Utils.getAttrInBigClaster(lines, "D:\\Mobile.txt", "D:\\Mobile2.txt");*/


        //     Utils.listFilesForFolder(new File("/home/beka/Рабочий стол/BD/"));
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

        //Utils.getFreq2("/home/beka/Рабочий стол/mini2.txt","/home/beka/Рабочий стол/names2.txt");
        //    Utils.change("/home/beka/Рабочий стол/project/ValueKey.txt", "/home/beka/Рабочий стол/project/ValueKey2.txt");

        //Utils.aaa("/media/beka/6B0065904B67B69B/beta/value.txt", "/media/beka/6B0065904B67B69B/beta/keyValueBeta.txt");
        //  Utils.stripDuplicatesFromFile("/media/beka/6B0065904B67B69B/beta/keyValueBeta.txt","/media/beka/6B0065904B67B69B/beta/keyValueBeta2.txt");

        //Utils.count();


        /*HashMap<String, Integer> hm = new HashMap<>();
        Integer am;
        for (String i : Utils.readValue("D:\\Mobile.txt")) {


            am = hm.get(i);
            hm.put(i, am == null ? 1 : am + 1);
        }

        for (Object key : hm.keySet().toArray()) {

            if (hm.get(key) == 1) {

                hm.remove(key);
            }
        }

        for (Map.Entry<String, Integer> entry : hm.entrySet()) {
            String key = entry.getKey().trim();
            System.out.println(key + ":" + entry.getValue());
        }
        System.out.println(hm.size());*/

/*

        Utils.writeFile(Utils.getElements("attr.txt"),
                Utils.getElements("KeyAndValue.txt"),
                "mini.txt");
*/
        //  Utils.listFilesForFolder(new File("C:\\Users\\Admin\\IdeaProjects\\Ontology\\data\\"));


       /* ArrayList<String> list = (ArrayList<String>) Utils.readValue("D:\\term.txt");
        for (String s : list
                ) {
            System.out.println(s);
        }
        Utils.getAttrInBigClaster(list, "D:\\Mobile.txt", "D:\\Mobile2.txt");*/

        //Utils.stripDuplicatesFromFile("D:\\Mobile2.txt", "D:\\Mobile.txt");
        //Utils.stripDuplicatesFromFile("D:\\term.txt", "D:\\term2.txt");

    }
}