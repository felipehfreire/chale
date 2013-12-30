package br.com.chale.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="bigDecimalConverter")
public class BigDecimalConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		BigDecimal decimal = null;;
		try {
			Locale.setDefault(new Locale("nl", "NL"));
			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
			df.setParseBigDecimal(true);
			decimal = (BigDecimal) df.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return decimal;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		 DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale ("pt", "BR"));
		 DecimalFormat df1 = new DecimalFormat("#,##0.00", dfs);
		 return df1.format (value);
	}

}
