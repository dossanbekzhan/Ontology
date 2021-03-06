/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.instrument.internal;

public class InstrumentParticipantMeta extends com.pullenti.ner.ReferentClass {

    public InstrumentParticipantMeta() {
        super();
        addFeature(com.pullenti.ner.instrument.InstrumentParticipant.ATTR_TYPE, "Тип", 0, 1);
        addFeature(com.pullenti.ner.instrument.InstrumentParticipant.ATTR_REF, "Ссылка на объект", 0, 1).setShowAsParent(true);
        addFeature(com.pullenti.ner.instrument.InstrumentParticipant.ATTR_DELEGATE, "Ссылка на представителя", 0, 1).setShowAsParent(true);
        addFeature(com.pullenti.ner.instrument.InstrumentParticipant.ATTR_GROUND, "Основание", 0, 1).setShowAsParent(true);
    }

    @Override
    public String getName() {
        return com.pullenti.ner.instrument.InstrumentParticipant.OBJ_TYPENAME;
    }


    @Override
    public String getCaption() {
        return "Участник";
    }


    public static String IMAGEID = "participant";

    @Override
    public String getImageId(com.pullenti.ner.Referent obj) {
        return IMAGEID;
    }

    public static InstrumentParticipantMeta GLOBALMETA = new InstrumentParticipantMeta();
}
