package me.kristijanbobovec.museum.model.dto.museum;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import me.kristijanbobovec.museum.model.dto.exhibit.ExhibitXMLExport;

import java.util.List;

public class MuseumXMLExportDTO {

    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private int idMuseum;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "address")
    private String adress;

    @JacksonXmlProperty(localName = "establishmentYear")
    private int establishmentYear;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "imagePath")
    private String imagePath;

    @JacksonXmlProperty(localName = "cityId")
    private int cityId;

    @JacksonXmlElementWrapper(localName = "exhibits")
    @JacksonXmlProperty(localName = "exhibit")
    private List<ExhibitXMLExport> exhibits;

    public int getIdMuseum() {
        return idMuseum;
    }

    public void setIdMuseum(int idMuseum) {
        this.idMuseum = idMuseum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(int establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public List<ExhibitXMLExport> getExhibits() {
        return exhibits;
    }

    public void setExhibits(List<ExhibitXMLExport> exhibits) {
        this.exhibits = exhibits;
    }

    public MuseumXMLExportDTO() {
    }

    public MuseumXMLExportDTO(int idMuseum, String name, String adress, int establishmentYear, String description, String imagePath, int cityId, List<ExhibitXMLExport> exhibits) {
        this.idMuseum = idMuseum;
        this.name = name;
        this.adress = adress;
        this.establishmentYear = establishmentYear;
        this.description = description;
        this.imagePath = imagePath;
        this.cityId = cityId;
        this.exhibits = exhibits;
    }

    @Override
    public String toString() {
        return "MuseumXMLExportDTO{" +
                "idMuseum=" + idMuseum +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", establishmentYear=" + establishmentYear +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", cityId=" + cityId +
                ", exhibits=" + exhibits +
                '}';
    }
}
