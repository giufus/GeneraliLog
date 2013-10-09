package it.generali.test;

import java.io.*;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class MainRegex {

    public static void main(String[] args) throws IOException, URISyntaxException, ParseException {

        System.out.println("INSERT LOG PATH: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input = bufferedReader.readLine();


        //File file = new File(input);
        File file = new File("/Users/giufus/LAVORO/GENERALI/LOGS/log_appl_agw.log");
        FileInputStream fStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fStream));

        String strLine;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");


        List<String> logsPatterns = new ArrayList<String>();
        List<Long> dates = new ArrayList<Long>();
        List<String> logList = new ArrayList<String>();

        logsPatterns.add(".*STS Input START.*https://sapcc3bil");
        logsPatterns.add(".*STS Input END.*https://sapcc3bil");

        Pattern pOpen = Pattern.compile(logsPatterns.get(0));
        Pattern pClose = Pattern.compile(logsPatterns.get(1));

        int counter = 0;

        while ((strLine = br.readLine()) != null)   {
            if (pOpen.matcher(strLine).lookingAt()) {
                System.out.println(strLine);
                System.out.println(simpleDateFormat.parse(strLine.split(" INFO")[0]));
                System.out.println("*************************");
                dates.add(simpleDateFormat.parse(strLine.split(" INFO")[0]).getTime());
                logList.add(strLine);
                counter++;
            }

            if (pClose.matcher(strLine).lookingAt()) {
                System.out.println(strLine);
                System.out.println(simpleDateFormat.parse(strLine.split(" WARNING")[0]));
                System.out.println("*************************");
                dates.add(simpleDateFormat.parse(strLine.split(" WARNING")[0]).getTime());
                logList.add(strLine);
                counter++;
            }
        }



        br.close();

        System.out.println(dates.toString());

        if (dates.size()%2 != 0) {
            System.out.println("odd logs");
            dates.remove(dates.size()-1);
            System.out.println(dates.toString());
        }

        for (int i = dates.size()-1; i > 0; i=i-2) {
            System.out.println((dates.get(i) - dates.get(i-1)) + " MILLISECONDS BETWEEN JBOSS/STS for SAP WS CALLING...");
        }


    }
}
