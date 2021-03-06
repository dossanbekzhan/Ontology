/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.address;

/**
 * Сущность, представляющая адрес
 */
public class AddressReferent extends com.pullenti.ner.Referent {

    public AddressReferent() {
        super(OBJ_TYPENAME);
        setInstanceOf(com.pullenti.ner.address.internal.MetaAddress.globalMeta);
    }

    public static final String OBJ_TYPENAME = "ADDRESS";

    public static final String ATTR_STREET = "STREET";

    public static final String ATTR_HOUSE = "HOUSE";

    public static final String ATTR_HOUSETYPE = "HOUSETYPE";

    public static final String ATTR_CORPUS = "CORPUS";

    public static final String ATTR_BUILDING = "BUILDING";

    public static final String ATTR_BUILDINGTYPE = "BUILDINGTYPE";

    public static final String ATTR_CORPUSORFLAT = "CORPUSORFLAT";

    public static final String ATTR_PORCH = "PORCH";

    public static final String ATTR_FLOOR = "FLOOR";

    public static final String ATTR_OFFICE = "OFFICE";

    public static final String ATTR_FLAT = "FLAT";

    public static final String ATTR_KILOMETER = "KILOMETER";

    public static final String ATTR_PLOT = "PLOT";

    public static final String ATTR_BLOCK = "BLOCK";

    public static final String ATTR_BOX = "BOX";

    public static final String ATTR_GEO = "GEO";

    public static final String ATTR_ZIP = "ZIP";

    public static final String ATTR_POSTOFFICEBOX = "POSTOFFICEBOX";

    public static final String ATTR_CSP = "CSP";

    public static final String ATTR_METRO = "METRO";

    public static final String ATTR_DETAIL = "DETAIL";

    public static final String ATTR_DETAILPARAM = "DETAILPARAM";

    public static final String ATTR_MISC = "MISC";

    public static final String ATTR_FIAS = "FIAS";

    public static final String ATTR_BTI = "BTI";

