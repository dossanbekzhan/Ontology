/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.org.internal;

public class OrgItemTermin extends com.pullenti.ner.core.Termin {

    public OrgItemTermin(String s, com.pullenti.morph.MorphLang _lang, com.pullenti.ner.org.OrgProfile p1, com.pullenti.ner.org.OrgProfile p2) {
        super(s, _lang, false);
        if(_globalInstance == null) return;
        if (p1 != com.pullenti.ner.org.OrgProfile.UNDEFINED) 
            profiles.add(p1);
        if (p2 != com.pullenti.ner.org.OrgProfile.UNDEFINED) 
            profiles.add(p2);
    }

    public Types getTyp() {
        if (isPurePrefix) 
            return Types.PREFIX;
        return m_Typ;
    }

    public Types setTyp(Types value) {
        if (value == Types.PREFIX) {
            isPurePrefix = true;
            m_Typ = Types.ORG;
        }
        else {
            m_Typ = value;
            if (m_Typ == Types.DEP || m_Typ == Types.DEPADD) {
                if (!profiles.contains(com.pullenti.ner.org.OrgProfile.UNIT)) 
                    profiles.add(com.pullenti.ner.org.OrgProfile.UNIT);
            }
        }
        return value;
    }


    private Types m_Typ = Types.UNDEFINED;

    /**
     * Признак того, что тип обязательно входит в имя (например, Министерство)
     */
    public boolean mustBePartofName = false;

    /**
     * Чистый префикс, никогда не входит в имя (типа ООО)
     */
    public boolean isPurePrefix;

    public boolean canBeNormalDep;

    public boolean canHasNumber;

    public boolean canHasSingleName;

    public boolean canHasLatinName;

    public boolean mustHasCapitalName;

    public boolean isTop;

    public boolean canBeSingleGeo;

    /**
     * Корень - сомнительное слово (типа: организация или движение)
     */
    public boolean isDoubtWord;

    public float coeff;

    public java.util.ArrayList<com.pullenti.ner.org.OrgProfile> profiles = new java.util.ArrayList<>();

    public com.pullenti.ner.org.OrgProfile getProfile() {
        return com.pullenti.ner.org.OrgProfile.UNDEFINED;
    }

    public com.pullenti.ner.org.OrgProfile setProfile(com.pullenti.ner.org.OrgProfile value) {
        profiles.add(value);
        return value;
    }


    private void copyFrom(OrgItemTermin it) {
        profiles.addAll(it.profiles);
        isPurePrefix = it.isPurePrefix;
        canBeNormalDep = it.canBeNormalDep;
        canHasNumber = it.canHasNumber;
        canHasSingleName = it.canHasSingleName;
        canHasLatinName = it.canHasLatinName;
        mustBePartofName = it.mustBePartofName;
        mustHasCapitalName = it.mustHasCapitalName;
        isTop = it.isTop;
        canBeNormalDep = it.canBeNormalDep;
        canBeSingleGeo = it.canBeSingleGeo;
        isDoubtWord = it.isDoubtWord;
        coeff = it.coeff;
    }

