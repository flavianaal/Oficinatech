package io.programe.converter;

import io.programe.enuns.Estado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "estadoconverter", forClass = Estado.class)
public class EstadoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            // Verifica se o valor passado corresponde ao nome de algum enum
            return Estado.valueOf(value);
        } catch (IllegalArgumentException e) {
            // Caso o valor não seja válido, lança um erro ou retorna null
            return null; 
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return ""; // Retorna string vazia se o valor for nulo
        }
        if (value instanceof Estado) {
            return ((Estado) value).name(); // Retorna o nome do enum como String
        } else {
            // Valor não é do tipo Estado
            return "";
        }
    }
}
