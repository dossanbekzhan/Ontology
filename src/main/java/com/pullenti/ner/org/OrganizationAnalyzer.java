/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.org;

public class OrganizationAnalyzer extends com.pullenti.ner.Analyzer {

    private com.pullenti.ner.ReferentToken tryAttachPoliticParty(com.pullenti.ner.Token t, OrgAnalyzerData ad, boolean onlyAbbrs) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        com.pullenti.ner.core.TerminToken nameTok = null;
        com.pullenti.ner.Token root = null;
        java.util.ArrayList<com.pullenti.ner.core.TerminToken> prevToks = null;
        int prevWords = 0;
        com.pullenti.ner.ReferentToken _geo = null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        double coef = (double)0;
        int wordsAfter = 0;
        boolean isFraction = false;
        boolean isPolitic = false;
        for(; t != null; t = t.getNext()) {
            if (t != t0 && t.isNewlineBefore()) 
                break;
            if (onlyAbbrs) 
                break;
            if (t.isHiphen()) {
                if (prevToks == null) 
                    return null;
                continue;
            }
            com.pullenti.ner.core.TerminToken tokN = m_PoliticNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tokN != null) {
                if (!t.chars.isAllLower()) 
                    break;
                t1 = tokN.getEndToken();
            }
            com.pullenti.ner.core.TerminToken tok = m_PoliticPrefs.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null) {
                if (t.getMorph()._getClass().isAdjective()) {
                    com.pullenti.ner.ReferentToken rt = t.kit.processReferent("GEO", t);
                    if (rt != null) {
                        _geo = rt;
                        t1 = (t = rt.getEndToken());
                        coef += 0.5;
                        continue;
                    }
                }
                if (t.endChar < t1.endChar) 
                    continue;
                break;
            }
            if (tok.termin.tag != null && tok.termin.tag2 != null) {
                if (t.endChar < t1.endChar) 
                    continue;
                break;
            }
            if (tok.termin.tag == null && tok.termin.tag2 == null) 
                isPolitic = true;
            if (prevToks == null) 
                prevToks = new java.util.ArrayList<>();
            prevToks.add(tok);
            if (tok.termin.tag == null) {
                coef += ((double)1);
                prevWords++;
            }
            else if (tok.getMorph()._getClass().isAdjective()) 
                coef += 0.5;
            t = tok.getEndToken();
            if (t.endChar > t1.endChar) 
                t1 = t;
        }
        if (t == null) 
            return null;
        if (t.isValue("ПАРТИЯ", null) || t.isValue("ФРОНТ", null) || t.isValue("ГРУППИРОВКА", null)) {
            if (!t.isValue("ПАРТИЯ", null)) 
                isPolitic = true;
            root = t;
            coef += 0.5;
            if (t.chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                coef += 0.5;
            t1 = t;
            t = t.getNext();
        }
        else if (t.isValue("ФРАКЦИЯ", null)) {
            root = (t1 = t);
            isFraction = true;
            if (t.getNext() != null && (t.getNext().getReferent() instanceof OrganizationReferent)) 
                coef += ((double)2);
            else 
                return null;
        }
        com.pullenti.ner.core.BracketSequenceToken br = null;
        if ((((nameTok = m_PoliticNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO)))) != null) {
            coef += 0.5;
            isPolitic = true;
            if (!t.chars.isAllLower()) 
                coef += 0.5;
            if (nameTok.getLengthChar() > 10) 
                coef += 0.5;
            else if (t.chars.isAllUpper()) 
                coef += 0.5;
            t1 = nameTok.getEndToken();
            t = t1.getNext();
        }
        else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false) && (((br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 10)))) != null) {
            if ((((nameTok = m_PoliticNames.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO)))) != null) 
                coef += 1.5;
            else if (onlyAbbrs) 
                return null;
            else if (t.getNext() != null && t.getNext().isValue("О", null)) 
                return null;
            else 
                for(com.pullenti.ner.Token tt = t.getNext(); tt != null && tt.endChar <= br.endChar; tt = tt.getNext()) {
                    com.pullenti.ner.core.TerminToken tok2 = m_PoliticPrefs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok2 != null && tok2.termin.tag == null) {
                        if (tok2.termin.tag2 == null) 
                            isPolitic = true;
                        coef += 0.5;
                        wordsAfter++;
                    }
                    else if (m_PoliticSuffs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                        coef += 0.5;
                        wordsAfter++;
                    }
                    else if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        coef += 0.5;
                    else if (tt instanceof com.pullenti.ner.ReferentToken) {
                        coef = (double)0;
                        break;
                    }
                    else {
                        com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                        if ((com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.VERB) || com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.ADVERB) || mc.isPronoun()) || mc.isPersonalPronoun()) {
                            coef = (double)0;
                            break;
                        }
                        if (mc.isNoun() || mc.isUndefined()) 
                            coef -= 0.5;
                    }
                }
            t1 = br.getEndToken();
            t = t1.getNext();
        }
        else if (onlyAbbrs) 
            return null;
        else if (root != null) {
            for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                    break;
                if (tt.getWhitespacesBeforeCount() > 2) 
                    break;
                if (tt.getMorph()._getClass().isPreposition()) {
                    if (tt != root.getNext()) 
                        break;
                    continue;
                }
                if (tt.isAnd()) {
                    com.pullenti.ner.core.NounPhraseToken npt2 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0);
                    if (npt2 != null && m_PoliticSuffs.tryParse(npt2.getEndToken(), com.pullenti.ner.core.TerminParseAttr.NO) != null && com.pullenti.morph.CharsInfo.ooEq(npt2.getEndToken().chars, tt.getPrevious().chars)) 
                        continue;
                    break;
                }
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0);
                if (npt == null) 
                    break;
                if (npt.noun.isValue("ПАРТИЯ", null) || npt.noun.isValue("ФРОНТ", null)) 
                    break;
                double co = (double)0;
                for(com.pullenti.ner.Token ttt = tt; ttt != null && ttt.endChar <= npt.endChar; ttt = ttt.getNext()) {
                    com.pullenti.ner.core.TerminToken tok2 = m_PoliticPrefs.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok2 != null && tok2.termin.tag == null) {
                        if (tok2.termin.tag2 == null) 
                            isPolitic = true;
                        co += 0.5;
                        wordsAfter++;
                    }
                    else if (m_PoliticSuffs.tryParse(ttt, com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                        co += 0.5;
                        wordsAfter++;
                    }
                    else if (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        co += 0.5;
                }
                if (co == 0) {
                    if (!npt.getMorph().getCase().isGenitive()) 
                        break;
                    com.pullenti.ner.core.TerminToken lastSuf = m_PoliticSuffs.tryParse(tt.getPrevious(), com.pullenti.ner.core.TerminParseAttr.NO);
                    if (((wordsAfter > 0 && com.pullenti.morph.CharsInfo.ooEq(npt.getEndToken().chars, tt.getPrevious().chars))) || ((lastSuf != null && lastSuf.termin.tag != null)) || ((tt.getPrevious() == root && npt.getEndToken().chars.isAllLower() && npt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) && root.chars.isCapitalUpper())) {
                        com.pullenti.ner.ReferentToken pp = tt.kit.processReferent("PERSON", tt);
                        if (pp != null) 
                            break;
                        wordsAfter++;
                    }
                    else 
                        break;
                }
                t1 = (tt = npt.getEndToken());
                t = t1.getNext();
                coef += co;
            }
        }
        if (t != null && (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && (t.getWhitespacesBeforeCount() < 3)) {
            t1 = t;
            coef += 0.5;
        }
        for(com.pullenti.ner.Token tt = t0.getPrevious(); tt != null; tt = tt.getPrevious()) {
            if (!((tt instanceof com.pullenti.ner.TextToken))) {
                OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org1 != null && org1.containsProfile(OrgProfile.POLICY)) 
                    coef += 0.5;
                continue;
            }
            if (!tt.chars.isLetter()) 
                continue;
            if (tt.getMorph()._getClass().isPreposition() || tt.getMorph()._getClass().isConjunction()) 
                continue;
            if (m_PoliticPrefs.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                coef += 0.5;
                if (tt.isValue("ФРАКЦИЯ", null)) 
                    coef += 0.5;
            }
            else 
                break;
        }
        if (coef < 1) 
            return null;{
                if (root == null) {
                    if (nameTok == null && br == null) 
                        return null;
                }
                else if ((nameTok == null && wordsAfter == 0 && br == null) && !isFraction) {
                    if ((coef < 2) || prevWords == 0) 
                        return null;
                }
            }
        OrganizationReferent _org = new OrganizationReferent();
        if (br != null && nameTok != null && (nameTok.endChar < br.getEndToken().getPrevious().endChar)) 
            nameTok = null;
        if (nameTok != null) 
            isPolitic = true;
        if (isFraction) {
            _org.addProfile(OrgProfile.POLICY);
            _org.addProfile(OrgProfile.UNIT);
        }
        else if (isPolitic) {
            _org.addProfile(OrgProfile.POLICY);
            _org.addProfile(OrgProfile.UNION);
        }
        else 
            _org.addProfile(OrgProfile.UNION);
        if (nameTok != null) {
            isPolitic = true;
            _org.addName(nameTok.termin.getCanonicText(), true, null);
            if (nameTok.termin.additionalVars != null) {
                for(com.pullenti.ner.core.Termin v : nameTok.termin.additionalVars) {
                    _org.addName(v.getCanonicText(), true, null);
                }
            }
            if (nameTok.termin.acronym != null) {
                com.pullenti.ner.geo.GeoReferent geo1 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(nameTok.termin.tag, com.pullenti.ner.geo.GeoReferent.class);
                if (geo1 == null) 
                    _org.addName(nameTok.termin.acronym, true, null);
                else if (_geo != null) {
                    if (geo1.canBeEquals(_geo.referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                        _org.addName(nameTok.termin.acronym, true, null);
                }
                else if (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                    if (geo1.canBeEquals(t1.getReferent(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                        _org.addName(nameTok.termin.acronym, true, null);
                }
                else if (nameTok.getBeginToken() == nameTok.getEndToken() && nameTok.getBeginToken().isValue(nameTok.termin.acronym, null)) {
                    _org.addName(nameTok.termin.acronym, true, null);
                    com.pullenti.ner.ReferentToken rtg = new com.pullenti.ner.ReferentToken(geo1.clone(), nameTok.getBeginToken(), nameTok.getEndToken(), null);
                    rtg.setDefaultLocalOnto(t0.kit.processor);
                    _org.addGeoObject(rtg);
                }
            }
        }
        else if (br != null) {
            String nam = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
            _org.addName(nam, true, null);
            if (root == null) {
                String nam2 = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                if (com.pullenti.n2j.Utils.stringsNe(nam2, nam)) 
                    _org.addName(nam, true, null);
            }
        }
        if (root != null) {
            com.pullenti.ner.Token typ1 = root;
            if (_geo != null) 
                typ1 = _geo.getBeginToken();
            if (prevToks != null) {
                for(com.pullenti.ner.core.TerminToken p : prevToks) {
                    if (p.termin.tag == null) {
                        if (p.beginChar < typ1.beginChar) 
                            typ1 = p.getBeginToken();
                        break;
                    }
                }
            }
            String typ = com.pullenti.ner.core.MiscHelper.getTextValue(typ1, root, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
            if (typ != null) {
                if (br == null) {
                    String nam = null;
                    com.pullenti.ner.Token t2 = t1;
                    if (t2.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                        t2 = t2.getPrevious();
                    if (t2.endChar > root.endChar) {
                        nam = typ + " " + com.pullenti.ner.core.MiscHelper.getTextValue(root.getNext(), t2, com.pullenti.ner.core.GetTextAttr.NO);
                        _org.addName(nam, true, null);
                    }
                }
                if (_org.getNames().size() == 0 && typ1 != root) 
                    _org.addName(typ, true, null);
                else 
                    _org.addTypeStr(typ.toLowerCase());
            }
            if (isFraction && (t1.getNext() instanceof com.pullenti.ner.ReferentToken)) {
                _org.addTypeStr("фракция");
                t1 = t1.getNext();
                _org.setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(t1.getReferent(), OrganizationReferent.class));
                if (t1.getNext() != null && t1.getNext().isValue("В", null) && (t1.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) {
                    OrganizationReferent oo = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t1.getNext().getNext().getReferent(), OrganizationReferent.class);
                    if (oo != null && oo.getKind() == OrganizationKind.GOVENMENT) {
                        t1 = t1.getNext().getNext();
                        _org.addSlot(OrganizationReferent.ATTR_MISC, oo, false, 0);
                    }
                    else if (t1.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                        t1 = t1.getNext().getNext();
                        _org.addSlot(OrganizationReferent.ATTR_MISC, t1.getReferent(), false, 0);
                    }
                }
            }
        }
        if (_geo != null) 
            _org.addGeoObject(_geo);
        else if (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
            _org.addGeoObject(t1.getReferent());
        return new com.pullenti.ner.ReferentToken(_org, t0, t1, null);
    }

    private static void _initPolitic() {
        m_PoliticPrefs = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"либеральный", "либерал", "лейбористский", "демократический", "коммунистрический", "большевистский", "социальный", "социал", "национал", "националистическая", "свободный", "радикальный", "леворадикальный", "радикал", "революционная", "левый", "правый", "социалистический", "рабочий", "трудовой", "республиканский", "народный", "аграрный", "монархический", "анархический", "прогрессивый", "прогрессистский", "консервативный", "гражданский", "фашистский", "марксистский", "ленинский", "маоистский", "имперский", "славянский", "анархический", "баскский", "конституционный", "пиратский", "патриотический", "русский"}) {
            m_PoliticPrefs.add(new com.pullenti.ner.core.Termin(s.toUpperCase(), new com.pullenti.morph.MorphLang(null), false));
        }
        for(String s : new String[] {"объединенный", "всероссийский", "общероссийский", "христианский", "независимый", "альтернативный"}) {
            m_PoliticPrefs.add(com.pullenti.ner.core.Termin._new2134(s.toUpperCase(), s));
        }
        for(String s : new String[] {"политический", "правящий", "оппозиционный", "запрешенный", "террористический", "запрещенный", "экстремистский"}) {
            m_PoliticPrefs.add(com.pullenti.ner.core.Termin._new118(s.toUpperCase(), s));
        }
        for(String s : new String[] {"активист", "член", "руководство", "лидер", "глава", "демонстрация", "фракция", "съезд", "пленум", "террорист", "парламент", "депутат", "парламентарий", "оппозиция", "дума", "рада"}) {
            m_PoliticPrefs.add(com.pullenti.ner.core.Termin._new120(s.toUpperCase(), s, s));
        }
        m_PoliticSuffs = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"коммунист", "социалист", "либерал", "республиканец", "националист", "радикал", "лейборист", "анархист", "патриот", "консерватор", "левый", "правый", "новый", "зеленые", "демократ", "фашист", "защитник", "труд", "равенство", "прогресс", "жизнь", "мир", "родина", "отечество", "отчизна", "республика", "революция", "революционер", "народовластие", "фронт", "сила", "платформа", "воля", "справедливость", "преображение", "преобразование", "солидарность", "управление", "демократия", "народ", "гражданин", "предприниматель", "предпринимательство", "бизнес", "пенсионер", "христианин"}) {
            m_PoliticSuffs.add(new com.pullenti.ner.core.Termin(s.toUpperCase(), new com.pullenti.morph.MorphLang(null), false));
        }
        for(String s : new String[] {"реформа", "свобода", "единство", "развитие", "освобождение", "любитель", "поддержка", "возрождение", "независимость"}) {
            m_PoliticSuffs.add(com.pullenti.ner.core.Termin._new118(s.toUpperCase(), s));
        }
        m_PoliticNames = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"Республиканская партия", "Демократическая партия;Демпартия", "Христианско демократический союз;ХДС", "Свободная демократическая партия;СвДП", "ЯБЛОКО", "ПАРНАС", "ПАМЯТЬ", "Движение против нелегальной иммиграции;ДПНИ", "НАЦИОНАЛ БОЛЬШЕВИСТСКАЯ ПАРТИЯ;НБП", "НАЦИОНАЛЬНЫЙ ФРОНТ;НАЦФРОНТ", "Национальный патриотический фронт;НПФ", "Батькивщина;Батькiвщина", "НАРОДНАЯ САМООБОРОНА", "Гражданская платформа", "Народная воля", "Славянский союз", "ПРАВЫЙ СЕКТОР", "ПЕГИДА;PEGIDA", "Венгерский гражданский союз;ФИДЕС", "БЛОК ЮЛИИ ТИМОШЕНКО;БЮТ", "Аль Каида;Аль Каеда;Аль Кайда;Al Qaeda;Al Qaida", "Талибан;движение талибан", "Бригады мученников Аль Аксы", "Хезболла;Хезбалла;Хизбалла", "Народный фронт освобождения палестины;НФОП", "Организация освобождения палестины;ООП", "Союз исламского джихада;Исламский джихад", "Аль-Джихад;Египетский исламский джихад", "Братья-мусульмане;Аль Ихван альМуслимун", "ХАМАС", "Движение за освобождение Палестины;ФАТХ", "Фронт Аль Нусра;Аль Нусра", "Джабхат ан Нусра"}) {
            String[] pp = com.pullenti.n2j.Utils.split(s.toUpperCase(), String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new118(pp[0], OrgProfile.POLICY);
            for(int i = 0; i < pp.length; i++) {
                if ((pp[i].length() < 5) && t.acronym == null) {
                    t.acronym = pp[i];
                    if (t.acronym.endsWith("Р") || t.acronym.endsWith("РФ")) 
                        t.tag = com.pullenti.ner.geo.internal.MiscLocationHelper.getGeoReferentByName("RU");
                    else if (t.acronym.endsWith("У")) 
                        t.tag = com.pullenti.ner.geo.internal.MiscLocationHelper.getGeoReferentByName("UA");
                    else if (t.acronym.endsWith("СС")) 
                        t.tag = com.pullenti.ner.geo.internal.MiscLocationHelper.getGeoReferentByName("СССР");
                }
                else 
                    t.addVariant(pp[i], false);
            }
            m_PoliticNames.add(t);
        }
    }

    private static com.pullenti.ner.core.TerminCollection m_PoliticPrefs;

    private static com.pullenti.ner.core.TerminCollection m_PoliticSuffs;

    private static com.pullenti.ner.core.TerminCollection m_PoliticNames;

    private com.pullenti.ner.ReferentToken attachGlobalOrg(com.pullenti.ner.Token t, AttachType attachTyp, com.pullenti.ner.core.AnalyzerData ad, Object extGeo) {
        if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLatinLetter()) {
            if (com.pullenti.ner.core.MiscHelper.isEngArticle(t)) {
                com.pullenti.ner.ReferentToken res11 = attachGlobalOrg(t.getNext(), attachTyp, ad, extGeo);
                if (res11 != null) {
                    res11.setBeginToken(t);
                    return res11;
                }
            }
        }
        com.pullenti.ner.ReferentToken rt00 = tryAttachPoliticParty(t, (OrgAnalyzerData)com.pullenti.n2j.Utils.cast(ad, OrgAnalyzerData.class), true);
        if (rt00 != null) 
            return rt00;
        if (!((t instanceof com.pullenti.ner.TextToken))) {
            if (t != null && t.getReferent() != null && com.pullenti.n2j.Utils.stringsEq(t.getReferent().getTypeName(), "URI")) {
                com.pullenti.ner.ReferentToken rt = attachGlobalOrg((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).getBeginToken(), attachTyp, ad, null);
                if (rt != null && rt.endChar == t.endChar) {
                    rt.setBeginToken(rt.setEndToken(t));
                    return rt;
                }
            }
            return null;
        }
        String term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "ВС")) {
            if (t.getPrevious() != null) {
                if (t.getPrevious().isValue("ПРЕЗИДИУМ", null) || t.getPrevious().isValue("ПЛЕНУМ", null) || t.getPrevious().isValue("СЕССИЯ", null)) {
                    OrganizationReferent org00 = new OrganizationReferent();
                    org00.addName("ВЕРХОВНЫЙ СОВЕТ", true, null);
                    org00.addName("ВС", true, null);
                    org00.addTypeStr("совет");
                    org00.addProfile(OrgProfile.STATE);
                    com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), null, false, AttachType.NORMAL, true);
                    return new com.pullenti.ner.ReferentToken(org00, t, (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(te, t), null);
                }
            }
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                boolean isVc = false;
                if (t.getPrevious() != null && (t.getPrevious().getReferent() instanceof OrganizationReferent) && (((OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getPrevious().getReferent(), OrganizationReferent.class))).getKind() == OrganizationKind.MILITARY) 
                    isVc = true;
                else if (ad != null) {
                    for(com.pullenti.ner.Referent r : ad.getReferents()) {
                        if (r.findSlot(OrganizationReferent.ATTR_NAME, "ВООРУЖЕННЫЕ СИЛЫ", true) != null) {
                            isVc = true;
                            break;
                        }
                    }
                }
                if (isVc) {
                    OrganizationReferent org00 = new OrganizationReferent();
                    org00.addName("ВООРУЖЕННЫЕ СИЛЫ", true, null);
                    org00.addName("ВС", true, null);
                    org00.addTypeStr("армия");
                    org00.addProfile(OrgProfile.ARMY);
                    com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), null, false, AttachType.NORMAL, true);
                    return new com.pullenti.ner.ReferentToken(org00, t, (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(te, t), null);
                }
            }
        }
        if ((t.chars.isAllUpper() && ((com.pullenti.n2j.Utils.stringsEq(term, "АН") || com.pullenti.n2j.Utils.stringsEq(term, "ВАС"))) && t.getNext() != null) && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
            OrganizationReferent org00 = new OrganizationReferent();
            if (com.pullenti.n2j.Utils.stringsEq(term, "АН")) {
                org00.addName("АКАДЕМИЯ НАУК", true, null);
                org00.addTypeStr("академия");
                org00.addProfile(OrgProfile.SCIENCE);
            }
            else {
                org00.addName("ВЫСШИЙ АРБИТРАЖНЫЙ СУД", true, null);
                org00.addName("ВАС", true, null);
                org00.addTypeStr("суд");
                org00.addProfile(OrgProfile.JUSTICE);
            }
            com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), null, false, AttachType.NORMAL, true);
            return new com.pullenti.ner.ReferentToken(org00, t, (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(te, t), null);
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "ГД") && t.getPrevious() != null) {
            com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSONPROPERTY", t.getPrevious());
            if (rt != null && rt.referent != null && com.pullenti.n2j.Utils.stringsEq(rt.referent.getTypeName(), "PERSONPROPERTY")) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addName("ГОСУДАРСТВЕННАЯ ДУМА", true, null);
                org00.addName("ГОСДУМА", true, null);
                org00.addName("ГД", true, null);
                org00.addTypeStr("парламент");
                org00.addProfile(OrgProfile.STATE);
                com.pullenti.ner.Token te = attachTailAttributes(org00, t.getNext(), null, false, AttachType.NORMAL, true);
                return new com.pullenti.ner.ReferentToken(org00, t, (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(te, t), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "МЮ")) {
            boolean ok = false;
            if ((t.getPrevious() != null && t.getPrevious().isValue("В", null) && t.getPrevious().getPrevious() != null) && t.getPrevious().getPrevious().isValue("ЗАРЕГИСТРИРОВАТЬ", null)) 
                ok = true;
            else if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) 
                ok = true;
            if (ok) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("министерство");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("МИНИСТЕРСТВО ЮСТИЦИИ", true, null);
                org00.addName("МИНЮСТ", true, null);
                com.pullenti.ner.Token t1 = t;
                if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    t1 = t.getNext();
                    org00.addGeoObject(t1.getReferent());
                }
                return new com.pullenti.ner.ReferentToken(org00, t, t1, null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "ФС")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("парламент");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("ФЕДЕРАЛЬНОЕ СОБРАНИЕ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "МП")) {
            com.pullenti.ner.Token tt0 = t.getPrevious();
            if (tt0 != null && tt0.isChar('(')) 
                tt0 = tt0.getPrevious();
            OrganizationReferent org0 = null;
            boolean prev = false;
            if (tt0 != null) {
                org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt0.getReferent(), OrganizationReferent.class);
                if (org0 != null) 
                    prev = true;
            }
            if (t.getNext() != null && org0 == null) 
                org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
            if (org0 != null && org0.getKind() == OrganizationKind.CHURCH) {
                OrganizationReferent glob = new OrganizationReferent();
                glob.addTypeStr("патриархия");
                glob.addName("МОСКОВСКАЯ ПАТРИАРХИЯ", true, null);
                glob.setHigher(org0);
                glob.addProfile(OrgProfile.RELIGION);
                com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(glob, t, t, null);
                if (!prev) 
                    res.setEndToken(t.getNext());
                else {
                    res.setBeginToken(tt0);
                    if (tt0 != t.getPrevious() && res.getEndToken().getNext() != null && res.getEndToken().getNext().isChar(')')) 
                        res.setEndToken(res.getEndToken().getNext());
                }
                return res;
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "ГШ")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof OrganizationReferent) && (((OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class))).getKind() == OrganizationKind.MILITARY) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("генеральный штаб");
                org00.addProfile(OrgProfile.ARMY);
                org00.setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class));
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "ЗС")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("парламент");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("ЗАКОНОДАТЕЛЬНОЕ СОБРАНИЕ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "СФ")) {
            t.setInnerBool(true);
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("совет");
                org00.addProfile(OrgProfile.STATE);
                org00.addName("СОВЕТ ФЕДЕРАЦИИ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
            if (t.getNext() != null) {
                if (t.getNext().isValue("ФС", null) || (((t.getNext().getReferent() instanceof OrganizationReferent) && t.getNext().getReferent().findSlot(OrganizationReferent.ATTR_NAME, "ФЕДЕРАЛЬНОЕ СОБРАНИЕ", true) != null))) {
                    OrganizationReferent org00 = new OrganizationReferent();
                    org00.addTypeStr("совет");
                    org00.addProfile(OrgProfile.STATE);
                    org00.addName("СОВЕТ ФЕДЕРАЦИИ", true, null);
                    return new com.pullenti.ner.ReferentToken(org00, t, t, null);
                }
            }
        }
        if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq(term, "ФК")) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("казначейство");
                org00.addProfile(OrgProfile.FINANCE);
                org00.addName("ФЕДЕРАЛЬНОЕ КАЗНАЧЕЙСТВО", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
            if (attachTyp == AttachType.NORMALAFTERDEP) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("казначейство");
                org00.addProfile(OrgProfile.FINANCE);
                org00.addName("ФЕДЕРАЛЬНОЕ КАЗНАЧЕЙСТВО", true, null);
                return new com.pullenti.ner.ReferentToken(org00, t, t, null);
            }
        }
        if (t.chars.isAllUpper() && ((com.pullenti.n2j.Utils.stringsEq(term, "СК") || com.pullenti.n2j.Utils.stringsEq(term, "CK")))) {
            if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                for(com.pullenti.ner.Token tt = t.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt instanceof com.pullenti.ner.TextToken) {
                        if (tt.isCommaAnd()) 
                            continue;
                        if (tt instanceof com.pullenti.ner.NumberToken) 
                            continue;
                        if (!tt.chars.isLetter()) 
                            continue;
                        if ((tt.isValue("ЧАСТЬ", null) || tt.isValue("СТАТЬЯ", null) || tt.isValue("ПУНКТ", null)) || tt.isValue("СТ", null) || tt.isValue("П", null)) 
                            return null;
                        break;
                    }
                }
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("комитет");
                org00.addProfile(OrgProfile.UNIT);
                org00.addName("СЛЕДСТВЕННЫЙ КОМИТЕТ", true, null);
                org00.addGeoObject(t.getNext().getReferent());
                return new com.pullenti.ner.ReferentToken(org00, t, t.getNext(), null);
            }
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> gt1 = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGS.tryAttach(t.getNext(), null, false);
            if (gt1 == null && t.getNext() != null && t.kit.baseLanguage.isUa()) 
                gt1 = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t.getNext(), null, false);
            boolean ok = false;
            if (gt1 != null && gt1.get(0).item.referent.findSlot(OrganizationReferent.ATTR_NAME, "МВД", true) != null) 
                ok = true;
            if (ok) {
                OrganizationReferent org00 = new OrganizationReferent();
                org00.addTypeStr("комитет");
                org00.addName("СЛЕДСТВЕННЫЙ КОМИТЕТ", true, null);
                org00.addProfile(OrgProfile.UNIT);
                return new com.pullenti.ner.ReferentToken(org00, t, t, null);
            }
        }
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> gt = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGS.tryAttach(t, null, true);
        if (gt == null) 
            gt = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGS.tryAttach(t, null, false);
        if (gt == null && t != null && t.kit.baseLanguage.isUa()) {
            gt = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t, null, true);
            if (gt == null) 
                gt = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t, null, false);
        }
        if (gt == null) 
            return null;
        for(com.pullenti.ner.core.IntOntologyToken ot : gt) {
            OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(ot.item.referent, OrganizationReferent.class);
            if (org0 == null) 
                continue;
            if (ot.getBeginToken() == ot.getEndToken()) {
                if (gt.size() == 1) {
                    if ((ot.getBeginToken() instanceof com.pullenti.ner.TextToken) && com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(ot.getBeginToken(), com.pullenti.ner.TextToken.class))).term, "МГТУ")) {
                        com.pullenti.ner.org.internal.OrgItemTypeToken ty = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(ot.getBeginToken(), false, null);
                        if (ty != null) 
                            continue;
                    }
                }
                else {
                    if (ad == null) 
                        return null;
                    boolean ok = false;
                    for(com.pullenti.ner.Referent o : ad.getReferents()) {
                        if (o.canBeEquals(org0, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) 
                        return null;
                }
            }
            if (((t.chars.isAllLower() && attachTyp != AttachType.EXTONTOLOGY && extGeo == null) && !t.isValue("МИД", null) && !org0._typesContains("факультет")) && org0.getKind() != OrganizationKind.JUSTICE) {
                if (ot.getBeginToken() == ot.getEndToken()) 
                    continue;
                if (ot.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    continue;
                com.pullenti.ner.org.internal.OrgItemTypeToken tyty = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t, true, null);
                if (tyty != null && tyty.getEndToken() == ot.getEndToken()) 
                    continue;
                if (t.getNext() != null && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                }
                else if (com.pullenti.ner.org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t.getPrevious())) {
                }
                else 
                    continue;
            }
            if ((ot.getBeginToken() == ot.getEndToken() && (t.getLengthChar() < 6) && !t.chars.isAllUpper()) && !t.chars.isLastLower()) {
                if (org0.findSlot(OrganizationReferent.ATTR_NAME, (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, true) == null) {
                    if (t.isValue("МИД", null)) {
                    }
                    else 
                        continue;
                }
                else if (t.chars.isAllLower()) 
                    continue;
                else if (t.getLengthChar() < 3) 
                    continue;
                else if (t.getLengthChar() == 4) {
                    boolean hasVow = false;
                    for(char ch : (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.toCharArray()) {
                        if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch) || com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                            hasVow = true;
                    }
                    if (hasVow) 
                        continue;
                }
            }
            if (ot.getBeginToken() == ot.getEndToken() && com.pullenti.n2j.Utils.stringsEq(term, "МЭР")) 
                continue;
            if (ot.getBeginToken() == ot.getEndToken()) {
                if (t.getPrevious() == null || t.isWhitespaceBefore()) {
                }
                else if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && ((t.getPrevious().isCharOf(",:") || com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), false, false)))) {
                }
                else 
                    continue;
                if (t.getNext() == null || t.isWhitespaceAfter()) {
                }
                else if ((t.getNext() instanceof com.pullenti.ner.TextToken) && ((t.getNext().isCharOf(",.") || com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext(), false, null, false)))) {
                }
                else 
                    continue;
                if (t instanceof com.pullenti.ner.TextToken) {
                    boolean hasName = false;
                    for(String n : org0.getNames()) {
                        if (t.isValue(n, null)) {
                            hasName = true;
                            break;
                        }
                    }
                    if (!hasName) 
                        continue;
                }
                com.pullenti.ner.ReferentToken rt = t.kit.processReferent("TRANSPORT", t);
                if (rt != null) 
                    continue;
            }
            OrganizationReferent _org = null;
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.isValue("ДЕПАРТАМЕНТ", null) || t.isValue("КОМИТЕТ", null)) {
                    com.pullenti.ner.org.internal.OrgItemNameToken nnn = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t.getNext(), null, true, true);
                    if (nnn != null && nnn.endChar > ot.endChar) {
                        _org = new OrganizationReferent();
                        _org.addTypeStr((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).lemma.toLowerCase());
                        _org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(t, nnn.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE), true, null);
                        ot.setEndToken(nnn.getEndToken());
                    }
                }
            }
            if (_org == null) {
                _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(org0.clone(), OrganizationReferent.class);
                if (_org.getGeoObjects().size() > 0) {
                    for(com.pullenti.ner.Slot s : _org.getSlots()) {
                        if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                            com.pullenti.ner.Referent gg = (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class))).clone();
                            gg.getOccurrence().clear();
                            com.pullenti.ner.ReferentToken rtg = new com.pullenti.ner.ReferentToken(gg, t, t, null);
                            rtg.data = t.kit.getAnalyzerDataByAnalyzerName("GEO");
                            _org.getSlots().remove(s);
                            _org.addGeoObject(rtg);
                            break;
                        }
                    }
                }
                _org.addName(ot.termin.getCanonicText(), true, null);
            }
            if (extGeo != null) 
                _org.addGeoObject(extGeo);
            _org.isFromGlobalOntos = true;
            for(com.pullenti.ner.Token tt = ot.getBeginToken(); tt != null && (tt.endChar < ot.endChar); tt = tt.getNext()) {
                if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                    _org.addGeoObject(tt);
                    break;
                }
            }
            if ((t.getPrevious() instanceof com.pullenti.ner.TextToken) && (t.getWhitespacesBeforeCount() < 2) && t.getPrevious().getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.ReferentToken gg = t.kit.processReferent("GEO", t.getPrevious());
                if (gg != null && gg.getMorph()._getClass().isAdjective()) {
                    t = t.getPrevious();
                    _org.addGeoObject(gg);
                }
            }
            com.pullenti.ner.Token t1 = null;
            if (!org0.getTypes().contains("академия") && attachTyp != AttachType.NORMALAFTERDEP && attachTyp != AttachType.EXTONTOLOGY) 
                t1 = attachTailAttributes(_org, ot.getEndToken().getNext(), null, false, AttachType.NORMAL, true);
            else if (((((org0.getTypes().contains("министерство") || org0.getTypes().contains("парламент") || org0.getTypes().contains("совет")) || org0.getKind() == OrganizationKind.SCIENCE || org0.getKind() == OrganizationKind.STUDY) || org0.getKind() == OrganizationKind.JUSTICE || org0.getKind() == OrganizationKind.MILITARY)) && (ot.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(ot.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (_geo != null && _geo.isState()) {
                    _org.addGeoObject(_geo);
                    t1 = ot.getEndToken().getNext();
                }
            }
            if (t1 == null) 
                t1 = ot.getEndToken();
            com.pullenti.ner.org.internal.OrgItemEponymToken epp = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
            if (epp != null) {
                boolean exi = false;
                for(String v : epp.eponyms) {
                    if (_org.findSlot(OrganizationReferent.ATTR_EPONYM, v, true) != null) {
                        exi = true;
                        break;
                    }
                }
                if (!exi) {
                    for(int i = _org.getSlots().size() - 1; i >= 0; i--) {
                        if (com.pullenti.n2j.Utils.stringsEq(_org.getSlots().get(i).getTypeName(), OrganizationReferent.ATTR_EPONYM)) 
                            _org.getSlots().remove(i);
                    }
                    for(String vv : epp.eponyms) {
                        _org.addEponym(vv);
                    }
                }
                t1 = epp.getEndToken();
            }
            if (t1.getWhitespacesAfterCount() < 2) {
                com.pullenti.ner.org.internal.OrgItemTypeToken typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t1.getNext(), false, null);
                if (typ != null) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypeAccords(_org, typ)) {
                        if (typ.chars.isLatinLetter() && typ.root != null && typ.root.canBeNormalDep) {
                        }
                        else {
                            _org.addType(typ, false);
                            t1 = typ.getEndToken();
                        }
                    }
                }
            }
            if (_org.getGeoObjects().size() == 0 && t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isAdjective()) {
                com.pullenti.ner.ReferentToken grt = t.kit.processReferent("GEO", t.getPrevious());
                if (grt != null && grt.getEndToken().getNext() == t) {
                    _org.addGeoObject(grt);
                    t = t.getPrevious();
                }
            }
            if (_org.findSlot(OrganizationReferent.ATTR_NAME, "ВТБ", true) != null && t1.getNext() != null) {
                com.pullenti.ner.Token tt = t1.getNext();
                if (tt.isHiphen() && tt.getNext() != null) 
                    tt = tt.getNext();
                if (tt instanceof com.pullenti.ner.NumberToken) {
                    _org.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.NumberToken.class))).value).toString());
                    t1 = tt;
                }
            }
            if (!t.isWhitespaceBefore() && !t1.isWhitespaceAfter()) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getPrevious(), true, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), true, null, false)) {
                    t = t.getPrevious();
                    t1 = t1.getNext();
                }
            }
            return new com.pullenti.ner.ReferentToken(_org, t, t1, null);
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken tryAttachDepBeforeOrg(com.pullenti.ner.org.internal.OrgItemTypeToken typ, com.pullenti.ner.ReferentToken rtOrg) {
        if (typ == null) 
            return null;
        OrganizationReferent _org = (rtOrg == null ? null : (OrganizationReferent)com.pullenti.n2j.Utils.cast(rtOrg.referent, OrganizationReferent.class));
        com.pullenti.ner.Token t = typ.getEndToken();
        if (_org == null) {
            t = t.getNext();
            if (t != null && ((t.isValue("ПРИ", null) || t.isValue("AT", null) || t.isValue("OF", null)))) 
                t = t.getNext();
            if (t == null) 
                return null;
            _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class);
        }
        else 
            t = rtOrg.getEndToken();
        if (_org == null) 
            return null;
        com.pullenti.ner.Token t1 = t;
        if (t1.getNext() instanceof com.pullenti.ner.ReferentToken) {
            com.pullenti.ner.geo.GeoReferent geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(t1.getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            if (geo0 != null && com.pullenti.n2j.Utils.stringsEq(geo0.getAlpha2(), "RU")) 
                t1 = t1.getNext();
        }
        OrganizationReferent dep = new OrganizationReferent();
        dep.addType(typ, false);
        if (typ.name != null) {
            String nam = typ.name;
            if (Character.isDigit(nam.charAt(0))) {
                int i = nam.indexOf(' ');
                if (i > 0) {
                    dep.setNumber(nam.substring(0, 0+(i)));
                    nam = nam.substring(i + 1).trim();
                }
            }
            dep.addName(nam, true, null);
        }
        String ttt = (typ.root != null ? typ.root.getCanonicText() : typ.typ.toUpperCase());
        if ((((com.pullenti.n2j.Utils.stringsEq(ttt, "ОТДЕЛЕНИЕ") || com.pullenti.n2j.Utils.stringsEq(ttt, "ИНСПЕКЦИЯ") || com.pullenti.n2j.Utils.stringsEq(ttt, "ВІДДІЛЕННЯ")) || com.pullenti.n2j.Utils.stringsEq(ttt, "ІНСПЕКЦІЯ"))) && !t1.isNewlineAfter()) {
            com.pullenti.ner.org.internal.OrgItemNumberToken num = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, null);
            if (num != null) {
                dep.setNumber(num.number);
                t1 = num.getEndToken();
            }
        }
        if (dep.getTypes().contains("главное управление") || dep.getTypes().contains("головне управління") || (dep.getTypeName().indexOf("пограничное управление") >= 0)) {
            if (typ.getBeginToken() == typ.getEndToken()) {
                if (_org.getKind() != OrganizationKind.GOVENMENT && _org.getKind() != OrganizationKind.BANK) 
                    return null;
            }
        }
        if (!com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(_org, dep, false) && ((typ.root == null || !typ.root.canBeNormalDep))) {
            if (dep.getTypes().size() > 0 && _org.getTypes().contains(dep.getTypes().get(0)) && dep.canBeEquals(_org, com.pullenti.ner.Referent.EqualType.FORMERGING)) 
                dep.mergeSlots(_org, false);
            else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "управление") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "управління")) 
                dep.setHigher(_org);
            else 
                return null;
        }
        else 
            dep.setHigher(_org);
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(dep, typ.getBeginToken(), t1, null);
        correctDepAttrs(res, typ);
        if (typ.root != null && !typ.root.canBeNormalDep && dep.getNumber() == null) {
            if (typ.name != null && (typ.name.indexOf(" ") >= 0)) {
            }
            else if (dep.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) {
            }
            else if (typ.root.coeff > 0 && typ.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
            }
            else 
                return null;
        }
        return res;
    }

    private com.pullenti.ner.ReferentToken tryAttachDepAfterOrg(com.pullenti.ner.org.internal.OrgItemTypeToken typ) {
        if (typ == null) 
            return null;
        com.pullenti.ner.Token t = typ.getBeginToken().getPrevious();
        if (t != null && t.isCharOf(":(")) 
            t = t.getPrevious();
        if (t == null) 
            return null;
        OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class);
        if (_org == null) 
            return null;
        com.pullenti.ner.Token t1 = typ.getEndToken();
        OrganizationReferent dep = new OrganizationReferent();
        dep.addType(typ, false);
        if (typ.name != null) 
            dep.addName(typ.name, true, null);
        if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(_org, dep, false)) 
            dep.setHigher(_org);
        else if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(dep, _org, false) && _org.getHigher() == null) {
            _org.setHigher(dep);
            t = t.getNext();
        }
        else 
            t = t.getNext();
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(dep, t, t1, null);
        correctDepAttrs(res, typ);
        if (dep.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null) 
            return null;
        return res;
    }

    private com.pullenti.ner.ReferentToken tryAttachDep(com.pullenti.ner.org.internal.OrgItemTypeToken typ, AttachType attachTyp, boolean specWordBefore) {
        if (typ == null) 
            return null;
        com.pullenti.ner.Referent afterOrg = null;
        boolean afterOrgTemp = false;
        if ((typ.isNewlineAfter() && typ.name == null && com.pullenti.n2j.Utils.stringsNe(typ.typ, "курс")) && ((typ.root == null || !typ.root.canBeNormalDep))) {
            com.pullenti.ner.Token tt2 = typ.getEndToken().getNext();
            if (!specWordBefore || tt2 == null) 
                return null;
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt2, false, false)) {
            }
            else 
                return null;
        }
        if (!specWordBefore && typ.getEndToken().getNext() != null && (typ.getEndToken().getWhitespacesAfterCount() < 2)) {
            com.pullenti.ner.org.internal.OrgItemNameToken na0 = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(typ.getEndToken().getNext(), null, false, true);
            if (na0 != null && ((na0.stdOrgNameNouns > 0 || na0.isStdName))) 
                specWordBefore = true;
            else {
                com.pullenti.ner.ReferentToken rt00 = tryAttachOrg(typ.getEndToken().getNext(), null, AttachType.NORMALAFTERDEP, null, false, 0, -1);
                if (rt00 != null) {
                    afterOrg = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt00.referent, OrganizationReferent.class);
                    specWordBefore = true;
                    afterOrgTemp = true;
                }
                else if ((typ.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && typ.getEndToken().getNext().chars.isAllUpper()) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> rrr = tryAttachOrgs(typ.getEndToken().getNext(), null, 0);
                    if (rrr != null && rrr.size() == 1) {
                        afterOrg = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rrr.get(0).referent, OrganizationReferent.class);
                        specWordBefore = true;
                        afterOrgTemp = true;
                    }
                }
            }
        }
        if (((((((typ.root != null && typ.root.canBeNormalDep && !specWordBefore) && com.pullenti.n2j.Utils.stringsNe(typ.typ, "отделение") && com.pullenti.n2j.Utils.stringsNe(typ.typ, "инспекция")) && com.pullenti.n2j.Utils.stringsNe(typ.typ, "филиал") && com.pullenti.n2j.Utils.stringsNe(typ.typ, "аппарат")) && com.pullenti.n2j.Utils.stringsNe(typ.typ, "відділення") && com.pullenti.n2j.Utils.stringsNe(typ.typ, "інспекція")) && com.pullenti.n2j.Utils.stringsNe(typ.typ, "філія") && com.pullenti.n2j.Utils.stringsNe(typ.typ, "апарат")) && com.pullenti.n2j.Utils.stringsNe(typ.typ, "совет") && com.pullenti.n2j.Utils.stringsNe(typ.typ, "рада")) && (typ.typ.indexOf(' ') < 0) && attachTyp != AttachType.EXTONTOLOGY) 
            return null;
        if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) {
            if (!typ.getBeginToken().isValue("ОСП", null)) 
                return null;
        }
        OrganizationReferent dep = null;
        com.pullenti.ner.Token t0 = typ.getBeginToken();
        com.pullenti.ner.Token t1 = typ.getEndToken();
        dep = new OrganizationReferent();
        dep.addTypeStr(typ.typ.toLowerCase());
        dep.addProfile(OrgProfile.UNIT);
        if (typ.number != null) 
            dep.setNumber(typ.number);
        else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "курс") && !typ.isNewlineBefore()) {
            com.pullenti.ner.NumberToken nnn = com.pullenti.ner.core.NumberHelper.tryParseRomanBack(typ.getBeginToken().getPrevious());
            if (nnn != null) {
                if (nnn.value >= ((long)1) && nnn.value <= ((long)6)) {
                    dep.setNumber(((Long)nnn.value).toString());
                    t0 = nnn.getBeginToken();
                }
            }
        }
        com.pullenti.ner.Token t = typ.getEndToken().getNext();
        t1 = typ.getEndToken();
        if ((t instanceof com.pullenti.ner.TextToken) && afterOrg == null && (((com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "аппарат") || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "апарат") || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "совет")) || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "рада")))) {
            com.pullenti.ner.Token tt1 = t;
            if (tt1.isValue("ПРИ", null)) 
                tt1 = tt1.getNext();
            com.pullenti.ner.ReferentToken pr1 = t.kit.processReferent("PERSON", tt1);
            if (pr1 != null && com.pullenti.n2j.Utils.stringsEq(pr1.referent.getTypeName(), "PERSONPROPERTY")) {
                dep.addSlot(OrganizationReferent.ATTR_OWNER, pr1.referent, true, 0);
                pr1.setDefaultLocalOnto(t.kit.processor);
                dep.addExtReferent(pr1);
                if (com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "рат")) 
                    return new com.pullenti.ner.ReferentToken(dep, t0, pr1.getEndToken(), null);
                t1 = pr1.getEndToken();
                t = t1.getNext();
            }
        }
        com.pullenti.ner.Referent beforeOrg = null;
        for(com.pullenti.ner.Token ttt = typ.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
            if (ttt.getReferent() instanceof OrganizationReferent) {
                beforeOrg = ttt.getReferent();
                break;
            }
            else if (!((ttt instanceof com.pullenti.ner.TextToken))) 
                break;
            else if (ttt.chars.isLetter()) 
                break;
        }
        com.pullenti.ner.org.internal.OrgItemNumberToken num = null;
        java.util.ArrayList<com.pullenti.ner.org.internal.OrgItemNameToken> names = null;
        com.pullenti.ner.core.BracketSequenceToken br = null;
        com.pullenti.ner.org.internal.OrgItemNameToken pr = null;
        com.pullenti.ner.org.internal.OrgItemTypeToken ty0;
        boolean isPureOrg = false;
        boolean isPureDep = false;
        if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "операционное управление") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "операційне управління")) 
            isPureDep = true;
        com.pullenti.ner.Token afterOrgTok = null;
        com.pullenti.ner.core.BracketSequenceToken brName = null;
        for(; t != null; t = t.getNext()) {
            if (afterOrgTemp) 
                break;
            if (t.isChar(':')) {
                if (t.isNewlineAfter()) 
                    break;
                if (names != null || typ.name != null) 
                    break;
                continue;
            }
            if ((((num = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t, false, null)))) != null) {
                if (t.isNewlineBefore()) 
                    break;
                t1 = (t = num.getEndToken());
                break;
            }
            else if ((((ty0 = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t, true, null)))) != null && ty0.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL && !ty0.isDoubtRootWord()) 
                break;
            else if (names == null && (((br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100)))) != null) {
                if (!br.isQuoteType() || brName != null) 
                    br = null;
                else if (t.isNewlineBefore() && !specWordBefore) 
                    br = null;
                else {
                    boolean ok1 = true;
                    for(com.pullenti.ner.Token tt = br.getBeginToken(); tt != br.getEndToken(); tt = tt.getNext()) {
                        if (tt instanceof com.pullenti.ner.ReferentToken) {
                            ok1 = false;
                            break;
                        }
                    }
                    if (ok1) {
                        brName = br;
                        t = (t1 = br.getEndToken());
                    }
                    else 
                        br = null;
                }
                break;
            }
            else {
                com.pullenti.ner.Referent r = t.getReferent();
                if ((r == null && t.getMorph()._getClass().isPreposition() && t.getNext() != null) && (t.getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    dep.addGeoObject(t.getNext().getReferent());
                    t = t.getNext();
                    break;
                }
                if (r != null) {
                    if (r instanceof OrganizationReferent) {
                        afterOrg = r;
                        afterOrgTok = t;
                        break;
                    }
                    if ((r instanceof com.pullenti.ner.geo.GeoReferent) && names != null && t.getPrevious() != null) {
                        boolean isName = false;
                        if (t.getPrevious().isValue("СУБЪЕКТ", null) || t.getPrevious().isValue("СУБЄКТ", null)) 
                            isName = true;
                        if (!isName) 
                            break;
                    }
                    else 
                        break;
                }
                com.pullenti.ner.org.internal.OrgItemEponymToken epo = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t, true);
                if (epo != null) {
                    for(String e : epo.eponyms) {
                        dep.addEponym(e);
                    }
                    t1 = epo.getEndToken();
                    break;
                }
                if (!typ.chars.isAllUpper() && t.chars.isAllUpper()) {
                    com.pullenti.ner.org.internal.OrgItemNameToken na1 = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t, pr, attachTyp == AttachType.EXTONTOLOGY, false);
                    if (na1 != null && ((na1.isStdName || na1.stdOrgNameNouns > 0))) {
                    }
                    else 
                        break;
                }
                if ((t instanceof com.pullenti.ner.NumberToken) && typ.root != null && dep.getNumber() == null) {
                    if (t.getWhitespacesBeforeCount() > 1) 
                        break;
                    dep.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                    t1 = t;
                    continue;
                }
                if (isPureDep) 
                    break;
                if (!t.chars.isAllLower()) {
                    com.pullenti.ner.ReferentToken rtp = t.kit.processReferent("PERSON", t);
                    if (rtp != null && com.pullenti.n2j.Utils.stringsEq(rtp.referent.getTypeName(), "PERSONPROPERTY")) {
                        if (rtp.getMorph().getCase().isGenitive() && t == typ.getEndToken().getNext() && (t.getWhitespacesBeforeCount() < 4)) 
                            rtp = null;
                    }
                    if (rtp != null) 
                        break;
                }
                if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "генеральный штаб") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "генеральний штаб")) {
                    com.pullenti.ner.ReferentToken rtp = t.kit.processReferent("PERSONPROPERTY", t);
                    if (rtp != null) 
                        break;
                }
                com.pullenti.ner.org.internal.OrgItemNameToken na = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t, pr, attachTyp == AttachType.EXTONTOLOGY, names == null);
                if (t.isValue("ПО", null) && t.getNext() != null && t.getNext().isValue("РАЙОН", null)) 
                    na = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t.getNext().getNext(), pr, attachTyp == AttachType.EXTONTOLOGY, true);
                if (t.getMorph()._getClass().isPreposition() && ((t.isValue("ПРИ", null) || t.isValue("OF", null) || t.isValue("AT", null)))) {
                    if ((t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                        afterOrg = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
                        break;
                    }
                    com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t.getNext(), null, AttachType.NORMALAFTERDEP, null, false, 0, -1);
                    if (rt0 != null) {
                        afterOrg = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
                        afterOrgTemp = true;
                        break;
                    }
                }
                if (na == null) 
                    break;
                if (names == null) {
                    if (t.isNewlineBefore()) 
                        break;
                    if (com.pullenti.ner.core.NumberHelper.tryParseRoman(t) != null) 
                        break;
                    com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t, null, AttachType.NORMALAFTERDEP, null, false, 0, -1);
                    if (rt0 != null) {
                        afterOrg = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
                        afterOrgTemp = true;
                        break;
                    }
                    names = new java.util.ArrayList<>();
                }
                else if (t.getWhitespacesBeforeCount() > 2 && com.pullenti.morph.CharsInfo.ooNoteq(na.chars, pr.chars)) 
                    break;
                names.add(na);
                pr = na;
                t1 = (t = na.getEndToken());
            }
        }
        if (afterOrg == null) {
            for(com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
                if (ttt.getReferent() instanceof OrganizationReferent) {
                    afterOrg = ttt.getReferent();
                    break;
                }
                else if (!((ttt instanceof com.pullenti.ner.TextToken))) 
                    break;
                else if ((ttt.chars.isLetter() && !ttt.isValue("ПРИ", null) && !ttt.isValue("В", null)) && !ttt.isValue("OF", null) && !ttt.isValue("AT", null)) 
                    break;
            }
        }
        if ((afterOrg == null && t != null && t != t0) && (t.getWhitespacesBeforeCount() < 2)) {
            com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t, null, AttachType.NORMALAFTERDEP, null, false, 0, -1);
            if (rt0 == null && (((t.isValue("В", null) || t.isValue("ПРИ", null) || t.isValue("OF", null)) || t.isValue("AT", null)))) 
                rt0 = tryAttachOrg(t.getNext(), null, AttachType.NORMALAFTERDEP, null, false, 0, -1);
            if (rt0 != null) {
                afterOrg = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
                afterOrgTemp = true;
            }
        }
        float coef = typ.getCoef();
        if (typ.chars.isCapitalUpper()) 
            coef += 0.5F;
        if (br != null && names == null) {
            String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
            if (!com.pullenti.n2j.Utils.isNullOrEmpty(nam)) {
                if (nam.length() > 100) 
                    return null;
                coef += ((float)3);
                com.pullenti.ner.org.internal.OrgItemNameToken na = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(br.getBeginToken().getNext(), null, false, true);
                if (na != null && na.isStdName) {
                    coef += ((float)1);
                    if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "группа")) {
                        dep.getSlots().clear();
                        typ.typ = "группа компаний";
                        isPureOrg = true;
                    }
                    else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "група")) {
                        dep.getSlots().clear();
                        typ.typ = "група компаній";
                        isPureOrg = true;
                    }
                }
                if (isPureOrg) {
                    dep.addType(typ, false);
                    dep.addName(nam, true, null);
                }
                else 
                    dep.addNameStr(nam, typ, 1);
            }
        }
        else if (names != null) {
            int j;
            if (afterOrg != null || attachTyp == AttachType.HIGH) {
                coef += ((float)3);
                j = names.size();
            }
            else 
                for(j = 0; j < names.size(); j++) {
                    if (((names.get(j).isNewlineBefore() && !names.get(j).isAfterConjunction)) || ((com.pullenti.morph.CharsInfo.ooNoteq(names.get(j).chars, names.get(0).chars) && names.get(j).stdOrgNameNouns == 0))) 
                        break;
                    else {
                        if (com.pullenti.morph.CharsInfo.ooEq(names.get(j).chars, typ.chars)) 
                            coef += ((float)0.5);
                        if (names.get(j).isStdName) 
                            coef += ((float)2);
                        if (names.get(j).stdOrgNameNouns > 0) {
                            if (!typ.chars.isAllLower()) 
                                coef += ((float)names.get(j).stdOrgNameNouns);
                        }
                    }
                }
            t1 = names.get(j - 1).getEndToken();
            String s = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), t1, com.pullenti.ner.core.GetTextAttr.NO);
            if (!com.pullenti.n2j.Utils.isNullOrEmpty(s)) {
                if (s.length() > 150 && attachTyp != AttachType.EXTONTOLOGY) 
                    return null;
                dep.addNameStr(s, typ, 1);
            }
            if (num != null) {
                dep.setNumber(num.number);
                coef += ((float)2);
                t1 = num.getEndToken();
            }
        }
        else if (num != null) {
            dep.setNumber(num.number);
            coef += ((float)2);
            t1 = num.getEndToken();
            if (typ != null && ((com.pullenti.n2j.Utils.stringsEq(typ.typ, "лаборатория") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "лабораторія")))) 
                coef += ((float)1);
            if (typ.name != null) 
                dep.addNameStr(null, typ, 1);
        }
        else if (typ.name != null) {
            if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "курс") && Character.isDigit(typ.name.charAt(0))) 
                dep.setNumber(typ.name.substring(0, 0+(typ.name.indexOf(' '))));
            else 
                dep.addNameStr(null, typ, 1);
        }
        else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "кафедра") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "факультет")) {
            t = typ.getEndToken().getNext();
            if (t != null && t.isChar(':')) 
                t = t.getNext();
            if ((t != null && (t instanceof com.pullenti.ner.TextToken) && !t.isNewlineBefore()) && t.getMorph()._getClass().isAdjective()) {
                if (typ.getMorph().getGender() == t.getMorph().getGender()) {
                    String s = t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                    if (s != null) {
                        dep.addNameStr(s + " " + typ.typ.toUpperCase(), null, 1);
                        coef += ((float)2);
                        t1 = t;
                    }
                }
            }
        }
        else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "курс")) {
            t = typ.getEndToken().getNext();
            if (t != null && t.isChar(':')) 
                t = t.getNext();
            if (t != null && !t.isNewlineBefore()) {
                int val = 0;
                if (t instanceof com.pullenti.ner.NumberToken) {
                    if (!t.getMorph()._getClass().isNoun()) {
                        if (t.isWhitespaceAfter() || t.getNext().isCharOf(";,")) 
                            val = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value;
                    }
                }
                else {
                    com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRoman(t);
                    if (nt != null) {
                        val = (int)nt.value;
                        t = nt.getEndToken();
                    }
                }
                if (val > 0 && (val < 8)) {
                    dep.setNumber(((Integer)val).toString());
                    t1 = t;
                    coef += ((float)4);
                }
            }
            if (dep.getNumber() == null) {
                t = typ.getBeginToken().getPrevious();
                if (t != null && !t.isNewlineAfter()) {
                    int val = 0;
                    if (t instanceof com.pullenti.ner.NumberToken) {
                        if (!t.getMorph()._getClass().isNoun()) {
                            if (t.isWhitespaceBefore() || t.getPrevious().isCharOf(",")) 
                                val = (int)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value;
                        }
                    }
                    else {
                        com.pullenti.ner.NumberToken nt = com.pullenti.ner.core.NumberHelper.tryParseRomanBack(t);
                        if (nt != null) {
                            val = (int)nt.value;
                            t = nt.getBeginToken();
                        }
                    }
                    if (val > 0 && (val < 8)) {
                        dep.setNumber(((Integer)val).toString());
                        t0 = t;
                        coef += ((float)4);
                    }
                }
            }
        }
        else if (typ.root != null && typ.root.canBeNormalDep && afterOrg != null) {
            coef += ((float)3);
            if (!afterOrgTemp) 
                dep.setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(afterOrg, OrganizationReferent.class));
            if (afterOrgTok != null) 
                t1 = afterOrgTok;
        }
        else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "генеральный штаб") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "генеральний штаб")) 
            coef += ((float)3);
        if (beforeOrg != null) 
            coef += ((float)1);
        if (afterOrg != null) {
            coef += ((float)2);
            if (typ.name != null && com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(afterOrg, OrganizationReferent.class), dep, false)) {
                coef += ((float)1);
                if (!typ.chars.isAllLower()) 
                    coef += 0.5F;
            }
        }
        if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "курс") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "группа") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "група")) {
            if (dep.getNumber() == null) 
                coef = (float)0;
            else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "курс")) {
                int n;
                com.pullenti.n2j.Outargwrapper<Integer> inoutarg2139 = new com.pullenti.n2j.Outargwrapper<>();
                boolean inoutres2140 = com.pullenti.n2j.Utils.parseInteger(dep.getNumber(), inoutarg2139);
                n = (inoutarg2139.value != null ? inoutarg2139.value : 0);
                if (inoutres2140) {
                    if (n > 0 && (n < 9)) 
                        coef += ((float)2);
                }
            }
        }
        if (t1.getNext() != null && t1.getNext().isChar('(')) {
            com.pullenti.ner.Token ttt = t1.getNext().getNext();
            if ((ttt != null && ttt.getNext() != null && ttt.getNext().isChar(')')) && (ttt instanceof com.pullenti.ner.TextToken)) {
                if (dep.getNameVars().containsKey((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.TextToken.class))).term)) {
                    coef += ((float)2);
                    dep.addName((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.TextToken.class))).term, true, ttt);
                    t1 = ttt.getNext();
                }
            }
        }
        com.pullenti.ner.org.internal.OrgItemEponymToken _ep = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
        if (_ep != null) {
            coef += ((float)2);
            for(String e : _ep.eponyms) {
                dep.addEponym(e);
            }
            t1 = _ep.getEndToken();
        }
        if (brName != null) {
            String str1 = com.pullenti.ner.core.MiscHelper.getTextValue(brName.getBeginToken().getNext(), brName.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
            if (str1 != null) 
                dep.addName(str1, true, null);
        }
        if (dep.getSlots().size() == 0) 
            return null;
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(dep, t0, t1, null);
        correctDepAttrs(res, typ);
        if (dep.getNumber() != null) 
            coef += ((float)2);
        if (isPureDep) 
            coef += ((float)2);
        if (specWordBefore) {
            if (dep.findSlot(OrganizationReferent.ATTR_NAME, null, true) != null) 
                coef += ((float)2);
        }
        if (coef > 3 || attachTyp == AttachType.EXTONTOLOGY) 
            return res;
        else 
            return null;
    }

    private void correctDepAttrs(com.pullenti.ner.ReferentToken res, com.pullenti.ner.org.internal.OrgItemTypeToken typ) {
        com.pullenti.ner.Token t0 = res.getBeginToken();
        OrganizationReferent dep = (OrganizationReferent)com.pullenti.n2j.Utils.cast(res.referent, OrganizationReferent.class);
        if ((((((typ != null && typ.root != null && typ.root.canHasNumber)) || dep.getTypes().contains("отдел") || dep.getTypes().contains("отделение")) || dep.getTypes().contains("инспекция") || dep.getTypes().contains("лаборатория")) || dep.getTypes().contains("відділ") || dep.getTypes().contains("відділення")) || dep.getTypes().contains("інспекція") || dep.getTypes().contains("лабораторія")) {
            if (((t0.getPrevious() instanceof com.pullenti.ner.NumberToken) && (t0.getWhitespacesBeforeCount() < 2) && !t0.getPrevious().getMorph()._getClass().isNoun()) && t0.getPrevious().isWhitespaceBefore()) {
                String nn = ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t0.getPrevious(), com.pullenti.ner.NumberToken.class))).value).toString();
                if (dep.getNumber() == null || com.pullenti.n2j.Utils.stringsEq(dep.getNumber(), nn)) {
                    dep.setNumber(nn);
                    t0 = t0.getPrevious();
                    res.setBeginToken(t0);
                }
            }
        }
        if (dep.getTypes().contains("управление") || dep.getTypes().contains("департамент") || dep.getTypes().contains("управління")) {
            for(com.pullenti.ner.Slot s : dep.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_GEO) && (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class);
                    if (g.isState() && com.pullenti.n2j.Utils.stringsEq(g.getAlpha2(), "RU")) {
                        dep.getSlots().remove(s);
                        break;
                    }
                }
            }
        }
        com.pullenti.ner.Token t1 = res.getEndToken();
        if (t1.getNext() == null) 
            return;
        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
        if (br != null && (t1.getWhitespacesAfterCount() < 2) && br.isQuoteType()) {
            Object g = isGeo(br.getBeginToken().getNext(), false);
            if (g instanceof com.pullenti.ner.ReferentToken) {
                if ((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(g, com.pullenti.ner.ReferentToken.class))).getEndToken().getNext() == br.getEndToken()) {
                    dep.addGeoObject(g);
                    t1 = res.setEndToken(br.getEndToken());
                }
            }
            else if ((g instanceof com.pullenti.ner.Referent) && br.getBeginToken().getNext().getNext() == br.getEndToken()) {
                dep.addGeoObject(g);
                t1 = res.setEndToken(br.getEndToken());
            }
            else if (br.getBeginToken().getNext().isValue("О", null) || br.getBeginToken().getNext().isValue("ОБ", null)) {
            }
            else {
                String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (nam != null) {
                    dep.addName(nam, true, br.getBeginToken().getNext());
                    t1 = res.setEndToken(br.getEndToken());
                }
            }
        }
        boolean prep = false;
        if (t1.getNext() != null) {
            if (t1.getNext().getMorph()._getClass().isPreposition()) {
                if (t1.getNext().isValue("В", null) || t1.getNext().isValue("ПО", null)) {
                    t1 = t1.getNext();
                    prep = true;
                }
            }
        }
        for(int k = 0; k < 2; k++) {
            if (t1.getNext() == null) 
                return;
            com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(t1.getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            boolean ge = false;
            if (_geo != null) {
                if (!dep.addGeoObject(_geo)) 
                    return;
                res.setEndToken(t1.getNext());
                ge = true;
            }
            else {
                com.pullenti.ner.ReferentToken rgeo = t1.kit.processReferent("GEO", t1.getNext());
                if (rgeo != null) {
                    if (!rgeo.getMorph()._getClass().isAdjective()) {
                        if (!dep.addGeoObject(rgeo)) 
                            return;
                        res.setEndToken(rgeo.getEndToken());
                        ge = true;
                    }
                }
            }
            if (!ge) 
                return;
            t1 = res.getEndToken();
            if (t1.getNext() == null) 
                return;
            boolean isAnd = false;
            if (t1.getNext().isAnd()) 
                t1 = t1.getNext();
            if (t1 == null) 
                return;
        }
    }

    private static com.pullenti.ner.MetaToken _tryAttachOrgMedTyp(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        String s = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        if (((t != null && com.pullenti.n2j.Utils.stringsEq(s, "Г") && t.getNext() != null) && t.getNext().isCharOf("\\/.") && t.getNext().getNext() != null) && t.getNext().getNext().isValue("Б", null)) {
            com.pullenti.ner.Token t1 = t.getNext().getNext();
            if (t.getNext().isChar('.') && t1.getNext() != null && t1.getNext().isChar('.')) 
                t1 = t1.getNext();
            return com.pullenti.ner.MetaToken._new2142(t, t1, "городская больница", com.pullenti.ner.MorphCollection._new2141(com.pullenti.morph.MorphGender.FEMINIE));
        }
        if ((com.pullenti.n2j.Utils.stringsEq(s, "ИН") && t.getNext() != null && t.getNext().isHiphen()) && t.getNext().getNext() != null && t.getNext().getNext().isValue("Т", null)) 
            return com.pullenti.ner.MetaToken._new2142(t, t.getNext().getNext(), "институт", com.pullenti.ner.MorphCollection._new2141(com.pullenti.morph.MorphGender.MASCULINE));
        if ((com.pullenti.n2j.Utils.stringsEq(s, "Б") && t.getNext() != null && t.getNext().isHiphen()) && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken) && ((t.getNext().getNext().isValue("ЦА", null) || t.getNext().getNext().isValue("ЦУ", null)))) 
            return com.pullenti.ner.MetaToken._new2142(t, t.getNext().getNext(), "больница", com.pullenti.ner.MorphCollection._new2141(com.pullenti.morph.MorphGender.FEMINIE));
        if (com.pullenti.n2j.Utils.stringsEq(s, "ГКБ")) 
            return com.pullenti.ner.MetaToken._new2142(t, t, "городская клиническая больница", com.pullenti.ner.MorphCollection._new2141(com.pullenti.morph.MorphGender.FEMINIE));
        if (t.isValue("ПОЛИКЛИНИКА", null)) 
            return com.pullenti.ner.MetaToken._new2142(t, t, "поликлиника", com.pullenti.ner.MorphCollection._new2141(com.pullenti.morph.MorphGender.FEMINIE));
        if (t.isValue("БОЛЬНИЦА", null)) 
            return com.pullenti.ner.MetaToken._new2142(t, t, "больница", com.pullenti.ner.MorphCollection._new2141(com.pullenti.morph.MorphGender.FEMINIE));
        if (t.isValue("ДЕТСКИЙ", null)) {
            com.pullenti.ner.MetaToken mt = _tryAttachOrgMedTyp(t.getNext());
            if (mt != null) {
                mt.setBeginToken(t);
                mt.tag = (mt.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE ? "детская" : "детский") + " " + mt.tag;
                return mt;
            }
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken tryAttachOrgMed(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        if (t == null) 
            return null;
        if (t.getPrevious() == null || t.getPrevious().getPrevious() == null) 
            return null;
        if ((t.getPrevious().getMorph()._getClass().isPreposition() && t.getPrevious().getPrevious().isValue("ДОСТАВИТЬ", null)) || t.getPrevious().getPrevious().isValue("ПОСТУПИТЬ", null)) {
        }
        else 
            return null;
        if (t.isValue("ТРАВМПУНКТ", null)) 
            t = t.getNext();
        else if (t.isValue("ТРАВМ", null)) {
            if ((t.getNext() != null && t.getNext().isChar('.') && t.getNext().getNext() != null) && t.getNext().getNext().isValue("ПУНКТ", null)) 
                t = t.getNext().getNext().getNext();
        }
        if (t instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.ner.MetaToken tt = _tryAttachOrgMedTyp(t.getNext());
            if (tt != null) {
                OrganizationReferent org1 = new OrganizationReferent();
                org1.addTypeStr((((String)com.pullenti.n2j.Utils.cast(tt.tag, String.class))).toLowerCase());
                org1.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                return new com.pullenti.ner.ReferentToken(org1, t, tt.getEndToken(), null);
            }
        }
        com.pullenti.ner.MetaToken typ = _tryAttachOrgMedTyp(t);
        String adj = null;
        if (typ == null && t.chars.isCapitalUpper() && t.getMorph()._getClass().isAdjective()) {
            typ = _tryAttachOrgMedTyp(t.getNext());
            if (typ != null) 
                adj = t.getNormalCaseText(com.pullenti.morph.MorphClass.ADJECTIVE, true, typ.getMorph().getGender(), false);
        }
        if (typ == null) 
            return null;
        OrganizationReferent _org = new OrganizationReferent();
        String s = ((String)com.pullenti.n2j.Utils.cast(typ.tag, String.class));
        _org.addTypeStr(s.toLowerCase());
        if (adj != null) 
            _org.addName(adj + " " + s.toUpperCase(), true, null);
        com.pullenti.ner.Token t1 = typ.getEndToken();
        com.pullenti.ner.org.internal.OrgItemEponymToken epo = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
        if (epo != null) {
            for(String v : epo.eponyms) {
                _org.addEponym(v);
            }
            t1 = epo.getEndToken();
        }
        if (t1.getNext() instanceof com.pullenti.ner.TextToken) {
            if (t1.getNext().isValue("СКЛИФОСОФСКОГО", null) || t1.getNext().isValue("СЕРБСКОГО", null) || t1.getNext().isValue("БОТКИНА", null)) {
                _org.addEponym((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class))).term);
                t1 = t1.getNext();
            }
        }
        com.pullenti.ner.org.internal.OrgItemNumberToken num = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, null);
        if (num != null) {
            _org.setNumber(num.number);
            t1 = num.getEndToken();
        }
        if (_org.getSlots().size() > 1) 
            return new com.pullenti.ner.ReferentToken(_org, t, t1, null);
        return null;
    }

    private com.pullenti.ner.ReferentToken tryAttachPropNames(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        com.pullenti.ner.ReferentToken rt = _tryAttachOrgSportAssociations(t, ad);
        if (rt == null) 
            rt = _tryAttachOrgNames(t, ad);
        if (rt == null) 
            return null;
        com.pullenti.ner.Token t0 = rt.getBeginToken().getPrevious();
        if ((t0 instanceof com.pullenti.ner.TextToken) && (t0.getWhitespacesAfterCount() < 2) && t0.getMorph()._getClass().isAdjective()) {
            com.pullenti.ner.ReferentToken rt0 = t0.kit.processReferent("GEO", t0);
            if (rt0 != null && rt0.getMorph()._getClass().isAdjective()) {
                rt.setBeginToken(rt0.getBeginToken());
                (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))).addGeoObject(rt0);
            }
        }
        if (rt.getEndToken().getWhitespacesAfterCount() < 2) {
            com.pullenti.ner.Token tt1 = attachTailAttributes((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class), rt.getEndToken().getNext(), ad, true, AttachType.NORMAL, true);
            if (tt1 != null) 
                rt.setEndToken(tt1);
        }
        return rt;
    }

    private com.pullenti.ner.ReferentToken _tryAttachOrgNames(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.core.BracketSequenceToken br = null;
        com.pullenti.ner.Token tName1 = null;
        OrgProfile prof = OrgProfile.UNDEFINED;
        OrgProfile prof2 = OrgProfile.UNDEFINED;
        String typ = null;
        boolean ok = false;
        com.pullenti.ner.ReferentToken uri = null;
        if (!((t instanceof com.pullenti.ner.TextToken)) || !t.chars.isLetter()) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                if ((((br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 15)))) != null) 
                    t = t0.getNext();
                else 
                    return null;
            }
            else if (t.getReferent() != null && com.pullenti.n2j.Utils.stringsEq(t.getReferent().getTypeName(), "URI")) {
                com.pullenti.ner.Referent r = t.getReferent();
                String s = r.getStringValue("SCHEME");
                if (com.pullenti.n2j.Utils.stringsEq(s, "HTTP")) {
                    prof = OrgProfile.MEDIA;
                    tName1 = t;
                }
            }
            else if ((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && t.chars.isLetter()) {
                if ((t.getNext() != null && (t.getNext().getWhitespacesAfterCount() < 3) && t.getNext().chars.isLatinLetter()) && ((t.getNext().isValue("POST", null) || t.getNext().isValue("TODAY", null)))) {
                    tName1 = t.getNext();
                    if (_isStdPressEnd(tName1)) 
                        prof = OrgProfile.MEDIA;
                }
                else 
                    return null;
            }
            else 
                return null;
        }
        else if (t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "ИА")) {
            prof = OrgProfile.MEDIA;
            t = t.getNext();
            typ = "информационное агенство";
            if (t == null || t.getWhitespacesBeforeCount() > 2) 
                return null;
            com.pullenti.ner.ReferentToken re = _tryAttachOrgNames(t, ad);
            if (re != null) {
                re.setBeginToken(t0);
                (((OrganizationReferent)com.pullenti.n2j.Utils.cast(re.referent, OrganizationReferent.class))).addTypeStr(typ);
                return re;
            }
            if (t.chars.isLatinLetter()) {
                com.pullenti.ner.org.internal.OrgItemEngItem nam = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttach(t, false);
                if (nam != null) {
                    ok = true;
                    tName1 = nam.getEndToken();
                }
                else {
                    com.pullenti.ner.org.internal.OrgItemNameToken nam1 = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t, null, false, true);
                    if (nam1 != null) {
                        ok = true;
                        tName1 = nam1.getEndToken();
                    }
                }
            }
        }
        else if (((t.chars.isLatinLetter() && t.getNext() != null && t.getNext().isChar('.')) && !t.getNext().isWhitespaceAfter() && t.getNext().getNext() != null) && t.getNext().getNext().chars.isLatinLetter()) {
            tName1 = t.getNext().getNext();
            prof = OrgProfile.MEDIA;
            if (tName1.getNext() == null) {
            }
            else if (tName1.getWhitespacesAfterCount() > 0) {
            }
            else if (tName1.getNext().isChar(',')) {
            }
            else if (tName1.getLengthChar() > 1 && tName1.getNext().isCharOf(".") && tName1.getNext().isWhitespaceAfter()) {
            }
            else if (br != null && br.getEndToken().getPrevious() == tName1) {
            }
            else 
                return null;
        }
        else if (t.chars.isAllLower() && br == null) 
            return null;
        com.pullenti.ner.Token t00 = t0.getPrevious();
        if (t00 != null && t00.getMorph()._getClass().isAdjective()) 
            t00 = t00.getPrevious();
        if (t00 != null && t00.getMorph()._getClass().isPreposition()) 
            t00 = t00.getPrevious();
        com.pullenti.ner.core.TerminToken tok = m_PropNames.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok == null && t.chars.isLatinLetter() && t.isValue("THE", null)) 
            tok = m_PropNames.tryParse(t.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
        if (tok != null) 
            prof = (OrgProfile)tok.termin.tag;
        if (br != null) {
            com.pullenti.ner.Token t1 = br.getEndToken().getPrevious();
            for(com.pullenti.ner.Token tt = br.getBeginToken(); tt != null && tt.endChar <= br.endChar; tt = tt.getNext()) {
                com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                if (com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.VERB)) 
                    return null;
                if (com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.ADVERB)) 
                    return null;
                if (tt.isCharOf("?:")) 
                    return null;
                if (tt == br.getBeginToken().getNext() || tt == br.getEndToken().getPrevious()) {
                    if (((tt.isValue("ЖУРНАЛ", null) || tt.isValue("ГАЗЕТА", null) || tt.isValue("ПРАВДА", null)) || tt.isValue("ИЗВЕСТИЯ", null) || tt.isValue("НОВОСТИ", null)) || tt.isValue("ВЕДОМОСТИ", null)) {
                        ok = true;
                        prof = OrgProfile.MEDIA;
                        prof2 = OrgProfile.PRESS;
                    }
                }
            }
            if (!ok && _isStdPressEnd(t1)) {
                if (br.getBeginToken().getNext().chars.isCapitalUpper() && (br.getLengthChar() < 15)) {
                    ok = true;
                    prof = OrgProfile.MEDIA;
                    prof2 = OrgProfile.PRESS;
                }
            }
            else if (t1.isValue("FM", null)) {
                ok = true;
                prof = OrgProfile.MEDIA;
                typ = "радиостанция";
            }
            else if (((t1.isValue("РУ", null) || t1.isValue("RU", null) || t1.isValue("NET", null))) && t1.getPrevious() != null && t1.getPrevious().isChar('.')) 
                prof = OrgProfile.MEDIA;
            com.pullenti.ner.Token b = br.getBeginToken().getNext();
            if (b.isValue("THE", null)) 
                b = b.getNext();
            if (_isStdPressEnd(b) || b.isValue("ВЕЧЕРНИЙ", null)) {
                ok = true;
                prof = OrgProfile.MEDIA;
            }
        }
        if ((tok == null && !ok && tName1 == null) && prof == OrgProfile.UNDEFINED) {
            if (br == null || !t.chars.isCapitalUpper()) 
                return null;
            com.pullenti.ner.core.TerminToken tok1 = m_PropPref.tryParse(t00, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok1 != null) {
                OrgProfile pr = (OrgProfile)tok1.termin.tag;
                if (prof != OrgProfile.UNDEFINED && prof != pr) 
                    return null;
            }
            else {
                if (t.chars.isLetter() && !t.chars.isCyrillicLetter()) {
                    for(com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) 
                            continue;
                        if (tt.getWhitespacesBeforeCount() > 2) 
                            break;
                        if (!tt.chars.isLetter() || tt.chars.isCyrillicLetter()) 
                            break;
                        if (_isStdPressEnd(tt)) {
                            tName1 = tt;
                            prof = OrgProfile.MEDIA;
                            ok = true;
                            break;
                        }
                    }
                }
                if (tName1 == null) 
                    return null;
            }
        }
        if (tok != null) {
            if (tok.getBeginToken().chars.isAllLower() && br == null) {
            }
            else if (tok.getBeginToken() != tok.getEndToken()) 
                ok = true;
            else if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tok.getBeginToken())) 
                return null;
            else if (br == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tok.getBeginToken().getPrevious(), false, false)) 
                return null;
            else if (tok.chars.isAllUpper()) 
                ok = true;
        }
        if (!ok) {
            int cou = 0;
            for(com.pullenti.ner.Token tt = t0.getPrevious(); tt != null && (cou < 100); tt = tt.getPrevious(),cou++) {
                if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt.getNext())) 
                    break;
                com.pullenti.ner.core.TerminToken tok1 = m_PropPref.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok1 != null) {
                    OrgProfile pr = (OrgProfile)tok1.termin.tag;
                    if (prof != OrgProfile.UNDEFINED && prof != pr) 
                        continue;
                    if (tok1.termin.tag2 != null && prof == OrgProfile.UNDEFINED) 
                        continue;
                    prof = pr;
                    ok = true;
                    break;
                }
                OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org1 != null && org1.findSlot(OrganizationReferent.ATTR_PROFILE, null, true) != null) {
                    if ((org1.containsProfile(prof) || prof == OrgProfile.UNDEFINED)) {
                        ok = true;
                        prof = org1.getProfiles().get(0);
                        break;
                    }
                }
            }
            cou = 0;
            if (!ok) {
                for(com.pullenti.ner.Token tt = t.getNext(); tt != null && (cou < 10); tt = tt.getNext(),cou++) {
                    if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(tt) && prof != OrgProfile.SPORT) 
                        break;
                    com.pullenti.ner.core.TerminToken tok1 = m_PropPref.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
                    if (tok1 != null) {
                        OrgProfile pr = (OrgProfile)tok1.termin.tag;
                        if (prof != OrgProfile.UNDEFINED && prof != pr) 
                            continue;
                        if (tok1.termin.tag2 != null && prof == OrgProfile.UNDEFINED) 
                            continue;
                        prof = pr;
                        ok = true;
                        break;
                    }
                    OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                    if (org1 != null && org1.findSlot(OrganizationReferent.ATTR_PROFILE, null, true) != null) {
                        if ((org1.containsProfile(prof) || prof == OrgProfile.UNDEFINED)) {
                            ok = true;
                            prof = org1.getProfiles().get(0);
                            break;
                        }
                    }
                }
            }
            if (!ok) 
                return null;
        }
        if (prof == OrgProfile.UNDEFINED) 
            return null;
        OrganizationReferent _org = new OrganizationReferent();
        _org.addProfile(prof);
        if (prof2 != OrgProfile.UNDEFINED) 
            _org.addProfile(prof2);
        if (prof == OrgProfile.SPORT) 
            _org.addTypeStr("спортивный клуб");
        if (typ != null) 
            _org.addTypeStr(typ);
        if (br != null && ((tok == null || tok.getEndToken() != br.getEndToken().getPrevious()))) {
            String nam;
            if (tok != null) {
                nam = com.pullenti.ner.core.MiscHelper.getTextValue(tok.getEndToken().getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (nam != null) 
                    nam = tok.termin.getCanonicText() + " " + nam;
                else 
                    nam = tok.termin.getCanonicText();
            }
            else 
                nam = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
            if (nam != null) 
                _org.addName(nam, true, null);
        }
        else if (tName1 != null) {
            String nam = com.pullenti.ner.core.MiscHelper.getTextValue(t, tName1, com.pullenti.ner.core.GetTextAttr.NO);
            if (nam != null) 
                nam = nam.replace(". ", ".");
            _org.addName(nam, true, null);
        }
        else if (tok != null) {
            _org.addName(tok.termin.getCanonicText(), true, null);
            if (tok.termin.acronym != null) 
                _org.addName(tok.termin.acronym, true, null);
            if (tok.termin.additionalVars != null) {
                for(com.pullenti.ner.core.Termin v : tok.termin.additionalVars) {
                    _org.addName(v.getCanonicText(), true, null);
                }
            }
        }
        else 
            return null;
        if ((((((prof.value()) & (OrgProfile.MEDIA.value()))) != (OrgProfile.UNDEFINED.value()))) && t0.getPrevious() != null) {
            if ((t0.getPrevious().isValue("ЖУРНАЛ", null) || t0.getPrevious().isValue("ИЗДАНИЕ", null) || t0.getPrevious().isValue("ИЗДАТЕЛЬСТВО", null)) || t0.getPrevious().isValue("АГЕНТСТВО", null)) {
                t0 = t0.getPrevious();
                _org.addTypeStr(t0.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase());
                if (!t0.getPrevious().isValue("АГЕНТСТВО", null)) 
                    _org.addProfile(OrgProfile.PRESS);
            }
        }
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(_org, t0, t, null);
        if (br != null) 
            res.setEndToken(br.getEndToken());
        else if (tok != null) 
            res.setEndToken(tok.getEndToken());
        else if (tName1 != null) 
            res.setEndToken(tName1);
        else 
            return null;
        return res;
    }

    private static boolean _isStdPressEnd(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return false;
        String str = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        if ((((((((com.pullenti.n2j.Utils.stringsEq(str, "NEWS") || com.pullenti.n2j.Utils.stringsEq(str, "PRESS") || com.pullenti.n2j.Utils.stringsEq(str, "PRESSE")) || com.pullenti.n2j.Utils.stringsEq(str, "ПРЕСС") || com.pullenti.n2j.Utils.stringsEq(str, "НЬЮС")) || com.pullenti.n2j.Utils.stringsEq(str, "TIMES") || com.pullenti.n2j.Utils.stringsEq(str, "TIME")) || com.pullenti.n2j.Utils.stringsEq(str, "ТАЙМС") || com.pullenti.n2j.Utils.stringsEq(str, "POST")) || com.pullenti.n2j.Utils.stringsEq(str, "ПОСТ") || com.pullenti.n2j.Utils.stringsEq(str, "TODAY")) || com.pullenti.n2j.Utils.stringsEq(str, "ТУДЕЙ") || com.pullenti.n2j.Utils.stringsEq(str, "DAILY")) || com.pullenti.n2j.Utils.stringsEq(str, "ДЕЙЛИ") || com.pullenti.n2j.Utils.stringsEq(str, "ИНФОРМ")) || com.pullenti.n2j.Utils.stringsEq(str, "INFORM")) 
            return true;
        return false;
    }

    private com.pullenti.ner.ReferentToken _tryAttachOrgSportAssociations(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        if (t == null) 
            return null;
        int cou = 0;
        String typ = null;
        com.pullenti.ner.Token t1 = null;
        com.pullenti.ner.geo.GeoReferent _geo = null;
        if (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if (rt.getEndToken().isValue("ФЕДЕРАЦИЯ", null) || rt.getBeginToken().isValue("ФЕДЕРАЦИЯ", null)) {
                typ = "федерация";
                _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
            }
            t1 = t;
            if (t.getPrevious() != null && t.getPrevious().getMorph()._getClass().isAdjective()) {
                if (m_Sports.tryParse(t.getPrevious(), com.pullenti.ner.core.TerminParseAttr.NO) != null) {
                    cou++;
                    t = t.getPrevious();
                }
            }
        }
        else {
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt == null) 
                return null;
            if (npt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                return null;
            if (((npt.noun.isValue("АССОЦИАЦИЯ", null) || npt.noun.isValue("ФЕДЕРАЦИЯ", null) || npt.noun.isValue("СОЮЗ", null)) || npt.noun.isValue("СБОРНАЯ", null) || npt.noun.isValue("КОМАНДА", null)) || npt.noun.isValue("КЛУБ", null)) 
                typ = npt.noun.getNormalCaseText(com.pullenti.morph.MorphClass.NOUN, true, com.pullenti.morph.MorphGender.UNDEFINED, false).toLowerCase();
            else if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper() && com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, "ФК")) 
                typ = "команда";
            else 
                return null;
            if (com.pullenti.n2j.Utils.stringsEq(typ, "команда")) 
                cou--;
            for(com.pullenti.ner.MetaToken a : npt.adjectives) {
                com.pullenti.ner.core.TerminToken tok = m_Sports.tryParse(a.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null) 
                    cou++;
                else if (a.getBeginToken().isValue("ОЛИМПИЙСКИЙ", null)) 
                    cou++;
            }
            if (t1 == null) 
                t1 = npt.getEndToken();
        }
        com.pullenti.ner.Token t11 = t1;
        String propname = null;
        String delWord = null;
        for(com.pullenti.ner.Token tt = t1.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.getWhitespacesBeforeCount() > 3) 
                break;
            if (tt.isCommaAnd()) 
                continue;
            if (tt.getMorph()._getClass().isPreposition() && !tt.getMorph()._getClass().isAdverb() && !tt.getMorph()._getClass().isVerb()) 
                continue;
            if (tt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) {
                t1 = tt;
                _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                if (com.pullenti.n2j.Utils.stringsEq(typ, "сборная")) 
                    cou++;
                continue;
            }
            if (tt.isValue("СТРАНА", null) && (tt instanceof com.pullenti.ner.TextToken)) {
                t1 = (t11 = tt);
                delWord = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
                continue;
            }
            com.pullenti.ner.core.TerminToken tok = m_Sports.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                cou++;
                t1 = (t11 = (tt = tok.getEndToken()));
                continue;
            }
            if (tt.chars.isAllLower() || tt.getMorphClassInDictionary().isVerb()) {
            }
            else 
                tok = m_PropNames.tryParse(tt, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                propname = tok.termin.getCanonicText();
                cou++;
                t1 = (tt = tok.getEndToken());
                if (cou == 0 && com.pullenti.n2j.Utils.stringsEq(typ, "команда")) 
                    cou++;
                continue;
            }
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null) 
                    break;
                tok = m_PropNames.tryParse(tt.getNext(), com.pullenti.ner.core.TerminParseAttr.NO);
                if (tok != null || cou > 0) {
                    propname = com.pullenti.ner.core.MiscHelper.getTextValue(tt.getNext(), br.getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    cou++;
                    tt = (t1 = br.getEndToken());
                    continue;
                }
                break;
            }
            com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(tt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt1 == null) 
                break;
            tok = m_Sports.tryParse(npt1.noun.getBeginToken(), com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok == null) 
                break;
            cou++;
            t1 = (t11 = (tt = tok.getEndToken()));
        }
        if (cou <= 0) 
            return null;
        OrganizationReferent _org = new OrganizationReferent();
        _org.addTypeStr(typ);
        if (com.pullenti.n2j.Utils.stringsEq(typ, "федерация")) 
            _org.addTypeStr("ассоциация");
        String _name = com.pullenti.ner.core.MiscHelper.getTextValue(t, t11, com.pullenti.ner.core.GetTextAttr.of((com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()) | (com.pullenti.ner.core.GetTextAttr.IGNOREGEOREFERENT.value())));
        if (_name != null && delWord != null) {
            if ((_name.indexOf(" " + delWord) >= 0)) 
                _name = _name.replace(" " + delWord, "");
        }
        if (_name != null) 
            _name = _name.replace(" РОССИЯ", "").replace(" РОССИИ", "");
        if (propname != null) {
            _org.addName(propname, true, null);
            if (_name != null) 
                _org.addTypeStr(_name.toLowerCase());
        }
        else if (_name != null) 
            _org.addName(_name, true, null);
        if (_geo != null) 
            _org.addGeoObject(_geo);
        _org.addProfile(OrgProfile.SPORT);
        return new com.pullenti.ner.ReferentToken(_org, t, t1, null);
    }

    private static com.pullenti.ner.core.TerminCollection m_Sports;

    private static com.pullenti.ner.core.TerminCollection m_PropNames;

    private static com.pullenti.ner.core.TerminCollection m_PropPref;

    private static void _initSport() {
        m_Sports = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"акробатика;акробатический;акробат", "бадминтон;бадминтонный;бадминтонист", "баскетбол;баскетбольный;баскетболист", "бейсбол;бейсбольный;бейсболист", "биатлон;биатлонный;биатлонист", "бильярд;бильярдный;бильярдист", "бобслей;бобслейный;бобслеист", "боулинг", "боевое искуство", "бокс;боксерский;боксер", "борьба;борец", "водное поло", "волейбол;волейбольный;волейболист", "гандбол;гандбольный;гандболист", "гольф;гольфный;гольфист", "горнолыжный спорт", "слалом;;слаломист", "сквош", "гребля", "дзюдо;дзюдоистский;дзюдоист", "карате;;каратист", "керлинг;;керлингист", "коньки;конькобежный;конькобежец", "легкая атлетика;легкоатлетический;легкоатлет", "лыжных гонок", "мотоцикл;мотоциклетный;мотоциклист", "тяжелая атлетика;тяжелоатлетический;тяжелоатлет", "ориентирование", "плавание;;пловец", "прыжки", "регби;;регбист", "пятиборье", "гимнастика;гимнастический;гимнаст", "самбо;;самбист", "сумо;;сумист", "сноуборд;сноубордический;сноубордист", "софтбол;софтбольный;софтболист", "стрельба;стрелковый", "спорт;спортивный", "теннис;теннисный;теннисист", "триатлон", "тхэквондо", "ушу;;ушуист", "фехтование;фехтовальный;фехтовальщик", "фигурное катание;;фигурист", "фристайл;фристальный", "футбол;футбольный;футболист", "мини-футбол", "хоккей;хоккейный;хоккеист", "хоккей на траве", "шахматы;шахматный;шахматист", "шашки;шашечный"}) {
            String[] pp = com.pullenti.n2j.Utils.split(s.toUpperCase(), String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
            t.initByNormalText(pp[0], com.pullenti.morph.MorphLang.RU);
            if (pp.length > 1 && !com.pullenti.n2j.Utils.isNullOrEmpty(pp[1])) 
                t.addVariant(pp[1], true);
            if (pp.length > 2 && !com.pullenti.n2j.Utils.isNullOrEmpty(pp[2])) 
                t.addVariant(pp[2], true);
            m_Sports.add(t);
        }
        for(String s : new String[] {"байдарка", "каноэ", "лук", "трава", "коньки", "трамплин", "двоеборье", "батут", "вода", "шпага", "сабля", "лыжи", "скелетон"}) {
            m_Sports.add(com.pullenti.ner.core.Termin._new2134(s.toUpperCase(), s));
        }
        m_PropNames = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"СПАРТАК", "ЦСКА", "ЗЕНИТ!", "ТЕРЕК", "КРЫЛЬЯ СОВЕТОВ", "ДИНАМО", "АНЖИ", "КУБАНЬ", "АЛАНИЯ", "ТОРПЕДО", "АРСЕНАЛ!", "ЛОКОМОТИВ", "МЕТАЛЛУРГ!", "РОТОР", "СКА", "СОКОЛ!", "ХИМИК!", "ШИННИК", "РУБИН", "ШАХТЕР", "САЛАВАТ ЮЛАЕВ", "ТРАКТОР!", "АВАНГАРД!", "АВТОМОБИЛИСТ!", "АТЛАНТ!", "ВИТЯЗЬ!", "НАЦИОНАЛЬНАЯ ХОККЕЙНАЯ ЛИГА;НХЛ", "КОНТИНЕНТАЛЬНАЯ ХОККЕЙНАЯ ЛИГА;КХЛ", "СОЮЗ ЕВРОПЕЙСКИХ ФУТБОЛЬНЫХ АССОЦИАЦИЙ;УЕФА;UEFA", "Женская теннисная ассоциация;WTA", "Международная федерация бокса;IBF", "Всемирная боксерская организация;WBO", "РЕАЛ", "МАНЧЕСТЕР ЮНАЙТЕД", "манчестер сити", "БАРСЕЛОНА!", "БАВАРИЯ!", "ЧЕЛСИ", "ЛИВЕРПУЛЬ!", "ЮВЕНТУС", "НАПОЛИ", "БОЛОНЬЯ", "ФУЛХЭМ", "ЭВЕРТОН", "ФИЛАДЕЛЬФИЯ", "ПИТТСБУРГ", "ИНТЕР!", "Аякс", "ФЕРРАРИ;FERRARI", "РЕД БУЛЛ;RED BULL", "МАКЛАРЕН;MCLAREN", "МАКЛАРЕН-МЕРСЕДЕС;MCLAREN-MERCEDES"}) {
            String ss = s.toUpperCase();
            boolean isBad = false;
            if (ss.endsWith("!")) {
                isBad = true;
                ss = ss.substring(0, 0+(ss.length() - 1));
            }
            String[] pp = com.pullenti.n2j.Utils.split(ss, String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new118(pp[0], OrgProfile.SPORT);
            if (!isBad) 
                t.tag2 = ss;
            if (pp.length > 1) {
                if (pp[1].length() < 4) 
                    t.acronym = pp[1];
                else 
                    t.addVariant(pp[1], false);
            }
            m_PropNames.add(t);
        }
        for(String s : new String[] {"ИТАР ТАСС;ТАСС;Телеграфное агентство советского союза", "Интерфакс;Interfax", "REGNUM", "ЛЕНТА.РУ;Lenta.ru", "Частный корреспондент;ЧасКор", "РИА Новости;Новости!;АПН", "Росбалт;RosBalt", "УНИАН", "ИНФОРОС;inforos", "Эхо Москвы", "Сноб!", "Серебряный дождь", "Вечерняя Москва;Вечерка", "Московский Комсомолец;Комсомолка", "Коммерсантъ;Коммерсант", "Афиша", "Аргументы и факты;АИФ", "Викиновости", "РосБизнесКонсалтинг;РБК", "Газета.ру", "Русский Репортер!", "Ведомости", "Вести!", "Рамблер Новости", "Живой Журнал;ЖЖ;livejournal;livejournal.ru", "Новый Мир", "Новая газета", "Правда!", "Известия!", "Бизнес!", "Русская жизнь!", "НТВ Плюс", "НТВ", "ВГТРК", "ТНТ", "Муз ТВ;МузТВ", "АСТ", "Эксмо", "Астрель", "Терра!", "Финанс!", "Собеседник!", "Newsru.com", "Nature!", "Россия сегодня;Russia Today;RT!", "БЕЛТА", "Ассошиэйтед Пресс;Associated Press", "France Press;France Presse;Франс пресс;Agence France Presse;AFP", "СИНЬХУА", "Gallup", "Cable News Network;CNN", "CBS News", "ABC News", "GoogleNews;Google News", "FoxNews;Fox News", "Reuters;Рейтер", "British Broadcasting Corporation;BBC;БиБиСи;BBC News", "MSNBC", "Голос Америки", "Аль Джазира;Al Jazeera", "Радио Свобода", "Радио Свободная Европа", "Guardian;Гардиан", "Daily Telegraph", "Times;Таймс!", "Independent!", "Financial Times", "Die Welt", "Bild!", "La Pepublica;Република!", "Le Monde", "People Daily", "BusinessWeek", "Economist!", "Forbes;Форбс", "Los Angeles Times", "New York Times", "Wall Street Journal;WSJ", "Washington Post", "Le Figaro;Фигаро", "Bloomberg", "DELFI!"}) {
            String ss = s.toUpperCase();
            boolean isBad = false;
            if (ss.endsWith("!")) {
                isBad = true;
                ss = ss.substring(0, 0+(ss.length() - 1));
            }
            String[] pp = com.pullenti.n2j.Utils.split(ss, String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new118(pp[0], OrgProfile.MEDIA);
            if (!isBad) 
                t.tag2 = ss;
            for(int ii = 1; ii < pp.length; ii++) {
                if ((pp[ii].length() < 4) && t.acronym == null) 
                    t.acronym = pp[ii];
                else 
                    t.addVariant(pp[ii], false);
            }
            m_PropNames.add(t);
        }
        for(String s : new String[] {"Машина времени!", "ДДТ", "Биттлз;Bittles", "ABBA;АББА", "Океан Эльзы;Океан Эльзи", "Аквариум!", "Крематорий!", "Наутилус;Наутилус Помпилиус!", "Пусси Райот;Пусси Риот;Pussy Riot", "Кино!", "Алиса!", "Агата Кристи!", "Чайф", "Ария!", "Земфира!", "Браво!", "Черный кофе!", "Воскресение!", "Урфин Джюс", "Сплин!", "Пикник!", "Мумий Троль", "Коррозия металла", "Арсенал!", "Ночные снайперы!", "Любэ", "Ласковый май!", "Noize MC", "Linkin Park", "ac dc", "green day!", "Pink Floyd;Пинк Флойд", "Depeche Mode", "Bon Jovi", "Nirvana;Нирвана!", "Queen;Квин!", "Nine Inch Nails", "Radioheads", "Pet Shop Boys", "Buggles"}) {
            String ss = s.toUpperCase();
            boolean isBad = false;
            if (ss.endsWith("!")) {
                isBad = true;
                ss = ss.substring(0, 0+(ss.length() - 1));
            }
            String[] pp = com.pullenti.n2j.Utils.split(ss, String.valueOf(';'), false);
            com.pullenti.ner.core.Termin t = com.pullenti.ner.core.Termin._new118(pp[0], OrgProfile.MUSIC);
            if (!isBad) 
                t.tag2 = ss;
            for(int ii = 1; ii < pp.length; ii++) {
                if ((pp[ii].length() < 4) && t.acronym == null) 
                    t.acronym = pp[ii];
                else 
                    t.addVariant(pp[ii], false);
            }
            m_PropNames.add(t);
        }
        m_PropPref = new com.pullenti.ner.core.TerminCollection();
        for(String s : new String[] {"ФАНАТ", "БОЛЕЛЬЩИК", "гонщик", "вратарь", "нападающий", "голкипер", "полузащитник", "полу-защитник", "центрфорвард", "центр-форвард", "форвард", "игрок", "легионер", "спортсмен"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new118(s.toUpperCase(), OrgProfile.SPORT));
        }
        for(String s : new String[] {"защитник", "капитан", "пилот", "игра", "поле", "стадион", "гонка", "чемпионат", "турнир", "заезд", "матч", "кубок", "олипмиада", "финал", "полуфинал", "победа", "поражение", "разгром", "дивизион", "олипмиада", "финал", "полуфинал", "играть", "выигрывать", "выиграть", "проигрывать", "проиграть", "съиграть"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new120(s.toUpperCase(), OrgProfile.SPORT, s));
        }
        for(String s : new String[] {"корреспондент", "фотокорреспондент", "репортер", "журналист", "тележурналист", "телеоператор", "главный редактор", "главред", "телеведущий", "редколлегия", "обозреватель", "сообщать", "сообщить", "передавать", "передать", "писать", "написать", "издавать", "пояснить", "пояснять", "разъяснить", "разъяснять", "сказать", "говорить", "спрашивать", "спросить", "отвечать", "ответить", "выяснять", "выяснить", "цитировать", "процитировать", "рассказать", "рассказывать", "информировать", "проинформировать", "поведать", "напечатать", "напоминать", "напомнить", "узнать", "узнавать", "репортаж", "интервью", "информации", "сведение", "ИА", "информагенство", "информагентство", "информационный", "газета", "журнал"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new118(s.toUpperCase(), OrgProfile.MEDIA));
        }
        for(String s : new String[] {"сообщение", "статья", "номер", "журнал", "издание", "издательство", "агентство", "цитата", "редактор", "комментатор", "по данным", "оператор", "вышедший", "отчет", "вопрос", "читатель", "слушатель", "телезритель", "источник", "собеедник"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new120(s.toUpperCase(), OrgProfile.MEDIA, s));
        }
        for(String s : new String[] {"музыкант", "певец", "певица", "ударник", "гитарист", "клавишник", "солист", "солистка", "исполнитель", "исполнительница", "исполнять", "исполнить", "концерт", "гастроль", "выступление", "известный", "известнейший", "популярный", "популярнейший", "рокгруппа", "панкгруппа", "группа", "альбом", "пластинка", "грампластинка", "концертный", "музыка", "песня", "сингл", "хит", "суперхит", "запись", "студия"}) {
            m_PropPref.add(com.pullenti.ner.core.Termin._new118(s.toUpperCase(), OrgProfile.MEDIA));
        }
    }

    private com.pullenti.ner.ReferentToken tryAttachArmy(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        if (!((t instanceof com.pullenti.ner.NumberToken)) || t.getWhitespacesAfterCount() > 2) 
            return null;
        com.pullenti.ner.org.internal.OrgItemTypeToken typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true, ad);
        if (typ == null) 
            return null;
        if (typ.root != null && typ.root.profiles.contains(OrgProfile.ARMY)) {
            com.pullenti.ner.ReferentToken rt = tryAttachOrg(t.getNext(), ad, AttachType.HIGH, null, false, 0, -1);
            if (rt != null) {
                if (rt.getBeginToken() == typ.getBeginToken()) {
                    rt.setBeginToken(t);
                    (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))).setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
                }
                return rt;
            }
            OrganizationReferent _org = new OrganizationReferent();
            _org.addType(typ, false);
            _org.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.NumberToken.class))).value).toString());
            return new com.pullenti.ner.ReferentToken(_org, t, typ.getEndToken(), null);
        }
        return null;
    }

    private static final int maxOrgName = 200;

    private com.pullenti.ner.ReferentToken tryAttachOrg(com.pullenti.ner.Token t, OrgAnalyzerData ad, AttachType attachTyp, com.pullenti.ner.org.internal.OrgItemTypeToken multTyp, boolean isAdditionalAttach, int level, int step) {
        if (level > 2 || t == null) 
            return null;
        if (t.chars.isLatinLetter() && com.pullenti.ner.core.MiscHelper.isEngArticle(t)) {
            com.pullenti.ner.ReferentToken re = tryAttachOrg(t.getNext(), ad, attachTyp, multTyp, isAdditionalAttach, level, step);
            if (re != null) {
                re.setBeginToken(t);
                return re;
            }
        }
        OrganizationReferent _org = null;
        java.util.ArrayList<com.pullenti.ner.org.internal.OrgItemTypeToken> types = null;
        if (multTyp != null) {
            types = new java.util.ArrayList<>();
            types.add(multTyp);
        }
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token t1 = t;
        java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> otExLi = null;
        com.pullenti.ner.org.internal.OrgItemTypeToken typ = null;
        boolean hiph = false;
        boolean specWordBefore = false;
        boolean ok;
        boolean inBrackets = false;
        com.pullenti.ner.ReferentToken rt0 = null;
        for(; t != null; t = t.getNext()) {
            if (t.getReferent() instanceof OrganizationReferent) 
                break;
            rt0 = attachGlobalOrg(t, attachTyp, ad, null);
            if ((rt0 == null && typ != null && typ.geo != null) && typ.getBeginToken().getNext() == typ.getEndToken()) {
                rt0 = attachGlobalOrg(typ.getEndToken(), attachTyp, ad, typ.geo);
                if (rt0 != null) 
                    rt0.setBeginToken(typ.getBeginToken());
            }
            if (rt0 != null) {
                if (attachTyp == AttachType.MULTIPLE) {
                    if (types == null || types.size() == 0) 
                        return null;
                    if (!com.pullenti.ner.org.internal.OrgItemTypeToken.isTypeAccords((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class), types.get(0))) 
                        return null;
                    (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).addType(types.get(0), false);
                    if ((rt0.getBeginToken().beginChar - types.get(0).getEndToken().getNext().endChar) < 3) 
                        rt0.setBeginToken(types.get(0).getBeginToken());
                    break;
                }
                if (typ != null && !typ.getEndToken().getMorph()._getClass().isVerb()) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypeAccords((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class), typ)) {
                        rt0.setBeginToken(typ.getBeginToken());
                        (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).addType(typ, false);
                    }
                }
                break;
            }
            if (t.isHiphen()) {
                if (t == t0 || types == null) {
                    if (otExLi != null) 
                        break;
                    return null;
                }
                if ((typ != null && typ.root != null && typ.root.canHasNumber) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) {
                }
                else 
                    hiph = true;
                continue;
            }
            if (ad != null && otExLi == null) {
                boolean ok1 = false;
                com.pullenti.ner.Token tt = t;
                if (t.getInnerBool()) 
                    ok1 = true;
                else if (t.chars.isAllLower()) {
                }
                else if (t.chars.isLetter()) 
                    ok1 = true;
                else if (t.getPrevious() != null && com.pullenti.ner.core.BracketHelper.isBracket(t.getPrevious(), false)) 
                    ok1 = true;
                else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false) && t.getNext() != null) {
                    ok1 = true;
                    tt = t.getNext();
                }
                if (ok1 && tt != null) {
                    otExLi = ad.locOrgs.tryAttach(tt, null, false);
                    if (otExLi == null && t.kit.ontology != null) {
                        if ((((otExLi = t.kit.ontology.attachToken(OrganizationReferent.OBJ_TYPENAME, tt)))) != null) {
                        }
                    }
                    if (otExLi == null && tt.getLengthChar() == 2 && tt.chars.isAllUpper()) {
                        otExLi = ad.localOntology.tryAttach(tt, null, false);
                        if (otExLi != null) {
                            if (tt.kit.getSofa().getText().length() > 300) 
                                otExLi = null;
                        }
                    }
                }
                if (otExLi != null) 
                    t.setInnerBool(true);
            }
            if ((step >= 0 && !t.getInnerBool() && t == t0) && (t instanceof com.pullenti.ner.TextToken)) 
                typ = null;
            else {
                typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t, attachTyp == AttachType.EXTONTOLOGY, ad);
                if (typ == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t.getNext(), attachTyp == AttachType.EXTONTOLOGY, ad);
                        if (typ != null && typ.getEndToken() == br.getEndToken().getPrevious() && ((com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(br.getEndToken().getNext(), true, false) || t.isChar('(')))) {
                            typ.setEndToken(br.getEndToken());
                            typ.setBeginToken(t);
                        }
                        else 
                            typ = null;
                    }
                }
            }
            if (typ == null) 
                break;
            if (types == null) {
                if ((((com.pullenti.n2j.Utils.stringsEq(typ.typ, "главное управление") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "главное территориальное управление") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "головне управління")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "головне територіальне управління") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "пограничное управление"))) && otExLi != null) 
                    break;
                types = new java.util.ArrayList<>();
                t0 = typ.getBeginToken();
                if (typ.isNotTyp && typ.getEndToken().getNext() != null) 
                    t0 = typ.getEndToken().getNext();
                if (com.pullenti.ner.org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(typ.getBeginToken().getPrevious())) 
                    specWordBefore = true;
            }
            else {
                ok = true;
                for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticTT(ty, typ)) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) 
                    break;
                if (typ.isDep()) 
                    break;
                if (inBrackets) 
                    break;
                com.pullenti.ner.org.internal.OrgItemTypeToken typ0 = _lastTyp(types);
                if (hiph && ((t.getWhitespacesBeforeCount() > 0 && ((typ0 != null && typ0.isDoubtRootWord()))))) 
                    break;
                if (typ.getEndToken() == typ.getBeginToken()) {
                    if (typ.isValue("ОРГАНИЗАЦИЯ", "ОРГАНІЗАЦІЯ") || typ.isValue("УПРАВЛІННЯ", "")) 
                        break;
                }
                if (typ0.isDep() || com.pullenti.n2j.Utils.stringsEq(typ0.typ, "департамент")) 
                    break;
                if ((typ0.root != null && typ0.root.isPurePrefix && typ.root != null) && !typ.root.isPurePrefix && !typ.getBeginToken().chars.isAllLower()) {
                    if ((typ0.typ.indexOf("НИИ") >= 0)) 
                        break;
                }
                boolean pref0 = typ0.root != null && typ0.root.isPurePrefix;
                boolean pref = typ.root != null && typ.root.isPurePrefix;
                if (!pref0 && !pref) {
                    if (typ0.name != null && typ0.name.length() != typ0.typ.length()) {
                        if (t.getWhitespacesBeforeCount() > 1) 
                            break;
                    }
                    if (!typ0.getMorph().getCase().isUndefined() && !typ.getMorph().getCase().isUndefined()) {
                        if (!((com.pullenti.morph.MorphCase.ooBitand(typ0.getMorph().getCase(), typ.getMorph().getCase()))).isNominative() && !hiph) {
                            if (!typ.getMorph().getCase().isNominative()) 
                                break;
                        }
                    }
                    if (typ0.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED && typ.getMorph().getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                        if ((((typ0.getMorph().getNumber().value()) & (typ.getMorph().getNumber().value()))) == (com.pullenti.morph.MorphNumber.UNDEFINED.value())) 
                            break;
                    }
                }
                if (!pref0 && pref && !hiph) {
                    boolean nom = false;
                    for(com.pullenti.morph.MorphBaseInfo m : typ.getMorph().getItems()) {
                        if (m.getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && m.getCase().isNominative()) {
                            nom = true;
                            break;
                        }
                    }
                    if (!nom) {
                        if (com.pullenti.morph.LanguageHelper.endsWith(typ0.typ, "фракция") || com.pullenti.morph.LanguageHelper.endsWith(typ0.typ, "фракція")) {
                        }
                        else 
                            break;
                    }
                }
                for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticTT(ty, typ)) 
                        return null;
                }
            }
            types.add(typ);
            inBrackets = false;
            if (typ.name != null) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(typ.getBeginToken().getPrevious(), true, false) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(typ.getEndToken().getNext(), false, null, false)) {
                    typ.setBeginToken(typ.getBeginToken().getPrevious());
                    typ.setEndToken(typ.getEndToken().getNext());
                    if (typ.getBeginToken().endChar < t0.beginChar) 
                        t0 = typ.getBeginToken();
                    inBrackets = true;
                }
            }
            t = typ.getEndToken();
            hiph = false;
        }
        if ((types == null && otExLi == null && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) && rt0 == null) {
            ok = false;
            if (!ok) {
                if (t0 != null && t0.getMorph()._getClass().isAdjective() && t0.getNext() != null) {
                    if ((((rt0 = tryAttachOrg(t0.getNext(), ad, attachTyp, multTyp, isAdditionalAttach, level + 1, step)))) != null) {
                        if (rt0.getBeginToken() == t0) 
                            return rt0;
                    }
                }
                if (attachTyp == AttachType.NORMAL) {
                    if ((((rt0 = tryAttachOrgMed(t, ad)))) != null) 
                        return rt0;
                }
                if ((((t0.kit.recurseLevel < 4) && (t0 instanceof com.pullenti.ner.TextToken) && t0.getPrevious() != null) && t0.getLengthChar() > 2 && !t0.chars.isAllLower()) && !t0.isNewlineAfter() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t0)) {
                    typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t0.getNext(), false, null);
                    if (typ != null) {
                        t0.kit.recurseLevel++;
                        com.pullenti.ner.ReferentToken rrr = tryAttachOrg(t0.getNext(), ad, attachTyp, multTyp, isAdditionalAttach, level + 1, step);
                        t0.kit.recurseLevel--;
                        if (rrr == null) {
                            if (specWordBefore || t0.getPrevious().isValue("ТЕРРИТОРИЯ", null)) {
                                OrganizationReferent org0 = new OrganizationReferent();
                                org0.addType(typ, false);
                                org0.addName((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t0, com.pullenti.ner.TextToken.class))).term, false, t0);
                                t1 = typ.getEndToken();
                                t1 = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(attachTailAttributes(org0, t1.getNext(), ad, false, AttachType.NORMAL, false), t1);
                                return new com.pullenti.ner.ReferentToken(org0, t0, t1, null);
                            }
                        }
                    }
                }
                for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
                    if (tt.isAnd()) {
                        if (tt == t) 
                            break;
                        continue;
                    }
                    if ((((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && !tt.chars.isAllLower()) && !tt.chars.isCapitalUpper() && tt.getLengthChar() > 1) && (tt.getWhitespacesAfterCount() < 2)) {
                        com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
                        if (mc.isUndefined()) {
                        }
                        else if ((tt.getLengthChar() < 5) && !mc.isConjunction() && !mc.isPreposition()) {
                        }
                        else 
                            break;
                    }
                    else 
                        break;
                    if ((tt.getNext() instanceof com.pullenti.ner.ReferentToken) && (tt.getNext().getReferent() instanceof OrganizationReferent)) {
                        com.pullenti.ner.Token ttt = t.getPrevious();
                        if ((((ttt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter() && !ttt.chars.isAllLower()) && !ttt.chars.isCapitalUpper() && ttt.getLengthChar() > 1) && ttt.getMorphClassInDictionary().isUndefined() && (ttt.getWhitespacesAfterCount() < 2)) 
                            break;
                        com.pullenti.ner.Token tt0 = t;
                        for(t = t.getPrevious(); t != null; t = t.getPrevious()) {
                            if (!((t instanceof com.pullenti.ner.TextToken)) || t.getWhitespacesAfterCount() > 2) 
                                break;
                            else if (t.isAnd()) {
                            }
                            else if ((t.chars.isLetter() && !t.chars.isAllLower() && !t.chars.isCapitalUpper()) && t.getLengthChar() > 1 && t.getMorphClassInDictionary().isUndefined()) 
                                tt0 = t;
                            else 
                                break;
                        }
                        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt, com.pullenti.ner.core.GetTextAttr.NO);
                        if (com.pullenti.n2j.Utils.stringsEq(nam, "СЭД")) 
                            break;
                        OrganizationReferent own = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt.getNext().getReferent(), OrganizationReferent.class);
                        if (own.getProfiles().contains(OrgProfile.UNIT)) 
                            break;
                        if (com.pullenti.n2j.Utils.stringsEq(nam, "НК") || com.pullenti.n2j.Utils.stringsEq(nam, "ГК")) 
                            return new com.pullenti.ner.ReferentToken(own, t, tt.getNext(), null);
                        OrganizationReferent org0 = new OrganizationReferent();
                        org0.addProfile(OrgProfile.UNIT);
                        org0.addName(nam, true, null);
                        if (nam.indexOf(' ') > 0) 
                            org0.addName(nam.replace(" ", ""), true, null);
                        org0.setHigher(own);
                        t1 = tt.getNext();
                        com.pullenti.ner.Token ttt1 = attachTailAttributes(org0, t1, ad, true, attachTyp, false);
                        return new com.pullenti.ner.ReferentToken(org0, tt0, (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(ttt1, t1), null);
                    }
                }
                return null;
            }
        }
        if (types != null && types.size() > 1 && attachTyp != AttachType.EXTONTOLOGY) {
            if (com.pullenti.n2j.Utils.stringsEq(types.get(0).typ, "предприятие") || com.pullenti.n2j.Utils.stringsEq(types.get(0).typ, "підприємство")) {
                types.remove(0);
                t0 = types.get(0).getBeginToken();
            }
        }
        if (rt0 == null) {
            rt0 = _TryAttachOrg_(t0, t, ad, types, specWordBefore, attachTyp, multTyp, isAdditionalAttach, level);
            if (rt0 != null && otExLi != null) {
                for(com.pullenti.ner.core.IntOntologyToken ot : otExLi) {
                    if ((ot.endChar > rt0.endChar && ot.item != null && ot.item.owner != null) && ot.item.owner.isExtOntology) {
                        rt0 = null;
                        break;
                    }
                    else if (ot.endChar < rt0.beginChar) {
                        otExLi = null;
                        break;
                    }
                }
            }
            if (rt0 != null) {
                if (types != null && rt0.getBeginToken() == types.get(0).getBeginToken()) {
                    for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                        (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).addType(ty, true);
                    }
                }
                if ((rt0.getBeginToken() == t0 && t0.getPrevious() != null && t0.getPrevious().getMorph()._getClass().isAdjective()) && (t0.getWhitespacesBeforeCount() < 2)) {
                    if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).getGeoObjects().size() == 0) {
                        Object _geo = isGeo(t0.getPrevious(), true);
                        if (_geo != null) {
                            if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).addGeoObject(_geo)) 
                                rt0.setBeginToken(t0.getPrevious());
                        }
                    }
                }
            }
        }
        if (otExLi != null && rt0 == null && (otExLi.size() < 10)) {
            for(com.pullenti.ner.core.IntOntologyToken ot : otExLi) {
                OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(ot.item.referent, OrganizationReferent.class);
                if (org0 == null) 
                    continue;
                if (org0.getNames().size() == 0 && org0.getEponyms().size() == 0) 
                    continue;
                com.pullenti.ner.org.internal.OrgItemTypeToken tyty = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(ot.getBeginToken(), true, null);
                if (tyty != null && tyty.getBeginToken() == ot.getEndToken()) 
                    continue;
                com.pullenti.ner.Token ts = ot.getBeginToken();
                com.pullenti.ner.Token te = ot.getEndToken();
                boolean isQuots = false;
                boolean isVeryDoubt = false;
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ts.getPrevious(), false, false) && com.pullenti.ner.core.BracketHelper.isBracket(ts.getPrevious(), false)) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(te.getNext(), false, null, false)) {
                        if (ot.getLengthChar() < 2) 
                            continue;
                        if (ot.getLengthChar() == 2 && !org0.getNames().contains(te.getSourceText())) {
                        }
                        else {
                            isQuots = true;
                            ts = ts.getPrevious();
                            te = te.getNext();
                        }
                    }
                    else 
                        continue;
                }
                ok = types != null;
                if (ot.getEndToken().getNext() != null && (ot.getEndToken().getNext().getReferent() instanceof OrganizationReferent)) 
                    ok = true;
                else if (ot.getEndToken() != ot.getBeginToken()) {
                    if (!ot.getBeginToken().chars.isAllLower()) 
                        ok = true;
                    else if (specWordBefore || isQuots) 
                        ok = true;
                }
                else if (ot.getBeginToken() instanceof com.pullenti.ner.TextToken) {
                    ok = false;
                    int len = ot.getBeginToken().getLengthChar();
                    if (!ot.chars.isAllLower()) {
                        if (!ot.chars.isAllUpper() && ot.getMorph()._getClass().isPreposition()) 
                            continue;
                        boolean nameEq = false;
                        for(String n : org0.getNames()) {
                            if (ot.getBeginToken().isValue(n, null)) {
                                nameEq = true;
                                break;
                            }
                        }
                        com.pullenti.ner.TextAnnotation ano = org0.findNearOccurence(ot.getBeginToken());
                        if (ano == null) {
                            if (!ot.item.owner.isExtOntology) {
                                if (len < 3) 
                                    continue;
                                else 
                                    isVeryDoubt = true;
                            }
                        }
                        else {
                            int d = ano.beginChar - ot.getBeginToken().beginChar;
                            if (d < 0) 
                                d = -d;
                            if (d > 2000) {
                                if (len < 3) 
                                    continue;
                                else if (len < 5) 
                                    isVeryDoubt = true;
                            }
                            else if (d > 100) {
                                if (len < 3) 
                                    continue;
                            }
                            else if (len < 3) {
                                if (d > 100 || !ot.getBeginToken().chars.isAllUpper()) 
                                    isVeryDoubt = true;
                            }
                        }
                        if (((ot.getBeginToken().chars.isAllUpper() || ot.getBeginToken().chars.isLastLower())) && ((len > 3 || ((len == 3 && ((nameEq || ano != null))))))) 
                            ok = true;
                        else if (specWordBefore || types != null || isQuots) 
                            ok = true;
                        else if (ot.getLengthChar() < 3) 
                            continue;
                        else if (ot.item.owner.isExtOntology && ot.getBeginToken().getMorphClassInDictionary().isUndefined() && ((len > 3 || ((len == 3 && ((nameEq || ano != null))))))) 
                            ok = true;
                        else if (ot.getBeginToken().chars.isLatinLetter()) 
                            ok = true;
                        else if ((nameEq && !ot.chars.isAllLower() && !ot.item.owner.isExtOntology) && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(ot.getBeginToken())) 
                            ok = true;
                    }
                }
                else if (ot.getBeginToken() instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.Referent r = ot.getBeginToken().getReferent();
                    if (com.pullenti.n2j.Utils.stringsNe(r.getTypeName(), "DENOMINATION") && !isQuots) 
                        ok = false;
                }
                if (!ok) {
                }
                if (ok) {
                    ok = false;
                    _org = new OrganizationReferent();
                    if (types != null) {
                        for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                            _org.addType(ty, false);
                        }
                        if (!_org.canBeEquals(org0, com.pullenti.ner.Referent.EqualType.FORMERGING)) 
                            continue;
                    }
                    else 
                        for(String ty : org0.getTypes()) {
                            _org.addTypeStr(ty);
                        }
                    if (org0.getNumber() != null && (ot.getBeginToken().getPrevious() instanceof com.pullenti.ner.NumberToken) && _org.getNumber() == null) {
                        if (com.pullenti.n2j.Utils.stringsNe(org0.getNumber(), ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ot.getBeginToken().getPrevious(), com.pullenti.ner.NumberToken.class))).value).toString()) && (ot.getBeginToken().getWhitespacesBeforeCount() < 2)) {
                            if (_org.getNames().size() > 0 || _org.getHigher() != null) {
                                isVeryDoubt = false;
                                ok = true;
                                _org.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ot.getBeginToken().getPrevious(), com.pullenti.ner.NumberToken.class))).value).toString());
                                if (org0.getHigher() != null) 
                                    _org.setHigher(org0.getHigher());
                                t0 = ot.getBeginToken().getPrevious();
                            }
                        }
                    }
                    if (_org.getNumber() == null) {
                        com.pullenti.ner.Token ttt = ot.getEndToken().getNext();
                        com.pullenti.ner.org.internal.OrgItemNumberToken nnn = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(ttt, (org0.getNumber() != null || !ot.isWhitespaceAfter()), null);
                        if (nnn == null && !ot.isWhitespaceAfter() && ttt != null) {
                            if (ttt.isHiphen() && ttt.getNext() != null) 
                                ttt = ttt.getNext();
                            if (ttt instanceof com.pullenti.ner.NumberToken) 
                                nnn = com.pullenti.ner.org.internal.OrgItemNumberToken._new1663(ot.getEndToken().getNext(), ttt, ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.NumberToken.class))).value).toString());
                        }
                        if (nnn != null) {
                            _org.setNumber(nnn.number);
                            te = nnn.getEndToken();
                        }
                    }
                    boolean norm = (ot.getEndToken().endChar - ot.getBeginToken().beginChar) > 5;
                    String s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(ot, com.pullenti.ner.core.GetTextAttr.of(((((norm ? com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE : com.pullenti.ner.core.GetTextAttr.NO))).value()) | (com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES.value())));
                    _org.addName(s, true, (norm ? null : ot.getBeginToken()));
                    if (types == null || types.size() == 0) {
                        String s1 = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(ot, com.pullenti.ner.core.GetTextAttr.IGNOREARTICLES);
                        if (com.pullenti.n2j.Utils.stringsNe(s1, s) && norm) 
                            _org.addName(s1, true, ot.getBeginToken());
                    }
                    t1 = te;
                    if (t1.isChar(')') && t1.isNewlineAfter()) {
                    }
                    else {
                        t1 = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(attachMiddleAttributes(_org, t1.getNext()), t1);
                        if (attachTyp != AttachType.NORMALAFTERDEP) 
                            t1 = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(attachTailAttributes(_org, t1.getNext(), ad, false, AttachType.NORMAL, false), t1);
                    }
                    OrganizationReferent hi = null;
                    if (t1.getNext() != null) 
                        hi = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t1.getNext().getReferent(), OrganizationReferent.class);
                    if (org0.getHigher() != null && hi != null && otExLi.size() == 1) {
                        if (hi.canBeEquals(org0.getHigher(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                            _org.setHigher(hi);
                            t1 = t1.getNext();
                        }
                    }
                    if ((_org.getEponyms().size() == 0 && _org.getNumber() == null && isVeryDoubt) && types == null) 
                        continue;
                    if (!_org.canBeEqualsEx(org0, true, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                        if (t != null && com.pullenti.ner.org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t.getPrevious())) 
                            ok = true;
                        else if (!isVeryDoubt && ok) {
                        }
                        else {
                            if (!isVeryDoubt) {
                                if (_org.getEponyms().size() > 0 || _org.getNumber() != null || _org.getHigher() != null) 
                                    ok = true;
                            }
                            ok = false;
                        }
                    }
                    else if (_org.canBeEquals(org0, com.pullenti.ner.Referent.EqualType.DIFFERENTTEXTS)) {
                        _org.mergeSlots(org0, false);
                        ok = true;
                    }
                    else if (org0.getHigher() == null || _org.getHigher() != null || ot.item.owner.isExtOntology) {
                        ok = true;
                        _org.mergeSlots(org0, false);
                    }
                    else if (!ot.item.owner.isExtOntology && _org.canBeEquals(org0, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                        if (org0.getHigher() == null) 
                            _org.mergeSlots(org0, false);
                        ok = true;
                    }
                    if (!ok) 
                        continue;
                    if (ts.beginChar < t0.beginChar) 
                        t0 = ts;
                    rt0 = new com.pullenti.ner.ReferentToken(_org, t0, t1, null);
                    if (_org.getKind() == OrganizationKind.DEPARTMENT) 
                        correctDepAttrs(rt0, typ);
                    if (ot.item.owner.isExtOntology) {
                        for(com.pullenti.ner.Slot sl : _org.getSlots()) {
                            if (sl.getValue() instanceof com.pullenti.ner.Referent) {
                                boolean ext = false;
                                for(com.pullenti.ner.Slot ss : org0.getSlots()) {
                                    if (ss.getValue() == sl.getValue()) {
                                        ext = true;
                                        break;
                                    }
                                }
                                if (!ext) 
                                    continue;
                                com.pullenti.ner.Referent rr = (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class))).clone();
                                rr.getOccurrence().clear();
                                _org.uploadSlot(sl, rr);
                                com.pullenti.ner.ReferentToken rtEx = new com.pullenti.ner.ReferentToken(rr, t0, t1, null);
                                rtEx.setDefaultLocalOnto(t0.kit.processor);
                                _org.addExtReferent(rtEx);
                                for(com.pullenti.ner.Slot sss : rr.getSlots()) {
                                    if (sss.getValue() instanceof com.pullenti.ner.Referent) {
                                        com.pullenti.ner.Referent rrr = (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(sss.getValue(), com.pullenti.ner.Referent.class))).clone();
                                        rrr.getOccurrence().clear();
                                        rr.uploadSlot(sss, rrr);
                                        com.pullenti.ner.ReferentToken rtEx2 = new com.pullenti.ner.ReferentToken(rrr, t0, t1, null);
                                        rtEx2.setDefaultLocalOnto(t0.kit.processor);
                                        (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class))).addExtReferent(rtEx2);
                                    }
                                }
                            }
                        }
                    }
                    _correctAfter(rt0);
                    return rt0;
                }
            }
        }
        if ((rt0 == null && types != null && types.size() == 1) && types.get(0).name == null) {
            com.pullenti.ner.Token tt0 = null;
            if (com.pullenti.ner.core.MiscHelper.isEngArticle(types.get(0).getBeginToken())) 
                tt0 = types.get(0).getBeginToken();
            else if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(types.get(0).getEndToken().getNext())) 
                tt0 = types.get(0).getBeginToken();
            else {
                com.pullenti.ner.Token tt00 = types.get(0).getBeginToken().getPrevious();
                if (tt00 != null && (tt00.getWhitespacesAfterCount() < 2) && tt00.chars.isLatinLetter() == types.get(0).chars.isLatinLetter()) {
                    if (com.pullenti.ner.core.MiscHelper.isEngArticle(tt00)) 
                        tt0 = tt00;
                    else if (tt00.getMorph()._getClass().isPreposition() || tt00.getMorph()._getClass().isPronoun()) 
                        tt0 = tt00;
                }
            }
            int cou = 100;
            if (tt0 != null) {
                for(com.pullenti.ner.Token tt00 = tt0.getPrevious(); tt00 != null && cou > 0; tt00 = tt00.getPrevious(),cou--) {
                    if (tt00.getReferent() instanceof OrganizationReferent) {
                        if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypeAccords(((OrganizationReferent)com.pullenti.n2j.Utils.cast(tt00.getReferent(), OrganizationReferent.class)), types.get(0))) 
                            rt0 = new com.pullenti.ner.ReferentToken(tt00.getReferent(), tt0, types.get(0).getEndToken(), null);
                        break;
                    }
                }
            }
        }
        if (rt0 != null) 
            correctOwnerBefore(rt0);
        if (hiph && !inBrackets && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) {
            boolean ok1 = false;
            if (rt0 != null && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt0.getEndToken(), true, null, false)) {
                if (types.size() > 0) {
                    com.pullenti.ner.org.internal.OrgItemTypeToken ty = types.get(types.size() - 1);
                    if (ty.getEndToken().getNext() != null && ty.getEndToken().getNext().isHiphen() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ty.getEndToken().getNext().getNext(), true, false)) 
                        ok1 = true;
                }
            }
            else if (rt0 != null && rt0.getEndToken().getNext() != null && rt0.getEndToken().getNext().isHiphen()) {
                com.pullenti.ner.org.internal.OrgItemTypeToken ty = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(rt0.getEndToken().getNext().getNext(), false, null);
                if (ty == null) 
                    ok1 = true;
            }
            if (!ok1) 
                return null;
        }
        if (attachTyp == AttachType.MULTIPLE && t != null) {
            if (t.chars.isAllLower()) 
                return null;
        }
        if (rt0 == null) 
            return rt0;
        boolean doubt = rt0.tag != null;
        _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
        if (doubt && ad != null) {
            java.util.ArrayList<com.pullenti.ner.Referent> rli = ad.localOntology.tryAttachByReferent(_org, null, true);
            if (rli != null && rli.size() > 0) 
                doubt = false;
            else 
                for(com.pullenti.ner.core.IntOntologyItem it : ad.localOntology.getItems()) {
                    if (it.referent != null) {
                        if (it.referent.canBeEquals(_org, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                            doubt = false;
                            break;
                        }
                    }
                }
        }
        if ((ad != null && t != null && t.kit.ontology != null) && attachTyp == AttachType.NORMAL && doubt) {
            java.util.ArrayList<com.pullenti.ner.ExtOntologyItem> rli = t.kit.ontology.attachReferent(_org);
            if (rli != null) {
                if (rli.size() >= 1) 
                    doubt = false;
            }
        }
        if (doubt) 
            return null;
        _correctAfter(rt0);
        return rt0;
    }

    private void _correctAfter(com.pullenti.ner.ReferentToken rt0) {
        if (rt0 == null) 
            return;
        if (!rt0.isNewlineAfter() && rt0.getEndToken().getNext() != null && rt0.getEndToken().getNext().isChar('(')) {
            com.pullenti.ner.Token tt = rt0.getEndToken().getNext().getNext();
            if (tt instanceof com.pullenti.ner.TextToken) {
                if (tt.isChar(')')) 
                    rt0.setEndToken(tt);
                else if ((tt.getLengthChar() > 2 && (tt.getLengthChar() < 7) && tt.chars.isLatinLetter()) && tt.chars.isAllUpper()) {
                    String act = tt.getSourceText().toUpperCase();
                    if ((tt.getNext() instanceof com.pullenti.ner.NumberToken) && !tt.isWhitespaceAfter() && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(tt.getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                        tt = tt.getNext();
                        act += tt.getSourceText();
                    }
                    if (tt.getNext() != null && tt.getNext().isChar(')')) {
                        rt0.referent.addSlot(OrganizationReferent.ATTR_MISC, act, false, 0);
                        rt0.setEndToken(tt.getNext());
                    }
                }
                else {
                    OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
                    if (_org.getKind() == OrganizationKind.BANK && tt.chars.isLatinLetter()) {
                    }
                }
            }
        }
    }

    private static com.pullenti.ner.org.internal.OrgItemTypeToken _lastTyp(java.util.ArrayList<com.pullenti.ner.org.internal.OrgItemTypeToken> types) {
        if (types == null) 
            return null;
        for(int i = types.size() - 1; i >= 0; i--) {
            return types.get(i);
        }
        return null;
    }

    private com.pullenti.ner.ReferentToken _TryAttachOrg_(com.pullenti.ner.Token t0, com.pullenti.ner.Token t, OrgAnalyzerData ad, java.util.ArrayList<com.pullenti.ner.org.internal.OrgItemTypeToken> types, boolean specWordBefore, AttachType attachTyp, com.pullenti.ner.org.internal.OrgItemTypeToken multTyp, boolean isAdditionalAttach, int level) {
        if (t0 == null) 
            return null;
        com.pullenti.ner.Token t1 = t;
        com.pullenti.ner.org.internal.OrgItemTypeToken typ = _lastTyp(types);
        if (typ != null) {
            if (typ.isDep()) {
                com.pullenti.ner.ReferentToken rt0 = tryAttachDep(typ, attachTyp, specWordBefore);
                if (rt0 != null) 
                    return rt0;
                if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "группа") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "група")) 
                    typ.setDep(false);
                else 
                    return null;
            }
            if (typ.isNewlineAfter() && typ.name == null) {
                if (t1 != null && (t1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && typ.getProfiles().contains(OrgProfile.STATE)) {
                }
                else if (typ.root != null && ((typ.root.coeff >= 3 || typ.root.isPurePrefix))) {
                }
                else if (typ.getCoef() >= 4) {
                }
                else if ((typ.getCoef() >= 3 && (typ.getNewlinesAfterCount() < 2) && typ.getEndToken().getNext() != null) && typ.getEndToken().getNext().getMorph()._getClass().isPreposition()) {
                }
                else 
                    return null;
            }
            if (typ != multTyp && ((typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL && !Character.isUpperCase(typ.typ.charAt(0))))) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                }
                else if (typ.getEndToken().isValue("ВЛАСТЬ", null)) {
                }
                else 
                    return null;
            }
            if (attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
                if (((com.pullenti.n2j.Utils.stringsEq(typ.typ, "предприятие") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "підприємство"))) && !specWordBefore && types.size() == 1) 
                    return null;
            }
        }
        OrganizationReferent _org = new OrganizationReferent();
        if (types != null) {
            for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                _org.addType(ty, false);
            }
        }
        if (typ != null && typ.root != null && typ.root.isPurePrefix) {
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isAllUpper() && !t.isNewlineAfter()) {
                com.pullenti.ner.core.BracketSequenceToken b = com.pullenti.ner.core.BracketHelper.tryParse(t.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (b != null && b.isQuoteType()) {
                    _org.addTypeStr((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                    t = t.getNext();
                }
                else {
                    String s = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    if (s.length() == 2 && s.charAt(s.length() - 1) == 'К') {
                        _org.addTypeStr(s);
                        t = t.getNext();
                    }
                    else if (((t.getMorphClassInDictionary().isUndefined() && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isCapitalUpper() && t.getNext().getNext() != null) && !t.getNext().isNewlineAfter()) {
                        if (t.getNext().getNext().isCharOf(",.;") || com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext(), false, null, false)) {
                            _org.addTypeStr(s);
                            t = t.getNext();
                        }
                    }
                }
            }
            else if ((t instanceof com.pullenti.ner.TextToken) && t.getMorph()._getClass().isAdjective() && !t.chars.isAllLower()) {
                com.pullenti.ner.ReferentToken rtg = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(isGeo(t, true), com.pullenti.ner.ReferentToken.class);
                if (rtg != null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rtg.getEndToken().getNext(), false, false)) {
                    _org.addGeoObject(rtg);
                    t = rtg.getEndToken().getNext();
                }
            }
            else if ((t != null && (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && t.getNext() != null) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) {
                _org.addGeoObject(t.getReferent());
                t = t.getNext();
            }
        }
        com.pullenti.ner.Token te = null;
        OrganizationKind ki0 = _org.getKind();
        if (((((ki0 == OrganizationKind.GOVENMENT || ki0 == OrganizationKind.AIRPORT || ki0 == OrganizationKind.FACTORY) || ki0 == OrganizationKind.PARTY || ki0 == OrganizationKind.JUSTICE) || ki0 == OrganizationKind.MILITARY)) && t != null) {
            Object g = isGeo(t, false);
            if (g == null && t.getMorph()._getClass().isPreposition() && t.getNext() != null) 
                g = isGeo(t.getNext(), false);
            if (g != null) {
                if (_org.addGeoObject(g)) {
                    te = (t1 = getGeoEndToken(g, t));
                    t = t1.getNext();
                    java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> gt = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGS.tryAttach(t, null, false);
                    if (gt == null && t != null && t.kit.baseLanguage.isUa()) 
                        gt = com.pullenti.ner.org.internal.OrgGlobal.GLOBALORGSUA.tryAttach(t, null, false);
                    if (gt != null && gt.size() == 1) {
                        if (_org.canBeEquals(gt.get(0).item.referent, com.pullenti.ner.Referent.EqualType.FORMERGING)) {
                            _org.mergeSlots(gt.get(0).item.referent, false);
                            return new com.pullenti.ner.ReferentToken(_org, t0, gt.get(0).getEndToken(), null);
                        }
                    }
                }
            }
        }
        if (typ != null && typ.root != null && ((typ.root.canBeSingleGeo && !typ.root.canHasSingleName))) {
            if (_org.getGeoObjects().size() > 0 && te != null) 
                return new com.pullenti.ner.ReferentToken(_org, t0, te, null);
            Object r = null;
            te = (t1 = (typ != multTyp ? typ.getEndToken() : t0.getPrevious()));
            if (t != null && t1.getNext() != null) {
                r = isGeo(t1.getNext(), false);
                if (r == null && t1.getNext().getMorph()._getClass().isPreposition()) 
                    r = isGeo(t1.getNext().getNext(), false);
            }
            if (r != null) {
                if (!_org.addGeoObject(r)) 
                    return null;
                te = getGeoEndToken(r, t1.getNext());
            }
            if (_org.getGeoObjects().size() > 0 && te != null) {
                com.pullenti.ner.core.NounPhraseToken npt11 = com.pullenti.ner.core.NounPhraseHelper.tryParse(te.getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt11 != null && (te.getWhitespacesAfterCount() < 2) && npt11.noun.isValue("ДЕПУТАТ", null)) {
                }
                else 
                    return new com.pullenti.ner.ReferentToken(_org, t0, te, null);
            }
        }
        if (typ != null && (((com.pullenti.n2j.Utils.stringsEq(typ.typ, "милиция") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "полиция") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "міліція")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "поліція")))) {
            if (_org.getGeoObjects().size() > 0 && te != null) 
                return new com.pullenti.ner.ReferentToken(_org, t0, te, null);
            else 
                return null;
        }
        if (t != null && t.getMorph()._getClass().isProperName()) {
            com.pullenti.ner.ReferentToken rt1 = t.kit.processReferent("PERSON", t);
            if (rt1 != null && (rt1.getWhitespacesAfterCount() < 2)) {
                if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt1.getEndToken().getNext(), true, false)) 
                    t = rt1.getEndToken().getNext();
                else if (rt1.getEndToken().getNext() != null && rt1.getEndToken().getNext().isHiphen() && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt1.getEndToken().getNext().getNext(), true, false)) 
                    t = rt1.getEndToken().getNext().getNext();
            }
        }
        else if ((t != null && t.chars.isCapitalUpper() && t.getMorph()._getClass().isProperSurname()) && t.getNext() != null && (t.getWhitespacesAfterCount() < 2)) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext(), true, false)) 
                t = t.getNext();
            else if (((t.getNext().isCharOf(":") || t.getNext().isHiphen())) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t.getNext().getNext(), true, false)) 
                t = t.getNext().getNext();
        }
        com.pullenti.ner.Token tMax = null;
        com.pullenti.ner.core.BracketSequenceToken br = null;
        if (t != null) {
            br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (typ != null && br == null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                if (t.getNext() != null && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                    OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
                    if (!com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(_org, org0)) {
                        org0.mergeSlots(_org, false);
                        return new com.pullenti.ner.ReferentToken(org0, t0, t.getNext(), null);
                    }
                }
                if (((com.pullenti.n2j.Utils.stringsEq(typ.typ, "компания") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "предприятие") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "организация")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "компанія") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "підприємство")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "організація")) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 1)) 
                        return null;
                }
                com.pullenti.ner.org.internal.OrgItemTypeToken ty2 = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t.getNext(), false, null);
                if (ty2 != null) {
                    java.util.ArrayList<com.pullenti.ner.org.internal.OrgItemTypeToken> typs2 = new java.util.ArrayList<>();
                    typs2.add(ty2);
                    com.pullenti.ner.ReferentToken rt2 = _TryAttachOrg_(t.getNext(), ty2.getEndToken().getNext(), ad, typs2, true, AttachType.HIGH, null, isAdditionalAttach, level + 1);
                    if (rt2 != null) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt2.referent, OrganizationReferent.class);
                        if (!com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(_org, org0)) {
                            org0.mergeSlots(_org, false);
                            rt2.setBeginToken(t0);
                            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt2.getEndToken().getNext(), false, null, false)) 
                                rt2.setEndToken(rt2.getEndToken().getNext());
                            return rt2;
                        }
                    }
                }
            }
        }
        if (br != null && typ != null && _org.getKind() == OrganizationKind.GOVENMENT) {
            if (typ.root != null && !typ.root.canHasSingleName) 
                br = null;
        }
        if (br != null && br.isQuoteType()) {
            if (br.getBeginToken().getNext().isValue("О", null) || br.getBeginToken().getNext().isValue("ОБ", null)) 
                br = null;
            else if (br.getBeginToken().getPrevious() != null && br.getBeginToken().getPrevious().isChar(':')) 
                br = null;
        }
        if (br != null && br.isQuoteType() && ((br.getOpenChar() != '<' || ((typ != null && typ.root != null && typ.root.isPurePrefix))))) {
            if (t.isNewlineBefore() && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) {
                if (!br.isNewlineAfter()) 
                    return null;
            }
            if (_org.findSlot(OrganizationReferent.ATTR_TYPE, "организация", true) != null || _org.findSlot(OrganizationReferent.ATTR_TYPE, "організація", true) != null) 
                return null;
            if (typ != null && ((((com.pullenti.n2j.Utils.stringsEq(typ.typ, "компания") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "предприятие") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "организация")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "компанія") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "підприємство")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "організація")))) {
                if (com.pullenti.ner.org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 1)) 
                    return null;
            }
            com.pullenti.ner.org.internal.OrgItemNameToken nn = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t.getNext(), null, false, true);
            if (nn != null && nn.isIgnoredPart) 
                t = nn.getEndToken();
            OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
            if (org0 != null) {
                if (!com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(_org, org0) && t.getNext().getNext() != null) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext(), false, null, false)) {
                        org0.mergeSlots(_org, false);
                        return new com.pullenti.ner.ReferentToken(org0, t0, t.getNext().getNext(), null);
                    }
                    if ((t.getNext().getNext().getReferent() instanceof OrganizationReferent) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext().getNext().getNext(), false, null, false)) {
                        org0.mergeSlots(_org, false);
                        return new com.pullenti.ner.ReferentToken(org0, t0, t.getNext(), null);
                    }
                }
                return null;
            }
            if (br.internal.size() > 1) 
                return null;
            com.pullenti.ner.org.internal.OrgItemNameToken na0 = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(br.getBeginToken().getNext(), null, false, true);
            if (na0 != null && na0.isEmptyWord && na0.getEndToken().getNext() == br.getEndToken()) 
                return null;
            com.pullenti.ner.ReferentToken rt0 = tryAttachOrg(t.getNext(), null, attachTyp, null, isAdditionalAttach, level + 1, -1);
            String abbr = null;
            com.pullenti.ner.Token tt00 = (rt0 == null ? null : rt0.getBeginToken());
            if (((rt0 == null && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.TextToken)) && t.getNext().chars.isAllUpper() && t.getNext().getLengthChar() > 2) && t.getNext().chars.isCyrillicLetter()) {
                rt0 = tryAttachOrg(t.getNext().getNext(), null, attachTyp, null, isAdditionalAttach, level + 1, -1);
                if (rt0 != null && rt0.getBeginToken() == t.getNext().getNext()) {
                    tt00 = t.getNext();
                    abbr = t.getNext().getSourceText();
                }
                else 
                    rt0 = null;
            }
            boolean ok2 = false;
            if (rt0 != null) {
                if (rt0.getEndToken() == br.getEndToken().getPrevious() || rt0.getEndToken() == br.getEndToken()) 
                    ok2 = true;
                else if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt0.getEndToken(), false, null, false) && rt0.endChar > br.endChar) {
                    com.pullenti.ner.core.BracketSequenceToken br2 = com.pullenti.ner.core.BracketHelper.tryParse(br.getEndToken().getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br2 != null && rt0.getEndToken() == br2.getEndToken()) 
                        ok2 = true;
                }
            }
            if (ok2) {
                org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
                if (typ != null && com.pullenti.n2j.Utils.stringsEq(typ.typ, "служба") && ((org0.getKind() == OrganizationKind.MEDIA || org0.getKind() == OrganizationKind.PRESS))) {
                    if (br.getBeginToken() == rt0.getBeginToken() && br.getEndToken() == rt0.getEndToken()) 
                        return rt0;
                }
                com.pullenti.ner.org.internal.OrgItemTypeToken typ1 = null;
                if (tt00 != t.getNext()) {
                    typ1 = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t.getNext(), false, ad);
                    if (typ1 != null && typ1.getEndToken().getNext() == tt00) 
                        _org.addType(typ1, false);
                }
                boolean hi = false;
                if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org0, _org, true)) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(org0, _org)) 
                        hi = true;
                }
                if (hi) {
                    _org.setHigher(org0);
                    rt0.setDefaultLocalOnto(t.kit.processor);
                    _org.addExtReferent(rt0);
                    if (typ1 != null) 
                        _org.addType(typ1, true);
                    if (abbr != null) 
                        _org.addName(abbr, true, null);
                }
                else if (!com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(org0, _org)) {
                    _org.mergeSlots(org0, true);
                    if (abbr != null) {
                        for(com.pullenti.ner.Slot s : _org.getSlots()) {
                            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_NAME)) 
                                _org.uploadSlot(s, abbr + " " + s.getValue());
                        }
                    }
                }
                else 
                    rt0 = null;
                if (rt0 != null) {
                    com.pullenti.ner.Token t11 = br.getEndToken();
                    if (rt0.endChar > t11.endChar) 
                        t11 = rt0.getEndToken();
                    com.pullenti.ner.org.internal.OrgItemEponymToken ep11 = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t11.getNext(), true);
                    if (ep11 != null) {
                        t11 = ep11.getEndToken();
                        for(String e : ep11.eponyms) {
                            _org.addEponym(e);
                        }
                    }
                    t1 = attachTailAttributes(_org, t11.getNext(), null, true, attachTyp, false);
                    if (t1 == null) 
                        t1 = t11;
                    if (typ != null) {
                        if ((typ.name != null && typ.geo == null && _org.getNames().size() > 0) && !_org.getNames().contains(typ.name)) 
                            _org.addTypeStr(typ.name.toLowerCase());
                    }
                    return new com.pullenti.ner.ReferentToken(_org, t0, t1, null);
                }
            }
            if (rt0 != null && (rt0.endChar < br.getEndToken().getPrevious().endChar)) {
                com.pullenti.ner.ReferentToken rt1 = tryAttachOrg(rt0.getEndToken().getNext(), null, attachTyp, null, isAdditionalAttach, level + 1, -1);
                if (rt1 != null && rt1.getEndToken().getNext() == br.getEndToken()) 
                    return rt1;
                OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.getEndToken().getNext().getReferent(), OrganizationReferent.class);
                if (org1 != null && br.getEndToken().getPrevious() == rt0.getEndToken()) {
                }
            }
            for(int step = 0; step < 2; step++) {
                com.pullenti.ner.Token tt0 = t.getNext();
                com.pullenti.ner.Token tt1 = null;
                boolean pref = true;
                int notEmpty = 0;
                for(t1 = t.getNext(); t1 != null && t1 != br.getEndToken(); t1 = t1.getNext()) {
                    if (t1.isChar('(')) {
                        if (notEmpty == 0) 
                            break;
                        com.pullenti.ner.Referent r = null;
                        if (t1.getNext() != null) 
                            r = t1.getNext().getReferent();
                        if (r != null && t1.getNext().getNext() != null && t1.getNext().getNext().isChar(')')) {
                            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                                _org.addGeoObject(r);
                                break;
                            }
                        }
                        if (level == 0) {
                            com.pullenti.ner.ReferentToken rt = tryAttachOrg(t1.getNext(), null, AttachType.HIGH, null, false, level + 1, -1);
                            if (rt != null && rt.getEndToken().getNext() != null && rt.getEndToken().getNext().isChar(')')) {
                                if (!OrganizationReferent.canBeSecondDefinition(_org, (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))) 
                                    break;
                                _org.mergeSlots(rt.referent, false);
                            }
                        }
                        break;
                    }
                    else if ((((org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t1.getReferent(), OrganizationReferent.class)))) != null) {
                        if (((t1.getPrevious() instanceof com.pullenti.ner.NumberToken) && t1.getPrevious().getPrevious() == br.getBeginToken() && !com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(_org, org0)) && org0.getNumber() == null) {
                            org0.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1.getPrevious(), com.pullenti.ner.NumberToken.class))).value).toString());
                            org0.mergeSlots(_org, false);
                            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) 
                                t1 = t1.getNext();
                            return new com.pullenti.ner.ReferentToken(org0, t0, t1, null);
                        }
                        com.pullenti.ner.org.internal.OrgItemNameToken ne = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(br.getBeginToken().getNext(), null, attachTyp == AttachType.EXTONTOLOGY, true);
                        if (ne != null && ne.isIgnoredPart && ne.getEndToken().getNext() == t1) {
                            org0.mergeSlots(_org, false);
                            if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t1.getNext(), false, null, false)) 
                                t1 = t1.getNext();
                            return new com.pullenti.ner.ReferentToken(org0, t0, t1, null);
                        }
                        return null;
                    }
                    else {
                        typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t1, false, null);
                        if (typ != null && types != null) {
                            for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                                if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticTT(ty, typ)) {
                                    typ = null;
                                    break;
                                }
                            }
                        }
                        if (typ != null) {
                            if (typ.isDoubtRootWord() && ((typ.getEndToken().getNext() == br.getEndToken() || ((typ.getEndToken().getNext() != null && typ.getEndToken().getNext().isHiphen()))))) 
                                typ = null;
                            else if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                                typ = null;
                            else if (!typ.getMorph().getCase().isUndefined() && !typ.getMorph().getCase().isNominative()) 
                                typ = null;
                            else if (typ.getBeginToken() == typ.getEndToken()) {
                                com.pullenti.ner.Token ttt = typ.getEndToken().getNext();
                                if (ttt != null && ttt.isHiphen()) 
                                    ttt = ttt.getNext();
                                if (ttt != null) {
                                    if (ttt.isValue("БАНК", null)) 
                                        typ = null;
                                }
                            }
                        }
                        com.pullenti.ner.org.internal.OrgItemEponymToken _ep = null;
                        if (typ == null) 
                            _ep = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t1, false);
                        com.pullenti.ner.org.internal.OrgItemNumberToken nu = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t1, false, null);
                        if (nu != null && !((t1 instanceof com.pullenti.ner.NumberToken))) {
                            _org.setNumber(nu.number);
                            tt1 = t1.getPrevious();
                            t1 = nu.getEndToken();
                            notEmpty += 2;
                            continue;
                        }
                        boolean brSpec = false;
                        if ((br.internal.size() == 0 && (br.getEndToken().getNext() instanceof com.pullenti.ner.TextToken) && ((!br.getEndToken().getNext().chars.isAllLower()))) && com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(br.getEndToken().getNext().getNext(), true, null, false)) 
                            brSpec = true;
                        if (typ != null && ((pref || !typ.isDep()))) {
                            if (notEmpty > 1) {
                                com.pullenti.ner.ReferentToken rrr = tryAttachOrg(typ.getBeginToken(), ad, AttachType.NORMAL, null, false, level + 1, -1);
                                if (rrr != null) {
                                    br.setEndToken((t1 = typ.getBeginToken().getPrevious()));
                                    break;
                                }
                            }
                            if (((attachTyp == AttachType.EXTONTOLOGY || attachTyp == AttachType.HIGH)) && ((typ.root == null || !typ.root.isPurePrefix))) 
                                pref = false;
                            else if (typ.name == null) {
                                _org.addType(typ, false);
                                if (pref) 
                                    tt0 = typ.getEndToken().getNext();
                                else if (typ.root != null && typ.root.isPurePrefix) {
                                    tt1 = typ.getBeginToken().getPrevious();
                                    break;
                                }
                            }
                            else if (typ.getEndToken().getNext() != br.getEndToken()) {
                                _org.addType(typ, false);
                                if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "банк")) 
                                    pref = false;
                                else {
                                    _org.addTypeStr(typ.name.toLowerCase());
                                    _org.addTypeStr(typ.altTyp);
                                    if (pref) 
                                        tt0 = typ.getEndToken().getNext();
                                }
                            }
                            else if (brSpec) {
                                _org.addType(typ, false);
                                _org.addTypeStr(typ.name.toLowerCase());
                                notEmpty += 2;
                                tt0 = br.getEndToken().getNext();
                                t1 = tt0.getNext();
                                br.setEndToken(t1);
                                break;
                            }
                            if (typ != multTyp) {
                                t1 = typ.getEndToken();
                                if (typ.geo != null) 
                                    _org.addType(typ, false);
                            }
                        }
                        else if (_ep != null) {
                            for(String e : _ep.eponyms) {
                                _org.addEponym(e);
                            }
                            notEmpty += 3;
                            t1 = _ep.getBeginToken().getPrevious();
                            break;
                        }
                        else if (t1 == t.getNext() && (t1 instanceof com.pullenti.ner.TextToken) && t1.chars.isAllLower()) 
                            return null;
                        else if (t1.chars.isLetter() || (t1 instanceof com.pullenti.ner.NumberToken)) {
                            if (brSpec) {
                                tt0 = br.getBeginToken();
                                t1 = br.getEndToken().getNext().getNext();
                                String ss = com.pullenti.ner.core.MiscHelper.getTextValue(br.getEndToken(), t1, com.pullenti.ner.core.GetTextAttr.NO);
                                if (!com.pullenti.n2j.Utils.isNullOrEmpty(ss)) {
                                    _org.addName(ss, true, br.getEndToken().getNext());
                                    br.setEndToken(t1);
                                }
                                break;
                            }
                            pref = false;
                            notEmpty++;
                        }
                    }
                }
                boolean canHasNum = false;
                boolean canHasLatinName = false;
                if (types != null) {
                    for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                        if (ty.root != null) {
                            if (ty.root.canHasNumber) 
                                canHasNum = true;
                            if (ty.root.canHasLatinName) 
                                canHasLatinName = true;
                        }
                    }
                }
                te = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(tt1, t1);
                if (te != null && tt0 != null && (tt0.beginChar < te.beginChar)) {
                    for(com.pullenti.ner.Token ttt = tt0; ttt != te && ttt != null; ttt = ttt.getNext()) {
                        com.pullenti.ner.org.internal.OrgItemNameToken oin = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(ttt, null, attachTyp == AttachType.EXTONTOLOGY, ttt == tt0);
                        if (oin != null) {
                            if (oin.isIgnoredPart && ttt == tt0) {
                                tt0 = oin.getEndToken().getNext();
                                if (tt0 == null) 
                                    break;
                                ttt = tt0.getPrevious();
                                continue;
                            }
                            if (oin.isStdTail) {
                                com.pullenti.ner.org.internal.OrgItemEngItem ei = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttach(oin.getBeginToken(), false);
                                if (ei == null && oin.getBeginToken().isComma()) 
                                    ei = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttach(oin.getBeginToken().getNext(), false);
                                if (ei != null) {
                                    _org.addTypeStr(ei.fullValue);
                                    if (ei.shortValue != null) 
                                        _org.addTypeStr(ei.shortValue);
                                }
                                te = ttt.getPrevious();
                                break;
                            }
                        }
                        if ((ttt != tt0 && (ttt instanceof com.pullenti.ner.ReferentToken) && ttt.getNext() == te) && (ttt.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                            if (ttt.getPrevious() != null && ttt.getPrevious().getMorphClassInDictionary().isAdjective()) 
                                continue;
                            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.REFERENTCANBENOUN, 0);
                            if (npt != null && npt.getEndToken() == ttt) {
                            }
                            else {
                                te = ttt.getPrevious();
                                if (te.getMorph()._getClass().isPreposition() && te.getPrevious() != null) 
                                    te = te.getPrevious();
                            }
                            _org.addGeoObject(ttt.getReferent());
                            break;
                        }
                    }
                }
                if (te != null && tt0 != null && (tt0.beginChar < te.beginChar)) {
                    if ((te.getPrevious() instanceof com.pullenti.ner.NumberToken) && canHasNum) {
                        boolean err = false;
                        com.pullenti.ner.NumberToken num1 = (com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(te.getPrevious(), com.pullenti.ner.NumberToken.class);
                        if (_org.getNumber() != null && com.pullenti.n2j.Utils.stringsNe(_org.getNumber(), ((Long)num1.value).toString())) 
                            err = true;
                        else if (te.getPrevious().getPrevious() == null) 
                            err = true;
                        else if (!te.getPrevious().getPrevious().isHiphen() && !te.getPrevious().getPrevious().chars.isLetter()) 
                            err = true;
                        else if (num1.value == ((long)0)) 
                            err = true;
                        if (!err) {
                            _org.setNumber(((Long)num1.value).toString());
                            te = te.getPrevious().getPrevious();
                            if (te != null && ((te.isHiphen() || te.isValue("N", null) || te.isValue("№", null)))) 
                                te = te.getPrevious();
                        }
                    }
                }
                String s = (te == null ? null : com.pullenti.ner.core.MiscHelper.getTextValue(tt0, te, com.pullenti.ner.core.GetTextAttr.NO));
                String s1 = (te == null ? null : com.pullenti.ner.core.MiscHelper.getTextValue(tt0, te, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE));
                if ((te != null && (te.getPrevious() instanceof com.pullenti.ner.NumberToken) && canHasNum) && _org.getNumber() == null) {
                    _org.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(te.getPrevious(), com.pullenti.ner.NumberToken.class))).value).toString());
                    com.pullenti.ner.Token tt11 = te.getPrevious();
                    if (tt11.getPrevious() != null && tt11.getPrevious().isHiphen()) 
                        tt11 = tt11.getPrevious();
                    if (tt11.getPrevious() != null) {
                        s = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt11.getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                        s1 = com.pullenti.ner.core.MiscHelper.getTextValue(tt0, tt11.getPrevious(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                    }
                }
                if (!com.pullenti.n2j.Utils.isNullOrEmpty(s)) {
                    if (tt0.getMorph()._getClass().isPreposition() && tt0 != br.getBeginToken().getNext()) {
                        for(String ty : _org.getTypes()) {
                            if (!(ty.indexOf(" ") >= 0) && Character.isLowerCase(ty.charAt(0))) {
                                s = ty.toUpperCase() + " " + s;
                                s1 = null;
                                break;
                            }
                        }
                    }
                    if (s.length() > maxOrgName) 
                        return null;
                    if (s1 != null && com.pullenti.n2j.Utils.stringsNe(s1, s) && s1.length() <= s.length()) 
                        _org.addName(s1, true, null);
                    _org.addName(s, true, tt0);
                    boolean ok1 = false;
                    for(char c : s.toCharArray()) {
                        if (Character.isLetterOrDigit(c)) {
                            ok1 = true;
                            break;
                        }
                    }
                    if (!ok1) 
                        return null;
                    if (br.getBeginToken().getNext().chars.isAllLower()) 
                        return null;
                    if (_org.getTypes().size() == 0) {
                        com.pullenti.ner.org.internal.OrgItemTypeToken ty = _lastTyp(types);
                        if (ty != null && ty.getCoef() >= 4) {
                        }
                        else {
                            if (attachTyp == AttachType.NORMAL) 
                                return null;
                            if (_org.getNames().size() == 1 && (_org.getNames().get(0).length() < 2) && (br.getLengthChar() < 5)) 
                                return null;
                        }
                    }
                }
                else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1, false, false)) {
                    com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(t1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br1 == null) 
                        break;
                    t = br1.getBeginToken();
                    br = br1;
                    continue;
                }
                else if (((_org.getNumber() != null || _org.getEponyms().size() > 0)) && t1 == br.getEndToken()) {
                }
                else if (_org.getGeoObjects().size() > 0 && _org.getTypes().size() > 2) {
                }
                else 
                    return null;
                t1 = br.getEndToken();
                if (_org.getNumber() == null && t1.getNext() != null && (t1.getWhitespacesAfterCount() < 2)) {
                    com.pullenti.ner.org.internal.OrgItemNumberToken num1 = (com.pullenti.ner.org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 1) ? null : com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, typ));
                    if (num1 != null) {
                        _org.setNumber(num1.number);
                        t1 = num1.getEndToken();
                    }
                    else 
                        t1 = attachTailAttributes(_org, t1.getNext(), null, true, attachTyp, false);
                }
                else 
                    t1 = attachTailAttributes(_org, t1.getNext(), null, true, attachTyp, false);
                if (t1 == null) 
                    t1 = br.getEndToken();
                boolean ok0 = false;
                if (types != null) {
                    for(com.pullenti.ner.org.internal.OrgItemTypeToken ty : types) {
                        if (ty.name != null) 
                            _org.addTypeStr(ty.name.toLowerCase());
                        if (attachTyp != AttachType.MULTIPLE && (ty.beginChar < t0.beginChar) && !ty.isNotTyp) 
                            t0 = ty.getBeginToken();
                        if (!ty.isDoubtRootWord() || ty.getCoef() > 0 || ty.geo != null) 
                            ok0 = true;
                        else if (com.pullenti.n2j.Utils.stringsEq(ty.typ, "движение") && ((!br.getBeginToken().getNext().chars.isAllLower() || !ty.chars.isAllLower()))) {
                            if (!br.getBeginToken().getNext().getMorph().getCase().isGenitive()) 
                                ok0 = true;
                        }
                        else if (com.pullenti.n2j.Utils.stringsEq(ty.typ, "АО")) {
                            if (ty.getBeginToken().chars.isAllUpper() && (ty.getWhitespacesAfterCount() < 2) && com.pullenti.ner.core.BracketHelper.isBracket(ty.getEndToken().getNext(), true)) 
                                ok0 = true;
                            else 
                                for(com.pullenti.ner.Token tt2 = t1.getNext(); tt2 != null; tt2 = tt2.getNext()) {
                                    if (tt2.isComma()) 
                                        continue;
                                    if (tt2.isValue("ИМЕНОВАТЬ", null)) 
                                        ok0 = true;
                                    if (tt2.isValue("В", null) && tt2.getNext() != null) {
                                        if (tt2.getNext().isValue("ЛИЦО", null) || tt2.getNext().isValue("ДАЛЬШЕЙШЕМ", null) || tt2.getNext().isValue("ДАЛЕЕ", null)) 
                                            ok0 = true;
                                    }
                                    break;
                                }
                        }
                    }
                }
                if (_org.getEponyms().size() == 0 && (t1.getWhitespacesAfterCount() < 2)) {
                    com.pullenti.ner.org.internal.OrgItemEponymToken _ep = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t1.getNext(), false);
                    if (_ep != null) {
                        for(String e : _ep.eponyms) {
                            _org.addEponym(e);
                        }
                        ok0 = true;
                        t1 = _ep.getEndToken();
                    }
                }
                if (_org.getNames().size() == 0) {
                    s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                    s1 = (te == null ? null : com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE));
                    _org.addName(s, true, br.getBeginToken().getNext());
                    _org.addName(s1, true, null);
                }
                if (!ok0) {
                    if (com.pullenti.ner.org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t0.getPrevious())) 
                        ok0 = true;
                }
                if (!ok0 && attachTyp != AttachType.NORMAL) 
                    ok0 = true;
                if (ok0) 
                    return new com.pullenti.ner.ReferentToken(_org, t0, t1, null);
                else 
                    return com.pullenti.ner.ReferentToken._new711(_org, t0, t1, _org);
            }
        }
        com.pullenti.ner.org.internal.OrgItemNumberToken num = null;
        com.pullenti.ner.org.internal.OrgItemNumberToken _num;
        com.pullenti.ner.org.internal.OrgItemEponymToken epon = null;
        com.pullenti.ner.org.internal.OrgItemEponymToken _epon;
        java.util.ArrayList<com.pullenti.ner.org.internal.OrgItemNameToken> names = null;
        com.pullenti.ner.org.internal.OrgItemNameToken pr = null;
        com.pullenti.ner.ReferentToken ownOrg = null;
        if (t1 == null) 
            t1 = t0;
        else if (t != null && t.getPrevious() != null && t.getPrevious().beginChar >= t0.beginChar) 
            t1 = t.getPrevious();
        br = null;
        boolean ok = false;
        for(; t != null; t = t.getNext()) {
            com.pullenti.ner.ReferentToken rt;
            if ((((rt = attachGlobalOrg(t, attachTyp, ad, null)))) != null) {
                if (t == t0) {
                    if (!t.chars.isAllLower()) 
                        return rt;
                    return null;
                }
                if (level == 0) {
                    rt = tryAttachOrg(t, null, attachTyp, multTyp, isAdditionalAttach, level + 1, -1);
                    if (rt != null) 
                        return rt;
                }
            }
            if ((((_num = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t, typ != null && typ.root != null && typ.root.canHasNumber, typ)))) != null) {
                if ((typ == null || typ.root == null || !typ.root.canHasNumber) || num != null) 
                    break;
                if (t.getWhitespacesBeforeCount() > 2) {
                    if (typ.getEndToken().getNext() == t && com.pullenti.ner.core.MiscHelper.checkNumberPrefix(t) != null) {
                    }
                    else 
                        break;
                }
                if (com.pullenti.n2j.Utils.stringsEq(typ.root.getCanonicText(), "СУД") && typ.name != null) {
                    if ((((typ.name.startsWith("ВЕРХОВНЫЙ") || typ.name.startsWith("АРБИТРАЖНЫЙ") || typ.name.startsWith("ВЫСШИЙ")) || typ.name.startsWith("КОНСТИТУЦИОН") || typ.name.startsWith("ВЕРХОВНИЙ")) || typ.name.startsWith("АРБІТРАЖНИЙ") || typ.name.startsWith("ВИЩИЙ")) || typ.name.startsWith("КОНСТИТУЦІЙН")) {
                        typ.setCoef((float)3);
                        break;
                    }
                }
                num = _num;
                t1 = (t = num.getEndToken());
                continue;
            }
            if ((((_epon = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t, false)))) != null) {
                epon = _epon;
                t1 = (t = epon.getEndToken());
                continue;
            }
            if ((((typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t, false, ad)))) != null) {
                if (typ.getMorph().getCase().isGenitive()) {
                    if (typ.getEndToken().isValue("СЛУЖБА", null) || typ.getEndToken().isValue("УПРАВЛЕНИЕ", "УПРАВЛІННЯ")) 
                        typ = null;
                }
                if (typ != null) {
                    if (!typ.isDoubtRootWord() && attachTyp != AttachType.EXTONTOLOGY) 
                        break;
                    if (types == null && t0 == t) 
                        break;
                    if (_lastTyp(types) != null && attachTyp != AttachType.EXTONTOLOGY) {
                        if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticTT(typ, _lastTyp(types))) {
                            if (names != null && ((typ.getMorph().getCase().isGenitive() || typ.getMorph().getCase().isInstrumental())) && (t.getWhitespacesBeforeCount() < 2)) {
                            }
                            else 
                                break;
                        }
                    }
                }
            }
            if ((((br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100)))) != null) {
                if (ownOrg != null && !(((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class))).isFromGlobalOntos) 
                    break;
                if (t.isNewlineBefore() && ((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP))) 
                    break;
                typ = _lastTyp(types);
                if ((_org.findSlot(OrganizationReferent.ATTR_TYPE, "организация", true) != null || _org.findSlot(OrganizationReferent.ATTR_TYPE, "движение", true) != null || _org.findSlot(OrganizationReferent.ATTR_TYPE, "організація", true) != null) || _org.findSlot(OrganizationReferent.ATTR_TYPE, "рух", true) != null) {
                    if (((typ == null || (typ.getCoef() < 2))) && !specWordBefore) 
                        return null;
                }
                if (br.isQuoteType()) {
                    if (br.getOpenChar() == '<' || br.getWhitespacesBeforeCount() > 1) 
                        break;
                    rt = tryAttachOrg(t, null, AttachType.HIGH, null, false, level + 1, -1);
                    if (rt == null) 
                        break;
                    OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
                    if (names != null && names.size() == 1) {
                        if (((!names.get(0).isNounPhrase && names.get(0).chars.isAllUpper())) || org0.getNames().size() > 0) {
                            if (!names.get(0).getBeginToken().getMorph()._getClass().isPreposition()) {
                                if (org0.getNames().size() == 0) 
                                    _org.addTypeStr(names.get(0).value);
                                else {
                                    for(String n : org0.getNames()) {
                                        _org.addName(names.get(0).value + " " + n, true, null);
                                        if (typ != null && typ.root != null && typ.root.getTyp() != com.pullenti.ner.org.internal.OrgItemTermin.Types.PREFIX) 
                                            _org.addName(typ.typ.toUpperCase() + " " + com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(names.get(0), com.pullenti.ner.core.GetTextAttr.NO) + " " + n, true, null);
                                    }
                                    if (typ != null) 
                                        typ.setCoef((float)4);
                                }
                                names = null;
                            }
                        }
                    }
                    if (names != null && names.size() > 0 && !specWordBefore) 
                        break;
                    if (!_org.canBeEquals(org0, com.pullenti.ner.Referent.EqualType.FORMERGING)) 
                        break;
                    _org.mergeSlots(org0, true);
                    t1 = (tMax = (t = rt.getEndToken()));
                    ok = true;
                    continue;
                }
                else if (br.getOpenChar() == '(') {
                    if (t.getNext().getReferent() != null && t.getNext().getNext() == br.getEndToken()) {
                        com.pullenti.ner.Referent r = t.getNext().getReferent();
                        if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                            _org.addGeoObject(r);
                            tMax = (t1 = (t = br.getEndToken()));
                            continue;
                        }
                    }
                    else if (((t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.isLetter() && !t.getNext().chars.isAllLower()) && t.getNext().getNext() == br.getEndToken()) {
                        typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true, null);
                        if (typ != null) {
                            OrganizationReferent or0 = new OrganizationReferent();
                            or0.addType(typ, false);
                            if (or0.getKind() != OrganizationKind.UNDEFINED && _org.getKind() != OrganizationKind.UNDEFINED) {
                                if (_org.getKind() != or0.getKind()) 
                                    break;
                            }
                            if (com.pullenti.ner.core.MiscHelper.testAcronym(t.getNext(), t0, t.getPrevious())) 
                                _org.addName(t.getNext().getSourceText(), true, null);
                            else 
                                _org.addType(typ, false);
                            t1 = (t = (tMax = br.getEndToken()));
                            continue;
                        }
                        else {
                            com.pullenti.ner.org.internal.OrgItemNameToken nam = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t.getNext(), null, attachTyp == AttachType.EXTONTOLOGY, true);
                            if (nam != null && nam.isEmptyWord) 
                                break;
                            if (attachTyp == AttachType.NORMAL) {
                                OrganizationReferent org0 = new OrganizationReferent();
                                org0.addName((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term, true, t.getNext());
                                if (!OrganizationReferent.canBeSecondDefinition(_org, org0)) 
                                    break;
                            }
                            _org.addName((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term, true, t.getNext());
                            tMax = (t1 = (t = br.getEndToken()));
                            continue;
                        }
                    }
                }
                break;
            }
            if (ownOrg != null) {
                if (names == null && t.isValue("ПО", null)) {
                }
                else if (names != null && t.isCommaAnd()) {
                }
                else 
                    break;
            }
            typ = _lastTyp(types);
            if (typ != null && typ.root != null && typ.root.isPurePrefix) {
                if (pr == null && names == null) {
                    pr = new com.pullenti.ner.org.internal.OrgItemNameToken(t, t);
                    pr.getMorph().setCase(com.pullenti.morph.MorphCase.NOMINATIVE);
                }
            }
            com.pullenti.ner.org.internal.OrgItemNameToken na = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t, pr, attachTyp == AttachType.EXTONTOLOGY, names == null);
            if (na == null && t != null) {
                if (_org.getKind() == OrganizationKind.CHURCH || ((typ != null && typ.typ != null && (typ.typ.indexOf("фермер") >= 0)))) {
                    com.pullenti.ner.ReferentToken prt = t.kit.processReferent("PERSON", t);
                    if (prt != null) {
                        na = com.pullenti.ner.org.internal.OrgItemNameToken._new2164(t, prt.getEndToken(), true);
                        na.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(na, com.pullenti.ner.core.GetTextAttr.NO);
                        na.chars = com.pullenti.morph.CharsInfo._new2165(true);
                        na.setMorph(prt.getMorph());
                        String sur = prt.referent.getStringValue("LASTNAME");
                        if (sur != null) {
                            for(com.pullenti.ner.Token tt = t; tt != null && tt.endChar <= prt.endChar; tt = tt.getNext()) {
                                if (tt.isValue(sur, null)) {
                                    na.value = com.pullenti.ner.core.MiscHelper.getTextValue(tt, tt, com.pullenti.ner.core.GetTextAttr.NO);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (na == null) {
                if (attachTyp == AttachType.EXTONTOLOGY) {
                    if (t.isChar(',') || t.isAnd()) 
                        continue;
                }
                if (t.getReferent() instanceof OrganizationReferent) {
                    ownOrg = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                    continue;
                }
                if (t.isValue("ПРИ", null) && (t.getNext() instanceof com.pullenti.ner.ReferentToken) && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                    t = t.getNext();
                    ownOrg = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                    continue;
                }
                if ((((names == null && t.isChar('/') && (t.getNext() instanceof com.pullenti.ner.TextToken)) && !t.isWhitespaceAfter() && t.getNext().chars.isAllUpper()) && t.getNext().getLengthChar() >= 3 && (t.getNext().getNext() instanceof com.pullenti.ner.TextToken)) && !t.getNext().isWhitespaceAfter() && t.getNext().getNext().isChar('/')) 
                    na = com.pullenti.ner.org.internal.OrgItemNameToken._new2166(t, t.getNext().getNext(), t.getNext().getSourceText().toUpperCase(), t.getNext().chars);
                else if (names == null && typ != null && ((com.pullenti.n2j.Utils.stringsEq(typ.typ, "движение") || _org.getKind() == OrganizationKind.PARTY))) {
                    com.pullenti.ner.Token tt1 = null;
                    if (t.isValue("ЗА", null) || t.isValue("ПРОТИВ", null)) 
                        tt1 = t.getNext();
                    else if (t.isValue("В", null) && t.getNext() != null) {
                        if (t.getNext().isValue("ЗАЩИТА", null) || t.getNext().isValue("ПОДДЕРЖКА", null)) 
                            tt1 = t.getNext();
                    }
                    else if (typ.chars.isCapitalUpper() && !com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(typ.getBeginToken())) {
                        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                        if ((mc.isAdverb() || mc.isPronoun() || mc.isPersonalPronoun()) || mc.isVerb() || mc.isConjunction()) {
                        }
                        else if (t.chars.isLetter()) 
                            tt1 = t;
                        else if (typ.getBeginToken() != typ.getEndToken()) 
                            typ.setCoef(typ.getCoef() + ((float)3));
                    }
                    if (tt1 != null) {
                        na = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(tt1, pr, true, false);
                        if (na != null) {
                            na.setBeginToken(t);
                            typ.setCoef(typ.getCoef() + ((float)3));
                        }
                    }
                }
                if (na == null) 
                    break;
            }
            if (num != null || epon != null) 
                break;
            if (attachTyp == AttachType.MULTIPLE || attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
                if (!na.isStdTail && !na.chars.isLatinLetter() && na.stdOrgNameNouns == 0) {
                    if (t.getMorph()._getClass().isProperName()) 
                        break;
                    com.pullenti.morph.MorphClass cla = t.getMorphClassInDictionary();
                    if (cla.isProperSurname() || ((t.getMorph().getLanguage().isUa() && t.getMorph()._getClass().isProperSurname()))) {
                        if (names == null && _org.getKind() == OrganizationKind.AIRPORT) {
                        }
                        else if (typ != null && typ.root != null && com.pullenti.n2j.Utils.stringsEq(typ.root.acronym, "ФОП")) {
                        }
                        else if (typ != null && (typ.typ.indexOf("фермер") >= 0)) {
                        }
                        else 
                            break;
                    }
                    if (cla.isUndefined() && na.chars.isCyrillicLetter() && na.chars.isCapitalUpper()) {
                        if ((t.getPrevious() != null && !t.getPrevious().getMorph()._getClass().isPreposition() && !t.getPrevious().getMorph()._getClass().isConjunction()) && t.getPrevious().chars.isAllLower()) {
                            if ((t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().chars.isLetter()) && !t.getNext().chars.isAllLower()) 
                                break;
                        }
                    }
                    if (typ != null && com.pullenti.n2j.Utils.stringsEq(typ.typ, "союз") && !t.getMorph().getCase().isGenitive()) 
                        break;
                    com.pullenti.ner.ReferentToken pit = t.kit.processReferent("PERSONPROPERTY", t);
                    if (pit != null) {
                        if (pit.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR && pit.getBeginToken() != pit.getEndToken()) 
                            break;
                    }
                }
            }
            if (t.isValue("ИМЕНИ", "ІМЕНІ") || t.isValue("ИМ", "ІМ")) 
                break;
            pr = na;
            if (attachTyp == AttachType.EXTONTOLOGY) {
                if (names == null) 
                    names = new java.util.ArrayList<>();
                names.add(na);
                t1 = (t = na.getEndToken());
                continue;
            }
            if (names == null) {
                if (tMax != null) 
                    break;
                if (t.getPrevious() != null && t.isNewlineBefore() && attachTyp != AttachType.EXTONTOLOGY) {
                    if (t.getNewlinesAfterCount() > 1 || !t.chars.isAllLower()) 
                        break;
                    if (t.getMorph()._getClass().isPreposition() && typ != null && (((com.pullenti.n2j.Utils.stringsEq(typ.typ, "комитет") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "комиссия") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "комітет")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "комісія")))) {
                    }
                    else if (na.stdOrgNameNouns > 0) {
                    }
                    else 
                        break;
                }
                else if (t.getPrevious() != null && t.getWhitespacesBeforeCount() > 1 && attachTyp != AttachType.EXTONTOLOGY) {
                    if (t.getWhitespacesBeforeCount() > 10) 
                        break;
                    if (com.pullenti.morph.CharsInfo.ooNoteq(t.chars, t.getPrevious().chars)) 
                        break;
                }
                if (t.chars.isAllLower() && _org.getKind() == OrganizationKind.JUSTICE) {
                    if (t.isValue("ПО", null) && t.getNext() != null && t.getNext().isValue("ПРАВО", null)) {
                    }
                    else if (t.isValue("З", null) && t.getNext() != null && t.getNext().isValue("ПРАВ", null)) {
                    }
                    else 
                        break;
                }
                if (_org.getKind() == OrganizationKind.FEDERATION) {
                    if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isConjunction()) 
                        break;
                }
                if (t.chars.isAllLower() && ((_org.getKind() == OrganizationKind.AIRPORT || _org.getKind() == OrganizationKind.HOTEL))) 
                    break;
                if ((typ != null && typ.getLengthChar() == 2 && ((com.pullenti.n2j.Utils.stringsEq(typ.typ, "АО") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "СП")))) && !specWordBefore && attachTyp == AttachType.NORMAL) {
                    if (!na.chars.isLatinLetter()) 
                        break;
                }
                if (t.chars.isLatinLetter() && typ != null && com.pullenti.morph.LanguageHelper.endsWithEx(typ.typ, "служба", "сервис", "сервіс", null)) 
                    break;
                if (typ != null && ((typ.root == null || !typ.root.isPurePrefix))) {
                    if (typ.chars.isLatinLetter() && na.chars.isLatinLetter()) {
                        if (!t.isValue("OF", null)) 
                            break;
                    }
                    if ((na.isInDictionary && na.getMorph().getLanguage().isCyrillic() && na.chars.isAllLower()) && !na.getMorph().getCase().isUndefined()) {
                        if (na.preposition == null) {
                            if (!na.getMorph().getCase().isGenitive()) 
                                break;
                            if (_org.getKind() == OrganizationKind.PARTY && !specWordBefore) {
                                if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "лига")) {
                                }
                                else 
                                    break;
                            }
                            if (na.getMorph().getNumber() != com.pullenti.morph.MorphNumber.PLURAL) {
                                com.pullenti.ner.ReferentToken prr = t.kit.processReferent("PERSONPROPERTY", t);
                                if (prr != null) {
                                    if (com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(na.getEndToken().getNext(), false) != null) {
                                    }
                                    else 
                                        break;
                                }
                            }
                        }
                    }
                    if (na.preposition != null) {
                        if (_org.getKind() == OrganizationKind.PARTY) {
                            if (com.pullenti.n2j.Utils.stringsEq(na.preposition, "ЗА") || com.pullenti.n2j.Utils.stringsEq(na.preposition, "ПРОТИВ")) {
                            }
                            else if (com.pullenti.n2j.Utils.stringsEq(na.preposition, "В")) {
                                if (na.value.startsWith("ЗАЩИТ") && na.value.startsWith("ПОДДЕРЖ")) {
                                }
                                else 
                                    break;
                            }
                            else 
                                break;
                        }
                        else {
                            if (com.pullenti.n2j.Utils.stringsEq(na.preposition, "В")) 
                                break;
                            if (typ.isDoubtRootWord()) {
                                if (com.pullenti.morph.LanguageHelper.endsWithEx(typ.typ, "комитет", "комиссия", "комітет", "комісія") && ((t.isValue("ПО", null) || t.isValue("З", null)))) {
                                }
                                else if (names == null && na.stdOrgNameNouns > 0) {
                                }
                                else 
                                    break;
                            }
                        }
                    }
                    else if (na.chars.isCapitalUpper() && na.chars.isCyrillicLetter()) {
                        com.pullenti.ner.ReferentToken prt = na.kit.processReferent("PERSON", na.getBeginToken());
                        if (prt != null) {
                            if (_org.getKind() == OrganizationKind.CHURCH) {
                                na.setEndToken(prt.getEndToken());
                                na.isStdName = true;
                                na.value = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(na, com.pullenti.ner.core.GetTextAttr.NO);
                            }
                            else if ((typ != null && typ.typ != null && (typ.typ.indexOf("фермер") >= 0)) && names == null) 
                                na.setEndToken(prt.getEndToken());
                            else 
                                break;
                        }
                    }
                }
                if (na.isEmptyWord) 
                    break;
                if (na.isStdTail) {
                    if (na.chars.isLatinLetter() && na.chars.isAllUpper() && (na.getLengthChar() < 4)) {
                        na.isStdTail = false;
                        na.value = na.getSourceText().toUpperCase();
                    }
                    else 
                        break;
                }
                names = new java.util.ArrayList<>();
            }
            else {
                com.pullenti.ner.org.internal.OrgItemNameToken na0 = names.get(names.size() - 1);
                if (na0.isStdTail) 
                    break;
                if (na.preposition == null) {
                    if ((!na.chars.isLatinLetter() && na.chars.isAllLower() && !na.isAfterConjunction) && !na.getMorph().getCase().isGenitive()) 
                        break;
                }
            }
            names.add(na);
            t1 = (t = na.getEndToken());
        }
        typ = _lastTyp(types);
        boolean doHigherAlways = false;
        if (typ != null) {
            if (((attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP)) && typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                return null;
            if (com.pullenti.morph.LanguageHelper.endsWithEx(typ.typ, "комитет", "комиссия", "комітет", "комісія")) {
            }
            else if (com.pullenti.n2j.Utils.stringsEq(typ.typ, "служба") && ownOrg != null && typ.name != null) {
                OrganizationKind ki = (((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class))).getKind();
                if (ki == OrganizationKind.PRESS || ki == OrganizationKind.MEDIA) {
                    typ.setCoef(typ.getCoef() + ((float)3));
                    doHigherAlways = true;
                }
                else 
                    ownOrg = null;
            }
            else if ((((typ.isDoubtRootWord() || com.pullenti.n2j.Utils.stringsEq(typ.typ, "организация") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "управление")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "служба") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "общество")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "союз") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "організація")) || com.pullenti.n2j.Utils.stringsEq(typ.typ, "керування") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "суспільство")) 
                ownOrg = null;
            if (_org.getKind() == OrganizationKind.GOVENMENT) {
                if (names == null && ((typ.name == null || com.pullenti.n2j.Utils.stringsCompare(typ.name, typ.typ, true) == 0))) {
                    if ((attachTyp != AttachType.EXTONTOLOGY && com.pullenti.n2j.Utils.stringsNe(typ.typ, "следственный комитет") && com.pullenti.n2j.Utils.stringsNe(typ.typ, "кабинет министров")) && com.pullenti.n2j.Utils.stringsNe(typ.typ, "слідчий комітет")) {
                        if (((com.pullenti.n2j.Utils.stringsEq(typ.typ, "администрация") || com.pullenti.n2j.Utils.stringsEq(typ.typ, "адміністрація"))) && (typ.getEndToken().getNext() instanceof com.pullenti.ner.TextToken)) {
                            com.pullenti.ner.ReferentToken rt1 = typ.kit.processReferent("PERSONPROPERTY", typ.getEndToken().getNext());
                            if (rt1 != null && typ.getEndToken().getNext().getMorph().getCase().isGenitive()) {
                                com.pullenti.ner.geo.GeoReferent _geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(rt1.referent.getValue("REF"), com.pullenti.ner.geo.GeoReferent.class);
                                if (_geo != null) {
                                    _org.addName("АДМИНИСТРАЦИЯ " + (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(typ.getEndToken().getNext(), com.pullenti.ner.TextToken.class))).term, true, null);
                                    _org.addGeoObject(_geo);
                                    return new com.pullenti.ner.ReferentToken(_org, typ.getBeginToken(), rt1.getEndToken(), null);
                                }
                            }
                        }
                        if ((typ.getCoef() < 5) || typ.chars.isAllLower()) 
                            return null;
                    }
                }
            }
        }
        else if (names != null && names.get(0).chars.isAllLower()) {
            if (attachTyp != AttachType.EXTONTOLOGY) 
                return null;
        }
        boolean always = false;
        String _name = null;
        if (((num != null || _org.getNumber() != null || epon != null) || attachTyp == AttachType.HIGH || attachTyp == AttachType.EXTONTOLOGY) || ownOrg != null) {
            if (names != null) {
                if ((names.size() == 1 && names.get(0).chars.isAllUpper() && attachTyp == AttachType.EXTONTOLOGY) && isAdditionalAttach) 
                    _org.addName(com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), names.get(names.size() - 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO), true, names.get(0).getBeginToken());
                else {
                    _name = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), names.get(names.size() - 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                    if ((names.get(0).isNounPhrase && typ != null && typ.root != null) && !typ.root.isPurePrefix && multTyp == null) 
                        _name = ((String)com.pullenti.n2j.Utils.notnull(typ.name, (typ != null && typ.typ != null ? typ.typ.toUpperCase() : null))) + " " + _name;
                }
            }
            else if (typ != null && typ.name != null && ((typ.root == null || !typ.root.isPurePrefix))) {
                if (typ.chars.isAllLower() && !typ.canBeOrganization && (typ.getNameWordsCount() < 3)) 
                    _org.addTypeStr(typ.name.toLowerCase());
                else 
                    _name = typ.name;
                if (typ != multTyp) {
                    if (t1.endChar < typ.getEndToken().endChar) 
                        t1 = typ.getEndToken();
                }
            }
            if (_name != null) {
                if (_name.length() > maxOrgName) 
                    return null;
                _org.addName(_name, true, null);
            }
            if (num != null) 
                _org.setNumber(num.number);
            if (epon != null) {
                for(String e : epon.eponyms) {
                    _org.addEponym(e);
                }
            }
            ok = attachTyp == AttachType.EXTONTOLOGY;
            for(com.pullenti.ner.Slot a : _org.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsNe(a.getTypeName(), OrganizationReferent.ATTR_TYPE)) {
                    ok = true;
                    break;
                }
            }
            if (attachTyp == AttachType.NORMAL) {
                if (typ == null) 
                    ok = false;
                else if ((typ.endChar - typ.beginChar) < 2) {
                    if (num == null && epon == null) 
                        ok = false;
                    else if (epon == null) {
                        if (t1.isWhitespaceAfter() || t1.getNext() == null) {
                        }
                        else if (t1.getNext().isCharOf(".,;") && t1.getNext().isWhitespaceAfter()) {
                        }
                        else 
                            ok = false;
                    }
                }
            }
            if ((!ok && typ != null && typ.canBeDepBeforeOrganization) && ownOrg != null) {
                _org.addTypeStr((ownOrg.kit.baseLanguage.isUa() ? "підрозділ" : "подразделение"));
                _org.setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class));
                t1 = ownOrg;
                ok = true;
            }
            else if (typ != null && ownOrg != null && com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class)), _org, true)) {
                if (com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class), _org)) {
                    if (_org.getKind() == OrganizationKind.DEPARTMENT && !typ.canBeDepBeforeOrganization) {
                    }
                    else {
                        _org.setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class));
                        if (t1.endChar < ownOrg.endChar) 
                            t1 = ownOrg;
                        ok = true;
                    }
                }
            }
        }
        else if (names != null) {
            if (typ == null) {
                if (names.get(0).isStdName && specWordBefore) {
                    _org.addName(names.get(0).value, true, null);
                    t1 = names.get(0).getEndToken();
                    t = attachTailAttributes(_org, t1.getNext(), null, true, attachTyp, false);
                    if (t != null) 
                        t1 = t;
                    return new com.pullenti.ner.ReferentToken(_org, t0, t1, null);
                }
                return null;
            }
            if (typ.root != null && typ.root.mustHasCapitalName) {
                if (names.get(0).chars.isAllLower()) 
                    return null;
            }
            if (names.get(0).chars.isLatinLetter()) {
                if (typ.root != null && !typ.root.canHasLatinName) {
                    if (!typ.chars.isLatinLetter()) 
                        return null;
                }
                if (names.get(0).chars.isAllLower() && !typ.chars.isLatinLetter()) 
                    return null;
                StringBuilder tmp = new StringBuilder();
                tmp.append(names.get(0).value);
                t1 = names.get(0).getEndToken();
                for(int j = 1; j < names.size(); j++) {
                    if (!names.get(j).isStdTail && ((names.get(j).isNewlineBefore() || !names.get(j).chars.isLatinLetter()))) {
                        tMax = names.get(j).getBeginToken().getPrevious();
                        if (typ.geo == null && _org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) 
                            _org.getSlots().remove(_org.findSlot(OrganizationReferent.ATTR_GEO, null, true));
                        break;
                    }
                    else {
                        t1 = names.get(j).getEndToken();
                        if (names.get(j).isStdTail) {
                            com.pullenti.ner.org.internal.OrgItemEngItem ei = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttach(names.get(j).getBeginToken(), false);
                            if (ei != null) {
                                _org.addTypeStr(ei.fullValue);
                                if (ei.shortValue != null) 
                                    _org.addTypeStr(ei.shortValue);
                            }
                            break;
                        }
                        if (names.get(j - 1).getEndToken().isChar('.') && !names.get(j - 1).value.endsWith(".")) 
                            tmp.append(".").append(names.get(j).value);
                        else 
                            tmp.append(" ").append(names.get(j).value);
                    }
                }
                if (tmp.length() > maxOrgName) 
                    return null;
                String nnn = tmp.toString();
                if (nnn.startsWith("OF ") || nnn.startsWith("IN ")) 
                    tmp.insert(0, (((String)com.pullenti.n2j.Utils.notnull(typ.name, typ.typ))).toUpperCase() + " ");
                if (tmp.length() < 3) {
                    if (tmp.length() < 2) 
                        return null;
                    if (types != null && names.get(0).chars.isAllUpper()) {
                    }
                    else 
                        return null;
                }
                ok = true;
                _org.addName(tmp.toString(), true, null);
            }
            else if (typ.root != null && typ.root.isPurePrefix) {
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(typ.getEndToken(), com.pullenti.ner.TextToken.class);
                if (tt == null) 
                    return null;
                if (tt.isNewlineAfter()) 
                    return null;
                if (typ.getBeginToken() == typ.getEndToken() && tt.chars.isAllLower()) 
                    return null;
                if (names.get(0).chars.isAllLower()) {
                    if (!names.get(0).getMorph().getCase().isGenitive()) 
                        return null;
                }
                t1 = names.get(0).getEndToken();
                for(int j = 1; j < names.size(); j++) {
                    if (names.get(j).isNewlineBefore() || com.pullenti.morph.CharsInfo.ooNoteq(names.get(j).chars, names.get(0).chars)) 
                        break;
                    else 
                        t1 = names.get(j).getEndToken();
                }
                ok = true;
                _name = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), t1, com.pullenti.ner.core.GetTextAttr.NO);
                if (num == null && (t1 instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) {
                    com.pullenti.ner.Token tt1 = t1.getPrevious();
                    if (tt1 != null && tt1.isHiphen()) 
                        tt1 = tt1.getPrevious();
                    if (tt1 != null && tt1.endChar > names.get(0).beginChar && (tt1 instanceof com.pullenti.ner.TextToken)) {
                        _name = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), tt1, com.pullenti.ner.core.GetTextAttr.NO);
                        _org.setNumber(((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).value).toString());
                    }
                }
                if (_name.length() > maxOrgName) 
                    return null;
                _org.addName(_name, true, names.get(0).getBeginToken());
            }
            else {
                if (typ.isDep()) 
                    return null;
                if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL && attachTyp != AttachType.MULTIPLE) 
                    return null;
                StringBuilder tmp = new StringBuilder();
                float koef = typ.getCoef();
                if (koef >= 4) 
                    always = true;
                if (_org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) 
                    koef += ((float)1);
                if (specWordBefore) 
                    koef += ((float)1);
                if (names.get(0).chars.isAllLower() && typ.chars.isAllLower() && !specWordBefore) {
                    if (koef >= 3) {
                        if (t != null && (t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                        }
                        else 
                            koef -= ((float)3);
                    }
                }
                if (typ.charsRoot.isCapitalUpper()) 
                    koef += ((float)0.5);
                if (types.size() > 1) 
                    koef += ((float)(types.size() - 1));
                if (typ.name != null) {
                    for(com.pullenti.ner.Token to = typ.getBeginToken(); to != typ.getEndToken() && to != null; to = to.getNext()) {
                        if (com.pullenti.ner.org.internal.OrgItemTypeToken.isStdAdjective(to, false)) 
                            koef += ((float)2);
                        if (to.chars.isCapitalUpper()) 
                            koef += ((float)0.5);
                    }
                }
                OrganizationKind ki = _org.getKind();
                if (attachTyp == AttachType.MULTIPLE && ((typ.name == null || typ.name.length() == typ.typ.length()))) {
                }
                else if (((((ki == OrganizationKind.MEDIA || ki == OrganizationKind.PARTY || ki == OrganizationKind.PRESS) || ki == OrganizationKind.FACTORY || ki == OrganizationKind.AIRPORT) || ((typ.root != null && typ.root.mustHasCapitalName)) || ki == OrganizationKind.BANK) || (typ.typ.indexOf("предприятие") >= 0) || (typ.typ.indexOf("организация") >= 0)) || (typ.typ.indexOf("підприємство") >= 0) || (typ.typ.indexOf("організація") >= 0)) {
                    if (typ.name != null) 
                        _org.addTypeStr(typ.name.toLowerCase());
                }
                else 
                    tmp.append((String)com.pullenti.n2j.Utils.notnull(typ.name, (typ != null && typ.typ != null ? typ.typ.toUpperCase() : null)));
                if (typ != multTyp) 
                    t1 = typ.getEndToken();
                for(int j = 0; j < names.size(); j++) {
                    if (((names.get(j).isNewlineBefore() && j > 0)) || names.get(j).isNounPhrase != names.get(0).isNounPhrase) 
                        break;
                    else if (com.pullenti.morph.CharsInfo.ooNoteq(names.get(j).chars, names.get(0).chars) && com.pullenti.morph.CharsInfo.ooNoteq(names.get(j).getBeginToken().chars, names.get(0).chars)) 
                        break;
                    else {
                        if (j == 0 && names.get(j).preposition == null && names.get(j).isInDictionary) {
                            if (!names.get(j).getMorph().getCase().isGenitive() && ((typ.root != null && !typ.root.canHasSingleName))) 
                                break;
                        }
                        if ((j == 0 && names.get(j).getWhitespacesBeforeCount() > 2 && names.get(j).getNewlinesBeforeCount() == 0) && names.get(j).getBeginToken().getPrevious() != null) 
                            koef -= ((((float)names.get(j).getWhitespacesBeforeCount())) / ((float)2));
                        if (names.get(j).isStdName) 
                            koef += ((float)4);
                        else if (names.get(j).stdOrgNameNouns > 0 && ((ki == OrganizationKind.GOVENMENT || com.pullenti.morph.LanguageHelper.endsWith(typ.typ, "центр")))) 
                            koef += ((float)names.get(j).stdOrgNameNouns);
                        if (ki == OrganizationKind.AIRPORT && j == 0) 
                            koef++;
                        t1 = names.get(j).getEndToken();
                        if (names.get(j).isNounPhrase) {
                            if (!names.get(j).chars.isAllLower()) {
                                com.pullenti.morph.MorphCase ca = names.get(j).getMorph().getCase();
                                if ((ca.isDative() || ca.isGenitive() || ca.isInstrumental()) || ca.isPrepositional()) 
                                    koef += ((float)0.5);
                                else 
                                    continue;
                            }
                            else if (((j == 0 || names.get(j).isAfterConjunction)) && names.get(j).getMorph().getCase().isGenitive() && names.get(j).preposition == null) 
                                koef += ((float)0.5);
                            if (j == (names.size() - 1)) {
                                if (names.get(j).getEndToken().getNext() instanceof com.pullenti.ner.TextToken) {
                                    if (names.get(j).getEndToken().getNext().getMorphClassInDictionary().isVerb()) 
                                        koef += 0.5F;
                                }
                            }
                        }
                        for(com.pullenti.ner.Token to = names.get(j).getBeginToken(); to != null; to = to.getNext()) {
                            if (to instanceof com.pullenti.ner.TextToken) {
                                if (attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
                                    if (to.chars.isCapitalUpper()) 
                                        koef += ((float)0.5);
                                    else if ((j == 0 && ((to.chars.isAllUpper() || to.chars.isLastLower())) && to.getLengthChar() > 2) && typ.root != null && typ.root.canHasLatinName) 
                                        koef += ((float)1);
                                }
                                else if (to.chars.isAllUpper() || to.chars.isCapitalUpper()) 
                                    koef += ((float)1);
                            }
                            if (to == names.get(j).getEndToken()) 
                                break;
                        }
                    }
                }
                for(com.pullenti.ner.Token ttt = typ.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                    if (ttt.getReferent() instanceof OrganizationReferent) {
                        koef += ((float)1);
                        break;
                    }
                    else if (!((ttt instanceof com.pullenti.ner.TextToken))) 
                        break;
                    else if (ttt.chars.isLetter()) 
                        break;
                }
                OrganizationKind oki = _org.getKind();
                if (oki == OrganizationKind.GOVENMENT || oki == OrganizationKind.STUDY || oki == OrganizationKind.PARTY) 
                    koef += ((float)(int)names.size());
                if (attachTyp != AttachType.NORMAL && attachTyp != AttachType.NORMALAFTERDEP) 
                    koef += ((float)3);
                com.pullenti.ner.core.BracketSequenceToken br1 = null;
                if ((t1.getWhitespacesAfterCount() < 2) && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t1.getNext(), true, false)) {
                    br1 = com.pullenti.ner.core.BracketHelper.tryParse(t1.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br1 != null && (br1.getLengthChar() < 30)) {
                        String sss = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br1, com.pullenti.ner.core.GetTextAttr.NO);
                        if (sss != null && sss.length() > 2) {
                            _org.addName(sss, true, br1.getBeginToken().getNext());
                            koef += ((float)1);
                            t1 = br1.getEndToken();
                        }
                        else 
                            br1 = null;
                    }
                }
                if (koef >= 3 && t1.getNext() != null) {
                    com.pullenti.ner.Referent r = t1.getNext().getReferent();
                    if (r != null && ((com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), gEONAME) || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), OrganizationReferent.OBJ_TYPENAME)))) 
                        koef += ((float)1);
                    else if (isGeo(t1.getNext(), false) != null) 
                        koef += ((float)1);
                    else if (t1.getNext().isChar('(') && isGeo(t1.getNext().getNext(), false) != null) 
                        koef += ((float)1);
                    else if (specWordBefore && t1.kit.processReferent("PERSON", t1.getNext()) != null) 
                        koef += ((float)1);
                }
                if (koef >= 4) 
                    ok = true;
                if (!ok) {
                    if ((oki == OrganizationKind.PRESS || oki == OrganizationKind.FEDERATION || _org.getTypes().contains("агентство")) || ((oki == OrganizationKind.PARTY && com.pullenti.ner.org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t0.getPrevious())))) {
                        if (!names.get(0).isNewlineBefore() && !names.get(0).getMorph()._getClass().isProper()) {
                            if (names.get(0).getMorph().getCase().isGenitive() && names.get(0).isInDictionary) {
                                if (typ.chars.isAllLower() && !names.get(0).chars.isAllLower()) {
                                    ok = true;
                                    t1 = names.get(0).getEndToken();
                                }
                            }
                            else if (!names.get(0).isInDictionary && names.get(0).chars.isAllUpper()) {
                                ok = true;
                                tmp.setLength(0);
                                t1 = names.get(0).getEndToken();
                            }
                        }
                    }
                }
                if ((!ok && oki == OrganizationKind.FEDERATION && names.get(0).getMorph().getCase().isGenitive()) && koef > 0) {
                    if (isGeo(names.get(names.size() - 1).getEndToken().getNext(), false) != null) 
                        ok = true;
                }
                if (!ok && typ != null && typ.root != null) {
                    if (names.size() == 1 && ((names.get(0).chars.isAllUpper() || names.get(0).chars.isLastLower()))) {
                        if ((ki == OrganizationKind.BANK || ki == OrganizationKind.CULTURE || ki == OrganizationKind.HOTEL) || ki == OrganizationKind.MEDIA || ki == OrganizationKind.MEDICAL) 
                            ok = true;
                    }
                }
                if (ok) {
                    com.pullenti.ner.Token tt1 = t1;
                    if (br1 != null) 
                        tt1 = br1.getBeginToken().getPrevious();
                    if ((tt1.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && (((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(tt1.getReferent(), com.pullenti.ner.geo.GeoReferent.class))).isState()) {
                        if (names.get(0).getBeginToken() != tt1) {
                            tt1 = t1.getPrevious();
                            _org.addGeoObject(t1.getReferent());
                        }
                    }
                    String s = com.pullenti.ner.core.MiscHelper.getTextValue(names.get(0).getBeginToken(), tt1, com.pullenti.ner.core.GetTextAttr.NO);
                    if ((tt1 == names.get(0).getEndToken() && typ != null && typ.typ != null) && (typ.typ.indexOf("фермер") >= 0) && names.get(0).value != null) 
                        s = names.get(0).value;
                    com.pullenti.morph.MorphClass cla = tt1.getMorphClassInDictionary();
                    if ((names.get(0).getBeginToken() == t1 && s != null && t1.getMorph().getCase().isGenitive()) && t1.chars.isCapitalUpper()) {
                        if (cla.isUndefined() || cla.isProperGeo()) {
                            if (ki == OrganizationKind.MEDICAL || ki == OrganizationKind.JUSTICE) {
                                com.pullenti.ner.geo.GeoReferent _geo = new com.pullenti.ner.geo.GeoReferent();
                                _geo.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, t1.getNormalCaseText(new com.pullenti.morph.MorphClass(null), false, com.pullenti.morph.MorphGender.UNDEFINED, false), false, 0);
                                _geo.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_TYPE, (t1.kit.baseLanguage.isUa() ? "місто" : "город"), false, 0);
                                com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(_geo, t1, t1, null);
                                rt.data = ad;
                                _org.addGeoObject(rt);
                                s = null;
                            }
                        }
                    }
                    if (s != null) {
                        if (tmp.length() == 0) {
                            if (names.get(0).getMorph().getCase().isGenitive() || names.get(0).preposition != null) {
                                if (names.get(0).chars.isAllLower()) 
                                    tmp.append((String)com.pullenti.n2j.Utils.notnull(typ.name, typ.typ));
                            }
                        }
                        if (tmp.length() > 0) 
                            tmp.append(' ');
                        tmp.append(s);
                    }
                    if (tmp.length() > maxOrgName) 
                        return null;
                    _org.addName(tmp.toString(), true, names.get(0).getBeginToken());
                    if (types.size() > 1 && types.get(0).name != null) 
                        _org.addTypeStr(types.get(0).name.toLowerCase());
                }
            }
        }
        else {
            if (typ == null) 
                return null;
            if (types.size() == 2 && types.get(0).getCoef() > typ.getCoef()) 
                typ = types.get(0);
            if ((com.pullenti.n2j.Utils.stringsEq(typ.typ, "банк") && (t instanceof com.pullenti.ner.ReferentToken) && !t.isNewlineBefore()) && typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                if (typ.name != null) {
                    if (typ.getBeginToken().chars.isAllLower()) 
                        _org.addTypeStr(typ.name.toLowerCase());
                    else {
                        _org.addName(typ.name, true, null);
                        String s0 = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(typ, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
                        if (com.pullenti.n2j.Utils.stringsNe(s0, typ.name)) 
                            _org.addName(s0, true, null);
                    }
                }
                com.pullenti.ner.Referent r = t.getReferent();
                if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), gEONAME) && com.pullenti.morph.MorphCase.ooNoteq(t.getMorph().getCase(), com.pullenti.morph.MorphCase.NOMINATIVE)) {
                    _org.addGeoObject(r);
                    return new com.pullenti.ner.ReferentToken(_org, t0, t, null);
                }
            }
            if (((typ.root != null && typ.root.isPurePrefix)) && (typ.getCoef() < 4)) 
                return null;
            if (typ.root != null && typ.root.mustHasCapitalName) 
                return null;
            if (typ.name == null) {
                if (((typ.typ.endsWith("университет") || typ.typ.endsWith("університет"))) && isGeo(typ.getEndToken().getNext(), false) != null) 
                    always = true;
                else if (((_org.getKind() == OrganizationKind.JUSTICE || _org.getKind() == OrganizationKind.AIRPORT)) && _org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) {
                }
                else if (typ.getCoef() >= 4) 
                    always = true;
                else if (typ.chars.isCapitalUpper()) {
                    if (typ.getEndToken().getNext() != null && ((typ.getEndToken().getNext().isHiphen() || typ.getEndToken().getNext().isCharOf(":")))) {
                    }
                    else {
                        java.util.ArrayList<com.pullenti.ner.core.IntOntologyItem> li = (ad == null ? null : ad.localOntology.tryAttachByItem(_org.createOntologyItem()));
                        if (li != null && li.size() > 0) {
                            for(com.pullenti.ner.core.IntOntologyItem ll : li) {
                                com.pullenti.ner.Referent r = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.notnull(ll.referent, ((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(ll.tag, com.pullenti.ner.Referent.class)));
                                if (r != null) {
                                    if (_org.canBeEquals(r, com.pullenti.ner.Referent.EqualType.FORMERGING)) {
                                        com.pullenti.ner.Token ttt = typ.getEndToken();
                                        com.pullenti.ner.org.internal.OrgItemNumberToken nu = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(ttt.getNext(), true, null);
                                        if (nu != null) {
                                            if (com.pullenti.n2j.Utils.stringsNe((((OrganizationReferent)com.pullenti.n2j.Utils.cast(r, OrganizationReferent.class))).getNumber(), nu.number)) 
                                                ttt = null;
                                            else {
                                                _org.setNumber(nu.number);
                                                ttt = nu.getEndToken();
                                            }
                                        }
                                        else if (li.size() > 1) 
                                            ttt = null;
                                        if (ttt != null) 
                                            return new com.pullenti.ner.ReferentToken(r, typ.getBeginToken(), ttt, null);
                                    }
                                }
                            }
                        }
                    }
                    return null;
                }
                else {
                    int cou = 0;
                    for(com.pullenti.ner.Token tt = typ.getBeginToken().getPrevious(); tt != null && (cou < 200); tt = tt.getPrevious(),cou++) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                        if (org0 == null) 
                            continue;
                        if (!org0.canBeEquals(_org, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                            continue;
                        tt = (com.pullenti.ner.Token)com.pullenti.n2j.Utils.notnull(attachTailAttributes(_org, typ.getEndToken().getNext(), ad, false, attachTyp, false), (typ != null ? typ.getEndToken() : null));
                        if (!org0.canBeEquals(_org, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                            break;
                        _org.mergeSlots(org0, true);
                        return new com.pullenti.ner.ReferentToken(_org, typ.getBeginToken(), tt, null);
                    }
                    if (typ.root != null && typ.root.canBeSingleGeo && t1.getNext() != null) {
                        Object ggg = isGeo(t1.getNext(), false);
                        if (ggg != null) {
                            _org.addGeoObject(ggg);
                            t1 = getGeoEndToken(ggg, t1.getNext());
                            return new com.pullenti.ner.ReferentToken(_org, t0, t1, null);
                        }
                    }
                    return null;
                }
            }
            if (typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL || typ == multTyp) 
                return null;
            float koef = typ.getCoef();
            if (typ.getNameWordsCount() == 1 && typ.name != null && typ.name.length() > typ.typ.length()) 
                koef++;
            if (specWordBefore) 
                koef += ((float)1);
            ok = false;
            if (typ.charsRoot.isCapitalUpper()) {
                koef += ((float)0.5);
                if (typ.getNameWordsCount() == 1) 
                    koef += ((float)0.5);
            }
            if (epon != null) 
                koef += ((float)2);
            boolean hasNonstdWords = false;
            for(com.pullenti.ner.Token to = typ.getBeginToken(); to != typ.getEndToken() && to != null; to = to.getNext()) {
                if (com.pullenti.ner.org.internal.OrgItemTypeToken.isStdAdjective(to, false)) {
                    if (typ.root != null && typ.root.coeff > 0) 
                        koef += ((float)(com.pullenti.ner.org.internal.OrgItemTypeToken.isStdAdjective(to, true) ? 1 : (int)0.5F));
                }
                else 
                    hasNonstdWords = true;
                if (to.chars.isCapitalUpper() && !to.getMorph()._getClass().isPronoun()) 
                    koef += ((float)0.5);
            }
            if (!hasNonstdWords && _org.getKind() == OrganizationKind.GOVENMENT) 
                koef -= ((float)2);
            if (typ.chars.isAllLower() && (typ.getCoef() < 4)) 
                koef -= ((float)2);
            if (koef > 1 && typ.getNameWordsCount() > 2) 
                koef += ((float)2);
            for(com.pullenti.ner.Token ttt = typ.getBeginToken().getPrevious(); ttt != null; ttt = ttt.getPrevious()) {
                if (ttt.getReferent() instanceof OrganizationReferent) {
                    koef += ((float)1);
                    break;
                }
                else if (!((ttt instanceof com.pullenti.ner.TextToken))) 
                    break;
                else if (ttt.chars.isLetter()) 
                    break;
            }
            for(com.pullenti.ner.Token ttt = typ.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                if (ttt.getReferent() instanceof OrganizationReferent) {
                    koef += ((float)1);
                    break;
                }
                else if (!((ttt instanceof com.pullenti.ner.TextToken))) 
                    break;
                else if (ttt.chars.isLetter()) 
                    break;
            }
            if (typ.getWhitespacesBeforeCount() > 4 && typ.getWhitespacesAfterCount() > 4) 
                koef += ((float)0.5);
            if (typ.canBeOrganization) 
                koef += ((float)3);
            _org.addType(typ, false);
            if (((_org.getKind() == OrganizationKind.BANK || _org.getKind() == OrganizationKind.JUSTICE)) && typ.name != null && typ.name.length() > typ.typ.length()) 
                koef += ((float)1);
            if (_org.getKind() == OrganizationKind.JUSTICE && _org.getGeoObjects().size() > 0) 
                always = true;
            if (_org.getKind() == OrganizationKind.AIRPORT) {
                for(com.pullenti.ner.geo.GeoReferent g : _org.getGeoObjects()) {
                    if (g.isCity()) 
                        always = true;
                }
            }
            if (koef > 3 || always) 
                ok = true;
            if (((_org.getKind() == OrganizationKind.PARTY || _org.getKind() == OrganizationKind.JUSTICE)) && typ.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                if (_org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null && typ.name != null && typ.name.length() > typ.typ.length()) 
                    ok = true;
                else if (typ.getCoef() >= 4) 
                    ok = true;
                else if (typ.getNameWordsCount() > 2) 
                    ok = true;
            }
            if (ok) {
                if (typ.name != null && !typ.isNotTyp) {
                    if (typ.name.length() > maxOrgName || com.pullenti.n2j.Utils.stringsCompare(typ.name, typ.typ, true) == 0) 
                        return null;
                    _org.addName(typ.name, true, null);
                }
                t1 = typ.getEndToken();
            }
        }
        if (!ok || _org.getSlots().size() == 0) 
            return null;
        if (attachTyp == AttachType.NORMAL || attachTyp == AttachType.NORMALAFTERDEP) {
            ok = always;
            for(com.pullenti.ner.Slot s : _org.getSlots()) {
                if (com.pullenti.n2j.Utils.stringsNe(s.getTypeName(), OrganizationReferent.ATTR_TYPE) && com.pullenti.n2j.Utils.stringsNe(s.getTypeName(), OrganizationReferent.ATTR_PROFILE)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) 
                return null;
        }
        if (tMax != null && (t1.endChar < tMax.beginChar)) 
            t1 = tMax;
        t = attachTailAttributes(_org, t1.getNext(), null, true, attachTyp, false);
        if (t != null) 
            t1 = t;
        if (ownOrg != null && _org.getHigher() == null) {
            if (doHigherAlways || com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class), _org, false)) {
                _org.setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(ownOrg.referent, OrganizationReferent.class));
                if (ownOrg.beginChar > t1.beginChar) {
                    t1 = ownOrg;
                    t = attachTailAttributes(_org, t1.getNext(), null, true, attachTyp, false);
                    if (t != null) 
                        t1 = t;
                }
            }
        }
        if (attachTyp == AttachType.NORMAL && ((typ == null || (typ.getCoef() < 4)))) {
            if (_org.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null || ((typ != null && typ.geo != null))) {
                boolean isAllLow = true;
                for(t = t0; t != t1.getNext(); t = t.getNext()) {
                    if (t.chars.isLetter()) {
                        if (!t.chars.isAllLower()) 
                            isAllLow = false;
                    }
                    else if (!((t instanceof com.pullenti.ner.TextToken))) 
                        isAllLow = false;
                }
                if (isAllLow && !specWordBefore) 
                    return null;
            }
        }
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(_org, t0, t1, null);
        if (types != null && types.size() > 0) {
            res.setMorph(types.get(0).getMorph());
            if (types.get(0).isNotTyp && types.get(0).getBeginToken() == t0 && (types.get(0).endChar < t1.endChar)) 
                res.setBeginToken(types.get(0).getEndToken().getNext());
        }
        else 
            res.setMorph(t0.getMorph());
        if ((_org.getNumber() == null && t1.getNext() != null && (t1.getWhitespacesAfterCount() < 2)) && typ != null && ((typ.root == null || typ.root.canHasNumber))) {
            com.pullenti.ner.org.internal.OrgItemNumberToken num1 = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t1.getNext(), false, typ);
            if (num1 == null && t1.getNext().isHiphen()) 
                num1 = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t1.getNext().getNext(), false, typ);
            if (num1 != null) {
                if (com.pullenti.ner.org.internal.OrgItemTypeToken.isDecreeKeyword(t0.getPrevious(), 2)) {
                }
                else {
                    _org.setNumber(num1.number);
                    t1 = num1.getEndToken();
                    res.setEndToken(t1);
                }
            }
        }
        return res;
    }

    private com.pullenti.ner.ReferentToken tryAttachOrgBefore(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        if (t == null || t.getPrevious() == null) 
            return null;
        int minEndChar = t.getPrevious().endChar;
        int maxEndChar = t.endChar;
        com.pullenti.ner.Token t0 = t.getPrevious();
        if ((t0 instanceof com.pullenti.ner.ReferentToken) && (t0.getReferent() instanceof OrganizationReferent) && t0.getPrevious() != null) {
            minEndChar = t0.getPrevious().endChar;
            t0 = t0.getPrevious();
        }
        com.pullenti.ner.ReferentToken res = null;
        for(; t0 != null; t0 = t0.getPrevious()) {
            if (t0.getWhitespacesAfterCount() > 1) 
                break;
            int cou = 0;
            com.pullenti.ner.Token tt0 = t0;
            String num = null;
            com.pullenti.ner.Token numEt = null;
            for(com.pullenti.ner.Token ttt = t0; ttt != null; ttt = ttt.getPrevious()) {
                if (ttt.getWhitespacesAfterCount() > 1) 
                    break;
                if (ttt.isHiphen() || ttt.isChar('.')) 
                    continue;
                if (ttt instanceof com.pullenti.ner.NumberToken) {
                    if (num != null) 
                        break;
                    num = ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt, com.pullenti.ner.NumberToken.class))).value).toString();
                    numEt = ttt;
                    tt0 = ttt.getPrevious();
                    continue;
                }
                com.pullenti.ner.org.internal.OrgItemNumberToken nn = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(ttt, false, null);
                if (nn != null) {
                    num = nn.number;
                    numEt = nn.getEndToken();
                    tt0 = ttt.getPrevious();
                    continue;
                }
                if ((++cou) > 10) 
                    break;
                if (ttt.isValue("НАПРАВЛЕНИЕ", "НАПРЯМОК")) {
                    if (num != null || (((ttt.getPrevious() instanceof com.pullenti.ner.NumberToken) && (ttt.getWhitespacesBeforeCount() < 3)))) {
                        OrganizationReferent oo = new OrganizationReferent();
                        oo.addProfile(OrgProfile.UNIT);
                        oo.addTypeStr((((ttt.getMorph().getLanguage().isUa() ? "НАПРЯМОК" : "НАПРАВЛЕНИЕ"))).toLowerCase());
                        com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken(oo, ttt, ttt, null);
                        if (numEt != null && num != null) {
                            oo.addSlot(OrganizationReferent.ATTR_NUMBER, num, false, 0);
                            rt0.setEndToken(numEt);
                            return rt0;
                        }
                        if (ttt.getPrevious() instanceof com.pullenti.ner.NumberToken) {
                            rt0.setBeginToken(ttt.getPrevious());
                            oo.addSlot(OrganizationReferent.ATTR_NUMBER, ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ttt.getPrevious(), com.pullenti.ner.NumberToken.class))).value).toString(), false, 0);
                            return rt0;
                        }
                    }
                }
                com.pullenti.ner.org.internal.OrgItemTypeToken typ1 = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(ttt, true, null);
                if (typ1 == null) {
                    if (cou == 1) 
                        break;
                    continue;
                }
                if (typ1.getEndToken() == tt0) 
                    t0 = ttt;
            }
            com.pullenti.ner.ReferentToken rt = tryAttachOrg(t0, ad, AttachType.NORMAL, null, false, 0, -1);
            if (rt != null) {
                if (rt.endChar >= minEndChar && rt.endChar <= maxEndChar) {
                    OrganizationReferent oo = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
                    if (oo.getHigher() != null && oo.getHigher().getHigher() != null && oo.getHigher() == rt.getEndToken().getReferent()) 
                        return rt;
                    if (rt.beginChar < t.beginChar) 
                        return rt;
                    res = rt;
                }
                else 
                    break;
            }
            else if (!((t0 instanceof com.pullenti.ner.TextToken))) 
                break;
            else if (!t0.chars.isLetter()) {
                if (!com.pullenti.ner.core.BracketHelper.isBracket(t0, false)) 
                    break;
            }
        }
        if (res != null) 
            return null;
        com.pullenti.ner.org.internal.OrgItemTypeToken typ = null;
        for(t0 = t.getPrevious(); t0 != null; t0 = t0.getPrevious()) {
            if (t0.getWhitespacesAfterCount() > 1) 
                break;
            if (t0 instanceof com.pullenti.ner.NumberToken) 
                continue;
            if (t0.isChar('.') || t0.isHiphen()) 
                continue;
            if (!((t0 instanceof com.pullenti.ner.TextToken))) 
                break;
            if (!t0.chars.isLetter()) 
                break;
            com.pullenti.ner.org.internal.OrgItemTypeToken ty = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t0, true, ad);
            if (ty != null) {
                com.pullenti.ner.org.internal.OrgItemNumberToken nn = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(ty.getEndToken().getNext(), true, ty);
                if (nn != null) {
                    ty.setEndToken(nn.getEndToken());
                    ty.number = nn.number;
                }
                else if ((ty.getEndToken().getNext() instanceof com.pullenti.ner.NumberToken) && (ty.getWhitespacesAfterCount() < 2)) {
                    ty.setEndToken(ty.getEndToken().getNext());
                    ty.number = ((Long)(((com.pullenti.ner.NumberToken)com.pullenti.n2j.Utils.cast(ty.getEndToken(), com.pullenti.ner.NumberToken.class))).value).toString();
                }
                if (ty.endChar >= minEndChar && ty.endChar <= maxEndChar) 
                    typ = ty;
                else 
                    break;
            }
        }
        if (typ != null && typ.isDep()) 
            res = tryAttachDepBeforeOrg(typ, null);
        return res;
    }

    public static final String ANALYZER_NAME = "ORGANIZATION";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new OrganizationAnalyzer();
    }

    /**
     * Текст начинается с номера, если есть
     */
    public boolean textStartsWithNumber = false;

    @Override
    public String getCaption() {
        return "Организации";
    }


    @Override
    public String getDescription() {
        return "Организации, предприятия, компании...";
    }


    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.org.internal.MetaOrganization.globalMeta});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<>();
        res.put(OrgProfile.UNIT.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("dep.png"));
        res.put(OrgProfile.UNION.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("party.png"));
        res.put(OrgProfile.COMPETITION.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("festival.png"));
        res.put(OrgProfile.HOLDING.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("holding.png"));
        res.put(OrgProfile.STATE.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("gov.png"));
        res.put(OrgProfile.FINANCE.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("bank.png"));
        res.put(OrgProfile.EDUCATION.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("study.png"));
        res.put(OrgProfile.SCIENCE.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("science.png"));
        res.put(OrgProfile.INDUSTRY.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("factory.png"));
        res.put(OrgProfile.TRADE.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("trade.png"));
        res.put(OrgProfile.POLICY.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("politics.png"));
        res.put(OrgProfile.JUSTICE.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("justice.png"));
        res.put(OrgProfile.ENFORCEMENT.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("gov.png"));
        res.put(OrgProfile.ARMY.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("military.png"));
        res.put(OrgProfile.SPORT.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("sport.png"));
        res.put(OrgProfile.RELIGION.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("church.png"));
        res.put(OrgProfile.MUSIC.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("music.png"));
        res.put(OrgProfile.MEDIA.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("media.png"));
        res.put(OrgProfile.PRESS.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("press.png"));
        res.put(OrgProfile.HOTEL.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("hotel.png"));
        res.put(OrgProfile.MEDICINE.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("medicine.png"));
        res.put(OrgProfile.TRANSPORT.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("train.png"));
        res.put(OrganizationKind.BANK.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("bank.png"));
        res.put(OrganizationKind.CULTURE.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("culture.png"));
        res.put(OrganizationKind.DEPARTMENT.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("dep.png"));
        res.put(OrganizationKind.FACTORY.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("factory.png"));
        res.put(OrganizationKind.GOVENMENT.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("gov.png"));
        res.put(OrganizationKind.MEDICAL.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("medicine.png"));
        res.put(OrganizationKind.PARTY.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("party.png"));
        res.put(OrganizationKind.STUDY.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("study.png"));
        res.put(OrganizationKind.FEDERATION.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("federation.png"));
        res.put(OrganizationKind.CHURCH.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("church.png"));
        res.put(OrganizationKind.MILITARY.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("military.png"));
        res.put(OrganizationKind.AIRPORT.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("avia.png"));
        res.put(OrganizationKind.FESTIVAL.toString(), com.pullenti.ner.org.internal.ResourceHelper.getBytes("festival.png"));
        res.put(com.pullenti.ner.org.internal.MetaOrganization.ORGIMAGEID, com.pullenti.ner.org.internal.ResourceHelper.getBytes("org.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.n2j.Utils.stringsEq(type, OrganizationReferent.OBJ_TYPENAME)) 
            return new OrganizationReferent();
        return null;
    }

    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {com.pullenti.ner.geo.GeoReferent.OBJ_TYPENAME, com.pullenti.ner.address.AddressReferent.OBJ_TYPENAME});
    }


    @Override
    public int getProgressWeight() {
        return 45;
    }


    public static class OrgAnalyzerData extends com.pullenti.ner.core.AnalyzerDataWithOntology {
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            if (referent instanceof com.pullenti.ner.org.OrganizationReferent) 
                (((com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(referent, com.pullenti.ner.org.OrganizationReferent.class))).finalCorrection();
            int slots = referent.getSlots().size();
            com.pullenti.ner.Referent res = super.registerReferent(referent);
            if (!largeTextRegim && (res instanceof com.pullenti.ner.org.OrganizationReferent) && ((res == referent || res.getSlots().size() != slots))) {
                com.pullenti.ner.core.IntOntologyItem ioi = (((com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(res, com.pullenti.ner.org.OrganizationReferent.class))).createOntologyItemEx(3, true, false);
                if (ioi != null) 
                    locOrgs.addItem(ioi);
                java.util.ArrayList<String> names = (((com.pullenti.ner.org.OrganizationReferent)com.pullenti.n2j.Utils.cast(res, com.pullenti.ner.org.OrganizationReferent.class)))._getPureNames();
                if (names != null) {
                    for(String n : names) {
                        orgPureNames.add(new com.pullenti.ner.core.Termin(n, new com.pullenti.morph.MorphLang(null), false));
                    }
                }
            }
            return res;
        }
    
        public com.pullenti.ner.core.IntOntologyCollection locOrgs = new com.pullenti.ner.core.IntOntologyCollection();
    
        public com.pullenti.ner.core.TerminCollection orgPureNames = new com.pullenti.ner.core.TerminCollection();
    
        public com.pullenti.ner.core.TerminCollection aliases = new com.pullenti.ner.core.TerminCollection();
    
        public boolean largeTextRegim = false;
        public OrgAnalyzerData() {
            super();
        }
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new OrgAnalyzerData();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        OrgAnalyzerData ad = (OrgAnalyzerData)com.pullenti.n2j.Utils.cast(kit.getAnalyzerData(this), OrgAnalyzerData.class);
        if (kit.getSofa().getText().length() > 400000) 
            ad.largeTextRegim = true;
        else 
            ad.largeTextRegim = false;
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            t.setInnerBool(false);
        }
        int steps = 2;
        int max = steps;
        int delta = 100000;
        int parts = (((kit.getSofa().getText().length() + delta) - 1)) / delta;
        if (parts == 0) 
            parts = 1;
        max *= parts;
        int cur = 0;
        for(int step = 0; step < steps; step++) {
            int nextPos = delta;
            for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (t.beginChar > nextPos) {
                    nextPos += delta;
                    cur++;
                    if (!onProgress(cur, max, kit)) 
                        return;
                }
                if (step > 0 && (t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof OrganizationReferent)) {
                    com.pullenti.ner.MetaToken mt = _checkAliasAfter((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class), t.getNext());
                    if (mt != null) {
                        if (ad != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), t.getReferent(), false);
                            ad.aliases.add(term);
                        }
                        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(t.getReferent(), t, mt.getEndToken(), null);
                        kit.embedToken(rt);
                        t = rt;
                    }
                }
                while(true) {
                    java.util.ArrayList<com.pullenti.ner.ReferentToken> rts = tryAttachOrgs(t, ad, step);
                    if (rts == null || rts.size() == 0) 
                        break;
                    if (!com.pullenti.ner.MetaToken.check(rts)) 
                        break;
                    boolean emb = false;
                    for(com.pullenti.ner.ReferentToken rt : rts) {
                        if (!(((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))).checkCorrection()) 
                            continue;
                        rt.referent = ad.registerReferent(rt.referent);
                        if (rt.getBeginToken().getReferent() == rt.referent || rt.getEndToken().getReferent() == rt.referent) 
                            continue;
                        kit.embedToken(rt);
                        emb = true;
                        if (rt.beginChar <= t.beginChar) 
                            t = rt;
                    }
                    if ((rts.size() == 1 && t == rts.get(0) && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) && (t.getNext().getReferent() instanceof OrganizationReferent)) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rts.get(0).referent, OrganizationReferent.class);
                        OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getNext().getReferent(), OrganizationReferent.class);
                        if (org1.getHigher() == null && com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org0, org1, false) && !com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org1, org0, false)) {
                            com.pullenti.ner.ReferentToken rtt = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t.getNext(), com.pullenti.ner.ReferentToken.class);
                            kit.debedToken(rtt);
                            org1.setHigher(org0);
                            com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(ad.registerReferent(org1), t, rtt.getEndToken(), t.getNext().getMorph());
                            kit.embedToken(rt1);
                            t = rt1;
                        }
                    }
                    if (emb && !((t instanceof com.pullenti.ner.ReferentToken))) 
                        continue;
                    break;
                }
                if (step > 0) {
                    com.pullenti.ner.ReferentToken rt = checkOwnership(t);
                    if (rt != null) {
                        kit.embedToken(rt);
                        t = rt;
                    }
                }
                if ((t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof OrganizationReferent)) {
                    com.pullenti.ner.ReferentToken rt0 = (com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
                    while(rt0 != null) {
                        rt0 = tryAttachOrgBefore(rt0, ad);
                        if (rt0 == null) 
                            break;
                        _doPostAnalyze(rt0, ad);
                        rt0.referent = ad.registerReferent(rt0.referent);
                        kit.embedToken(rt0);
                        t = rt0;
                    }
                }
                if (step > 0 && (t instanceof com.pullenti.ner.ReferentToken) && (t.getReferent() instanceof OrganizationReferent)) {
                    com.pullenti.ner.MetaToken mt = _checkAliasAfter((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class), t.getNext());
                    if (mt != null) {
                        if (ad != null) {
                            com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                            term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), t.getReferent(), false);
                            ad.aliases.add(term);
                        }
                        com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(t.getReferent(), t, mt.getEndToken(), null);
                        kit.embedToken(rt);
                        t = rt;
                    }
                }
            }
            if (ad.getReferents().size() == 0) 
                break;
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> list = new java.util.ArrayList<>();
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class);
            if (_org == null) 
                continue;
            com.pullenti.ner.Token t1 = t.getNext();
            if (((t1 != null && t1.isChar('(') && t1.getNext() != null) && (t1.getNext().getReferent() instanceof OrganizationReferent) && t1.getNext().getNext() != null) && t1.getNext().getNext().isChar(')')) {
                OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t1.getNext().getReferent(), OrganizationReferent.class);
                if (org0 == _org || _org.getHigher() == org0) {
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(_org, t, t1.getNext().getNext(), t.getMorph());
                    kit.embedToken(rt1);
                    t = rt1;
                    t1 = t.getNext();
                }
                else if (_org.getHigher() == null && com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org0, _org, false) && !com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(_org, org0, false)) {
                    _org.setHigher(org0);
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.ReferentToken._new709(_org, t, t1.getNext().getNext(), t.getMorph());
                    kit.embedToken(rt1);
                    t = rt1;
                    t1 = t.getNext();
                }
            }
            com.pullenti.ner.TextToken ofTok = null;
            if (t1 != null) {
                if (t1.isCharOf(",;") || t1.isHiphen()) 
                    t1 = t1.getNext();
                else if (t1.isValue("ПРИ", null) || t1.isValue("OF", null) || t1.isValue("AT", null)) {
                    ofTok = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.TextToken.class);
                    t1 = t1.getNext();
                }
            }
            if (t1 == null) 
                break;
            OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t1.getReferent(), OrganizationReferent.class);
            if (org1 == null) 
                continue;
            if (ofTok == null) {
                if (_org.getHigher() == null && !com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org1, _org, false)) 
                    continue;
            }
            if (_org.getHigher() != null) {
                if (!_org.getHigher().canBeEquals(org1, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                    continue;
            }
            list.clear();
            list.add((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
            list.add((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t1, com.pullenti.ner.ReferentToken.class));
            if (ofTok != null && _org.getHigher() == null) {
                for(com.pullenti.ner.Token t2 = t1.getNext(); t2 != null; t2 = t2.getNext()) {
                    if (((t2 instanceof com.pullenti.ner.TextToken) && com.pullenti.n2j.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t2, com.pullenti.ner.TextToken.class))).term, ofTok.term) && t2.getNext() != null) && (t2.getNext().getReferent() instanceof OrganizationReferent)) {
                        t2 = t2.getNext();
                        if (org1.getHigher() != null) {
                            if (!org1.getHigher().canBeEquals(t2.getReferent(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                                break;
                        }
                        list.add((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(t2, com.pullenti.ner.ReferentToken.class));
                        org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t2.getReferent(), OrganizationReferent.class);
                    }
                    else 
                        break;
                }
            }
            com.pullenti.ner.ReferentToken rt0 = list.get(list.size() - 1);
            for(int i = list.size() - 2; i >= 0; i--) {
                _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(list.get(i).referent, OrganizationReferent.class);
                org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
                if (_org.getHigher() == null) {
                    _org.setHigher(org1);
                    _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(ad.registerReferent(_org), OrganizationReferent.class);
                }
                com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(_org, list.get(i), rt0, null);
                kit.embedToken(rt);
                t = rt;
                rt0 = rt;
            }
        }
        java.util.HashMap<String, java.util.ArrayList<OrganizationReferent>> owners = new java.util.HashMap<>();
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class);
            if (_org == null) 
                continue;
            OrganizationReferent hi = _org.getHigher();
            if (hi == null) 
                continue;
            for(String ty : _org.getTypes()) {
                java.util.ArrayList<OrganizationReferent> li;
                com.pullenti.n2j.Outargwrapper<java.util.ArrayList<OrganizationReferent>> inoutarg2170 = new com.pullenti.n2j.Outargwrapper<>();
                Boolean inoutres2171 = com.pullenti.n2j.Utils.tryGetValue(owners, ty, inoutarg2170);
                li = inoutarg2170.value;
                if (!inoutres2171) 
                    owners.put(ty, (li = new java.util.ArrayList<>()));
                java.util.ArrayList<OrganizationReferent> childs = null;
                if (!li.contains(hi)) {
                    li.add(hi);
                    hi.setTag((childs = new java.util.ArrayList<>()));
                }
                else 
                    childs = (java.util.ArrayList<OrganizationReferent>)com.pullenti.n2j.Utils.cast(hi.getTag(), java.util.ArrayList.class);
                if (childs != null && !childs.contains(_org)) 
                    childs.add(_org);
            }
        }
        java.util.ArrayList<OrganizationReferent> owns = new java.util.ArrayList<>();
        for(com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class);
            if (_org == null || _org.getHigher() != null) 
                continue;
            owns.clear();
            for(String ty : _org.getTypes()) {
                java.util.ArrayList<OrganizationReferent> li;
                com.pullenti.n2j.Outargwrapper<java.util.ArrayList<OrganizationReferent>> inoutarg2172 = new com.pullenti.n2j.Outargwrapper<>();
                Boolean inoutres2173 = com.pullenti.n2j.Utils.tryGetValue(owners, ty, inoutarg2172);
                li = inoutarg2172.value;
                if (!inoutres2173) 
                    continue;
                for(OrganizationReferent h : li) {
                    if (!owns.contains(h)) 
                        owns.add(h);
                }
            }
            if (owns.size() != 1) 
                continue;
            if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(owns.get(0), _org, true)) {
                java.util.ArrayList<OrganizationReferent> childs = (java.util.ArrayList<OrganizationReferent>)com.pullenti.n2j.Utils.cast(owns.get(0).getTag(), java.util.ArrayList.class);
                if (childs == null) 
                    continue;
                boolean hasNum = false;
                boolean hasGeo = false;
                for(OrganizationReferent oo : childs) {
                    if (oo.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null) 
                        hasGeo = true;
                    if (oo.findSlot(OrganizationReferent.ATTR_NUMBER, null, true) != null) 
                        hasNum = true;
                }
                if (hasNum != ((_org.findSlot(OrganizationReferent.ATTR_NUMBER, null, true) != null))) 
                    continue;
                if (hasGeo != ((_org.findSlot(OrganizationReferent.ATTR_GEO, null, true) != null))) 
                    continue;
                _org.setHigher(owns.get(0));
                if (_org.getKind() != OrganizationKind.DEPARTMENT) 
                    _org.setHigher(null);
            }
        }
    }

    public static com.pullenti.ner.MetaToken _checkAliasAfter(com.pullenti.ner.ReferentToken rt, com.pullenti.ner.Token t) {
        if ((t != null && t.isChar('<') && t.getNext() != null) && t.getNext().getNext() != null && t.getNext().getNext().isChar('>')) 
            t = t.getNext().getNext().getNext();
        if (t == null || t.getNext() == null || !t.isChar('(')) 
            return null;
        t = t.getNext();
        if (t.isValue("ДАЛЕЕ", null) || t.isValue("ДАЛІ", null)) 
            t = t.getNext();
        else if (t.isValue("HEREINAFTER", null) || t.isValue("ABBREVIATED", null) || t.isValue("HEREAFTER", null)) {
            t = t.getNext();
            if (t != null && t.isValue("REFER", null)) 
                t = t.getNext();
        }
        else 
            return null;
        while(t != null) {
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                break;
            else if (!t.chars.isLetter()) 
                t = t.getNext();
            else if (t.getMorph()._getClass().isPreposition() || t.getMorph()._getClass().isMisc() || t.isValue("ИМЕНОВАТЬ", null)) 
                t = t.getNext();
            else 
                break;
        }
        if (t == null) 
            return null;
        com.pullenti.ner.Token t1 = null;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.isNewlineBefore()) 
                break;
            else if (tt.isChar(')')) {
                t1 = tt.getPrevious();
                break;
            }
        }
        if (t1 == null) 
            return null;
        com.pullenti.ner.MetaToken mt = new com.pullenti.ner.MetaToken(t, t1.getNext(), null);
        String nam = com.pullenti.ner.core.MiscHelper.getTextValue(t, t1, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
        mt.tag = nam;
        if (nam.indexOf(' ') < 0) {
            for(com.pullenti.ner.Token tt = rt.getBeginToken(); tt != null && tt.endChar <= rt.endChar; tt = tt.getNext()) {
                if (tt.isValue((String)com.pullenti.n2j.Utils.cast(mt.tag, String.class), null)) 
                    return mt;
            }
            return null;
        }
        return mt;
    }

    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (begin == null) 
            return null;
        if (begin.kit.recurseLevel > 2) 
            return null;
        begin.kit.recurseLevel++;
        com.pullenti.ner.ReferentToken rt = tryAttachOrg(begin, null, AttachType.NORMAL, null, false, 0, -1);
        if (rt == null) 
            rt = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttachOrg(begin, false);
        if (rt == null) 
            rt = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttachOrg(begin, true);
        if (rt == null) 
            rt = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttachReferenceToExistOrg(begin);
        begin.kit.recurseLevel--;
        if (rt == null) 
            return null;
        rt.data = begin.kit.getAnalyzerData(this);
        return rt;
    }

    private java.util.ArrayList<com.pullenti.ner.ReferentToken> tryAttachOrgs(com.pullenti.ner.Token t, OrgAnalyzerData ad, int step) {
        if (t == null) 
            return null;
        if (ad != null && ad.localOntology.getItems().size() > 1000) 
            ad = null;
        if (t.chars.isLatinLetter() && com.pullenti.ner.core.MiscHelper.isEngArticle(t)) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> res11 = tryAttachOrgs(t.getNext(), ad, step);
            if (res11 != null && res11.size() > 0) {
                res11.get(0).setBeginToken(t);
                return res11;
            }
        }
        com.pullenti.ner.ReferentToken rt = null;
        com.pullenti.ner.org.internal.OrgItemTypeToken typ = null;
        if (step == 0 || t.getInnerBool()) {
            typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t, false, null);
            if (typ != null) 
                t.setInnerBool(true);
            if (typ == null || typ.chars.isLatinLetter()) {
                com.pullenti.ner.org.internal.OrgItemEngItem ltyp = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttach(t, false);
                if (ltyp != null) 
                    t.setInnerBool(true);
                else if (t.chars.isLatinLetter()) {
                    com.pullenti.ner.ReferentToken rte = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttachOrg(t, false);
                    if (rte != null) {
                        _doPostAnalyze(rte, ad);
                        java.util.ArrayList<com.pullenti.ner.ReferentToken> ree = new java.util.ArrayList<>();
                        ree.add(rte);
                        return ree;
                    }
                }
            }
        }
        com.pullenti.ner.ReferentToken rt00 = tryAttachSpec(t, ad);
        if (rt00 == null) 
            rt00 = _tryAttachOrgByAlias(t, ad);
        if (rt00 != null) {
            java.util.ArrayList<com.pullenti.ner.ReferentToken> res0 = new java.util.ArrayList<>();
            _doPostAnalyze(rt00, ad);
            res0.add(rt00);
            return res0;
        }
        if (typ != null) {
            if (typ.root == null || !typ.root.isPurePrefix) {
                if ((((typ.getMorph().getNumber().value()) & (com.pullenti.morph.MorphNumber.PLURAL.value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                    com.pullenti.ner.Token t1 = typ.getEndToken();
                    boolean ok = true;
                    boolean ok1 = false;
                    if (t1.getNext() != null && t1.getNext().isChar(',')) {
                        t1 = t1.getNext();
                        ok1 = true;
                        if (t1.getNext() != null && t1.getNext().isValue("КАК", null)) 
                            t1 = t1.getNext();
                        else 
                            ok = false;
                    }
                    if (t1.getNext() != null && t1.getNext().isValue("КАК", null)) {
                        t1 = t1.getNext();
                        ok1 = true;
                    }
                    if (t1.getNext() != null && t1.getNext().isChar(':')) 
                        t1 = t1.getNext();
                    if (t1 == t && t1.isNewlineAfter()) 
                        ok = false;
                    rt = null;
                    if (ok) {
                        if (!ok1 && typ.getCoef() > 0) 
                            ok1 = true;
                        if (ok1) 
                            rt = tryAttachOrg(t1.getNext(), ad, AttachType.MULTIPLE, typ, false, 0, -1);
                    }
                    if (rt != null) {
                        _doPostAnalyze(rt, ad);
                        java.util.ArrayList<com.pullenti.ner.ReferentToken> res = new java.util.ArrayList<>();
                        res.add(rt);
                        OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
                        if (ok1) 
                            rt.setBeginToken(t);
                        t1 = rt.getEndToken().getNext();
                        ok = true;
                        for(; t1 != null; t1 = t1.getNext()) {
                            if (t1.isNewlineBefore()) {
                                ok = false;
                                break;
                            }
                            boolean last = false;
                            if (t1.isChar(',')) {
                            }
                            else if (t1.isAnd() || t1.isOr()) 
                                last = true;
                            else {
                                if (res.size() < 2) 
                                    ok = false;
                                break;
                            }
                            t1 = t1.getNext();
                            com.pullenti.ner.org.internal.OrgItemTypeToken typ1 = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t1, true, ad);
                            if (typ1 != null) {
                                ok = false;
                                break;
                            }
                            rt = tryAttachOrg(t1, ad, AttachType.MULTIPLE, typ, false, 0, -1);
                            if (rt != null && rt.getBeginToken() == rt.getEndToken()) {
                                if (!rt.getBeginToken().getMorphClassInDictionary().isUndefined() && rt.getBeginToken().chars.isAllUpper()) 
                                    rt = null;
                            }
                            if (rt == null) {
                                if (res.size() < 2) 
                                    ok = false;
                                break;
                            }
                            _doPostAnalyze(rt, ad);
                            res.add(rt);
                            if (res.size() > 100) {
                                ok = false;
                                break;
                            }
                            _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
                            _org.addType(typ, false);
                            if (last) 
                                break;
                            t1 = rt.getEndToken();
                        }
                        if (ok && res.size() > 1) 
                            return res;
                    }
                }
            }
        }
        rt = null;
        if (typ != null && ((typ.isDep() || typ.canBeDepBeforeOrganization))) {
            rt = tryAttachDepBeforeOrg(typ, null);
            if (rt == null) 
                rt = tryAttachDepAfterOrg(typ);
            if (rt == null) 
                rt = tryAttachOrg(typ.getEndToken().getNext(), ad, AttachType.NORMALAFTERDEP, null, false, 0, -1);
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if ((step == 0 && rt == null && tt != null) && !tt.chars.isAllLower() && tt.chars.isCyrillicLetter()) {
            String s = tt.term;
            if (s.startsWith("ГУ") && s.length() > 3 && ((s.length() > 4 || com.pullenti.n2j.Utils.stringsEq(s, "ГУВД")))) {
                tt.term = (com.pullenti.n2j.Utils.stringsEq(s, "ГУВД") ? "МВД" : tt.term.substring(2));
                short inv = tt.invariantPrefixLength;
                tt.invariantPrefixLength = (short)0;
                short max = tt.maxLength;
                tt.maxLength = (short)tt.term.length();
                rt = tryAttachOrg(tt, ad, AttachType.NORMALAFTERDEP, null, false, 0, -1);
                tt.term = s;
                tt.invariantPrefixLength = inv;
                tt.maxLength = max;
                if (rt != null) {
                    typ = new com.pullenti.ner.org.internal.OrgItemTypeToken(tt, tt);
                    typ.typ = "главное управление";
                    com.pullenti.ner.ReferentToken rt0 = tryAttachDepBeforeOrg(typ, rt);
                    if (rt0 != null) {
                        rt.referent = ad.registerReferent(rt.referent);
                        rt.referent.addOccurence(new com.pullenti.ner.TextAnnotation(t, rt.getEndToken(), rt.referent));
                        (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class));
                        java.util.ArrayList<com.pullenti.ner.ReferentToken> li2 = new java.util.ArrayList<>();
                        _doPostAnalyze(rt0, ad);
                        li2.add(rt0);
                        return li2;
                    }
                }
            }
            else if (((((((((s.charAt(0) == 'У' && s.length() > 3 && tt.getMorphClassInDictionary().isUndefined())) || com.pullenti.n2j.Utils.stringsEq(s, "ОВД") || com.pullenti.n2j.Utils.stringsEq(s, "РОВД")) || com.pullenti.n2j.Utils.stringsEq(s, "ОМВД") || com.pullenti.n2j.Utils.stringsEq(s, "ОСБ")) || com.pullenti.n2j.Utils.stringsEq(s, "УПФ") || com.pullenti.n2j.Utils.stringsEq(s, "УФНС")) || com.pullenti.n2j.Utils.stringsEq(s, "ИФНС") || com.pullenti.n2j.Utils.stringsEq(s, "ИНФС")) || com.pullenti.n2j.Utils.stringsEq(s, "УВД") || com.pullenti.n2j.Utils.stringsEq(s, "УФМС")) || com.pullenti.n2j.Utils.stringsEq(s, "ОУФМС") || com.pullenti.n2j.Utils.stringsEq(s, "УФК")) || com.pullenti.n2j.Utils.stringsEq(s, "УФССП")) {
                if (com.pullenti.n2j.Utils.stringsEq(s, "ОВД") || com.pullenti.n2j.Utils.stringsEq(s, "УВД") || com.pullenti.n2j.Utils.stringsEq(s, "РОВД")) 
                    tt.term = "МВД";
                else if (com.pullenti.n2j.Utils.stringsEq(s, "ОСБ")) 
                    tt.term = "СБЕРБАНК";
                else if (com.pullenti.n2j.Utils.stringsEq(s, "УПФ")) 
                    tt.term = "ПФР";
                else if (com.pullenti.n2j.Utils.stringsEq(s, "УФНС") || com.pullenti.n2j.Utils.stringsEq(s, "ИФНС") || com.pullenti.n2j.Utils.stringsEq(s, "ИНФС")) 
                    tt.term = "ФНС";
                else if (com.pullenti.n2j.Utils.stringsEq(s, "УФМС") || com.pullenti.n2j.Utils.stringsEq(s, "ОУФМС")) 
                    tt.term = "ФМС";
                else 
                    tt.term = tt.term.substring(1);
                short inv = tt.invariantPrefixLength;
                tt.invariantPrefixLength = (short)0;
                short max = tt.maxLength;
                tt.maxLength = (short)tt.term.length();
                rt = tryAttachOrg(tt, ad, AttachType.NORMALAFTERDEP, null, false, 0, -1);
                tt.term = s;
                tt.invariantPrefixLength = inv;
                tt.maxLength = max;
                if (rt != null) {
                    OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
                    if (org1.getGeoObjects().size() == 0 && rt.getEndToken().getNext() != null) {
                        com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(rt.getEndToken().getNext().getReferent(), com.pullenti.ner.geo.GeoReferent.class);
                        if (g != null && g.isState()) {
                            org1.addGeoObject(g);
                            rt.setEndToken(rt.getEndToken().getNext());
                        }
                    }
                    typ = new com.pullenti.ner.org.internal.OrgItemTypeToken(tt, tt);
                    typ.typ = (s.charAt(0) == 'О' ? "отделение" : ((s.charAt(0) == 'И' ? "инспекция" : "управление")));
                    com.pullenti.morph.MorphGender gen = (s.charAt(0) == 'И' ? com.pullenti.morph.MorphGender.FEMINIE : com.pullenti.morph.MorphGender.NEUTER);
                    if (s.startsWith("ОУ")) 
                        typ.typ = "управление";
                    else if (s.startsWith("РО")) {
                        typ.typ = "отдел";
                        typ.altTyp = "районный отдел";
                        typ.nameIsName = true;
                        gen = com.pullenti.morph.MorphGender.MASCULINE;
                    }
                    com.pullenti.ner.ReferentToken rt0 = tryAttachDepBeforeOrg(typ, rt);
                    if (rt0 != null) {
                        OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class);
                        org0.addProfile(OrgProfile.UNIT);
                        if (org0.getNumber() == null && !tt.isNewlineAfter()) {
                            com.pullenti.ner.org.internal.OrgItemNumberToken num = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(tt.getNext(), true, typ);
                            if (num != null) {
                                org0.setNumber(num.number);
                                rt0.setEndToken(num.getEndToken());
                            }
                        }
                        Object _geo;
                        if (rt0.referent.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null) {
                            if ((((_geo = isGeo(rt0.getEndToken().getNext(), false)))) != null) {
                                if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).addGeoObject(_geo)) 
                                    rt0.setEndToken(getGeoEndToken(_geo, rt0.getEndToken().getNext()));
                            }
                            else if (rt0.getEndToken().getWhitespacesAfterCount() < 3) {
                                com.pullenti.ner.org.internal.OrgItemNameToken nam = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(rt0.getEndToken().getNext(), null, false, true);
                                if (nam != null) {
                                    if ((((_geo = isGeo(nam.getEndToken().getNext(), false)))) != null) {
                                        if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).addGeoObject(_geo)) 
                                            rt0.setEndToken(getGeoEndToken(_geo, nam.getEndToken().getNext()));
                                        (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).addName(nam.value, true, null);
                                    }
                                }
                            }
                        }
                        if (rt0.referent.getSlots().size() > 2) {
                            if (tt.getPrevious() != null && ((tt.getPrevious().getMorph()._getClass().isAdjective() && !tt.getPrevious().getMorph()._getClass().isVerb())) && tt.getWhitespacesBeforeCount() == 1) {
                                String adj = com.pullenti.morph.Morphology.getWordform(tt.getPrevious().getSourceText().toUpperCase(), com.pullenti.morph.MorphBaseInfo._new2174(com.pullenti.morph.MorphClass.ADJECTIVE, gen, tt.getPrevious().getMorph().getLanguage()));
                                if (adj != null && !adj.startsWith("УПОЛНОМОЧ") && !adj.startsWith("ОПЕРУПОЛНОМОЧ")) {
                                    String tyy = adj.toLowerCase() + " " + typ.typ;
                                    rt0.setBeginToken(tt.getPrevious());
                                    if (rt0.getBeginToken().getPrevious() != null && rt0.getBeginToken().getPrevious().isHiphen() && rt0.getBeginToken().getPrevious().getPrevious() != null) {
                                        com.pullenti.ner.Token tt0 = rt0.getBeginToken().getPrevious().getPrevious();
                                        if (com.pullenti.morph.CharsInfo.ooEq(tt0.chars, rt0.getBeginToken().chars) && (tt0 instanceof com.pullenti.ner.TextToken)) {
                                            adj = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt0, com.pullenti.ner.TextToken.class))).term;
                                            if (tt0.getMorph()._getClass().isAdjective() && !tt0.getMorph().containsAttr("неизм.", new com.pullenti.morph.MorphClass(null))) 
                                                adj = com.pullenti.morph.Morphology.getWordform(adj, com.pullenti.morph.MorphBaseInfo._new2174(com.pullenti.morph.MorphClass.ADJECTIVE, gen, tt0.getMorph().getLanguage()));
                                            tyy = adj.toLowerCase() + " " + tyy;
                                            rt0.setBeginToken(tt0);
                                        }
                                    }
                                    if (typ.nameIsName) 
                                        org0.addName(tyy.toUpperCase(), true, null);
                                    else 
                                        org0.addTypeStr(tyy);
                                }
                            }
                            for(com.pullenti.ner.geo.GeoReferent g : org1.getGeoObjects()) {
                                if (!g.isState()) {
                                    com.pullenti.ner.Slot sl = org1.findSlot(OrganizationReferent.ATTR_GEO, g, true);
                                    if (sl != null) 
                                        org1.getSlots().remove(sl);
                                    if (rt.getBeginToken().beginChar < rt0.getBeginToken().beginChar) 
                                        rt0.setBeginToken(rt.getBeginToken());
                                    org0.addGeoObject(g);
                                }
                            }
                            if (ad != null) 
                                rt.referent = ad.registerReferent(rt.referent);
                            rt.referent.addOccurence(new com.pullenti.ner.TextAnnotation(t, rt.getEndToken(), rt.referent));
                            (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt0.referent, OrganizationReferent.class))).setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class));
                            _doPostAnalyze(rt0, ad);
                            java.util.ArrayList<com.pullenti.ner.ReferentToken> li2 = new java.util.ArrayList<>();
                            li2.add(rt0);
                            return li2;
                        }
                    }
                    rt = null;
                }
            }
        }
        if (rt == null) {
            if (step > 0 && typ == null) {
                if (!com.pullenti.ner.core.BracketHelper.isBracket(t, false)) {
                    if (!t.chars.isLetter()) 
                        return null;
                    if (t.chars.isAllLower()) 
                        return null;
                }
            }
            rt = tryAttachOrg(t, ad, AttachType.NORMAL, null, false, 0, step);
            if (rt == null && step == 0) 
                rt = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttachOrg(t, false);
            if (rt != null) {
            }
        }
        if (((rt == null && step == 1 && typ != null) && typ.isDep() && typ.root != null) && !typ.root.canBeNormalDep) {
            if (com.pullenti.ner.org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(typ.getBeginToken().getPrevious())) 
                rt = tryAttachDep(typ, AttachType.HIGH, true);
        }
        if (rt == null && step == 0 && t != null) {
            boolean ok = false;
            if (t.getLengthChar() > 2 && !t.chars.isAllLower() && t.chars.isLatinLetter()) 
                ok = true;
            else if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) 
                ok = true;
            if (ok && t.getWhitespacesBeforeCount() != 1) 
                ok = false;
            if (ok && !com.pullenti.ner.org.internal.OrgItemTypeToken.checkPersonProperty(t.getPrevious())) 
                ok = false;
            if (ok) {
                OrganizationReferent _org = new OrganizationReferent();
                rt = new com.pullenti.ner.ReferentToken(_org, t, t, null);
                if (t.chars.isLatinLetter() && com.pullenti.ner.core.NumberHelper.tryParseRoman(t) == null) {
                    com.pullenti.ner.org.internal.OrgItemNameToken nam = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(t, null, false, true);
                    if (nam != null) {
                        StringBuilder _name = new StringBuilder();
                        _name.append(nam.value);
                        rt.setEndToken(nam.getEndToken());
                        for(com.pullenti.ner.Token ttt = nam.getEndToken().getNext(); ttt != null; ttt = ttt.getNext()) {
                            if (!ttt.chars.isLatinLetter()) 
                                break;
                            nam = com.pullenti.ner.org.internal.OrgItemNameToken.tryAttach(ttt, null, false, false);
                            if (nam == null) 
                                break;
                            rt.setEndToken(nam.getEndToken());
                            if (!nam.isStdTail) 
                                _name.append(" ").append(nam.value);
                            else {
                                com.pullenti.ner.org.internal.OrgItemEngItem ei = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttach(nam.getBeginToken(), false);
                                if (ei != null) {
                                    _org.addTypeStr(ei.fullValue);
                                    if (ei.shortValue != null) 
                                        _org.addTypeStr(ei.shortValue);
                                }
                            }
                        }
                        _org.addName(_name.toString(), true, null);
                    }
                }
                else {
                    com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                    if (br != null) {
                        com.pullenti.ner.ReferentToken rt11 = tryAttachOrg(t.getNext(), ad, AttachType.NORMAL, null, false, 0, -1);
                        if (rt11 != null && ((rt11.getEndToken() == br.getEndToken().getPrevious() || rt11.getEndToken() == br.getEndToken()))) {
                            rt11.setBeginToken(t);
                            rt11.setEndToken(br.getEndToken());
                            rt = rt11;
                            _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt11.referent, OrganizationReferent.class);
                        }
                        else {
                            _org.addName(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE), true, null);
                            _org.addName(com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO), true, br.getBeginToken().getNext());
                            if (br.getBeginToken().getNext() == br.getEndToken().getPrevious() && br.getBeginToken().getNext().getMorphClassInDictionary().isUndefined()) {
                                for(com.pullenti.morph.MorphBaseInfo wf : br.getBeginToken().getNext().getMorph().getItems()) {
                                    if (wf.getCase().isGenitive() && (wf instanceof com.pullenti.morph.MorphWordForm)) 
                                        _org.addName((((com.pullenti.morph.MorphWordForm)com.pullenti.n2j.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase, true, null);
                                }
                            }
                            rt.setEndToken(br.getEndToken());
                        }
                    }
                }
                if (_org.getSlots().size() == 0) 
                    rt = null;
            }
        }
        if (rt == null) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null || br.getLengthChar() > 100) 
                    br = null;
                if (br != null) {
                    com.pullenti.ner.Token t1 = br.getEndToken().getNext();
                    if (t1 != null && t1.isComma()) 
                        t1 = t1.getNext();
                    if (t1 != null && (t1.getWhitespacesBeforeCount() < 3)) {
                        if ((((typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t1, false, null)))) != null && typ.root != null && typ.root.getTyp() == com.pullenti.ner.org.internal.OrgItemTermin.Types.PREFIX) {
                            com.pullenti.ner.Token t2 = typ.getEndToken().getNext();
                            boolean ok = false;
                            if (t2 == null || t2.isNewlineBefore()) 
                                ok = true;
                            else if (t2.isCharOf(".,:;")) 
                                ok = true;
                            else if (t2 instanceof com.pullenti.ner.ReferentToken) 
                                ok = true;
                            if (ok) {
                                OrganizationReferent _org = new OrganizationReferent();
                                rt = new com.pullenti.ner.ReferentToken(_org, t, typ.getEndToken(), null);
                                _org.addType(typ, false);
                                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), br.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                                _org.addName(nam, true, null);
                                com.pullenti.ner.ReferentToken rt11 = tryAttachOrg(br.getBeginToken().getNext(), ad, AttachType.NORMAL, null, false, 0, -1);
                                if (rt11 != null && rt11.endChar <= typ.endChar) 
                                    _org.mergeSlots(rt11.referent, true);
                            }
                        }
                    }
                }
            }
            if (rt == null) 
                return null;
        }
        _doPostAnalyze(rt, ad);
        if (step > 0) {
            com.pullenti.ner.MetaToken mt = _checkAliasAfter(rt, rt.getEndToken().getNext());
            if (mt != null) {
                if (ad != null) {
                    com.pullenti.ner.core.Termin term = new com.pullenti.ner.core.Termin(null, new com.pullenti.morph.MorphLang(null), false);
                    term.initBy(mt.getBeginToken(), mt.getEndToken().getPrevious(), rt.referent, false);
                    ad.aliases.add(term);
                }
                rt.setEndToken(mt.getEndToken());
            }
        }
        java.util.ArrayList<com.pullenti.ner.ReferentToken> li = new java.util.ArrayList<>();
        li.add(rt);
        com.pullenti.ner.Token tt1 = rt.getEndToken().getNext();
        if (tt1 != null && tt1.isChar('(')) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt1, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) 
                tt1 = br.getEndToken().getNext();
        }
        if (tt1 != null && tt1.isCommaAnd()) {
            if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt1.getNext(), true, false)) {
                if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt.getEndToken(), true, null, false)) {
                    boolean ok = false;
                    for(com.pullenti.ner.Token ttt = tt1; ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isChar('.')) {
                            ok = true;
                            break;
                        }
                        if (ttt.isChar('(')) {
                            com.pullenti.ner.core.BracketSequenceToken br1 = com.pullenti.ner.core.BracketHelper.tryParse(ttt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                            if (br1 != null) {
                                ttt = br1.getEndToken();
                                continue;
                            }
                        }
                        if (!ttt.isCommaAnd()) 
                            break;
                        if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(ttt.getNext(), true, false)) 
                            break;
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(ttt.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br == null) 
                            break;
                        boolean addTyp = false;
                        com.pullenti.ner.ReferentToken rt1 = _TryAttachOrg_(ttt.getNext().getNext(), ttt.getNext().getNext(), ad, null, true, AttachType.NORMAL, null, false, 0);
                        if (rt1 == null || (rt1.endChar < (br.endChar - 1))) {
                            addTyp = true;
                            rt1 = _TryAttachOrg_(ttt.getNext(), ttt.getNext(), ad, null, true, AttachType.HIGH, null, false, 0);
                        }
                        if (rt1 == null || (rt1.endChar < (br.endChar - 1))) 
                            break;
                        li.add(rt1);
                        OrganizationReferent org1 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt1.referent, OrganizationReferent.class);
                        if (typ != null) 
                            ok = true;
                        if (org1.getTypes().size() == 0) 
                            addTyp = true;
                        if (addTyp) {
                            if (typ != null) 
                                org1.addType(typ, false);
                            String s = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                            if (s != null) {
                                boolean ex = false;
                                for(String n : org1.getNames()) {
                                    if (s.startsWith(n)) {
                                        ex = true;
                                        break;
                                    }
                                }
                                if (!ex) 
                                    org1.addName(s, true, br.getBeginToken().getNext());
                            }
                        }
                        if (ttt.isAnd()) {
                            ok = true;
                            break;
                        }
                        ttt = rt1.getEndToken();
                    }
                    if (!ok && li.size() > 1) 
                        for(int indRemoveRange = 1 + li.size() - 1 - 1, indMinIndex = 1; indRemoveRange >= indMinIndex; indRemoveRange--) li.remove(indRemoveRange);
                }
            }
        }
        return li;
    }

    private com.pullenti.ner.ReferentToken tryAttachSpec(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        com.pullenti.ner.ReferentToken rt = tryAttachPropNames(t, ad);
        if (rt == null) 
            rt = tryAttachPoliticParty(t, ad, false);
        if (rt == null) 
            rt = tryAttachArmy(t, ad);
        return rt;
    }

    private static boolean _corrBrackets(com.pullenti.ner.ReferentToken rt) {
        if (!com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(rt.getBeginToken().getPrevious(), true, false) || !com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(rt.getEndToken().getNext(), true, null, false)) 
            return false;
        rt.setBeginToken(rt.getBeginToken().getPrevious());
        rt.setEndToken(rt.getEndToken().getNext());
        return true;
    }

    private void _doPostAnalyze(com.pullenti.ner.ReferentToken rt, OrgAnalyzerData ad) {
        if (rt.getMorph().getCase().isUndefined()) {
            if (!rt.getBeginToken().chars.isAllUpper()) {
                com.pullenti.ner.core.NounPhraseToken npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(rt.getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt1 == null) 
                    npt1 = com.pullenti.ner.core.NounPhraseHelper.tryParse(rt.getBeginToken().getNext(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt1 != null) 
                    rt.setMorph(npt1.getMorph());
            }
        }
        OrganizationReferent o = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
        if ((rt.kit.ontology != null && o.ontologyItems == null && o.getHigher() == null) && o.m_TempParentOrg == null) {
            java.util.ArrayList<com.pullenti.ner.ExtOntologyItem> ot = rt.kit.ontology.attachReferent(o);
            if (ot != null && ot.size() == 1 && (ot.get(0).referent instanceof OrganizationReferent)) {
                OrganizationReferent oo = (OrganizationReferent)com.pullenti.n2j.Utils.cast(ot.get(0).referent, OrganizationReferent.class);
                o.mergeSlots(oo, false);
                o.ontologyItems = ot;
                for(com.pullenti.ner.Slot sl : o.getSlots()) {
                    if (sl.getValue() instanceof com.pullenti.ner.Referent) {
                        boolean ext = false;
                        for(com.pullenti.ner.Slot ss : oo.getSlots()) {
                            if (ss.getValue() == sl.getValue()) {
                                ext = true;
                                break;
                            }
                        }
                        if (!ext) 
                            continue;
                        com.pullenti.ner.Referent rr = (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class))).clone();
                        rr.getOccurrence().clear();
                        o.uploadSlot(sl, rr);
                        com.pullenti.ner.ReferentToken rtEx = new com.pullenti.ner.ReferentToken(rr, rt.getBeginToken(), rt.getEndToken(), null);
                        rtEx.setDefaultLocalOnto(rt.kit.processor);
                        o.addExtReferent(rtEx);
                        for(com.pullenti.ner.Slot sss : rr.getSlots()) {
                            if (sss.getValue() instanceof com.pullenti.ner.Referent) {
                                com.pullenti.ner.Referent rrr = (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(sss.getValue(), com.pullenti.ner.Referent.class))).clone();
                                rrr.getOccurrence().clear();
                                rr.uploadSlot(sss, rrr);
                                com.pullenti.ner.ReferentToken rtEx2 = new com.pullenti.ner.ReferentToken(rrr, rt.getBeginToken(), rt.getEndToken(), null);
                                rtEx2.setDefaultLocalOnto(rt.kit.processor);
                                (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(sl.getValue(), com.pullenti.ner.Referent.class))).addExtReferent(rtEx2);
                            }
                        }
                    }
                }
            }
        }
        if (o.getHigher() == null && o.m_TempParentOrg == null) {
            if ((rt.getBeginToken().getPrevious() instanceof com.pullenti.ner.ReferentToken) && (rt.getBeginToken().getPrevious().getReferent() instanceof OrganizationReferent)) {
                OrganizationReferent oo = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.getBeginToken().getPrevious().getReferent(), OrganizationReferent.class);
                if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(oo, o, false)) 
                    o.m_TempParentOrg = oo;
            }
            if (o.m_TempParentOrg == null && (rt.getEndToken().getNext() instanceof com.pullenti.ner.ReferentToken) && (rt.getEndToken().getNext().getReferent() instanceof OrganizationReferent)) {
                OrganizationReferent oo = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.getEndToken().getNext().getReferent(), OrganizationReferent.class);
                if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(oo, o, false)) 
                    o.m_TempParentOrg = oo;
            }
            if (o.m_TempParentOrg == null) {
                com.pullenti.ner.ReferentToken rt1 = tryAttachOrg(rt.getEndToken().getNext(), null, AttachType.NORMALAFTERDEP, null, false, 0, -1);
                if (rt1 != null && rt.getEndToken().getNext() == rt1.getBeginToken()) {
                    if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt1.referent, OrganizationReferent.class), o, false)) 
                        o.m_TempParentOrg = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt1.referent, OrganizationReferent.class);
                }
            }
        }
        if (rt.getEndToken().getNext() == null) 
            return;
        _corrBrackets(rt);
        if (rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().getMorph()._getClass().isAdjective() && (rt.getWhitespacesBeforeCount() < 2)) {
            if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))).getGeoObjects().size() == 0) {
                Object _geo = isGeo(rt.getBeginToken().getPrevious(), true);
                if (_geo != null) {
                    if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))).addGeoObject(_geo)) 
                        rt.setBeginToken(rt.getBeginToken().getPrevious());
                }
            }
        }
        com.pullenti.ner.Token ttt = rt.getEndToken().getNext();
        int errs = 1;
        boolean br = false;
        if (ttt != null && ttt.isChar('(')) {
            br = true;
            ttt = ttt.getNext();
        }
        java.util.ArrayList<com.pullenti.ner.Referent> refs = new java.util.ArrayList<>();
        boolean _keyword = false;
        boolean hasInn = false;
        int hasOk = 0;
        com.pullenti.ner.Token te = null;
        for(; ttt != null; ttt = ttt.getNext()) {
            if (ttt.isCharOf(",;") || ttt.getMorph()._getClass().isPreposition()) 
                continue;
            if (ttt.isChar(')')) {
                if (br) 
                    te = ttt;
                break;
            }
            com.pullenti.ner.Referent rr = ttt.getReferent();
            if (rr != null) {
                if (com.pullenti.n2j.Utils.stringsEq(rr.getTypeName(), "ADDRESS") || com.pullenti.n2j.Utils.stringsEq(rr.getTypeName(), "DATE") || ((com.pullenti.n2j.Utils.stringsEq(rr.getTypeName(), "GEO") && br))) {
                    if (_keyword || br || (ttt.getWhitespacesBeforeCount() < 2)) {
                        refs.add(rr);
                        te = ttt;
                        continue;
                    }
                    break;
                }
                if (com.pullenti.n2j.Utils.stringsEq(rr.getTypeName(), "URI")) {
                    String sch = rr.getStringValue("SCHEME");
                    if (sch == null) 
                        break;
                    if (com.pullenti.n2j.Utils.stringsEq(sch, "ИНН")) {
                        errs = 5;
                        hasInn = true;
                    }
                    else if (sch.startsWith("ОК")) 
                        hasOk++;
                    else if (com.pullenti.n2j.Utils.stringsNe(sch, "КПП") && com.pullenti.n2j.Utils.stringsNe(sch, "ОГРН") && !br) 
                        break;
                    refs.add(rr);
                    te = ttt;
                    if (ttt.getNext() != null && ttt.getNext().isChar('(')) {
                        com.pullenti.ner.core.BracketSequenceToken brrr = com.pullenti.ner.core.BracketHelper.tryParse(ttt.getNext(), com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (brrr != null) 
                            ttt = brrr.getEndToken();
                    }
                    continue;
                }
                else if (rr == rt.referent) 
                    continue;
            }
            if (ttt.isNewlineBefore() && !br) 
                break;
            if (ttt instanceof com.pullenti.ner.TextToken) {
                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(ttt, com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                if (npt != null) {
                    if ((npt.getEndToken().isValue("ДАТА", null) || npt.getEndToken().isValue("РЕГИСТРАЦИЯ", null) || npt.getEndToken().isValue("ЛИЦО", null)) || npt.getEndToken().isValue("ЮР", null) || npt.getEndToken().isValue("АДРЕС", null)) {
                        ttt = npt.getEndToken();
                        _keyword = true;
                        continue;
                    }
                }
                if (ttt.isValue("REGISTRATION", null) && ttt.getNext() != null && ttt.getNext().isValue("NUMBER", null)) {
                    StringBuilder tmp = new StringBuilder();
                    for(com.pullenti.ner.Token tt3 = ttt.getNext().getNext(); tt3 != null; tt3 = tt3.getNext()) {
                        if (tt3.isWhitespaceBefore() && tmp.length() > 0) 
                            break;
                        if (((tt3.isCharOf(":") || tt3.isHiphen())) && tmp.length() == 0) 
                            continue;
                        if (tt3 instanceof com.pullenti.ner.TextToken) 
                            tmp.append((((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(tt3, com.pullenti.ner.TextToken.class))).term);
                        else if (tt3 instanceof com.pullenti.ner.NumberToken) 
                            tmp.append(tt3.getSourceText());
                        else 
                            break;
                        rt.setEndToken((ttt = tt3));
                    }
                    if (tmp.length() > 0) 
                        rt.referent.addSlot(OrganizationReferent.ATTR_MISC, tmp.toString(), false, 0);
                    continue;
                }
                if ((ttt.isValue("REGISTERED", null) && ttt.getNext() != null && ttt.getNext().isValue("IN", null)) && (ttt.getNext().getNext() instanceof com.pullenti.ner.ReferentToken) && (ttt.getNext().getNext().getReferent() instanceof com.pullenti.ner.geo.GeoReferent)) {
                    rt.referent.addSlot(OrganizationReferent.ATTR_MISC, ttt.getNext().getNext().getReferent(), false, 0);
                    rt.setEndToken((ttt = ttt.getNext().getNext()));
                    continue;
                }
                if (br) {
                    com.pullenti.ner.org.internal.OrgItemTypeToken otyp = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(ttt, true, null);
                    if (otyp != null && (ttt.getWhitespacesBeforeCount() < 2) && otyp.geo == null) {
                        OrganizationReferent or1 = new OrganizationReferent();
                        or1.addType(otyp, false);
                        if (!com.pullenti.ner.org.internal.OrgItemTypeToken.isTypesAntagonisticOO(o, or1) && otyp.getEndToken().getNext() != null && otyp.getEndToken().getNext().isChar(')')) {
                            o.addType(otyp, false);
                            rt.setEndToken((ttt = otyp.getEndToken()));
                            if (br && ttt.getNext() != null && ttt.getNext().isChar(')')) {
                                rt.setEndToken(ttt.getNext());
                                break;
                            }
                            continue;
                        }
                    }
                }
            }
            _keyword = false;
            if ((--errs) <= 0) 
                break;
        }
        if (te != null && refs.size() > 0 && ((te.isChar(')') || hasInn || hasOk > 0))) {
            for(com.pullenti.ner.Referent rr : refs) {
                if (com.pullenti.n2j.Utils.stringsEq(rr.getTypeName(), gEONAME)) 
                    (((OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))).addGeoObject(rr);
                else 
                    rt.referent.addSlot(OrganizationReferent.ATTR_MISC, rr, false, 0);
            }
            rt.setEndToken(te);
        }
        if ((rt.getWhitespacesBeforeCount() < 2) && (rt.getBeginToken().getPrevious() instanceof com.pullenti.ner.TextToken) && rt.getBeginToken().getPrevious().chars.isAllUpper()) {
            String term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(rt.getBeginToken().getPrevious(), com.pullenti.ner.TextToken.class))).term;
            for(com.pullenti.ner.Slot s : o.getSlots()) {
                if (s.getValue() instanceof String) {
                    String a = com.pullenti.ner.core.MiscHelper.getAbbreviation((String)com.pullenti.n2j.Utils.cast(s.getValue(), String.class));
                    if (a != null && com.pullenti.n2j.Utils.stringsEq(a, term)) {
                        rt.setBeginToken(rt.getBeginToken().getPrevious());
                        break;
                    }
                }
            }
        }
    }

    private com.pullenti.ner.ReferentToken _tryAttachOrgByAlias(com.pullenti.ner.Token t, OrgAnalyzerData ad) {
        if (t == null) 
            return null;
        com.pullenti.ner.Token t0 = t;
        boolean br = false;
        if (t0.getNext() != null && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t0, true, false)) {
            t = t0.getNext();
            br = true;
        }
        if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && !t.chars.isAllLower()) {
            if (t.getLengthChar() > 3) {
            }
            else if (t.getLengthChar() > 1 && t.chars.isAllUpper()) {
            }
            else 
                return null;
        }
        else 
            return null;
        if (ad != null) {
            com.pullenti.ner.core.TerminToken tok = ad.aliases.tryParse(t, com.pullenti.ner.core.TerminParseAttr.NO);
            if (tok != null) {
                com.pullenti.ner.ReferentToken rt0 = new com.pullenti.ner.ReferentToken((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(tok.termin.tag, com.pullenti.ner.Referent.class), t0, tok.getEndToken(), null);
                if (br) {
                    if (com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(tok.getEndToken().getNext(), true, null, false)) 
                        rt0.setEndToken(tok.getEndToken().getNext());
                    else 
                        return null;
                }
                return rt0;
            }
        }
        if (!br) {
            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(t)) 
                return null;
            if (!com.pullenti.ner.org.internal.OrgItemTypeToken.checkOrgSpecialWordBefore(t0.getPrevious())) 
                return null;
            if (t.chars.isLatinLetter()) {
                if (t.getNext() != null && t.getNext().chars.isLatinLetter()) 
                    return null;
            }
            else if (t.getNext() != null && ((t.getNext().chars.isCyrillicLetter() || !t.getNext().chars.isAllLower()))) 
                return null;
        }
        else if (!com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t.getNext(), true, null, false)) 
            return null;
        int cou = 0;
        for(com.pullenti.ner.Token ttt = t.getPrevious(); ttt != null && (cou < 100); ttt = ttt.getPrevious(),cou++) {
            OrganizationReferent org00 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(ttt.getReferent(), OrganizationReferent.class);
            if (org00 == null) 
                continue;
            for(String n : org00.getNames()) {
                String str = n;
                int ii = n.indexOf(' ');
                if (ii > 0) 
                    str = n.substring(0, 0+(ii));
                if (t.isValue(str, null)) {
                    if (ad != null) 
                        ad.aliases.add(com.pullenti.ner.core.Termin._new118(str, org00));
                    String term = (((com.pullenti.ner.TextToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    if (ii < 0) 
                        org00.addName(term, true, t);
                    if (br) 
                        t = t.getNext();
                    com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(org00, t0, t, null);
                    return rt;
                }
            }
        }
        return null;
    }

    private com.pullenti.ner.Token attachMiddleAttributes(OrganizationReferent _org, com.pullenti.ner.Token t) {
        com.pullenti.ner.Token te = null;
        for(; t != null; t = t.getNext()) {
            com.pullenti.ner.org.internal.OrgItemNumberToken ont = com.pullenti.ner.org.internal.OrgItemNumberToken.tryAttach(t, false, null);
            if (ont != null) {
                _org.setNumber(ont.number);
                te = (t = ont.getEndToken());
                continue;
            }
            com.pullenti.ner.org.internal.OrgItemEponymToken oet = com.pullenti.ner.org.internal.OrgItemEponymToken.tryAttach(t, false);
            if (oet != null) {
                for(String v : oet.eponyms) {
                    _org.addEponym(v);
                }
                te = (t = oet.getEndToken());
                continue;
            }
            break;
        }
        return te;
    }

    private static final String gEONAME = "GEO";

    private Object isGeo(com.pullenti.ner.Token t, boolean canBeAdjective) {
        if (t == null) 
            return null;
        if (t.isValue("В", null) && t.getNext() != null) 
            t = t.getNext();
        com.pullenti.ner.Referent r = t.getReferent();
        if (r != null) {
            if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                if (t.getWhitespacesBeforeCount() <= 15 || t.getMorph().getCase().isGenitive()) 
                    return r;
            }
            if (r instanceof com.pullenti.ner.address.AddressReferent) {
            }
            return null;
        }
        if (t.getWhitespacesBeforeCount() > 15 && !canBeAdjective) 
            return null;
        com.pullenti.ner.ReferentToken rt = t.kit.processReferent("GEO", t);
        if (rt == null) 
            return null;
        if (t.getPrevious() != null && t.getPrevious().isValue("ОРДЕН", null)) 
            return null;
        if (!canBeAdjective) {
            if (rt.getMorph()._getClass().isAdjective()) 
                return null;
        }
        return rt;
    }

    private com.pullenti.ner.Token getGeoEndToken(Object _geo, com.pullenti.ner.Token t) {
        if (_geo instanceof com.pullenti.ner.ReferentToken) {
            if ((((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(_geo, com.pullenti.ner.ReferentToken.class))).getReferent() instanceof com.pullenti.ner.address.AddressReferent) 
                return t.getPrevious();
            return (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(_geo, com.pullenti.ner.ReferentToken.class))).getEndToken();
        }
        else if (t != null && t.getNext() != null && t.getMorph()._getClass().isPreposition()) 
            return t.getNext();
        else 
            return t;
    }

    private com.pullenti.ner.Token attachTailAttributes(OrganizationReferent _org, com.pullenti.ner.Token t, OrgAnalyzerData ad, boolean attachForNewOrg, AttachType attachTyp, boolean isGlobal) {
        com.pullenti.ner.Token t1 = null;
        OrganizationKind ki = _org.getKind();
        boolean canHasGeo = true;
        if (!canHasGeo) {
            if (_org._typesContains("комитет") || _org._typesContains("академия") || _org._typesContains("инспекция")) 
                canHasGeo = true;
        }
        for(; t != null; t = ((t == null ? null : t.getNext()))) {
            if (((t.isValue("ПО", null) || t.isValue("В", null) || t.isValue("IN", null))) && t.getNext() != null) {
                if (attachTyp == AttachType.NORMALAFTERDEP) 
                    break;
                if (!canHasGeo) 
                    break;
                Object r = isGeo(t.getNext(), false);
                if (r == null) 
                    break;
                if (!_org.addGeoObject(r)) 
                    break;
                t1 = getGeoEndToken(r, t.getNext());
                t = t1;
                continue;
            }
            if (t.isValue("ИЗ", null) && t.getNext() != null) {
                if (attachTyp == AttachType.NORMALAFTERDEP) 
                    break;
                if (!canHasGeo) 
                    break;
                Object r = isGeo(t.getNext(), false);
                if (r == null) 
                    break;
                if (!_org.addGeoObject(r)) 
                    break;
                t1 = getGeoEndToken(r, t.getNext());
                t = t1;
                continue;
            }
            if (canHasGeo && _org.findSlot(OrganizationReferent.ATTR_GEO, null, true) == null && !t.isNewlineBefore()) {
                Object r = isGeo(t, false);
                if (r != null) {
                    if (!_org.addGeoObject(r)) 
                        break;
                    t = (t1 = getGeoEndToken(r, t));
                    continue;
                }
                if (t.isChar('(')) {
                    r = isGeo(t.getNext(), false);
                    if ((r instanceof com.pullenti.ner.ReferentToken) && (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getEndToken().getNext() != null && (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getEndToken().getNext().isChar(')')) {
                        if (!_org.addGeoObject(r)) 
                            break;
                        t = (t1 = (((com.pullenti.ner.ReferentToken)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.ReferentToken.class))).getEndToken().getNext());
                        continue;
                    }
                    if ((r instanceof com.pullenti.ner.geo.GeoReferent) && t.getNext().getNext() != null && t.getNext().getNext().isChar(')')) {
                        if (!_org.addGeoObject(r)) 
                            break;
                        t = (t1 = t.getNext().getNext());
                        continue;
                    }
                }
            }
            if ((t.getReferent() instanceof com.pullenti.ner.geo.GeoReferent) && (t.getWhitespacesBeforeCount() < 2)) {
                if (_org.findSlot(OrganizationReferent.ATTR_GEO, t.getReferent(), true) != null) {
                    t1 = t;
                    continue;
                }
            }
            if (((t.isValue("ПРИ", null) || t.isValue("В", null))) && t.getNext() != null && (t.getNext() instanceof com.pullenti.ner.ReferentToken)) {
                com.pullenti.ner.Referent r = t.getNext().getReferent();
                if (r instanceof OrganizationReferent) {
                    if (t.isValue("В", null) && !com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(r, OrganizationReferent.class), _org, false)) {
                    }
                    else {
                        _org.setHigher((OrganizationReferent)com.pullenti.n2j.Utils.cast(r, OrganizationReferent.class));
                        t1 = t.getNext();
                        t = t1;
                        continue;
                    }
                }
            }
            if (t.chars.isLatinLetter() && (t.getWhitespacesBeforeCount() < 2)) {
                boolean hasLatinName = false;
                for(String s : _org.getNames()) {
                    if (com.pullenti.morph.LanguageHelper.isLatinChar(s.charAt(0))) {
                        hasLatinName = true;
                        break;
                    }
                }
                if (hasLatinName) {
                    com.pullenti.ner.org.internal.OrgItemEngItem eng = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttach(t, false);
                    if (eng != null) {
                        _org.addTypeStr(eng.fullValue);
                        if (eng.shortValue != null) 
                            _org.addTypeStr(eng.shortValue);
                        t = (t1 = eng.getEndToken());
                        continue;
                    }
                }
            }
            Object re = isGeo(t, false);
            if (re == null && t.isChar(',')) 
                re = isGeo(t.getNext(), false);
            if (re != null) {
                if (attachTyp != AttachType.NORMALAFTERDEP) {
                    if ((!canHasGeo && ki != OrganizationKind.BANK && ki != OrganizationKind.FEDERATION) && !_org.getTypes().contains("университет")) 
                        break;
                    if (!_org.addGeoObject(re)) 
                        break;
                    if (t.isChar(',')) 
                        t = t.getNext();
                    t1 = getGeoEndToken(re, t);
                    if (t1.endChar <= t.endChar) 
                        break;
                    t = t1;
                    continue;
                }
                else 
                    break;
            }
            if (t.isChar('(')) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null) 
                    break;
                if (t.getNext() != null && t.getNext().getReferent() != null) {
                    if (t.getNext().getNext() != br.getEndToken()) 
                        break;
                    com.pullenti.ner.Referent r = t.getNext().getReferent();
                    if (com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), gEONAME)) {
                        if (!_org.addGeoObject(r)) 
                            break;
                        t = (t1 = br.getEndToken());
                        continue;
                    }
                    if ((r instanceof OrganizationReferent) && !isGlobal) {
                        if (!attachForNewOrg && !_org.canBeEquals(r, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                            break;
                        _org.mergeSlots(r, true);
                        t = (t1 = br.getEndToken());
                        continue;
                    }
                    break;
                }
                if (!isGlobal) {
                    if (attachTyp != AttachType.EXTONTOLOGY) {
                        com.pullenti.ner.org.internal.OrgItemTypeToken typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true, null);
                        if (typ != null && typ.getEndToken() == br.getEndToken().getPrevious() && !typ.isDep()) {
                            _org.addType(typ, false);
                            if (typ.name != null) 
                                _org.addTypeStr(typ.name.toLowerCase());
                            t = (t1 = br.getEndToken());
                            continue;
                        }
                    }
                    com.pullenti.ner.ReferentToken rte = com.pullenti.ner.org.internal.OrgItemEngItem.tryAttachOrg(br.getBeginToken(), false);
                    if (rte != null) {
                        if (_org.canBeEquals(rte.referent, com.pullenti.ner.Referent.EqualType.FORMERGING)) {
                            _org.mergeSlots(rte.referent, true);
                            t = (t1 = rte.getEndToken());
                            continue;
                        }
                    }
                    String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                    if (nam != null) {
                        boolean eq = false;
                        for(com.pullenti.ner.Slot s : _org.getSlots()) {
                            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), OrganizationReferent.ATTR_NAME)) {
                                if (com.pullenti.ner.core.MiscHelper.canBeEqualCyrAndLatSS(nam, s.getValue().toString())) {
                                    _org.addName(nam, true, br.getBeginToken().getNext());
                                    eq = true;
                                    break;
                                }
                            }
                        }
                        if (eq) {
                            t = (t1 = br.getEndToken());
                            continue;
                        }
                    }
                    boolean oldName = false;
                    com.pullenti.ner.Token tt0 = t.getNext();
                    if (tt0 != null) {
                        if (tt0.isValue("РАНЕЕ", null)) {
                            oldName = true;
                            tt0 = tt0.getNext();
                        }
                        else if (tt0.getMorph()._getClass().isAdjective() && tt0.getNext() != null && ((tt0.getNext().isValue("НАЗВАНИЕ", null) || tt0.getNext().isValue("НАИМЕНОВАНИЕ", null)))) {
                            oldName = true;
                            tt0 = tt0.getNext().getNext();
                        }
                        if (oldName && tt0 != null) {
                            if (tt0.isHiphen() || tt0.isCharOf(",:")) 
                                tt0 = tt0.getNext();
                        }
                    }
                    com.pullenti.ner.ReferentToken rt = tryAttachOrg(tt0, ad, AttachType.HIGH, null, false, 0, -1);
                    if (rt == null) 
                        break;
                    if (!_org.canBeEquals(rt.referent, com.pullenti.ner.Referent.EqualType.FORMERGING)) 
                        break;
                    if (rt.getEndToken() != br.getEndToken().getPrevious()) 
                        break;
                    if (!attachForNewOrg && !_org.canBeEquals(rt.referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                        break;
                    if (attachTyp == AttachType.NORMAL) {
                        if (!oldName && !OrganizationReferent.canBeSecondDefinition(_org, (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class))) 
                            break;
                        com.pullenti.ner.org.internal.OrgItemTypeToken typ = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t.getNext(), true, null);
                        if (typ != null && typ.isDouterOrg) 
                            break;
                    }
                    _org.mergeSlots(rt.referent, true);
                    t = (t1 = br.getEndToken());
                    continue;
                }
                break;
            }
            else if (attachTyp == AttachType.EXTONTOLOGY && com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, true, false)) {
                com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                if (br == null) 
                    break;
                String nam = com.pullenti.ner.core.MiscHelper.getTextValueOfMetaToken(br, com.pullenti.ner.core.GetTextAttr.NO);
                if (nam != null) 
                    _org.addName(nam, true, br.getBeginToken().getNext());
                com.pullenti.ner.ReferentToken rt1 = tryAttachOrg(t.getNext(), ad, AttachType.HIGH, null, true, 0, -1);
                if (rt1 != null && rt1.getEndToken().getNext() == br.getEndToken()) {
                    _org.mergeSlots(rt1.referent, true);
                    t = (t1 = br.getEndToken());
                }
            }
            else 
                break;
        }
        if (t != null && (t.getWhitespacesBeforeCount() < 2) && ((ki == OrganizationKind.UNDEFINED || ki == OrganizationKind.BANK))) {
            com.pullenti.ner.org.internal.OrgItemTypeToken ty1 = com.pullenti.ner.org.internal.OrgItemTypeToken.tryAttach(t, false, null);
            if (ty1 != null && ty1.root != null && ty1.root.isPurePrefix) {
                if (t.kit.recurseLevel > 2) 
                    return null;
                t.kit.recurseLevel++;
                com.pullenti.ner.ReferentToken rt22 = tryAttachOrg(t, ad, AttachType.NORMAL, null, false, 0, -1);
                t.kit.recurseLevel--;
                if (rt22 == null) {
                    _org.addType(ty1, false);
                    t1 = ty1.getEndToken();
                }
            }
        }
        return t1;
    }

    private void correctOwnerBefore(com.pullenti.ner.ReferentToken res) {
        if (res == null) 
            return;
        if ((((OrganizationReferent)com.pullenti.n2j.Utils.cast(res.referent, OrganizationReferent.class))).getKind() == OrganizationKind.PRESS) {
            if (res.getBeginToken().isValue("КОРРЕСПОНДЕНТ", null) && res.getBeginToken() != res.getEndToken()) 
                res.setBeginToken(res.getBeginToken().getNext());
        }
        OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(res.referent, OrganizationReferent.class);
        if (_org.getHigher() != null) 
            return;
        OrganizationReferent hiBefore = null;
        int couBefore = 0;
        com.pullenti.ner.Token t0 = null;
        for(com.pullenti.ner.Token t = res.getBeginToken().getPrevious(); t != null; t = t.getPrevious()) {
            couBefore += t.getWhitespacesAfterCount();
            if (t.isChar(',')) {
                couBefore += 5;
                continue;
            }
            else if (t.isValue("ПРИ", null)) 
                return;
            if (t.isReferent()) {
                if ((((hiBefore = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class)))) != null) 
                    t0 = t;
            }
            break;
        }
        if (t0 == null) 
            return;
        if (!com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(hiBefore, _org, false)) 
            return;
        if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(_org, hiBefore, false)) 
            return;
        OrganizationReferent hiAfter = null;
        int couAfter = 0;
        for(com.pullenti.ner.Token t = res.getEndToken().getNext(); t != null; t = t.getNext()) {
            couBefore += t.getWhitespacesBeforeCount();
            if (t.isChar(',') || t.isValue("ПРИ", null)) {
                couAfter += 5;
                continue;
            }
            if (t.isReferent()) {
                hiAfter = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class);
                break;
            }
            com.pullenti.ner.ReferentToken rt = tryAttachOrg(t, null, AttachType.NORMAL, null, false, 0, -1);
            if (rt != null) 
                hiAfter = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
            break;
        }
        if (hiAfter != null) {
            if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(hiAfter, _org, false)) {
                if (couBefore >= couAfter) 
                    return;
            }
        }
        if (_org.getKind() == hiBefore.getKind() && _org.getKind() != OrganizationKind.UNDEFINED) {
            if (_org.getKind() != OrganizationKind.DEPARTMENT & _org.getKind() != OrganizationKind.GOVENMENT) 
                return;
        }
        _org.setHigher(hiBefore);
        res.setBeginToken(t0);
    }

    private com.pullenti.ner.ReferentToken checkOwnership(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.ReferentToken res = null;
        OrganizationReferent _org = (OrganizationReferent)com.pullenti.n2j.Utils.cast(t.getReferent(), OrganizationReferent.class);
        if (_org == null) 
            return null;
        com.pullenti.ner.Token tt0 = t;
        for(; t != null; ) {
            com.pullenti.ner.Token tt = t.getNext();
            boolean always = false;
            boolean br = false;
            if (tt != null && tt.getMorph()._getClass().isPreposition()) {
                if (tt.isValue("ПРИ", null)) 
                    always = true;
                else if (tt.isValue("В", null)) {
                }
                else 
                    break;
                tt = tt.getNext();
            }
            if ((tt != null && tt.isChar('(') && (tt.getNext() instanceof com.pullenti.ner.ReferentToken)) && tt.getNext().getNext() != null && tt.getNext().getNext().isChar(')')) {
                br = true;
                tt = tt.getNext();
            }
            if (tt instanceof com.pullenti.ner.ReferentToken) {
                OrganizationReferent org2 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org2 != null) {
                    boolean ok = com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org2, _org, false);
                    if (always || ok) 
                        ok = true;
                    else if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org2, _org, true)) {
                        com.pullenti.ner.Token t0 = t.getPrevious();
                        if (t0 != null && t0.isChar(',')) 
                            t0 = t0.getPrevious();
                        com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t0);
                        if (rt != null && com.pullenti.n2j.Utils.stringsEq(rt.referent.getTypeName(), "PERSONPROPERTY") && rt.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                            ok = true;
                    }
                    if (ok && ((_org.getHigher() == null || _org.getHigher().canBeEquals(org2, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)))) {
                        _org.setHigher(org2);
                        if (br) 
                            tt = tt.getNext();
                        if (_org.getHigher() == org2) {
                            if (res == null) 
                                res = new com.pullenti.ner.ReferentToken(_org, t, tt, null);
                            else 
                                res.setEndToken(tt);
                            t = tt;
                            if (_org.getGeoObjects().size() == 0) {
                                com.pullenti.ner.Token ttt = t.getNext();
                                if (ttt != null && ttt.isValue("В", null)) 
                                    ttt = ttt.getNext();
                                if (isGeo(ttt, false) != null) {
                                    _org.addGeoObject(ttt);
                                    res.setEndToken(ttt);
                                    t = ttt;
                                }
                            }
                            _org = org2;
                            continue;
                        }
                    }
                    if (_org.getHigher() != null && _org.getHigher().getHigher() == null && com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org2, _org.getHigher(), false)) {
                        _org.getHigher().setHigher(org2);
                        res = new com.pullenti.ner.ReferentToken(_org, t, tt, null);
                        if (br) 
                            res.setEndToken(tt.getNext());
                        return res;
                    }
                    if ((_org.getHigher() != null && org2.getHigher() == null && com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(_org.getHigher(), org2, false)) && com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org2, _org, false)) {
                        org2.setHigher(_org.getHigher());
                        _org.setHigher(org2);
                        res = new com.pullenti.ner.ReferentToken(_org, t, tt, null);
                        if (br) 
                            res.setEndToken(tt.getNext());
                        return res;
                    }
                }
            }
            break;
        }
        if (res != null) 
            return res;
        if (_org.getKind() == OrganizationKind.DEPARTMENT && _org.getHigher() == null && _org.m_TempParentOrg == null) {
            int cou = 0;
            for(com.pullenti.ner.Token tt = tt0.getPrevious(); tt != null; tt = tt.getPrevious()) {
                if (tt.isNewlineAfter()) 
                    cou += 10;
                if ((++cou) > 100) 
                    break;
                OrganizationReferent org0 = (OrganizationReferent)com.pullenti.n2j.Utils.cast(tt.getReferent(), OrganizationReferent.class);
                if (org0 == null) 
                    continue;
                java.util.ArrayList<OrganizationReferent> tmp = new java.util.ArrayList<>();
                for(; org0 != null; org0 = org0.getHigher()) {
                    if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(org0, _org, false)) {
                        _org.setHigher(org0);
                        break;
                    }
                    if (org0.getKind() != OrganizationKind.DEPARTMENT) 
                        break;
                    if (tmp.contains(org0)) 
                        break;
                    tmp.add(org0);
                }
                break;
            }
        }
        return null;
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        if (begin == null) 
            return null;
        com.pullenti.ner.ReferentToken rt = tryAttachOrg(begin, null, AttachType.EXTONTOLOGY, null, begin.getPrevious() != null, 0, -1);
        if (rt != null) {
            OrganizationReferent r = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.referent, OrganizationReferent.class);
            if (r.getHigher() == null && rt.getEndToken().getNext() != null) {
                OrganizationReferent h = (OrganizationReferent)com.pullenti.n2j.Utils.cast(rt.getEndToken().getNext().getReferent(), OrganizationReferent.class);
                if (h != null) {
                    if (com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(h, r, true) || !com.pullenti.ner.org.internal.OrgOwnershipHelper.canBeHigher(r, h, true)) {
                        r.setHigher(h);
                        rt.setEndToken(rt.getEndToken().getNext());
                    }
                }
            }
            if (rt.getBeginToken() != begin) {
                String nam = com.pullenti.ner.core.MiscHelper.getTextValue(begin, rt.getBeginToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                if (!com.pullenti.n2j.Utils.isNullOrEmpty(nam)) {
                    OrganizationReferent org0 = new OrganizationReferent();
                    org0.addName(nam, true, begin);
                    org0.setHigher(r);
                    rt = new com.pullenti.ner.ReferentToken(org0, begin, rt.getEndToken(), null);
                }
            }
            return rt;
        }
        com.pullenti.ner.Token t = begin;
        com.pullenti.ner.Token et = begin;
        for(; t != null; t = t.getNext()) {
            if (t.isCharOf(",;")) 
                break;
            et = t;
        }
        String _name = com.pullenti.ner.core.MiscHelper.getTextValue(begin, et, com.pullenti.ner.core.GetTextAttr.NO);
        if (com.pullenti.n2j.Utils.isNullOrEmpty(_name)) 
            return null;
        OrganizationReferent _org = new OrganizationReferent();
        _org.addName(_name, true, begin);
        return new com.pullenti.ner.ReferentToken(_org, begin, et, null);
    }

    private static boolean m_Inited = false;

    public static void initialize() throws Exception {
        if (m_Inited) 
            return;
        m_Inited = true;
        try {
            _initSport();
            _initPolitic();
            com.pullenti.ner.org.internal.OrgItemTypeToken.initialize();
            com.pullenti.ner.org.internal.OrgItemEngItem.initialize();
            com.pullenti.ner.org.internal.OrgItemNameToken.initialize();
            com.pullenti.ner.org.internal.OrgGlobal.initialize();
        } catch(Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.ProcessorService.registerAnalyzer(new OrganizationAnalyzer());
    }

    public static class AttachType implements Comparable<AttachType> {
    
        public static final AttachType NORMAL; // 0
    
        public static final AttachType NORMALAFTERDEP; // 1
    
        public static final AttachType MULTIPLE; // 2
    
        public static final AttachType HIGH; // 3
    
        public static final AttachType EXTONTOLOGY; // 4
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private AttachType(int val, String str) { m_val = val; m_str = str; }
        @Override
        public String toString() {
            if(m_str != null) return m_str;
            return ((Integer)m_val).toString();
        }
        @Override
        public int hashCode() {
            return (int)m_val;
        }
        @Override
        public int compareTo(AttachType v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, AttachType> mapIntToEnum; 
        private static java.util.HashMap<String, AttachType> mapStringToEnum; 
        public static AttachType of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            AttachType item = new AttachType(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static AttachType of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        static {
            mapIntToEnum = new java.util.HashMap< >();
            mapStringToEnum = new java.util.HashMap< >();
            NORMAL = new AttachType(0, "NORMAL");
            mapIntToEnum.put(NORMAL.value(), NORMAL);
            mapStringToEnum.put(NORMAL.m_str.toUpperCase(), NORMAL);
            NORMALAFTERDEP = new AttachType(1, "NORMALAFTERDEP");
            mapIntToEnum.put(NORMALAFTERDEP.value(), NORMALAFTERDEP);
            mapStringToEnum.put(NORMALAFTERDEP.m_str.toUpperCase(), NORMALAFTERDEP);
            MULTIPLE = new AttachType(2, "MULTIPLE");
            mapIntToEnum.put(MULTIPLE.value(), MULTIPLE);
            mapStringToEnum.put(MULTIPLE.m_str.toUpperCase(), MULTIPLE);
            HIGH = new AttachType(3, "HIGH");
            mapIntToEnum.put(HIGH.value(), HIGH);
            mapStringToEnum.put(HIGH.m_str.toUpperCase(), HIGH);
            EXTONTOLOGY = new AttachType(4, "EXTONTOLOGY");
            mapIntToEnum.put(EXTONTOLOGY.value(), EXTONTOLOGY);
            mapStringToEnum.put(EXTONTOLOGY.m_str.toUpperCase(), EXTONTOLOGY);
        }
    }

    public OrganizationAnalyzer() {
        super();
    }
    public static OrganizationAnalyzer _globalInstance;
    static {
        _globalInstance = new OrganizationAnalyzer(); 
    }
}
