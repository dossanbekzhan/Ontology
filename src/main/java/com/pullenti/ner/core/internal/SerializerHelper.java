/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the convertor N2JP from Pullenti C#.NET project.
 * See www.pullenti.ru/downloadpage.aspx.
 * 
 */

package com.pullenti.ner.core.internal;

public class SerializerHelper {

    public static void serializeInt(com.pullenti.n2j.Stream stream, int val) throws java.io.IOException {
        stream.write(java.nio.ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(val).array(), 0, 4);
    }

    public static int deserializeInt(com.pullenti.n2j.Stream stream) throws java.io.IOException {
        byte[] buf = new byte[4];
        stream.read(buf, 0, 4);
        return java.nio.ByteBuffer.wrap(buf, 0, 4).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static void serializeShort(com.pullenti.n2j.Stream stream, short val) throws java.io.IOException {
        stream.write(java.nio.ByteBuffer.allocate(2).order(java.nio.ByteOrder.LITTLE_ENDIAN).putShort(val).array(), 0, 2);
    }

    public static short deserializeShort(com.pullenti.n2j.Stream stream) throws java.io.IOException {
        byte[] buf = new byte[2];
        stream.read(buf, 0, 2);
        return java.nio.ByteBuffer.wrap(buf, 0, 2).order(java.nio.ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static void serializeString(com.pullenti.n2j.Stream stream, String val) throws java.io.IOException {
        if (val == null) {
            serializeInt(stream, -1);
            return;
        }
        if (com.pullenti.n2j.Utils.isNullOrEmpty(val)) {
            serializeInt(stream, 0);
            return;
        }
        byte[] data = com.pullenti.n2j.Utils.encodeCharset(java.nio.charset.Charset.forName("UTF-8"), val);
        serializeInt(stream, data.length);
        stream.write(data, 0, data.length);
    }

    public static String deserializeString(com.pullenti.n2j.Stream stream) throws java.io.IOException {
        int len = deserializeInt(stream);
        if (len < 0) 
            return null;
        if (len == 0) 
            return "";
        byte[] data = new byte[len];
        stream.read(data, 0, data.length);
        return com.pullenti.n2j.Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), data, 0, -1);
    }

    public static void serializeTokens(com.pullenti.n2j.Stream stream, com.pullenti.ner.Token t, int maxChar) throws java.io.IOException {
        int cou = 0;
        for(com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (maxChar > 0 && tt.endChar > maxChar) 
                break;
            cou++;
        }
        serializeInt(stream, cou);
        for(; cou > 0; cou--,t = t.getNext()) {
            serializeToken(stream, t);
        }
    }

    public static com.pullenti.ner.Token deserializeTokens(com.pullenti.n2j.Stream stream, com.pullenti.ner.core.AnalysisKit kit) throws java.io.IOException {
        int cou = deserializeInt(stream);
        if (cou == 0) 
            return null;
        com.pullenti.ner.Token res = null;
        com.pullenti.ner.Token prev = null;
        for(; cou > 0; cou--) {
            com.pullenti.ner.Token t = deserializeToken(stream, kit);
            if (t == null) 
                continue;
            if (res == null) 
                res = t;
            if (prev != null) 
                t.setPrevious(prev);
            prev = t;
        }
        for(com.pullenti.ner.Token t = res; t != null; t = t.getNext()) {
            if (t instanceof com.pullenti.ner.MetaToken) 
                _corrPrevNext((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class), t.getPrevious(), t.getNext());
        }
        return res;
    }

    private static void _corrPrevNext(com.pullenti.ner.MetaToken mt, com.pullenti.ner.Token prev, com.pullenti.ner.Token next) {
        mt.getBeginToken().m_Previous = prev;
        mt.getEndToken().m_Next = next;
        for(com.pullenti.ner.Token t = mt.getBeginToken(); t != null && t.endChar <= mt.endChar; t = t.getNext()) {
            if (t instanceof com.pullenti.ner.MetaToken) 
                _corrPrevNext((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class), t.getPrevious(), t.getNext());
        }
    }

    public static void serializeToken(com.pullenti.n2j.Stream stream, com.pullenti.ner.Token t) throws java.io.IOException {
        short typ = (short)0;
        if (t instanceof com.pullenti.ner.TextToken) 
            typ = (short)1;
        else if (t instanceof com.pullenti.ner.NumberToken) 
            typ = (short)2;
        else if (t instanceof com.pullenti.ner.ReferentToken) 
            typ = (short)3;
        else if (t instanceof com.pullenti.ner.MetaToken) 
            typ = (short)4;
        serializeShort(stream, typ);
        if (typ == ((short)0)) 
            return;
        t.serialize(stream);
        if (t instanceof com.pullenti.ner.MetaToken) 
            serializeTokens(stream, (((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(), t.endChar);
    }

    private static com.pullenti.ner.Token deserializeToken(com.pullenti.n2j.Stream stream, com.pullenti.ner.core.AnalysisKit kit) throws java.io.IOException {
        short typ = deserializeShort(stream);
        if (typ == ((short)0)) 
            return null;
        com.pullenti.ner.Token t = null;
        if (typ == ((short)1)) 
            t = new com.pullenti.ner.TextToken(null, kit);
        else if (typ == ((short)2)) 
            t = new com.pullenti.ner.NumberToken(null, null, (long)0, com.pullenti.ner.NumberSpellingType.DIGIT, kit);
        else if (typ == ((short)3)) 
            t = new com.pullenti.ner.ReferentToken(null, null, null, kit);
        else 
            t = new com.pullenti.ner.MetaToken(null, null, kit);
        t.deserialize(stream, kit);
        if (t instanceof com.pullenti.ner.MetaToken) {
            com.pullenti.ner.Token tt = deserializeTokens(stream, kit);
            if (tt != null) {
                (((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class))).m_BeginToken = tt;
                for(; tt != null; tt = tt.getNext()) {
                    (((com.pullenti.ner.MetaToken)com.pullenti.n2j.Utils.cast(t, com.pullenti.ner.MetaToken.class))).m_EndToken = tt;
                }
            }
        }
        return t;
    }
    public SerializerHelper() {
    }
}
