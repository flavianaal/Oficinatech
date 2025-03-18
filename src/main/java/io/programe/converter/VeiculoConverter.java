/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.converter;

import io.programe.modelo.Veiculo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Flaviana Andrade
 */
@FacesConverter(value = "veiculoconverter", forClass = Veiculo.class)
public class VeiculoConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            // Busca o cliente pelo ID armazenado
            return (Veiculo) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Veiculo) {
            Veiculo entity = (Veiculo) value;
            if (entity.getId() != null) {
                // Armazena o cliente no atributo para referência futura
                uiComponent.getAttributes().put(entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
    
}
