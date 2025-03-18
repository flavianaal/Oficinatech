package io.programe.converter;

import io.programe.modelo.Servico;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Converter para a entidade Servico no JSF.
 * 
 * @author Flaviana Andrade
 */
@FacesConverter(value = "servicoconverter", forClass = Servico.class)
public class ServicoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            // Busca o serviço pelo ID armazenado no atributo do componente
            return (Servico) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Servico) {
            Servico entity = (Servico) value;
            if (entity.getId() != null) {
                // Armazena o serviço no atributo do componente
                uiComponent.getAttributes().put(entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}
