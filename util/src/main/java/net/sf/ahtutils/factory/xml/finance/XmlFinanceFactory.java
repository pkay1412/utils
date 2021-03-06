package net.sf.ahtutils.factory.xml.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.model.interfaces.finance.UtilsFinance;
import net.sf.ahtutils.xml.finance.Finance;

public class XmlFinanceFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlFinanceFactory.class);
	
	public static <F extends UtilsFinance> Finance create(F ejb)
	{
		Finance xml = new Finance();
		xml.setValue(ejb.getValue());
		return xml;
	}
	
	public static <E extends Enum<E>> Finance build(E code, double value){return create(code.toString(),value);}
	public static Finance create(String code, double value)
	{
		Finance xml = new Finance();
		xml.setCode(code);
		xml.setValue(value);
		return xml;
	}
	
	public static Finance create(String code, String label)
	{
		Finance xml = new Finance();
		xml.setCode(code);
		xml.setLabel(label);
		return xml;
	}
}