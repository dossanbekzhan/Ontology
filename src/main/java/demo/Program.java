/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.

 * This class is generated using the convertor N2JP from Pullenti C#.NET project.

 * See www.pullenti.ru/downloadpage.aspx.

 *
 */

package demo;

import com.pullenti.morph.MorphBaseInfo;
import com.pullenti.morph.MorphCase;
import com.pullenti.morph.MorphClass;
import com.pullenti.morph.MorphLang;
import com.pullenti.ner.*;
import com.pullenti.ner.core.NounPhraseHelper;
import com.pullenti.ner.core.NounPhraseParseAttr;
import com.pullenti.ner.core.NounPhraseToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Program {


    public static void main(String[] args) throws Exception {

        for (String s : getSemObject("Обычно гайморит – это следствие осложнений после инфекционного заболевания, например, скарлатины, гриппа, простуды. Основные симптомы гайморита – затрудненное дыхание, постоянный насморк, заложенность носа и головная боль.Синдром Лемьера – редкое, но серьезное осложнение гнойной ангины, иногда ее называют постангинальным сепсисом")
                ) {
            System.out.println(s);
        }

    }
    public static void writeFile(List<String> phrase, String newFilename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFilename));

        for (String line : phrase) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static List<String> getSemObject(String text) throws Exception {

        List<String> semObjects = new ArrayList<>();

        Sdk.initialize(MorphLang.ooBitor(MorphLang.RU, MorphLang.EN));
        Processor proc = ProcessorService.createProcessor();
        AnalysisResult ar = proc.process(new SourceOfAnalysis(text), null, new MorphLang(MorphLang.RU));
        for (Token t = ar.firstToken; t != null; t = t.getNext()) {
            if (t instanceof TextToken && t.isLetters()) {

                NounPhraseToken npt = NounPhraseHelper.tryParse(t, NounPhraseParseAttr.ADJECTIVECANBELAST, 0);
                if (npt == null) continue;

                NounPhraseToken npt2 = NounPhraseHelper.tryParse(npt.getEndToken().getNext(), NounPhraseParseAttr.ADJECTIVECANBELAST, 0);

                if (npt2 == null) continue;

                if (!npt2.getMorph().getCase().isGenitive()) continue;

                MorphBaseInfo baseInfo = new MorphBaseInfo();
                MorphCase caseInfo = baseInfo.setCase(MorphCase.GENITIVE);
                MorphBaseInfo baseInfo2 = new MorphBaseInfo();
                baseInfo2.setCase(caseInfo);

                t = npt.getEndToken();
                String value = npt.noun.getNormalCaseText(MorphClass.NOUN, true, null, false).toLowerCase();
                String objectGenitive = npt2.noun.getSourceText().toLowerCase();
                String object = npt2.noun.getNormalCaseText(MorphClass.NOUN, true, null, false).toLowerCase();
                System.out.println(object + " " + value + " " + objectGenitive);
            }
        }


        return semObjects;
    }

    public static String readFile(String fileName) {
        String fullText = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fullText = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullText;
    }

}
