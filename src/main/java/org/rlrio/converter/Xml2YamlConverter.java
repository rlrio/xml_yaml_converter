package org.rlrio.converter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.rlrio.exception.Xml2YamlException;

public class Xml2YamlConverter {

    private static final String FILE_EXCEPTION_MESSAGE = "Something went wrong with opening file";
    private static final YAMLMapper YAML_MAPPER = new YAMLMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    public boolean convertXml2YamlFile(String source, String destination, String propertyToRemove) {
        try {
            Map<String, Object> stringObjectMap =
                    (removeProperty(readXmlToMap(source), propertyToRemove));
            writeYamlToFile(stringObjectMap, destination);
        } catch (Xml2YamlException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        return true;
    }

    private Map<String, Object> readXmlToMap(String xmlFilePath) throws Xml2YamlException {
        try {
            String str = "<tag>" + Files.readString(Path.of(xmlFilePath)) + "</tag>";
            return XML_MAPPER.readValue(str, Map.class);
        } catch (IOException e) {
            throw new Xml2YamlException(FILE_EXCEPTION_MESSAGE);
        }
    }

    private void writeYamlToFile(Object yaml, String destFile) throws Xml2YamlException {
        try {
            YAML_MAPPER.writeValue(new File(destFile), yaml);
        } catch (IOException e) {
            throw new Xml2YamlException(FILE_EXCEPTION_MESSAGE);
        }
    }

    private Map<String, Object> removeProperty(Map<String, Object> tree, String property) {
        String[] split = property.split("\\.");
        String child = split[split.length - 1];
        if (tree.containsKey(child)) {
            tree.remove(child);
            return tree;
        } else {
            Collection<Object> values = tree.values();
            Iterator<Object> iterator = values.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next instanceof Map) {
                    Map<String, Object> subtree = removeProperty((Map<String, Object>) next, child);
                    if (subtree.containsKey(child)) {
                        iterator.remove();
                        return subtree;
                    }
                    if (subtree.values().size() == 0) {
                        iterator.remove();
                        return tree;
                    }
                }
            }
        }
        return tree;
    }
}
