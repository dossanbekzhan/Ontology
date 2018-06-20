/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.internal;

/**
 * Элемент именной группы
 */
public class NounPhraseItem extends com.pullenti.ner.MetaToken {

    public NounPhraseItem(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public boolean conjBefore;

    public java.util.ArrayList<NounPhraseItemTextVar> adjMorph = new java.util.ArrayList<>();

    public boolean canBeAdj;

    public java.util.ArrayList<NounPhraseItemTextVar> nounMorph = new java.util.ArrayList<>();

    public boolean canBeNoun;

    public boolean canBeSurname;

    public boolean isStdAdjective;

    public boolean isDoubtAdjective;

    /**
     * Это признак количественного (число, НЕСКОЛЬКО, МНОГО)
     */
    public boolean canBeNumericAdj() {
        if (getBeginToken() instanceof com.pullenti.ner.NumberToken) 
            return (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(getBeginToken(), com.pullenti.ner.NumberToken.class))).value > ((long)1);
        if ((getBeginToken().isValue("НЕСКОЛЬКО", null) || getBeginToken().isValue("МНОГО", null) || getBeginToken().isValue("ПАРА", null)) || getBeginToken().isValue("ПОЛТОРА", null)) 
            return true;
        return false;
    }


    public boolean isPronoun() {
        return getBeginToken().getMorph()._getClass().isPronoun();
    }


    public boolean isPersonalPronoun() {
        return getBeginToken().getMorph()._getClass().isPersonalPronoun();
    }


    /**
     * Это признак причастия
     */
    public boolean isVerb() {
        return getBeginToken().getMorph()._getClass().isVerb();
    }


    public boolean isAdverb() {
        return getBeginToken().getMorph()._getClass().isAdverb();
    }


    public boolean canBeAdjForPersonalPronoun() {
        if (isPronoun() && canBeAdj) {
            if (getBeginToken().isValue("ВСЕ", null) || getBeginToken().isValue("ВЕСЬ", null) || getBeginToken().isValue("САМ", null)) 
                return true;
        }
        return false;
    }


    private String _corrChars(String str, boolean keep) {
        if (!keep) 
            return str;
        if (chars.isAllLower()) 
            return str.toLowerCase();
        if (chars.isCapitalUpper()) 
            return com.pullenti.ner.core.MiscHelper.convertFirstCharUpperAndOtherLower(str);
        return str;
    }

