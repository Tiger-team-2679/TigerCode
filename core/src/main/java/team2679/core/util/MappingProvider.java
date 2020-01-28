package team2679.core.util;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import team2679.core.spline.Point;

public class MappingProvider {
    private static final CsvMapper MAPPER = new CsvMapper();
    private static final CsvSchema pointSchema = MAPPER.schemaFor(Point.class).withHeader();
    public static final ObjectReader pointReader = MAPPER.readerFor(Point.class).with(pointSchema).forType(Point.class);
    public static final ObjectWriter pointWriter = MAPPER.writerFor(Point.class).with(pointSchema).forType(Point.class);
}