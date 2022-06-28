package org.rlrio.converter;

import static java.lang.System.lineSeparator;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class Xml2YamlConverterTest {

    private static final String sourceFile = "sample.xml";
    private static final String destinationFile = "output.yml";
    private static final String deleteProperty = "Company.EmplProfile";
    private static final String assertOutputFile = "src/test/java/resources/test.yml";

    private final Xml2YamlConverter xml2YamlConverter = new Xml2YamlConverter();

    @Test
    void testConvertXml2YamlFile() throws IOException {
        String assertOutputPath = assertOutputFile.replace("\\/", lineSeparator());
        xml2YamlConverter.convertXml2YamlFile(sourceFile, destinationFile, deleteProperty);
        String output = Files.readString(Path.of(destinationFile));
        String assertOutput = Files.readString(Path.of(assertOutputPath));
        assertEquals(assertOutput, output);
    }
}