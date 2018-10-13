package demo;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String GOOGLEURL = "http://www.google.kz/search?q=";
    private static ArrayList<Proxy> proxies = new ArrayList<>();
    private static int count = 0;

    public static void main(String[] args) throws Exception {
        proxies.add(new Proxy("6TWQKa", "0trmcR", "91.215.85.146", "8000"));//1
        proxies.add(new Proxy("", "", "", ""));


        ArrayList<String> list = (ArrayList<String>) readFile("ForCheck.txt");
        scrapWithOKHTTP(list);

        //writeFile(list, "miniWithOutGenitive.txt");

    }


    public static void scrapWithOKHTTP(List<String> phraseList) throws Exception {

        Writer writer1 = new FileWriter("bootstrapping.txt", true);

        try {

            for (int i = 0; i < proxies.size(); i++) {
                Authenticator.setDefault(new ProxyAuthenticator(proxies.get(i).getUsername(),
                        proxies.get(i).getPass(),
                        proxies.get(i).getHost(),
                        proxies.get(i).getPort()));

                for (int j = count; j < phraseList.size(); j++) {
                    String[] tempphrase = phraseList.get(j).split(";");

                    String forCheck = tempphrase[0];
                    String object = tempphrase[1];

                    String tempURL = GOOGLEURL + "\"" + forCheck + "\"" + "&hl=RU&cr=countryRU&lr=lang_ru";

                    OkHttpClient client = new OkHttpClient();

                    client.setConnectTimeout(15, TimeUnit.SECONDS);
                    client.setReadTimeout(15, TimeUnit.SECONDS);

                    Request request = new Request.Builder()
                            .url(tempURL)
                            .header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; fr-FR) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27")
                            .build();

                    Response response = client.newCall(request).execute();

                    System.out.println(response.message());

                    System.out.println(tempURL + " ");
                    if (response.message().equals("OK")) {
                        Document document = Jsoup.parse(response.body().string());
                        Utils.writeFile("html\\" + forCheck + ".html", document.toString());


                        Element divResultStats = document.select("div#resultStats").first();

                        Element divNotNull = document.select("div#topstuff").select("div.e").first();

                        if (divNotNull != null) {
                            System.out.println(divNotNull.text());
                            continue;
                        }

                        if (divResultStats == null) {
                            throw new RuntimeException("Unable to find results stats.");
                        }
                        Integer result = toInteger(divResultStats.text());
                        String[] split = forCheck.split("\\+");
                        System.out.println(result);
                        StringBuilder builder = new StringBuilder();
                        if (result >= 1000) {
                            builder.append(object)
                                    .append(";")
                                    .append(split[0])
                                    .append(";")
                                    .append(split[1])
                                    .append("\n");
                            writer1.write(builder.toString());
                            System.out.println("\n" + builder.toString() + "  = " + result);
                        }
                    } else {
                        System.out.println("____________next proxy______________");
                        Document document = Jsoup.parse(response.body().string());
                        //System.out.println(document);
                        count = j;
                        System.out.println("count :" + count + " proxy id:" + i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            writer1.flush();
            writer1.close();
        } finally {
            writer1.close();
        }
    }

    public static List<String> readFile(String fileName) throws IOException {
        List<String> phraseList = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while ((line) != null) {
            String[] split = line.split(";");
            String value = split[1];
            String object = split[0];
            String objectGenitive = split[2];


            phraseList.add(value + "+" + objectGenitive.trim() + ";" + object);
            //adding
            //phraseList.add(object + ";" + value);

            line = reader.readLine();
        }

        System.out.println("Phrase list size: " + phraseList.size());

        return phraseList;
    }

    public static void writeFile(List<String> phrase, String newFilename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFilename));

        for (String line : phrase) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }


    public static int toInteger(String text) {
        if (text == null || text == "") {
            return 0;
        }
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(text);
        String s = "";
        while (m.find()) {
            s += m.group();
        }
        if (s == null || s == "") {
            return 0;
        }
        int number = Integer.parseInt(s);
        return number;

    }


}



