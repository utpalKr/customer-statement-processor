package com.rabobank.customer.enumeration;

/**
 * This enum denotes the type of files to be extracted.
 * e.g. csv, xml etc
 */
public enum FileType {

    CSV("text/csv"),
    XML("application/xml"),
    TXT_XML("text/xml");

    private String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
