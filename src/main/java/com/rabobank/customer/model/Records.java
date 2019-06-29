package com.rabobank.customer.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * JaxB to Pojo conversion.
 */
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class Records {

    public Records() {
    }

    public Records(List<Record> records) {
        super();
        this.records = records;
    }

    @XmlElement(name = "record")
    private List<Record> records;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecord(List<Record> records) {
        this.records = records;
    }

}
