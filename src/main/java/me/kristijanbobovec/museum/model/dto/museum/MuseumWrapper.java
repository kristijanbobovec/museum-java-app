package me.kristijanbobovec.museum.model.dto.museum;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "museums")
public class MuseumWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "museum")
    private List<MuseumXMLExportDTO> museums;

    public MuseumWrapper(List<MuseumXMLExportDTO> museums) {
        this.museums = museums;
    }

    public void setMuseums(List<MuseumXMLExportDTO> museums) {
        this.museums = museums;
    }

    public List<MuseumXMLExportDTO> getMuseums() {
        return museums;
    }

    public MuseumWrapper() {
    }
}
