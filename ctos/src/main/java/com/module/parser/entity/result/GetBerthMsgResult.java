package com.module.parser.entity.result;

import com.module.parser.reflect.VueField;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CTOSRESULT")
@XmlType(name = "CTOSRESULT", propOrder = {"returnInfo", "dataTable"})
public class GetBerthMsgResult {
    @XmlElement(name = "RETURNINFO", required = true)
    private ReturnInfo returnInfo;
    @XmlElement(name = "SM003007", required = true)
    private List<DataTable> dataTable;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "SM003007")
    public static class DataTable{
        @XmlElement(name = "ID")
        @VueField(label = "id", sortable = true)
        private String id;
        @XmlElement(name = "BERTHGROUPCODE")
        @VueField(label = "berthGroupCode")
        private String berthGroupCode;
        @XmlElement(name = "BERTHNO")
        @VueField(label = "berthNo", sortable = true)
        private String berthNo;
        @XmlElement(name = "TICKET_ID")
        private String ticket_Id;
    }

}
