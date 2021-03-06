/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.org;

/**
 * Профили организации, хранятся в атрибутах ATTR_PROFILE, может быть несколько.
 */
public class OrgProfile implements Comparable<OrgProfile> {

    /**
     * Неопределённое
     */
    public static final OrgProfile UNDEFINED; // 0

    /**
     * Подразделение, отдел
     */
    public static final OrgProfile UNIT; // 1

    /**
     * Различные объединения людей (фонды, движения, партии, ассоциации)
     */
    public static final OrgProfile UNION; // 2

    /**
     * Соревнование, конкурс, чемпионат
     */
    public static final OrgProfile COMPETITION; // 3

    /**
     * Группы компаний, холдинги
     */
    public static final OrgProfile HOLDING; // 4

    /**
     * Государственные
     */
    public static final OrgProfile STATE; // 5

    /**
     * Бизнес, коммерция
     */
    public static final OrgProfile BUSINESS; // 6

    /**
     * Финансы (банки, фонды)
     */
    public static final OrgProfile FINANCE; // 7

    /**
     * Образование
     */
    public static final OrgProfile EDUCATION; // 8

    /**
     * Наука
     */
    public static final OrgProfile SCIENCE; // 9

    /**
     * Производство
     */
    public static final OrgProfile INDUSTRY; // 10

    /**
     * Торговля, реализация
     */
    public static final OrgProfile TRADE; // 11

    /**
     * Медицина
     */
    public static final OrgProfile MEDICINE; // 12

    /**
     * Политика
     */
    public static final OrgProfile POLICY; // 13

    /**
     * Судебная система
     */
    public static final OrgProfile JUSTICE; // 14

    /**
     * Силовые структуры
     */
    public static final OrgProfile ENFORCEMENT; // 15

    /**
     * Армейские структуры
     */
    public static final OrgProfile ARMY; // 16

    /**
     * Спорт
     */
    public static final OrgProfile SPORT; // 17

    /**
     * Религиозные
     */
    public static final OrgProfile RELIGION; // 18

    /**
     * Искусство
     */
    public static final OrgProfile ART; // 19

    /**
     * Музыка, группы
     */
    public static final OrgProfile MUSIC; // 20

    /**
     * Театры, выставки, музеи, концерты
     */
    public static final OrgProfile SHOW; // 21

    /**
     * Срадства массовой информации
     */
    public static final OrgProfile MEDIA; // 22

    /**
     * Издательства, газеты, журналы ... (обычно вместе с Media)
     */
    public static final OrgProfile PRESS; // 23

    /**
     * пансионаты, отели, дома отдыха
     */
    public static final OrgProfile HOTEL; // 24

    /**
     * Предприятия питания
     */
    public static final OrgProfile FOOD; // 25

