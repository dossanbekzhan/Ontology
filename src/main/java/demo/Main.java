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
    private static final String GOOGLEURL = "http://www.google.ru/search?q=";

    public static void main(String[] args) throws Exception {

        ArrayList<String> list = (ArrayList<String>) readFile("/home/beka/IdeaProjects/Ontology/c.txt");

        scrapWithOKHTTP(list);

    }


    public static void scrapWithOKHTTP(List<String> phraseList) throws Exception {
        List<ProxyAuthenticator> authUsers = new ArrayList<>();
        authUsers.add(new ProxyAuthenticator("P28Lso", "paFWBP", "91.243.52.211", "8000"));
        authUsers.add(new ProxyAuthenticator("MamY0H", "kSxe70", "146.185.198.67", "8000"));
        authUsers.add(new ProxyAuthenticator("P28Lso", "paFWBP", "91.243.55.125", "8000"));

        Writer writer1 = new FileWriter("/home/beka/IdeaProjects/Ontology/b.txt", true);


        Authenticator.setDefault(new ProxyAuthenticator("P28Lso", "paFWBP", "91.243.55.125", "8000"));

        for (String phrase : phraseList) {
            String tempURL = GOOGLEURL + "\"" + phrase + "\"";

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);

            Request request = new Request.Builder()
                    .url(tempURL)
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println(response.message());

            if (response.message().equals("OK")) {
                Document document = Jsoup.parse(response.body().string());
                System.out.println(tempURL + " ");

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
                String[] split = phrase.split("\\+");
                System.out.println(result);
                StringBuilder builder = new StringBuilder();
                if (result > 4400) {
                    builder.append(split[0])
                            .append(";")
                            .append(split[1])
                            .append("\n");
                    writer1.write(builder.toString());
                    System.out.println("\n" + builder.toString() + "  = " + result);

                }
            } else {
                System.out.println("--------------------------------------");
                System.out.println("switched to next proxy");
                break;
            }
        }


        writer1.flush();
        writer1.close();
    }

    public static List<String> readFile(String fileName) throws IOException {
        List<String> phraseList = new ArrayList<String>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(";");
            if (Integer.parseInt(values[2]) == 0) {
                String value = values[1];
                phraseList.add(values[0] + "+" + value);
            }
        }

        System.out.println("Phrase list size: " + phraseList.size());

        return phraseList;
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



