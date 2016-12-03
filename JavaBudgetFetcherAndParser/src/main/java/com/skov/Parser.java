package com.skov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: aogj
 * Date: 20-02-15
 * Time: 23:07
 * To change this template use File | Settings | File Templates.
 */
public class Parser {

    private static final String resourceDir = "C:\\projects\\theopenbudget\\JavaBudgetFetcherAndParser\\src\\main\\resources";
    public static String parseFile = resourceDir + "\\2016-12-03.txt";
    public static String outputFile = "C:\\projects\\theopenbudget\\app\\data\\budget.csv";
    public static boolean outputIsUdgifter = true;

    static int niveau1Count = 0;
    static int niveau2Count = 0;
    static int niveau3Count = 0;
    static int niveau4Count = 0;
    static int niveau5Count = 0;
    static int niveau5CountSuccess = 0;
    static int niveau5CountNotInteresting = 0;

    public static void main(String... args) throws Exception {
        log("welcome!");
        log("about to parse " + parseFile);
        ArrayList<String> outputList = doParse(parseFile);
        log("parsing done. I got " + outputList.size() + " lines");
        log("niveau1Count = " + niveau1Count);
        log("niveau2Count = " + niveau2Count);
        log("niveau3Count = " + niveau3Count);
        log("niveau4Count = " + niveau4Count);
        log("niveau5Count = " + niveau5Count);
        log("niveau5CountSuccess = " + niveau5CountSuccess);
        log("niveau5CountNotInteresting = " + niveau5CountNotInteresting);

        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");

        log("writing lines to file " + outputFile);
        int i = 0;
        for (String line : outputList) {
            //log(line);
            writer.println(line);
            i++;
        }
        log("wrote " + i + " lines to file");
        writer.close();
        log("bye");
    }

    public static ArrayList<String> doParse(String file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));

        //String headlines = "Portfolio,Department/Agency,Outcome,Program,Expense type,Appropriation type,Description,2013-14,2014-15,2015-16,2016-17,2017-18,Source document,Source table,URL";
        String headlines = "Portfolio,Department/Agency,Outcome,Program,Expense type,Appropriation type,Description,2015-16,2016-17,2017-18,2018-19,2019-20,Source document,Source table";
        ArrayList<String> outputList = new ArrayList<String>();
        outputList.add(headlines);

        try {
            String line = br.readLine();
            String niveau1 = "";
            String niveau2 = "";
            String niveau3 = "";
            String niveau4 = "";
            String niveau5 = "";


            while (line != null) {


                if (line.matches("[0-9](.*)")) {
                    niveau1 = line;
                    niveau1Count++;
                }

                if (line.matches("   [0-9](.*)")) {
                    niveau2 = line;
                    niveau2Count++;
                }

                if (line.matches("      [0-9](.*)")) {
                    niveau3 = line;
                    niveau3Count++;
                }

                if (line.matches("         [0-9](.*)")) {
                    niveau4 = line;
                    niveau4Count++;
                }

                if (line.matches("            [0-9](.*)")) {
                    niveau5 = line;
                    niveau5Count++;

                    //log("");
                    //log(niveau5);

                    int niveau5_0 = getBudgetValue(niveau5, 0, outputIsUdgifter);
                    int niveau5_1 = getBudgetValue(niveau5, 1, outputIsUdgifter);
                    int niveau5_2 = getBudgetValue(niveau5, 2, outputIsUdgifter);
                    int niveau5_3 = getBudgetValue(niveau5, 3, outputIsUdgifter);
                    int niveau5_4 = getBudgetValue(niveau5, 4, outputIsUdgifter);
                    String anm = getAnm(niveau5);
                    String parsedLine =
                        stripAwayLastHtml(niveau1) + "," +
                            stripAwayLastHtml(niveau2) + "," +
                            stripAwayLastHtml(niveau3) + "," +
                            stripAwayLastHtml(niveau4) + "," +
                            "Expense type" + "," +
                            "Appropriation type" + "," +
                            stripAwayLastHtml(niveau5) + "," +
                            niveau5_0 + "," +
                            niveau5_1 + "," +
                            niveau5_2 + "," +
                            niveau5_3 + "," +
                            niveau5_4 + "," +
                            //"<a href=\"javascript:visAnm('" + anm + "')\">Se anmærkninger" +
                            anm +
                            //",link," +
                            "";
                            //"Finansministeriets Rregnskabsdatabase" + "," +
                            //"link" + "," +
                            //"http://www.oes-cs.dk/olapdatabase/finanslov/index.cgi";


//"Dronningen","Statsydelse","Statsydelse","Statsydelse",Expense type,Appropriation type,"Almindelig virksomhed",77200,72300,79000,79000,79000,
// <a href="javascript:visAnm('153011101')">Se anmærkninger,link</a>,

//"Dronningen","Statsydelse","Statsydelse","Statsydelse",Expense type,Appropriation type,"Almindelig virksomhed",77200,72300,79000,79000,79000,
// Finansministeriets Rregnskabsdatabase,link,http://www.oes-cs.dk/olapdatabase/finanslov/index.cgi



                    //log(parsedLine);
                    if (niveau5_0 > 0 ||
                        niveau5_1 > 0 ||
                        niveau5_2 > 0 ||
                        niveau5_3 > 0 ||
                        niveau5_4 > 0) {

                        outputList.add(parsedLine);
                        niveau5CountSuccess++;
                    } else {
                        niveau5CountNotInteresting++;
                        //log("line not interesting... there is no positive numbers...: parsedLine=" + parsedLine);
                    }
                }

                line = br.readLine();
            }
        } finally {
            br.close();
        }

        return outputList;
    }

    private static String getAnm(String niveau5) {
        //            01110110 Almindelig virksomhed <A HREF="javascript:visAnm('153011101')">(Anm.)</A><TD>77,2<TD>72,3<TD>79,0<TD>79,0<TD>79,0<TD>79,0
        String result = "Ingen anmærkninger";
        try {
            int start = niveau5.indexOf("('") + 2;
            int end = niveau5.indexOf("')");
            String anm = niveau5.substring(start, end);

            result = "<a href=\"javascript:visAnm('" + anm + "')\">Se anmærkninger,link</a>,";

        } catch (Exception e) {
            System.out.println("exception getting anm... e=" + e);
        }
        return result;
    }

    static String stripAwayLastHtml(String str) {
        str = str.trim();
        int htmlStartIndexStart = str.indexOf(" ");
        int htmlStartIndexEnd = str.indexOf("<");
        str = str.substring(htmlStartIndexStart, htmlStartIndexEnd).trim();
        str = str.replaceAll("\"", "'");
        return "\"" + str + "\"";
    }

    /**
     * valueNumber is the period...
     *
     * @param str
     * @param valueNumber
     * @return
     */
    static int getBudgetValue(String str, int valueNumber, boolean useUdgifter) {

        for (int i = 0; i < valueNumber; i++) {
            int start = str.indexOf("<TD>") + 4;
            str = str.substring(start);
        }

        int start = str.indexOf("<TD>") + 4;
        str = str.substring(start);
        int end = str.indexOf("<TD>");
        str = str.substring(0, end);
        str = str.replace(".", "");
        str = str.replace(',', '.');
        //log(str);
        float value = Float.valueOf(str);
        value = value * 1000;
        int valueInt = Math.round(value);

        if (!useUdgifter) {
            valueInt = valueInt * -1; // byt om på udgifter så de bliver til indtægter...
        }

        if (valueInt < 0) {
            valueInt = 0; //set negative tal til 0
        }

        return valueInt;
    }

    public static void log(String txt) {
        System.out.println(txt);
    }

}