    @Override
    public String getNormalCaseText(com.pullenti.morph.MorphClass mc, boolean singleNumber, com.pullenti.morph.MorphGender gender, boolean keepChars) {
        if ((getBeginToken() instanceof com.pullenti.ner.ReferentToken) && getBeginToken() == getEndToken()) 
            return getBeginToken().getNormalCaseText(mc, singleNumber, gender, keepChars);
        String res = null;
        int maxCoef = 0;
        int defCoef = -1;
        for(com.pullenti.morph.MorphBaseInfo it : getMorph().getItems()) {
            NounPhraseItemTextVar v = (NounPhraseItemTextVar)com.pullenti.n2j.Utils.cast(it, NounPhraseItemTextVar.class);
            if (v.undefCoef > 0 && (((v.undefCoef < maxCoef) || defCoef >= 0))) 
                continue;
            if (singleNumber && v.singleNumberValue != null) {
                if (mc != null && ((gender == com.pullenti.morph.MorphGender.NEUTER || gender == com.pullenti.morph.MorphGender.FEMINIE)) && mc.isAdjective()) {
                    com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new1444(new com.pullenti.morph.MorphClass(mc), gender, com.pullenti.morph.MorphNumber.SINGULAR, com.pullenti.morph.MorphCase.NOMINATIVE, getMorph().getLanguage());
                    String str = com.pullenti.morph.Morphology.getWordform(v.singleNumberValue, bi);
                    if (str != null) 
                        res = str;
                }
                else 
                    res = v.singleNumberValue;
                if (v.undefCoef == 0) 
                    break;
                maxCoef = v.undefCoef;
                continue;
            }
            if (com.pullenti.n2j.Utils.isNullOrEmpty(v.normalValue)) 
                continue;
            if (Character.isDigit(v.normalValue.charAt(0)) && mc != null && mc.isAdjective()) {
                int val;
                com.pullenti.n2j.Outargwrapper<Integer> inoutarg1445 = new com.pullenti.n2j.Outargwrapper<>();
                boolean inoutres1446 = com.pullenti.n2j.Utils.parseInteger(v.normalValue, inoutarg1445);
                val = (inoutarg1445.value != null ? inoutarg1445.value : 0);
                if (inoutres1446) {
                    String str = com.pullenti.ner.core.NumberHelper.getNumberAdjective(val, gender, (singleNumber ? com.pullenti.morph.MorphNumber.SINGULAR : com.pullenti.morph.MorphNumber.PLURAL));
                    if (str != null) {
                        res = str;
                        if (v.undefCoef == 0) 
                            break;
                        maxCoef = v.undefCoef;
                        continue;
                    }
                }
            }
            String res1 = (((NounPhraseItemTextVar)com.pullenti.n2j.Utils.cast(it, NounPhraseItemTextVar.class))).normalValue;
            if (singleNumber) {
                if (com.pullenti.n2j.Utils.stringsEq(res1, "ДЕТИ")) 
                    res1 = "РЕБЕНОК";
                else if (com.pullenti.n2j.Utils.stringsEq(res1, "ЛЮДИ")) 
                    res1 = "ЧЕЛОВЕК";
            }
            maxCoef = v.undefCoef;
            if (v.undefCoef > 0) {
                res = res1;
                continue;
            }
            int defCo = 0;
            if (mc != null && mc.isAdjective() && v.undefCoef == 0) {
            }
            else if (((getBeginToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.n2j.Utils.stringsEq(res1, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(getBeginToken(), com.pullenti.ner.TextToken.class))).term) && it.getCase().isNominative()) && it.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                defCo = 1;
            if (res == null || defCo > defCoef) {
                res = res1;
                defCoef = defCo;
                if (defCo > 0) 
                    break;
            }
        }
        if (res != null) 
            return _corrChars(res, keepChars);
        if (res == null && getBeginToken() == getEndToken()) 
            res = getBeginToken().getNormalCaseText(mc, singleNumber, gender, keepChars);
        return (String)com.pullenti.n2j.Utils.notnull(res, "?");
    }

    @Override
    public boolean isValue(String term, String term2) {
        if (getBeginToken() != null) 
            return getBeginToken().isValue(term, term2);
        else 
            return false;
    }

