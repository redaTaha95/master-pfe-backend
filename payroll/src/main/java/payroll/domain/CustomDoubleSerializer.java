package payroll.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class CustomDoubleSerializer extends JsonSerializer<Double> {
    private static final int SCALE = 2; // Number of decimal places

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        BigDecimal roundedValue = BigDecimal.valueOf(value).setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        gen.writeNumber(roundedValue);
    }
}
