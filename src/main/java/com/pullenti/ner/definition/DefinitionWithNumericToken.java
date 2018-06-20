/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.definition;

/**
 * Для поддержки выделений тезисов с числовыми данными
 */
public class DefinitionWithNumericToken extends com.pullenti.ner.MetaToken {

    /**
     * Значение
     */
    public int number;

    /**
     * Начальная позиция числового значения в тексте
     */
    public int numberBeginChar;

    /**
     * Конечная позиция числового значения в тексте
     */
    public int numberEndChar;

    /**
     * Существительное (или именная группа) в единственном числе
     */
    public String noun;

    /**
     * Это же существительное во множественном числе и родительном падеже
     */
    public String nounsGenetive;

    /**
     * Подстрока из Text, содержащая числовое значение вместе с существительным 
     *  (чтобы потом при формировании вопроса можно было бы её заменить на что-угодно)
     */
    public String numberSubstring;

    /**
     * Текст тезиса
     */
    public String text;

    @Override
    public String toString() {
        return ((Integer)number).toString() + " " + ((String)com.pullenti.n2j.Utils.notnull(noun, "?")) + " (" + ((String)com.pullenti.n2j.Utils.notnull(nounsGenetive, "?")) + ")";
    }

    public DefinitionWithNumericToken(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        super(b, e, null);
    }

    /**
     * Выделить определение с указанного токена
     * @param t токен
     * @return 
     */
    public static DefinitionWithNumericToken tryParse(com.pullenti.ner.Token t) {
        if (!com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
            return null;
        com.pullenti.ner.Token tt = t;
        com.pullenti.ner.core.NounPhraseToken _noun = null;
        com.pullenti.ner.NumberToken num = null;
        for(; tt != null; tt = tt.getNext()) {
            if (tt != t && com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                return null;
            if (!((tt instanceof com.pullenti.ner.NumberToken))) 
                continue;
            if (tt.getWhitespacesAfterCount() > 2 || tt == t) 
                continue;
            if (tt.getMorph()._getClass().isAdjective()) 
                continue;
            com.pullenti.ner.core.NounPhraseToken nn = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (nn == null) 
                continue;
            num = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class);
            _noun = nn;
            break;
        }
        if (num == null) 
            return null;
        DefinitionWithNumericToken res = new DefinitionWithNumericToken(t, _noun.getEndToken());
        res.number = (int)num.value;
        res.numberBeginChar = num.beginChar;
        res.numberEndChar = num.endChar;
        res.noun = _noun.getNormalCaseText(new com.pullenti.morph.MorphClass(null), true, com.pullenti.morph.MorphGender.UNDEFINED, false);
        res.nounsGenetive = (String)com.pullenti.n2j.Utils.notnull(_noun.getMorphVariant(com.pullenti.morph.MorphCase.GENITIVE, true), (res != null ? res.noun : null));
        res.text = com.pullenti.ner.core.MiscHelper.getTextValue(t, num.getPrevious(), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        if (num.isWhitespaceBefore()) 
            res.text += " ";
        res.numberSubstring = com.pullenti.ner.core.MiscHelper.getTextValue(num, _noun.getEndToken(), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        res.text += res.numberSubstring;
        for(tt = _noun.getEndToken(); tt != null; tt = tt.getNext()) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt)) 
                break;
            res.setEndToken(tt);
        }
        if (res.getEndToken() != _noun.getEndToken()) {
            if (_noun.isWhitespaceAfter()) 
                res.text += " ";
            res.text += com.pullenti.ner.core.MiscHelper.getTextValue(_noun.getEndToken().getNext(), res.getEndToken(), com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.KEEPQUOTES.value()) | (com.pullenti.ner.core.GetTextAttr.KEEPREGISTER.value())));
        }
        return res;
    }
    public DefinitionWithNumericToken() {
        super();
    }
}
