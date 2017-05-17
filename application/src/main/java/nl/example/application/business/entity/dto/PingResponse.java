package nl.example.application.business.entity.dto;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import nl.example.application.business.control.Settings;

@Data
@XmlRootElement(name = "ping")
@XmlAccessorType(XmlAccessType.FIELD)
public class PingResponse {

    String application;
    String applicationVersion;
    LocalDateTime pingedAt;

    public PingResponse() {
        this.application = Settings.getArtifactId();
        this.applicationVersion = Settings.getVersion();
        this.pingedAt = LocalDateTime.now();
    }
}
