package demo;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class NlpDemo {
    public static void main(String[] args) throws IOException {
        Utils.replaceWord("/home/beka/Рабочий стол/lungs2.txt", "ЛЁГКИЕ", "легкие", "/home/beka/Рабочий стол/lungs.txt");
    }
}