    public static NounPhraseItem tryParse(com.pullenti.ner.Token t, java.util.ArrayList<NounPhraseItem> items, com.pullenti.ner.core.NounPhraseParseAttr attrs) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        boolean _canBeSurname = false;
        boolean _isDoubtAdj = false;
        com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
        if (rt != null && rt.getBeginToken() == rt.getEndToken()) {
            NounPhraseItem res = tryParse(rt.getBeginToken(), items, attrs);
            if (res != null) {
                res.setBeginToken(res.setEndToken(t));
                return res;
            }
        }
        if (rt != null && items != null && items.size() > 0) {
            NounPhraseItem res = new NounPhraseItem(t, t);
            for(com.pullenti.morph.MorphBaseInfo m : t.getMorph().getItems()) {
                NounPhraseItemTextVar v = new NounPhraseItemTextVar(m, null);
                v.normalValue = t.getReferent().toString();
                res.nounMorph.add(v);
            }
            res.canBeNoun = true;
            return res;
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
        }
        boolean hasLegalVerb = false;
        if (t instanceof com.pullenti.ner.TextToken) {
            if (!t.chars.isLetter()) 
                return null;
            String str = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            if (str.charAt(str.length() - 1) == 'А' || str.charAt(str.length() - 1) == 'О') {
                for(com.pullenti.morph.MorphBaseInfo wf : t.getMorph().getItems()) {
                    if ((wf instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                        if (wf._getClass().isVerb()) {
                            com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                            if (!mc.isNoun() && (((attrs.value()) & (com.pullenti.ner.core.NounPhraseParseAttr.IGNOREPARTICIPLES.value()))) == (com.pullenti.ner.core.NounPhraseParseAttr.NO.value())) {
                                if (!com.pullenti.morph.LanguageHelper.endsWithEx(str, "ОГО", "ЕГО", null, null)) 
                                    return null;
                            }
                            hasLegalVerb = true;
                        }
                        if (wf._getClass().isAdverb()) {
                            if (t.getNext() == null || !t.getNext().isHiphen()) {
                                if ((com.pullenti.n2j.Utils.stringsEq(str, "ВСЕГО") || com.pullenti.n2j.Utils.stringsEq(str, "ДОМА") || com.pullenti.n2j.Utils.stringsEq(str, "НЕСКОЛЬКО")) || com.pullenti.n2j.Utils.stringsEq(str, "МНОГО") || com.pullenti.n2j.Utils.stringsEq(str, "ПОРЯДКА")) {
                                }
                                else 
                                    return null;
                            }
                        }
                        if (wf._getClass().isAdjective()) {
                            if (wf.containsAttr("к.ф.", new com.pullenti.morph.MorphClass())) {
                                if (com.pullenti.morph.MorphClass.ooEq(t.getMorphClassInDictionary(), com.pullenti.morph.MorphClass.ADJECTIVE)) {
                                }
                                else 
                                    _isDoubtAdj = true;
                            }
                        }
                    }
                }
            }
            com.pullenti.morph.MorphClass mc0 = t.getMorph()._getClass();
            if (mc0.isProperSurname() && !t.chars.isAllLower()) {
                for(com.pullenti.morph.MorphBaseInfo wf : t.getMorph().getItems()) {
                    if (wf._getClass().isProperSurname() && wf.getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                        com.pullenti.morph.MorphWordForm wff = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class);
                        if (wff == null) 
                            continue;
                        String s = (String)com.pullenti.n2j.Utils.notnull(((String)com.pullenti.n2j.Utils.notnull(wff.normalFull, wff.normalCase)), "");
                        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "ИН", "ЕН", "ЫН", null)) 
                            return null;
                        if (wff.isInDictionary() && com.pullenti.morph.LanguageHelper.endsWith(s, "ОВ")) 
                            _canBeSurname = true;
                    }
                }
            }
            if (mc0.isProperName() && !t.chars.isAllLower()) {
                for(com.pullenti.morph.MorphBaseInfo wff : t.getMorph().getItems()) {
                    com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                    if (wf == null) 
                        continue;
                    if (com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ГОР")) 
                        continue;
                    if (wf._getClass().isProperName() && wf.isInDictionary()) {
                        if (wf.normalCase == null || !wf.normalCase.startsWith("ЛЮБ")) {
                            if (mc0.isAdjective() && t.getMorph().containsAttr("неизм.", new com.pullenti.morph.MorphClass(null))) {
                            }
                            else {
                                if (items == null || (items.size() < 1)) 
                                    return null;
                                if (!items.get(0).isStdAdjective) 
                                    return null;
                            }
                        }
                    }
                }
            }
            if (mc0.isAdjective() && t.getMorph().getItemsCount() == 1) {
                if (t.getMorph().getIndexerItem(0).containsAttr("в.ср.ст.", new com.pullenti.morph.MorphClass())) 
                    return null;
            }
            com.pullenti.morph.MorphClass mc1 = t.getMorphClassInDictionary();
            if (com.pullenti.morph.MorphClass.ooEq(mc1, com.pullenti.morph.MorphClass.VERB)) 
                return null;
            if (((((attrs.value()) & (com.pullenti.ner.core.NounPhraseParseAttr.IGNOREPARTICIPLES.value()))) == (com.pullenti.ner.core.NounPhraseParseAttr.IGNOREPARTICIPLES.value()) && t.getMorph()._getClass().isVerb() && !t.getMorph()._getClass().isNoun()) && !t.getMorph()._getClass().isProper()) {
                for(com.pullenti.morph.MorphBaseInfo wf : t.getMorph().getItems()) {
                    if (wf._getClass().isVerb()) {
                        if (wf.containsAttr("дейст.з.", new com.pullenti.morph.MorphClass())) {
                            if (com.pullenti.morph.LanguageHelper.endsWith((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "СЯ")) {
                            }
                            else 
                                return null;
                        }
                    }
                }
            }
        }
        com.pullenti.ner.Token t1 = null;
        for(int k = 0; k < 2; k++) {
            t = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(t1, t0);
            if (k == 0) {
                if ((((t0 instanceof com.pullenti.ner.TextToken)) && t0.getNext() != null && t0.getNext().isHiphen()) && t0.getNext().getNext() != null) {
                    if (!t0.isWhitespaceAfter() && !t0.getMorph()._getClass().isPronoun()) {
                        if (!t0.getNext().isWhitespaceAfter()) 
                            t = t0.getNext().getNext();
                        else if (t0.getNext().getNext().chars.isAllLower() && com.pullenti.morph.LanguageHelper.endsWith((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.TextToken.class))).term, "О")) 
                            t = t0.getNext().getNext();
                    }
                }
            }
            NounPhraseItem it = _new1447(t0, t, _canBeSurname);
            boolean canBePrepos = false;
            for(com.pullenti.morph.MorphBaseInfo v : t.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(v, com.pullenti.morph.MorphWordForm.class);
                if (v._getClass().isPreposition()) 
                    canBePrepos = true;
                if (v._getClass().isAdjective() || ((v._getClass().isPronoun() && !v._getClass().isPersonalPronoun())) || ((v._getClass().isNoun() && (t instanceof com.pullenti.ner.NumberToken)))) {
                    if (tryAccordVariant(items, (items == null ? 0 : items.size()), v)) {
                        boolean isDoub = false;
                        if (v.containsAttr("к.ф.", new com.pullenti.morph.MorphClass())) 
                            continue;
                        if (v.containsAttr("собир.", new com.pullenti.morph.MorphClass()) && !((t instanceof com.pullenti.ner.NumberToken))) {
                            if (wf != null && wf.isInDictionary()) 
                                return null;
                            continue;
                        }
                        if (v.containsAttr("сравн.", new com.pullenti.morph.MorphClass())) 
                            continue;
                        boolean ok = true;
                        if (t instanceof com.pullenti.ner.TextToken) {
                            String s = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                            if (com.pullenti.n2j.Utils.stringsEq(s, "ПРАВО") || com.pullenti.n2j.Utils.stringsEq(s, "ПРАВА")) 
                                ok = false;
                            else if (com.pullenti.morph.LanguageHelper.endsWith(s, "ОВ") && t.getMorphClassInDictionary().isNoun()) 
                                ok = false;
                            else if (wf != null && ((com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "САМ") || com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ТО")))) 
                                ok = true;
                        }
                        else if (t instanceof com.pullenti.ner.NumberToken) {
                            if (v._getClass().isNoun() && t.getMorph()._getClass().isAdjective()) 
                                ok = false;
                            else if (t.getMorph()._getClass().isNoun() && (((attrs.value()) & (com.pullenti.ner.core.NounPhraseParseAttr.PARSENUMERICASADJECTIVE.value()))) == (com.pullenti.ner.core.NounPhraseParseAttr.NO.value())) 
                                ok = false;
                        }
                        if (ok) {
                            it.adjMorph.add(new NounPhraseItemTextVar(v, t));
                            it.canBeAdj = true;
                            if (_isDoubtAdj && t0 == t) 
                                it.isDoubtAdjective = true;
                            if (hasLegalVerb && wf != null && wf.isInDictionary()) 
                                it.canBeNoun = true;
                        }
                    }
                }
                boolean _canBeNoun = false;
                if (t instanceof com.pullenti.ner.NumberToken) {
                }
                else if (v._getClass().isNoun() || ((wf != null && com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "САМ")))) 
                    _canBeNoun = true;
                else if (v._getClass().isPersonalPronoun()) {
                    if (items == null || items.size() == 0) 
                        _canBeNoun = true;
                    else {
                        for(NounPhraseItem it1 : items) {
                            if (it1.isVerb()) 
                                return null;
                        }
                        if (items.size() == 1) {
                            if (items.get(0).canBeAdjForPersonalPronoun()) 
                                _canBeNoun = true;
                        }
                    }
                }
                else if ((v._getClass().isPronoun() && ((items == null || items.size() == 0 || ((items.size() == 1 && items.get(0).canBeAdjForPersonalPronoun())))) && wf != null) && ((((com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ТОТ") || com.pullenti.n2j.Utils.stringsEq(wf.normalFull, "ТО") || com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ТО")) || com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ЭТО") || com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ВСЕ")) || com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ЧТО") || com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "КТО")))) {
                    if (com.pullenti.n2j.Utils.stringsEq(wf.normalCase, "ВСЕ")) {
                        if (t.getNext() != null && t.getNext().isValue("РАВНО", null)) 
                            return null;
                    }
                    _canBeNoun = true;
                }
                else if (wf != null && com.pullenti.n2j.Utils.stringsEq(((String)com.pullenti.n2j.Utils.notnull(wf.normalFull, wf.normalCase)), "КОТОРЫЙ")) 
                    return null;
                else if (v._getClass().isProper() && (t instanceof com.pullenti.ner.TextToken)) {
                    if (t.getLengthChar() > 4) 
                        _canBeNoun = true;
                }
                if (_canBeNoun) {
                    if (tryAccordVariant(items, (items == null ? 0 : items.size()), v)) {
                        it.nounMorph.add(new NounPhraseItemTextVar(v, t));
                        it.canBeNoun = true;
                    }
                }
            }
            if (t0 != t) {
                for(NounPhraseItemTextVar v : it.adjMorph) {
                    v.correctPrefix((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.TextToken.class), false);
                }
                for(NounPhraseItemTextVar v : it.nounMorph) {
                    v.correctPrefix((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.TextToken.class), true);
                }
            }
            if (k == 1 && it.canBeNoun && !it.canBeAdj) {
                if (t1 != null) 
                    it.setEndToken(t1);
                else 
                    it.setEndToken(t0.getNext().getNext());
                for(NounPhraseItemTextVar v : it.nounMorph) {
                    if (v.normalValue != null && (v.normalValue.indexOf('-') < 0)) 
                        v.normalValue = v.normalValue + "-" + it.getEndToken().getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                }
            }
            if (it.canBeSurname && it.canBeAdj) 
                it.canBeAdj = false;
            if (it.canBeAdj) {
                if (m_StdAdjectives.tryParse(it.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null) 
                    it.isStdAdjective = true;
            }
            if (canBePrepos && it.canBeNoun) {
                if (items != null && items.size() > 0) {
                    com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSEPREPOSITION.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEVERBS.value())), 0);
                    if (npt1 != null && npt1.endChar > t.endChar) 
                        return null;
                }
                else {
                    com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(t.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.of((com.pullenti.ner.core.NounPhraseParseAttr.PARSEPRONOUNS.value()) | (com.pullenti.ner.core.NounPhraseParseAttr.PARSEVERBS.value())), 0);
                    if (npt1 != null) {
                        com.pullenti.morph.MorphCase mc = com.pullenti.morph.LanguageHelper.getCaseAfterPreposition((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).lemma);
                        if (!((com.pullenti.morph.MorphCase.ooBitand(mc, npt1.getMorph().getCase()))).isUndefined()) 
                            return null;
                    }
                }
            }
            if (it.canBeNoun || it.canBeAdj || k == 1) {
                if (it.getBeginToken().getMorph()._getClass().isPronoun()) {
                    com.pullenti.ner.Token tt2 = it.getEndToken().getNext();
                    if ((tt2 != null && tt2.isHiphen() && !tt2.isWhitespaceAfter()) && !tt2.isWhitespaceBefore()) 
                        tt2 = tt2.getNext();
                    if (tt2 instanceof com.pullenti.ner.TextToken) {
                        String ss = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt2, com.pullenti.ner.TextToken.class))).term;
                        if ((com.pullenti.n2j.Utils.stringsEq(ss, "ЖЕ") || com.pullenti.n2j.Utils.stringsEq(ss, "БЫ") || com.pullenti.n2j.Utils.stringsEq(ss, "ЛИ")) || com.pullenti.n2j.Utils.stringsEq(ss, "Ж")) 
                            it.setEndToken(tt2);
                        else if (com.pullenti.n2j.Utils.stringsEq(ss, "НИБУДЬ") || com.pullenti.n2j.Utils.stringsEq(ss, "ЛИБО") || (((com.pullenti.n2j.Utils.stringsEq(ss, "ТО") && tt2.getPrevious().isHiphen())) && it.canBeAdj)) {
                            it.setEndToken(tt2);
                            for(NounPhraseItemTextVar m : it.adjMorph) {
                                m.normalValue = m.normalValue + "-" + ss;
                                if (m.singleNumberValue != null) 
                                    m.singleNumberValue = m.singleNumberValue + "-" + ss;
                            }
                        }
                    }
                }
                return it;
            }
            if (t0 == t) {
                if (t0.isValue("БИЗНЕС", null) && t0.getNext() != null && com.pullenti.morph.CharsInfo.ooEq(t0.getNext().chars, t0.chars)) {
                    t1 = t0.getNext();
                    continue;
                }
                return it;
            }
        }
        return null;
    }

    public boolean tryAccordVar(com.pullenti.morph.MorphBaseInfo v) {
        for(NounPhraseItemTextVar vv : adjMorph) {
            if (vv.checkAccord(v, false)) 
                return true;
        }
        if (canBeNumericAdj()) {
            if (v.getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                return true;
            if (getBeginToken() instanceof com.pullenti.ner.NumberToken) {
                long num = (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(getBeginToken(), com.pullenti.ner.NumberToken.class))).value;
                long dig = num % ((long)10);
                if ((((dig == ((long)2) || dig == ((long)3) || dig == ((long)4))) && (num < ((long)10))) || num > ((long)20)) {
                    if (v.getCase().isGenitive()) 
                        return true;
                }
            }
            String term = null;
            if (v instanceof com.pullenti.morph.MorphWordForm) 
                term = (((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(v, com.pullenti.morph.MorphWordForm.class))).normalCase;
            if (v instanceof NounPhraseItemTextVar) 
                term = (((NounPhraseItemTextVar)com.pullenti.n2j.Utils.cast(v, NounPhraseItemTextVar.class))).normalValue;
            if (com.pullenti.n2j.Utils.stringsEq(term, "ЛЕТ") || com.pullenti.n2j.Utils.stringsEq(term, "ЧЕЛОВЕК")) 
                return true;
        }
        return false;
    }

    public static boolean tryAccordVariant(java.util.ArrayList<NounPhraseItem> items, int count, com.pullenti.morph.MorphBaseInfo v) {
        if (items == null || items.size() == 0) 
            return true;
        for(int i = 0; i < count; i++) {
            boolean ok = items.get(i).tryAccordVar(v);
            if (!ok) 
                return false;
        }
        return true;
    }

    public static boolean tryAccordAdjAndNoun(NounPhraseItem adj, NounPhraseItem noun) {
        for(NounPhraseItemTextVar v : adj.adjMorph) {
            for(NounPhraseItemTextVar vv : noun.nounMorph) {
                if (v.checkAccord(vv, false)) 
                    return true;
            }
        }
        return false;
    }

    private static com.pullenti.ner.core.TerminCollection m_StdAdjectives;

    public static NounPhraseItem _new1447(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, boolean _arg3) {
        NounPhraseItem res = new NounPhraseItem(_arg1, _arg2);
        res.canBeSurname = _arg3;
        return res;
    }
    public NounPhraseItem() {
        super();
    }
    static {
        try {
            m_StdAdjectives = new com.pullenti.ner.core.TerminCollection();
            for(String s : new String[] {"СЕВЕРНЫЙ", "ЮЖНЫЙ", "ЗАПАДНЫЙ", "ВОСТОЧНЫЙ"}) {
                m_StdAdjectives.add(new com.pullenti.ner.core.Termin(s, new com.pullenti.morph.MorphLang(null), false));
            }
        } catch(Exception ex1448) {
        }
    }
}