    public static java.util.ArrayList<OrgItemTermin> deserializeSrc(org.w3c.dom.Node xml, OrgItemTermin set) throws Exception, NumberFormatException {
        java.util.ArrayList<OrgItemTermin> res = new java.util.ArrayList<>();
        boolean isSet = com.pullenti.n2j.Utils.stringsEq(com.pullenti.n2j.XmlDocumentWrapper.getLocalName(xml), "set");
        if (isSet) 
            res.add((set = new OrgItemTermin(null, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED)));
        if (xml.getAttributes() == null) 
            return res;
        for(org.w3c.dom.Node a : (new com.pullenti.n2j.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.n2j.XmlDocumentWrapper.getLocalName(a);
            if (!nam.startsWith("name")) 
                continue;
            com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
            if (com.pullenti.n2j.Utils.stringsEq(nam, "nameUa")) 
                _lang = com.pullenti.morph.MorphLang.UA;
            else if (com.pullenti.n2j.Utils.stringsEq(nam, "nameEn")) 
                _lang = com.pullenti.morph.MorphLang.EN;
            OrgItemTermin it = null;
            for(String s : com.pullenti.n2j.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                if (!com.pullenti.n2j.Utils.isNullOrEmpty(s)) {
                    if (it == null) {
                        res.add((it = new OrgItemTermin(s, _lang, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED)));
                        if (set != null) 
                            it.copyFrom(set);
                    }
                    else 
                        it.addVariant(s, false);
                }
            }
        }
        for(org.w3c.dom.Node a : (new com.pullenti.n2j.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.n2j.XmlDocumentWrapper.getLocalName(a);
            if (nam.startsWith("name")) 
                continue;
            if (nam.startsWith("abbr")) {
                com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
                if (com.pullenti.n2j.Utils.stringsEq(nam, "abbrUa")) 
                    _lang = com.pullenti.morph.MorphLang.UA;
                else if (com.pullenti.n2j.Utils.stringsEq(nam, "abbrEn")) 
                    _lang = com.pullenti.morph.MorphLang.EN;
                for(OrgItemTermin r : res) {
                    if (com.pullenti.morph.MorphLang.ooEq(r.lang, _lang)) 
                        r.acronym = a.getNodeValue();
                }
                continue;
            }
            if (com.pullenti.n2j.Utils.stringsEq(nam, "profile")) {
                java.util.ArrayList<com.pullenti.ner.org.OrgProfile> li = new java.util.ArrayList<>();
                for(String s : com.pullenti.n2j.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                    try {
                        com.pullenti.ner.org.OrgProfile p = com.pullenti.ner.org.OrgProfile.of(s);
                        if (p != com.pullenti.ner.org.OrgProfile.UNDEFINED) 
                            li.add(p);
                    } catch(Exception ex) {
                    }
                }
                for(OrgItemTermin r : res) {
                    r.profiles = li;
                }
                continue;
            }
            if (com.pullenti.n2j.Utils.stringsEq(nam, "coef")) {
                float v = com.pullenti.n2j.Utils.parseFloat(a.getNodeValue());
                for(OrgItemTermin r : res) {
                    r.coeff = v;
                }
                continue;
            }
            if (com.pullenti.n2j.Utils.stringsEq(nam, "partofname")) {
                for(OrgItemTermin r : res) {
                    r.mustBePartofName = com.pullenti.n2j.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.n2j.Utils.stringsEq(nam, "top")) {
                for(OrgItemTermin r : res) {
                    r.isTop = com.pullenti.n2j.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.n2j.Utils.stringsEq(nam, "geo")) {
                for(OrgItemTermin r : res) {
                    r.canBeSingleGeo = com.pullenti.n2j.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.n2j.Utils.stringsEq(nam, "purepref")) {
                for(OrgItemTermin r : res) {
                    r.isPurePrefix = com.pullenti.n2j.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.n2j.Utils.stringsEq(nam, "number")) {
                for(OrgItemTermin r : res) {
                    r.canHasNumber = com.pullenti.n2j.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            throw new Exception("Unknown Org Type Tag: " + a.getNodeName());
        }
        return res;
    }

    public static class Types implements Comparable<Types> {
    
        public static final Types UNDEFINED; // 0
    
        public static final Types ORG; // 1
    
        public static final Types PREFIX; // 2
    
        public static final Types DEP; // 3
    
        public static final Types DEPADD; // 4
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Types(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(Types v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Types> mapIntToEnum; 
        private static java.util.HashMap<String, Types> mapStringToEnum; 
        public static Types of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Types item = new Types(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Types of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        static {
            mapIntToEnum = new java.util.HashMap< >();
            mapStringToEnum = new java.util.HashMap< >();
            UNDEFINED = new Types(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            ORG = new Types(1, "ORG");
            mapIntToEnum.put(ORG.value(), ORG);
            mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
            PREFIX = new Types(2, "PREFIX");
            mapIntToEnum.put(PREFIX.value(), PREFIX);
            mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
            DEP = new Types(3, "DEP");
            mapIntToEnum.put(DEP.value(), DEP);
            mapStringToEnum.put(DEP.m_str.toUpperCase(), DEP);
            DEPADD = new Types(4, "DEPADD");
            mapIntToEnum.put(DEPADD.value(), DEPADD);
            mapStringToEnum.put(DEPADD.m_str.toUpperCase(), DEPADD);
        }
    }


    public static OrgItemTermin _new1670(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner.org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }
    public static OrgItemTermin _new1675(String _arg1, boolean _arg2, float _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg2;
        res.coeff = _arg3;
        return res;
    }
    public static OrgItemTermin _new1679(String _arg1, Types _arg2, float _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }
    public static OrgItemTermin _new1689(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner.org.OrgProfile _arg3, float _arg4, Types _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg4;
        res.setTyp(_arg5);
        res.isTop = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }
    public static OrgItemTermin _new1692(String _arg1, Types _arg2, com.pullenti.ner.org.OrgProfile _arg3, float _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        return res;
    }
    public static OrgItemTermin _new1693(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, com.pullenti.ner.org.OrgProfile _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.coeff = _arg5;
        return res;
    }
    public static OrgItemTermin _new1694(String _arg1, Types _arg2, com.pullenti.ner.org.OrgProfile _arg3, float _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }
    public static OrgItemTermin _new1697(String _arg1, float _arg2, Types _arg3, com.pullenti.ner.org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        return res;
    }
    public static OrgItemTermin _new1698(String _arg1, float _arg2, Types _arg3, com.pullenti.ner.org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }
    public static OrgItemTermin _new1699(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new1700(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        return res;
    }
    public static OrgItemTermin _new1701(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }
    public static OrgItemTermin _new1703(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }
    public static OrgItemTermin _new1705(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }
    public static OrgItemTermin _new1706(String _arg1, float _arg2, Types _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        return res;
    }
    public static OrgItemTermin _new1708(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }
    public static OrgItemTermin _new1711(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new1713(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canBeNormalDep = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new1715(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new1716(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new1717(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new1719(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner.org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        res.setProfile(_arg7);
        return res;
    }
    public static OrgItemTermin _new1726(String _arg1, float _arg2, String _arg3, Types _arg4, boolean _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new1727(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }
    public static OrgItemTermin _new1728(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new1729(String _arg1, float _arg2, com.pullenti.morph.MorphLang _arg3, Types _arg4, boolean _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.lang = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new1737(String _arg1, float _arg2, Types _arg3, com.pullenti.ner.org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }
    public static OrgItemTermin _new1738(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }
    public static OrgItemTermin _new1739(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        return res;
    }
    public static OrgItemTermin _new1742(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        return res;
    }
    public static OrgItemTermin _new1747(String _arg1, Types _arg2, String _arg3, com.pullenti.ner.org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }
    public static OrgItemTermin _new1750(String _arg1, float _arg2, Types _arg3, com.pullenti.ner.org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }
    public static OrgItemTermin _new1751(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, com.pullenti.ner.org.OrgProfile _arg4, Types _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setProfile(_arg4);
        res.setTyp(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }
    public static OrgItemTermin _new1754(String _arg1, float _arg2, Types _arg3, com.pullenti.ner.org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }
    public static OrgItemTermin _new1760(String _arg1, float _arg2, String _arg3, Types _arg4, com.pullenti.ner.org.OrgProfile _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }
    public static OrgItemTermin _new1765(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new1773(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner.org.OrgProfile _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        res.canHasLatinName = _arg6;
        return res;
    }
    public static OrgItemTermin _new1777(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.isDoubtWord = _arg3;
        return res;
    }
    public static OrgItemTermin _new1788(String _arg1, float _arg2, Types _arg3, boolean _arg4, String _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new1789(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, String _arg6, com.pullenti.ner.org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        res.setProfile(_arg7);
        return res;
    }
    public static OrgItemTermin _new1790(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }
    public static OrgItemTermin _new1800(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner.org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.setProfile(_arg7);
        return res;
    }
    public static OrgItemTermin _new1801(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner.org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.setProfile(_arg8);
        return res;
    }
    public static OrgItemTermin _new1804(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }
    public static OrgItemTermin _new1805(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }
    public static OrgItemTermin _new1807(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }
    public static OrgItemTermin _new1808(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }
    public static OrgItemTermin _new1809(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }
    public static OrgItemTermin _new1810(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }
    public static OrgItemTermin _new1811(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }
    public static OrgItemTermin _new1812(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.mustBePartofName = _arg4;
        return res;
    }
    public static OrgItemTermin _new1813(String _arg1, float _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setCanonicText(_arg4);
        return res;
    }
    public static OrgItemTermin _new1815(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.mustBePartofName = _arg5;
        return res;
    }
    public static OrgItemTermin _new1816(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setCanonicText(_arg5);
        return res;
    }
    public static OrgItemTermin _new1822(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }
    public static OrgItemTermin _new1823(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }
    public static OrgItemTermin _new1826(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }
    public static OrgItemTermin _new1828(String _arg1, Types _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canBeSingleGeo = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }
    public static OrgItemTermin _new1829(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }
    public static OrgItemTermin _new1830(String _arg1, float _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }
    public static OrgItemTermin _new1831(String _arg1, float _arg2, Types _arg3, String _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new1834(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        return res;
    }
    public static OrgItemTermin _new1838(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        return res;
    }
    public static OrgItemTermin _new1843(String _arg1, Types _arg2) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        return res;
    }
    public static OrgItemTermin _new1844(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        return res;
    }
    public static OrgItemTermin _new1846(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }
    public static OrgItemTermin _new1851(String _arg1, Types _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasNumber = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }
    public static OrgItemTermin _new1854(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasNumber = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }
    public static OrgItemTermin _new1855(String _arg1, String _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.acronym = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }
    public static OrgItemTermin _new1856(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }
    public static OrgItemTermin _new1861(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        return res;
    }
    public static OrgItemTermin _new1862(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }
    public static OrgItemTermin _new1866(String _arg1, Types _arg2, boolean _arg3, com.pullenti.ner.org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        res.setProfile(_arg4);
        return res;
    }
    public static OrgItemTermin _new1867(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new1871(String _arg1, Types _arg2, boolean _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        return res;
    }
    public static OrgItemTermin _new1872(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }
    public static OrgItemTermin _new1873(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.isDoubtWord = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }
    public static OrgItemTermin _new1880(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        return res;
    }
    public static OrgItemTermin _new1881(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }
    public static OrgItemTermin _new1882(String _arg1, Types _arg2, com.pullenti.ner.org.OrgProfile _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.acronym = _arg4;
        return res;
    }
    public static OrgItemTermin _new1883(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, com.pullenti.ner.org.OrgProfile _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.acronym = _arg5;
        return res;
    }
    public static OrgItemTermin _new1888(String _arg1, Types _arg2, String _arg3, com.pullenti.ner.org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        return res;
    }
    public static OrgItemTermin _new1889(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new1893(String _arg1, Types _arg2, com.pullenti.ner.org.OrgProfile _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        return res;
    }
    public static OrgItemTermin _new1910(String _arg1, Types _arg2, String _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        return res;
    }
    public static OrgItemTermin _new1911(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }
    public static OrgItemTermin _new1985(String _arg1, Types _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymCanBeLower = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }
    public static OrgItemTermin _new1986(String _arg1, Types _arg2, String _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }
    public static OrgItemTermin _new1987(String _arg1, Types _arg2, boolean _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        return res;
    }
    public static OrgItemTermin _new1988(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        return res;
    }
    public static OrgItemTermin _new1991(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        return res;
    }
    public static OrgItemTermin _new1993(String _arg1, Types _arg2, boolean _arg3, String _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        res.acronymSmart = _arg5;
        return res;
    }
    public static OrgItemTermin _new2004(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, String _arg5, String _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        res.acronymSmart = _arg6;
        return res;
    }
    public static OrgItemTermin _new2021(String _arg1, Types _arg2, String _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymSmart = _arg4;
        return res;
    }
    public static OrgItemTermin _new2028(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }
    public static OrgItemTermin _new2034(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }
    public static OrgItemTermin _new2037(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        return res;
    }
    public static OrgItemTermin _new2038(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, String _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        return res;
    }
    public static OrgItemTermin _new2043(String _arg1, Types _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }
    public static OrgItemTermin _new2054(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }
    public static OrgItemTermin _new2055(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }
    public static OrgItemTermin _new2056(String _arg1, Types _arg2, com.pullenti.ner.org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }
    public static OrgItemTermin _new2057(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }
    public static OrgItemTermin _new2058(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }
    public static OrgItemTermin _new2059(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }
    public static OrgItemTermin _new2060(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }
    public static OrgItemTermin _new2063(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }
    public static OrgItemTermin _new2064(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }
    public static OrgItemTermin _new2065(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        return res;
    }
    public static OrgItemTermin _new2067(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner.org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        res.setProfile(_arg7);
        return res;
    }
    public static OrgItemTermin _new2068(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner.org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        res.setProfile(_arg6);
        return res;
    }
    public static OrgItemTermin _new2069(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, com.pullenti.ner.org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.setProfile(_arg5);
        return res;
    }
    public static OrgItemTermin _new2070(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        return res;
    }
    public static OrgItemTermin _new2071(String _arg1, Types _arg2, float _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasSingleName = _arg4;
        return res;
    }
    public static OrgItemTermin _new2072(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }
    public static OrgItemTermin _new2083(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }
    public static OrgItemTermin _new2084(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }
    public static OrgItemTermin _new2085(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, String _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.acronym = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }
    public static OrgItemTermin _new2092(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }
    public static OrgItemTermin _new2093(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }
    public static OrgItemTermin _new2094(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner.org.OrgProfile _arg3, Types _arg4, float _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }
    public static OrgItemTermin _new2099(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner.org.OrgProfile _arg3, Types _arg4, float _arg5, boolean _arg6, String _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        res.acronym = _arg7;
        return res;
    }
    public static OrgItemTermin _new2100(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasSingleName = _arg4;
        res.mustHasCapitalName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }
    public static OrgItemTermin _new2101(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }
    public static OrgItemTermin _new2102(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner.org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.setProfile(_arg7);
        return res;
    }
    public static OrgItemTermin _new2103(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner.org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.setProfile(_arg8);
        return res;
    }
    public static OrgItemTermin _new2107(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }
    public static OrgItemTermin _new2108(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }
    public static OrgItemTermin _new2110(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }
    public static OrgItemTermin _new2111(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.canHasNumber = _arg8;
        return res;
    }
    public static OrgItemTermin _new2117(String _arg1, float _arg2, String _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, new com.pullenti.morph.MorphLang(null), com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }
    public static OrgItemTermin _new2122(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner.org.OrgProfile.UNDEFINED, com.pullenti.ner.org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }
    public OrgItemTermin() {
        super();
    }
    public static OrgItemTermin _globalInstance;
    static {
        _globalInstance = new OrgItemTermin(); 
    }
}
