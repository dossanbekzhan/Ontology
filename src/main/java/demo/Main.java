package demo;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.net.httpserver.HttpsServer;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
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

        ArrayList<String> list = (ArrayList<String>) readFile("/home/beka/IdeaProjects/Ontology/Data.txt");

        scrapWithOKHTTP(list);
       // scrap();
    }


    public static void scrapWithOKHTTP(List<String> phraseList) throws Exception {

        Writer writer1 = new FileWriter("/home/beka/IdeaProjects/Ontology/b.txt", true);

        // Authenticator.setDefault(new ProxyAuthenticator("RUS256954", "1834561", "188.68.1.149", "8080"));

        System.setProperty("http.proxyHost", "188.68.1.149");
        System.setProperty("http.proxyPort", "8085");

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
                Document document = Jsoup.parse(response.body().string());
                System.out.println(document);
                break;
            }
            writer1.flush();
            writer1.close();

        }


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


    public static void scrap() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("146.185.200.218", 1080));
        Request request = new Request.Builder()
                .get()
                .url("http://www.google.ru/search?q=болезнь+рак")
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setProxy(proxy);

        try {
            Response response = okHttpClient.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



