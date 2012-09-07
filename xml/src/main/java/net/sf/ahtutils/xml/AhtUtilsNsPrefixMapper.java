package net.sf.ahtutils.xml;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class AhtUtilsNsPrefixMapper extends NamespacePrefixMapper implements NsPrefixMapperInterface
{
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
    {
        if("http://ahtutils.aht-group.com".equals(namespaceUri) ){return "aht";}
        if("http://ahtutils.aht-group.com/status".equals(namespaceUri) ){return "s";}
        if("http://ahtutils.aht-group.com/report".equals(namespaceUri) ){return "r";}
        if("http://ahtutils.aht-group.com/access".equals(namespaceUri) ){return "acl";}
        if("http://ahtutils.aht-group.com/security".equals(namespaceUri) ){return "sec";}
        if("http://ahtutils.aht-group.com/dbseed".equals(namespaceUri) ){return "db";}
        if("http://ahtutils.aht-group.com/mail".equals(namespaceUri) ){return "m";}
        if("http://ahtutils.aht-group.com/finance".equals(namespaceUri) ){return "f";}
        
        if("http://ahtutils.aht-group.com/cloud/facebook".equals(namespaceUri) ){return "fb";}
        
        if("http://www.openfuxml.org/list".equals(namespaceUri) ){return "ofxL";}
        if("http://www.openfuxml.org".equals(namespaceUri) ){return "ofx";}
        if("http://www.openfuxml.org/chart".equals(namespaceUri) ){return "chart";}    
        
        if("http://exlp.sf.net/io".equals(namespaceUri) ){return "io";}
  
        return suggestion;
    }

    public String[] getPreDeclaredNamespaceUris()
    {
    	String[] result = new String[3];
    	result[2] = "http://www.openfuxml.org/chart";
    	result = new String[0];
        return result;
    }
}