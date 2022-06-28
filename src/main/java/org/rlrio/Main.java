package org.rlrio;

import static java.lang.System.lineSeparator;

import org.rlrio.converter.Xml2YamlConverter;

public class Main {
    private static final String sourceFile = "src/main/resources/sample.xml";
    private static final String destinationFile = "output.yml";
    private static final String deleteProperty = "Company.EmplProfile";

    public static void main(String[] args) {
        String source = sourceFile.replace("\\/", lineSeparator());
        Xml2YamlConverter converter = new Xml2YamlConverter();
        converter.convertXml2YamlFile(source, destinationFile, deleteProperty);
    }
}