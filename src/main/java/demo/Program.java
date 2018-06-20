/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.

 * This class is generated using the convertor N2JP from Pullenti C#.NET project.

 * See www.pullenti.ru/downloadpage.aspx.

 *
 */

package demo;

import com.pullenti.morph.MorphClass;
import com.pullenti.morph.MorphLang;
import com.pullenti.ner.*;
import com.pullenti.ner.core.NounPhraseHelper;
import com.pullenti.ner.core.NounPhraseParseAttr;
import com.pullenti.ner.core.NounPhraseToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Program {


    //«симптом гайморита», «заложенность носа», «следствие осложнения», «следствие заболевания»
    //«синдром Лемьера» және «осложнение ангины

    public static void main(String[] args) throws Exception {
        ArrayList<String> strings = (ArrayList<String>) readFile("D:\\key.txt");


        for (String s : strings
                ) {
            Sdk.initialize(MorphLang.ooBitor(MorphLang.RU, MorphLang.EN));
            Processor proc = ProcessorService.createProcessor();
            AnalysisResult ar = proc.process(new SourceOfAnalysis(s), null, new MorphLang(MorphLang.RU));

            for (Token t = ar.firstToken; t != null; t = t.getNext()) {
                if (t instanceof TextToken && t.isLetters()) {

                    NounPhraseToken npt = NounPhraseHelper.tryParse(t, NounPhraseParseAttr.ADJECTIVECANBELAST, 0);
                    if (npt == null) continue;

                    /*t = npt.getEndToken();*/
                    String word1 = npt.noun.getNormalCaseText(MorphClass.NOUN, true, null, false).toLowerCase();

                    System.out.println(word1);
                }
            }
        }
    }


    public static List<String> readFile(String fileName) throws IOException {
        List<String> phraseList = new ArrayList<String>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        while ((line = reader.readLine()) != null) {
            phraseList.add(line);
        }

        System.out.println("Phrase list size: " + phraseList.size());

        return phraseList;
    }

}
