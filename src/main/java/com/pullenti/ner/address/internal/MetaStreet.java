/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.address.internal;

public class MetaStreet extends com.pullenti.ner.ReferentClass {

    public MetaStreet() {
        super();
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_TYP, "Тип", 0, 0);
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_NAME, "Наименование", 1, 0);
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_NUMBER, "Номер", 0, 1);
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_SECNUMBER, "Доп.номер", 0, 1);
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_GEO, "Географический объект", 0, 1);
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_FIAS, "Объект ФИАС", 0, 1);
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_BTI, "Объект БТИ", 0, 1);
        addFeature(com.pullenti.ner.address.StreetReferent.ATTR_OKM, "Код ОКМ УМ", 0, 1);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.address.StreetReferent.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Улица";
    }


    public static String IMAGEID = "street";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static MetaStreet globalMeta = new MetaStreet();
}
