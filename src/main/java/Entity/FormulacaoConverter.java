package Entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FormulacaoConverter implements AttributeConverter<Formulacao, String> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Formulacao formulacao) {
        if (formulacao == null) return null;
        try {
            return MAPPER.writeValueAsString(formulacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter Formulacao para JSON", e);
        }
    }

    @Override
    public Formulacao convertToEntityAttribute(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            return MAPPER.readValue(json, Formulacao.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter JSON para Formulacao", e);
        }
    }
}