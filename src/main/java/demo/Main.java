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
    private static final String GOOGLEURL = "http://www.google.com/search?q=";
    private static ArrayList<Proxy> proxies = new ArrayList<>();
    private static int count = 0;

    public static void main(String[] args) throws Exception {
        // proxies.add(new Proxy("kBNBFJ", "CGvRE5", "146.185.198.97", "8000"));//1
        //  proxies.add(new Proxy("", "", "", ""));
        // proxies.add(new Proxy("fFNsRg", "nn93Tp", "185.148.27.248", "8000"));//2
        // proxies.add(new Proxy("BFNv46", "bMV1HM", "185.233.200.93", "9030"));//5
        //  proxies.add(new Proxy("2d29GP", "wc5LGQ", "5.101.85.61", "8000"));//4
        //proxies.add(new Proxy("MQPF0E", "gP9Ppm", "91.243.54.47", "8000"));//3
        //   proxies.add(new Proxy("2UUCe6", "CuVzrt", "185.221.162.116", "9338"));//6


        ArrayList<String> list = (ArrayList<String>) readFile("/home/beka/IdeaProjects/Ontology/KeyAndValue(Genitive).txt");

        scrapWithOKHTTP(list);

    }


    public static void scrapWithOKHTTP(List<String> phraseList) throws Exception {

        Writer writer1 = new FileWriter("/home/beka/IdeaProjects/Ontology/bootstrapping.txt", true);

        try {

            for (int i = 0; i < proxies.size(); i++) {
                Authenticator.setDefault(new ProxyAuthenticator(proxies.get(i).getUsername(), proxies.get(i).getPass(), proxies.get(i).getHost(), proxies.get(i).getPort()));

                for (int j = count; j < phraseList.size(); j++) {
                    String[] tempphrase = phraseList.get(j).split(";");

                    String forCheck = tempphrase[0];
                    String object = tempphrase[1];

                    String tempURL = GOOGLEURL + "\"" + forCheck + "\"";

                    OkHttpClient client = new OkHttpClient();
                    client.setConnectTimeout(15, TimeUnit.SECONDS);
                    client.setReadTimeout(15, TimeUnit.SECONDS);

                    Request request = new Request.Builder()
                            .url(tempURL)
                            .build();

                    Response response = client.newCall(request).execute();

                    System.out.println(response.message());

                    System.out.println(tempURL + " ");
                    if (response.message().equals("OK")) {
                        Document document = Jsoup.parse(response.body().string());


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
                        if (result >= 1300) {
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
                        Thread.sleep(10000);
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
            phraseList.add(value + "+" + objectGenitive + ";" + object);
            line = reader.readLine();
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



