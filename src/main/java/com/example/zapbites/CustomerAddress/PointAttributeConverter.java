package com.example.zapbites.CustomerAddress;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.data.geo.Point;

@Converter(autoApply = true)
public class PointAttributeConverter implements AttributeConverter<Point, String> {

    @Override
    public String convertToDatabaseColumn(Point point) {
        if (point == null) {
            return null;
        }

        return point.getX() + "," + point.getY();
    }

    @Override
    public Point convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        // Remove parentheses and split on comma
        value = value.replace("(", "").replace(")", "");
        String[] coordinates = value.split(",");
        if (coordinates.length != 2) {
            // Handle invalid format
            return null;
        }
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);

        return new Point(x, y);
    }

}