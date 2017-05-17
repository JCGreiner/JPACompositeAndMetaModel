package nl.example.application.business.entity.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ChangedAttribute {
    String className;
    String attributeName;
    String oldValue;
    String newValue;

    public static ChangedAttribute of(String className, String attributeName, String oldValue, String newValue) {
        ChangedAttribute result = new ChangedAttribute();
        result.attributeName = attributeName;
        result.className = className;
        result.oldValue = oldValue;
        result.newValue = newValue;
        return result;
    }
}
