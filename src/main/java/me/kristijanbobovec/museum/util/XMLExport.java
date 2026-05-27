package me.kristijanbobovec.museum.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.stage.FileChooser;
import me.kristijanbobovec.museum.Main;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.dto.museum.MuseumWrapper;
import me.kristijanbobovec.museum.repository.MuseumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class XMLExport {
    private static final Logger log = LoggerFactory.getLogger(XMLExport.class);

    private XMLExport() {}

    public static boolean exportMuseums() throws SQLException, IOException{
        MuseumWrapper museums = MuseumRepository.getAllMuseumsForXMLExport();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        fileChooser.setInitialFileName("museums.xml");

        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
        if (file != null) {
            ObjectMapper mapper = new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(file, museums);
            log.info(Messages.XML.EXPORT_SUCCESSFUL.formatted("Museums", UserSession.INSTANCE.getSession().username()));
            return true;
        }
        return false;
    }
}