    /**
     * Железные дороги, авиакомпании ...
     */
    public static final OrgProfile TRANSPORT; // 26

    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private OrgProfile(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(OrgProfile v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, OrgProfile> mapIntToEnum; 
    private static java.util.HashMap<String, OrgProfile> mapStringToEnum; 
    public static OrgProfile of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        OrgProfile item = new OrgProfile(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static OrgProfile of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    static {
        mapIntToEnum = new java.util.HashMap< >();
        mapStringToEnum = new java.util.HashMap< >();
        UNDEFINED = new OrgProfile(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        UNIT = new OrgProfile(1, "UNIT");
        mapIntToEnum.put(UNIT.value(), UNIT);
        mapStringToEnum.put(UNIT.m_str.toUpperCase(), UNIT);
        UNION = new OrgProfile(2, "UNION");
        mapIntToEnum.put(UNION.value(), UNION);
        mapStringToEnum.put(UNION.m_str.toUpperCase(), UNION);
        COMPETITION = new OrgProfile(3, "COMPETITION");
        mapIntToEnum.put(COMPETITION.value(), COMPETITION);
        mapStringToEnum.put(COMPETITION.m_str.toUpperCase(), COMPETITION);
        HOLDING = new OrgProfile(4, "HOLDING");
        mapIntToEnum.put(HOLDING.value(), HOLDING);
        mapStringToEnum.put(HOLDING.m_str.toUpperCase(), HOLDING);
        STATE = new OrgProfile(5, "STATE");
        mapIntToEnum.put(STATE.value(), STATE);
        mapStringToEnum.put(STATE.m_str.toUpperCase(), STATE);
        BUSINESS = new OrgProfile(6, "BUSINESS");
        mapIntToEnum.put(BUSINESS.value(), BUSINESS);
        mapStringToEnum.put(BUSINESS.m_str.toUpperCase(), BUSINESS);
        FINANCE = new OrgProfile(7, "FINANCE");
        mapIntToEnum.put(FINANCE.value(), FINANCE);
        mapStringToEnum.put(FINANCE.m_str.toUpperCase(), FINANCE);
        EDUCATION = new OrgProfile(8, "EDUCATION");
        mapIntToEnum.put(EDUCATION.value(), EDUCATION);
        mapStringToEnum.put(EDUCATION.m_str.toUpperCase(), EDUCATION);
        SCIENCE = new OrgProfile(9, "SCIENCE");
        mapIntToEnum.put(SCIENCE.value(), SCIENCE);
        mapStringToEnum.put(SCIENCE.m_str.toUpperCase(), SCIENCE);
        INDUSTRY = new OrgProfile(10, "INDUSTRY");
        mapIntToEnum.put(INDUSTRY.value(), INDUSTRY);
        mapStringToEnum.put(INDUSTRY.m_str.toUpperCase(), INDUSTRY);
        TRADE = new OrgProfile(11, "TRADE");
        mapIntToEnum.put(TRADE.value(), TRADE);
        mapStringToEnum.put(TRADE.m_str.toUpperCase(), TRADE);
        MEDICINE = new OrgProfile(12, "MEDICINE");
        mapIntToEnum.put(MEDICINE.value(), MEDICINE);
        mapStringToEnum.put(MEDICINE.m_str.toUpperCase(), MEDICINE);
        POLICY = new OrgProfile(13, "POLICY");
        mapIntToEnum.put(POLICY.value(), POLICY);
        mapStringToEnum.put(POLICY.m_str.toUpperCase(), POLICY);
        JUSTICE = new OrgProfile(14, "JUSTICE");
        mapIntToEnum.put(JUSTICE.value(), JUSTICE);
        mapStringToEnum.put(JUSTICE.m_str.toUpperCase(), JUSTICE);
        ENFORCEMENT = new OrgProfile(15, "ENFORCEMENT");
        mapIntToEnum.put(ENFORCEMENT.value(), ENFORCEMENT);
        mapStringToEnum.put(ENFORCEMENT.m_str.toUpperCase(), ENFORCEMENT);
        ARMY = new OrgProfile(16, "ARMY");
        mapIntToEnum.put(ARMY.value(), ARMY);
        mapStringToEnum.put(ARMY.m_str.toUpperCase(), ARMY);
        SPORT = new OrgProfile(17, "SPORT");
        mapIntToEnum.put(SPORT.value(), SPORT);
        mapStringToEnum.put(SPORT.m_str.toUpperCase(), SPORT);
        RELIGION = new OrgProfile(18, "RELIGION");
        mapIntToEnum.put(RELIGION.value(), RELIGION);
        mapStringToEnum.put(RELIGION.m_str.toUpperCase(), RELIGION);
        ART = new OrgProfile(19, "ART");
        mapIntToEnum.put(ART.value(), ART);
        mapStringToEnum.put(ART.m_str.toUpperCase(), ART);
        MUSIC = new OrgProfile(20, "MUSIC");
        mapIntToEnum.put(MUSIC.value(), MUSIC);
        mapStringToEnum.put(MUSIC.m_str.toUpperCase(), MUSIC);
        SHOW = new OrgProfile(21, "SHOW");
        mapIntToEnum.put(SHOW.value(), SHOW);
        mapStringToEnum.put(SHOW.m_str.toUpperCase(), SHOW);
        MEDIA = new OrgProfile(22, "MEDIA");
        mapIntToEnum.put(MEDIA.value(), MEDIA);
        mapStringToEnum.put(MEDIA.m_str.toUpperCase(), MEDIA);
        PRESS = new OrgProfile(23, "PRESS");
        mapIntToEnum.put(PRESS.value(), PRESS);
        mapStringToEnum.put(PRESS.m_str.toUpperCase(), PRESS);
        HOTEL = new OrgProfile(24, "HOTEL");
        mapIntToEnum.put(HOTEL.value(), HOTEL);
        mapStringToEnum.put(HOTEL.m_str.toUpperCase(), HOTEL);
        FOOD = new OrgProfile(25, "FOOD");
        mapIntToEnum.put(FOOD.value(), FOOD);
        mapStringToEnum.put(FOOD.m_str.toUpperCase(), FOOD);
        TRANSPORT = new OrgProfile(26, "TRANSPORT");
        mapIntToEnum.put(TRANSPORT.value(), TRANSPORT);
        mapStringToEnum.put(TRANSPORT.m_str.toUpperCase(), TRANSPORT);
    }
}
