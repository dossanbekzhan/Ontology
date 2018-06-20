/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.literature.internal;

public class CharacterVariantContainer {

    public java.util.ArrayList<CharacterVariant> all = new java.util.ArrayList<>();

    private java.util.HashMap<String, java.util.ArrayList<CharacterVariant>> m_HashByFirst = new java.util.HashMap<>();

    public void mergeCharItems(CharItem ci, CharItem ci2) {
        java.util.ArrayList<CharacterVariant> chas2 = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1481 = new com.pullenti.n2j.Outargwrapper<>();
        Boolean inoutres1482 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, ci2.values.get(0), inoutarg1481);
        chas2 = inoutarg1481.value;
        if (inoutres1482) 
            m_HashByFirst.remove(ci2.values.get(0));
        for(CharacterVariant it : all) {
            for(int i = 0; i < it.items.size(); i++) {
                if (it.items.get(i).item == ci2) 
                    it.items.get(i).item = ci;
            }
        }
        if (chas2 != null) {
            java.util.ArrayList<CharacterVariant> chas = null;
            com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1479 = new com.pullenti.n2j.Outargwrapper<>();
            Boolean inoutres1480 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, ci.values.get(0), inoutarg1479);
            chas = inoutarg1479.value;
            if (inoutres1480) 
                chas.addAll(chas2);
            else 
                m_HashByFirst.put(ci.values.get(0), chas2);
        }
    }

    public java.util.ArrayList<CharacterVariant> findOnFirst(CharItem item) {
        java.util.ArrayList<CharacterVariant> chas = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1483 = new com.pullenti.n2j.Outargwrapper<>();
        Boolean inoutres1484 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, item.values.get(0), inoutarg1483);
        chas = inoutarg1483.value;
        if (!inoutres1484) 
            m_HashByFirst.put(item.values.get(0), (chas = new java.util.ArrayList<>()));
        return chas;
    }

    public CharacterVariant find(java.util.ArrayList<CharItemVar> items) {
        if (items == null || items.size() == 0) 
            return null;
        java.util.ArrayList<CharacterVariant> chas = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1485 = new com.pullenti.n2j.Outargwrapper<>();
        Boolean inoutres1486 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, items.get(0).item.values.get(0), inoutarg1485);
        chas = inoutarg1485.value;
        if (!inoutres1486) 
            m_HashByFirst.put(items.get(0).item.values.get(0), (chas = new java.util.ArrayList<>()));
        for(CharacterVariant ch : chas) {
            if (ch.items.size() == items.size()) {
                if (!CharItemVar.isEquals(ch.items.get(0), items.get(0))) 
                    continue;
                int i;
                for(i = 1; i < items.size(); i++) {
                    if (!CharItemVar.isEquals(ch.items.get(i), items.get(i))) 
                        break;
                }
                if (i >= items.size()) 
                    return ch;
            }
        }
        return null;
    }

    public CharacterVariant register(java.util.ArrayList<CharItemVar> items) {
        CharacterVariant res = find(items);
        if (res != null) {
            for(int i = 0; i < items.size(); i++) {
                res.items.get(i).addOccures(items.get(i));
            }
            return res;
        }
        res = new CharacterVariant();
        res.items.addAll(items);
        all.add(res);
        java.util.ArrayList<CharacterVariant> chas = null;
        com.pullenti.n2j.Outargwrapper<java.util.ArrayList<CharacterVariant>> inoutarg1487 = new com.pullenti.n2j.Outargwrapper<>();
        Boolean inoutres1488 = com.pullenti.n2j.Utils.tryGetValue(m_HashByFirst, items.get(0).item.values.get(0), inoutarg1487);
        chas = inoutarg1487.value;
        if (!inoutres1488) 
            m_HashByFirst.put(items.get(0).item.values.get(0), (chas = new java.util.ArrayList<>()));
        chas.add(res);
        return res;
    }
    public CharacterVariantContainer() {
    }
}
