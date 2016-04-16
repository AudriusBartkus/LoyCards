package com.audbar.odre.loycards.Model;

/**
 * Created by Audrius on 2016-04-09.
 */
public class RDTUrl extends BaseRecord {
    public String url;
    public int prefix;


    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Prefix:" + prefix + "   " + NFCProtocol.getProtocol(prefix) +  url);
        return buffer.toString();
    }

}