    /**
     * Улица (кстати, их может быть несколько)
     */
    public java.util.ArrayList<com.pullenti.ner.Referent> getStreets() {
        java.util.ArrayList<com.pullenti.ner.Referent> res = new java.util.ArrayList<>();
        for(com.pullenti.ner.Slot s : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_STREET) && (s.getValue() instanceof com.pullenti.ner.Referent)) 
                res.add((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class));
        }
        return res;
    }


    /**
     * Дом
     */
    public String getHouse() {
        return getStringValue(ATTR_HOUSE);
    }

    /**
     * Дом
     */
    public String setHouse(String value) {
        addSlot(ATTR_HOUSE, value, true, 0);
        return value;
    }


    public AddressHouseType getHouseType() {
        String str = getStringValue(ATTR_HOUSETYPE);
        if (com.pullenti.n2j.Utils.isNullOrEmpty(str)) 
            return AddressHouseType.HOUSE;
        try {
            return AddressHouseType.of(str);
        } catch(Exception ex340) {
            return AddressHouseType.HOUSE;
        }
    }

    public AddressHouseType setHouseType(AddressHouseType value) {
        addSlot(ATTR_HOUSETYPE, value.toString().toUpperCase(), true, 0);
        return value;
    }


    /**
     * Строение
     */
    public String getBuilding() {
        return getStringValue(ATTR_BUILDING);
    }

    /**
     * Строение
     */
    public String setBuilding(String value) {
        addSlot(ATTR_BUILDING, value, true, 0);
        return value;
    }


    /**
     * Тип строения
     */
    public AddressBuildingType getBuildingType() {
        String str = getStringValue(ATTR_BUILDINGTYPE);
        if (com.pullenti.n2j.Utils.isNullOrEmpty(str)) 
            return AddressBuildingType.BUILDING;
        try {
            return AddressBuildingType.of(str);
        } catch(Exception ex341) {
            return AddressBuildingType.BUILDING;
        }
    }

    /**
     * Тип строения
     */
    public AddressBuildingType setBuildingType(AddressBuildingType value) {
        addSlot(ATTR_BUILDINGTYPE, value.toString().toUpperCase(), true, 0);
        return value;
    }


    /**
     * Корпус
     */
    public String getCorpus() {
        return getStringValue(ATTR_CORPUS);
    }

    /**
     * Корпус
     */
    public String setCorpus(String value) {
        addSlot(ATTR_CORPUS, value, true, 0);
        return value;
    }


    /**
     * Корпус или квартира
     */
    public String getCorpusOrFlat() {
        return getStringValue(ATTR_CORPUSORFLAT);
    }

    /**
     * Корпус или квартира
     */
    public String setCorpusOrFlat(String value) {
        addSlot(ATTR_CORPUSORFLAT, value, true, 0);
        return value;
    }


    /**
     * Этаж
     */
    public String getFloor() {
        return getStringValue(ATTR_FLOOR);
    }

    /**
     * Этаж
     */
    public String setFloor(String value) {
        addSlot(ATTR_FLOOR, value, true, 0);
        return value;
    }


    /**
     * Подъезд
     */
    public String getPotch() {
        return getStringValue(ATTR_PORCH);
    }

    /**
     * Подъезд
     */
    public String setPotch(String value) {
        addSlot(ATTR_PORCH, value, true, 0);
        return value;
    }


    /**
     * Квартира
     */
    public String getFlat() {
        return getStringValue(ATTR_FLAT);
    }

    /**
     * Квартира
     */
    public String setFlat(String value) {
        addSlot(ATTR_FLAT, value, true, 0);
        return value;
    }


    /**
     * Номер офиса
     */
    public String getOffice() {
        return getStringValue(ATTR_OFFICE);
    }

    /**
     * Номер офиса
     */
    public String setOffice(String value) {
        addSlot(ATTR_OFFICE, value, true, 0);
        return value;
    }


    /**
     * Номер участка
     */
    public String getPlot() {
        return getStringValue(ATTR_PLOT);
    }

    /**
     * Номер участка
     */
    public String setPlot(String value) {
        addSlot(ATTR_PLOT, value, true, 0);
        return value;
    }


    /**
     * Блок (ряд)
     */
    public String getBlock() {
        return getStringValue(ATTR_BLOCK);
    }

    /**
     * Блок (ряд)
     */
    public String setBlock(String value) {
        addSlot(ATTR_BLOCK, value, true, 0);
        return value;
    }


    /**
     * Бокс (гараж)
     */
    public String getBox() {
        return getStringValue(ATTR_BOX);
    }

    /**
     * Бокс (гараж)
     */
    public String setBox(String value) {
        addSlot(ATTR_BOX, value, true, 0);
        return value;
    }


    /**
     * Станция метро
     */
    public String getMetro() {
        return getStringValue(ATTR_METRO);
    }

    /**
     * Станция метро
     */
    public String setMetro(String value) {
        addSlot(ATTR_METRO, value, true, 0);
        return value;
    }


    /**
     * Километр
     */
    public String getKilometer() {
        return getStringValue(ATTR_KILOMETER);
    }

    /**
     * Километр
     */
    public String setKilometer(String value) {
        addSlot(ATTR_KILOMETER, value, true, 0);
        return value;
    }


    /**
     * Почтовый индекс
     */
    public String getZip() {
        return getStringValue(ATTR_ZIP);
    }

    /**
     * Почтовый индекс
     */
    public String setZip(String value) {
        addSlot(ATTR_ZIP, value, true, 0);
        return value;
    }


    /**
     * Почтовый ящик
     */
    public String getPostOfficeBox() {
        return getStringValue(ATTR_POSTOFFICEBOX);
    }

    /**
     * Почтовый ящик
     */
    public String setPostOfficeBox(String value) {
        addSlot(ATTR_POSTOFFICEBOX, value, true, 0);
        return value;
    }


    /**
     * ГСП (абонент городской служебной почты)
     */
    public String getCSP() {
        return getStringValue(ATTR_CSP);
    }

    /**
     * ГСП (абонент городской служебной почты)
     */
    public String setCSP(String value) {
        addSlot(ATTR_CSP, value, true, 0);
        return value;
    }


    /**
     * Ссылки на географические объекты (самого нижнего уровня)
     */
    public java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> getGeos() {
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> res = new java.util.ArrayList<>();
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_GEO) && (a.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) 
                res.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(a.getValue(), com.pullenti.ner.geo.GeoReferent.class));
            else if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_STREET) && (a.getValue() instanceof com.pullenti.ner.Referent)) {
                for(com.pullenti.ner.Slot s : (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(a.getValue(), com.pullenti.ner.Referent.class))).getSlots()) {
                    if (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent) 
                        res.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class));
                }
            }
        }
        for(int i = res.size() - 1; i > 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if (_isHigher(res.get(i), res.get(j))) {
                    res.remove(i);
                    break;
                }
                else if (_isHigher(res.get(j), res.get(i))) {
                    res.remove(j);
                    i--;
                }
            }
        }
        return res;
    }


    private static boolean _isHigher(com.pullenti.ner.geo.GeoReferent gHi, com.pullenti.ner.geo.GeoReferent gLo) {
        int i = 0;
        for(; gLo != null && (i < 10); gLo = gLo.getHigher(),i++) {
            if (gLo.canBeEquals(gHi, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) 
                return true;
        }
        return false;
    }

    @Override
    public com.pullenti.ner.Referent getParentReferent() {
        com.pullenti.ner.Referent sr = (com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(getValue(ATTR_STREET), com.pullenti.ner.Referent.class);
        if (sr != null) 
            return sr;
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> _geos = getGeos();
        for(com.pullenti.ner.geo.GeoReferent g : _geos) {
            if (g.isCity()) 
                return g;
        }
        for(com.pullenti.ner.geo.GeoReferent g : _geos) {
            if (g.isRegion() && !g.isState()) 
                return g;
        }
        if (_geos.size() > 0) 
            return _geos.get(0);
        return null;
    }


    public void addReferent(com.pullenti.ner.Referent r) {
        if (r == null) 
            return;
        com.pullenti.ner.geo.GeoReferent geo = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(r, com.pullenti.ner.geo.GeoReferent.class);
        if (geo != null) {
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_GEO)) {
                    com.pullenti.ner.geo.GeoReferent geo0 = (com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class);
                    if (geo0 == null) 
                        continue;
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geo0, geo)) {
                        if (geo.getHigher() == geo0 || geo.isCity()) {
                            uploadSlot(s, geo);
                            return;
                        }
                    }
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geo, geo0)) 
                        return;
                }
            }
            addSlot(ATTR_GEO, r, false, 0);
        }
        else if (((r instanceof StreetReferent)) || com.pullenti.n2j.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) 
            addSlot(ATTR_STREET, r, false, 0);
    }

    /**
     * ополнительная детализация места (пересечение, около ...)
     */
    public AddressDetailType getDetail() {
        String s = getStringValue(ATTR_DETAIL);
        if (s == null) 
            return AddressDetailType.UNDEFINED;
        try {
            Object res = AddressDetailType.of(s);
            if (res instanceof AddressDetailType) 
                return (AddressDetailType)res;
        } catch(Exception ex342) {
        }
        return AddressDetailType.UNDEFINED;
    }

    /**
     * ополнительная детализация места (пересечение, около ...)
     */
    public AddressDetailType setDetail(AddressDetailType value) {
        if (value != AddressDetailType.UNDEFINED) 
            addSlot(ATTR_DETAIL, value.toString().toUpperCase(), true, 0);
        return value;
    }


    @Override
    public String toString(boolean shortVariant, com.pullenti.morph.MorphLang lang, int lev) {
        StringBuilder res = new StringBuilder();
        String str = getStringValue(ATTR_DETAIL);
        if (str != null) 
            str = (String)com.pullenti.n2j.Utils.cast(com.pullenti.ner.address.internal.MetaAddress.globalMeta.detailFeature.convertInnerValueToOuterValue(str, lang), String.class);
        if (str != null) {
            res.append("[").append(str.toLowerCase());
            if ((((str = getStringValue(ATTR_DETAILPARAM)))) != null) 
                res.append(", ").append(str);
            res.append(']');
        }
        java.util.ArrayList<com.pullenti.ner.Referent> strs = getStreets();
        if (strs.size() == 0) {
            if (getMetro() != null) {
                if (res.length() > 0) 
                    res.append(' ');
                res.append((String)com.pullenti.n2j.Utils.notnull(getMetro(), ""));
            }
        }
        else {
            if (res.length() > 0) 
                res.append(' ');
            for(int i = 0; i < strs.size(); i++) {
                if (i > 0) 
                    res.append(", ");
                res.append(strs.get(i).toString(true, lang, 0));
            }
        }
        if (getKilometer() != null) 
            res.append(" ").append(getKilometer()).append("км.");
        if (getHouse() != null) {
            AddressHouseType ty = getHouseType();
            if (ty == AddressHouseType.ESTATE) 
                res.append(" влад.");
            else if (ty == AddressHouseType.HOUSEESTATE) 
                res.append(" домовл.");
            else 
                res.append(" д.");
            res.append((com.pullenti.n2j.Utils.stringsEq(getHouse(), "0") ? "Б/Н" : getHouse()));
        }
        if (getCorpus() != null) 
            res.append(" корп.").append((com.pullenti.n2j.Utils.stringsEq(getCorpus(), "0") ? "Б/Н" : getCorpus()));
        if (getBuilding() != null) {
            AddressBuildingType ty = getBuildingType();
            if (ty == AddressBuildingType.CONSTRUCTION) 
                res.append(" сооруж.");
            else if (ty == AddressBuildingType.LITER) 
                res.append(" лит.");
            else 
                res.append(" стр.");
            res.append((com.pullenti.n2j.Utils.stringsEq(getBuilding(), "0") ? "Б/Н" : getBuilding()));
        }
        if (getPotch() != null) 
            res.append(" под.").append(getPotch());
        if (getFloor() != null) 
            res.append(" эт.").append(getFloor());
        if (getFlat() != null) 
            res.append(" кв.").append(getFlat());
        if (getCorpusOrFlat() != null) 
            res.append(" корп.(кв.?)").append(getCorpusOrFlat());
        if (getOffice() != null) 
            res.append(" оф.").append(getOffice());
        if (getBlock() != null) 
            res.append(" блок ").append(getBlock());
        if (getPlot() != null) 
            res.append(" уч.").append(getPlot());
        if (getBox() != null) 
            res.append(" бокс ").append(getBox());
        if (getPostOfficeBox() != null) 
            res.append(" а\\я").append(getPostOfficeBox());
        if (getCSP() != null) 
            res.append(" ГСП-").append(getCSP());
        Object kladr = getValue(ATTR_FIAS);
        if (kladr instanceof com.pullenti.ner.Referent) {
            res.append(" (ФИАС: ").append(((String)com.pullenti.n2j.Utils.notnull((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(kladr, com.pullenti.ner.Referent.class))).getStringValue("GUID"), "?")));
            for(com.pullenti.ner.Slot s : getSlots()) {
                if (com.pullenti.n2j.Utils.stringsEq(s.getTypeName(), ATTR_FIAS) && (s.getValue() instanceof com.pullenti.ner.Referent) && s.getValue() != kladr) 
                    res.append(", ").append(((String)com.pullenti.n2j.Utils.notnull((((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.Referent.class))).getStringValue("GUID"), "?")));
            }
            res.append(')');
        }
        String bti = getStringValue(ATTR_BTI);
        if (bti != null) 
            res.append(" (БТИ ").append(bti).append(")");
        for(com.pullenti.ner.geo.GeoReferent g : getGeos()) {
            if (res.length() > 0 && res.charAt(res.length() - 1) == ' ') 
                res.setLength(res.length() - 1);
            if (res.length() > 0 && res.charAt(res.length() - 1) == ']') {
            }
            else if (res.length() > 0) 
                res.append(';');
            res.append(" ").append(g.toString(true, lang, lev + 1));
        }
        if (getZip() != null) 
            res.append("; ").append(getZip());
        return res.toString().trim();
    }

    @Override
    public boolean canBeEquals(com.pullenti.ner.Referent obj, com.pullenti.ner.Referent.EqualType typ) {
        AddressReferent addr = (AddressReferent)com.pullenti.n2j.Utils.cast(obj, AddressReferent.class);
        if (addr == null) 
            return false;
        java.util.ArrayList<com.pullenti.ner.Referent> strs1 = getStreets();
        java.util.ArrayList<com.pullenti.ner.Referent> strs2 = addr.getStreets();
        if (strs1.size() > 0 || strs2.size() > 0) {
            boolean ok = false;
            for(com.pullenti.ner.Referent s : strs1) {
                for(com.pullenti.ner.Referent ss : strs2) {
                    if (ss.canBeEquals(s, typ)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (!ok) 
                return false;
        }
        if (addr.getHouse() != null || getHouse() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getHouse(), getHouse())) 
                return false;
        }
        if (addr.getBuilding() != null || getBuilding() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getBuilding(), getBuilding())) 
                return false;
        }
        if (addr.getPlot() != null || getPlot() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getPlot(), getPlot())) 
                return false;
        }
        if (addr.getBox() != null || getBox() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getBox(), getBox())) 
                return false;
        }
        if (addr.getBlock() != null || getBlock() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getBlock(), getBlock())) 
                return false;
        }
        if (addr.getCorpus() != null || getCorpus() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getCorpus(), getCorpus())) {
                if (addr.getCorpus() != null && com.pullenti.n2j.Utils.stringsEq(addr.getCorpus(), getCorpusOrFlat())) {
                }
                else if (getCorpus() != null && com.pullenti.n2j.Utils.stringsEq(addr.getCorpusOrFlat(), getCorpus())) {
                }
                else 
                    return false;
            }
        }
        if (addr.getFlat() != null || getFlat() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getFlat(), getFlat())) {
                if (addr.getFlat() != null && com.pullenti.n2j.Utils.stringsEq(addr.getFlat(), getCorpusOrFlat())) {
                }
                else if (getFlat() != null && com.pullenti.n2j.Utils.stringsEq(addr.getCorpusOrFlat(), getFlat())) {
                }
                else 
                    return false;
            }
        }
        if (addr.getCorpusOrFlat() != null || getCorpusOrFlat() != null) {
            if (getCorpusOrFlat() != null && addr.getCorpusOrFlat() != null) {
                if (com.pullenti.n2j.Utils.stringsNe(getCorpusOrFlat(), addr.getCorpusOrFlat())) 
                    return false;
            }
            else if (getCorpusOrFlat() == null) {
                if (getCorpus() == null && getFlat() == null) 
                    return false;
            }
            else if (addr.getCorpusOrFlat() == null) {
                if (addr.getCorpus() == null && addr.getFlat() == null) 
                    return false;
            }
        }
        if (addr.getOffice() != null || getOffice() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getOffice(), getOffice())) 
                return false;
        }
        if (addr.getPotch() != null || getPotch() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getPotch(), getPotch())) 
                return false;
        }
        if (addr.getFloor() != null || getFloor() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getFloor(), getFloor())) 
                return false;
        }
        if (addr.getPostOfficeBox() != null || getPostOfficeBox() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getPostOfficeBox(), getPostOfficeBox())) 
                return false;
        }
        if (addr.getCSP() != null && getCSP() != null) {
            if (com.pullenti.n2j.Utils.stringsNe(addr.getCSP(), getCSP())) 
                return false;
        }
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos1 = getGeos();
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> geos2 = addr.getGeos();
        if (geos1.size() > 0 && geos2.size() > 0) {
            boolean ok = false;
            for(com.pullenti.ner.geo.GeoReferent g1 : geos1) {
                for(com.pullenti.ner.geo.GeoReferent g2 : geos2) {
                    if (g1.canBeEquals(g2, typ)) {
                        ok = true;
                        break;
                    }
                }
            }
            if (!ok) 
                return false;
        }
        return true;
    }

    @Override
    public void mergeSlots(com.pullenti.ner.Referent obj, boolean mergeStatistic) {
        super.mergeSlots(obj, mergeStatistic);
        if (getCorpusOrFlat() != null) {
            if (com.pullenti.n2j.Utils.stringsEq(getFlat(), getCorpusOrFlat())) 
                setCorpusOrFlat(null);
            else if (com.pullenti.n2j.Utils.stringsEq(getCorpus(), getCorpusOrFlat())) 
                setCorpusOrFlat(null);
        }
        correct();
    }

    public void correct() {
        java.util.ArrayList<com.pullenti.ner.geo.GeoReferent> _geos = new java.util.ArrayList<>();
        for(com.pullenti.ner.Slot a : getSlots()) {
            if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_GEO) && (a.getValue() instanceof com.pullenti.ner.geo.GeoReferent)) 
                _geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(a.getValue(), com.pullenti.ner.geo.GeoReferent.class));
            else if (com.pullenti.n2j.Utils.stringsEq(a.getTypeName(), ATTR_STREET) && (a.getValue() instanceof com.pullenti.ner.Referent)) {
                for(com.pullenti.ner.Slot s : (((com.pullenti.ner.Referent)com.pullenti.n2j.Utils.cast(a.getValue(), com.pullenti.ner.Referent.class))).getSlots()) {
                    if (s.getValue() instanceof com.pullenti.ner.geo.GeoReferent) 
                        _geos.add((com.pullenti.ner.geo.GeoReferent)com.pullenti.n2j.Utils.cast(s.getValue(), com.pullenti.ner.geo.GeoReferent.class));
                }
            }
        }
        for(int i = _geos.size() - 1; i > 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if (_isHigher(_geos.get(i), _geos.get(j))) {
                    com.pullenti.ner.Slot s = findSlot(ATTR_GEO, _geos.get(i), true);
                    if (s != null) 
                        getSlots().remove(s);
                    _geos.remove(i);
                    break;
                }
                else if (_isHigher(_geos.get(j), _geos.get(i))) {
                    com.pullenti.ner.Slot s = findSlot(ATTR_GEO, _geos.get(j), true);
                    if (s != null) 
                        getSlots().remove(s);
                    _geos.remove(j);
                    i--;
                }
            }
        }
        if (_geos.size() == 2) {
            com.pullenti.ner.geo.GeoReferent reg = null;
            com.pullenti.ner.geo.GeoReferent cit = null;
            for(int ii = 0; ii < _geos.size(); ii++) {
                if (_geos.get(ii).isTerritory() && _geos.get(ii).getHigher() != null) 
                    com.pullenti.n2j.Utils.putArrayValue(_geos, ii, _geos.get(ii).getHigher());
            }
            if (_geos.get(0).isCity() && _geos.get(1).isRegion()) {
                cit = _geos.get(0);
                reg = _geos.get(1);
            }
            else if (_geos.get(1).isCity() && _geos.get(0).isRegion()) {
                cit = _geos.get(1);
                reg = _geos.get(0);
            }
            if (cit != null && cit.getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(reg, cit)) {
                cit.setHigher(reg);
                com.pullenti.ner.Slot ss = findSlot(ATTR_GEO, reg, true);
                if (ss != null) 
                    getSlots().remove(ss);
                _geos = getGeos();
            }
            else {
                com.pullenti.ner.geo.GeoReferent stat = null;
                com.pullenti.ner.geo.GeoReferent geo = null;
                if (_geos.get(0).isState() && !_geos.get(1).isState()) {
                    stat = _geos.get(0);
                    geo = _geos.get(1);
                }
                else if (_geos.get(1).isState() && !_geos.get(0).isState()) {
                    stat = _geos.get(1);
                    geo = _geos.get(0);
                }
                if (stat != null) {
                    geo = geo.getTopHigher();
                    if (geo.getHigher() == null) {
                        geo.setHigher(stat);
                        com.pullenti.ner.Slot s = findSlot(ATTR_GEO, stat, true);
                        if (s != null) 
                            getSlots().remove(s);
                    }
                }
            }
        }
    }
}
