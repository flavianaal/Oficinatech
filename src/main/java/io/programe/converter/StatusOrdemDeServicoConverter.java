/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.programe.converter;

import io.programe.enuns.StatusOrdemDeServico;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Flaviana Andrade
 */
@FacesConverter(value = "statusconverter", forClass = StatusOrdemDeServico.class)
public class StatusOrdemDeServicoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (StatusOrdemDeServico status : StatusOrdemDeServico.values()) {
            if (status.getStatusOrdem().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null; // Retorna null se não encontrar um status correspondente
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return ""; // Retorna string vazia se o valor for nulo
        }
        if (value instanceof StatusOrdemDeServico) {
            return ((StatusOrdemDeServico) value).getStatusOrdem(); // Retorna o nome amigável
        }
        return "";
    }
}
