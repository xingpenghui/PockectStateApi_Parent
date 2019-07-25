package com.feri.pay.pockectstatepay_api.wechatpay.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/8/9 23:05
 */
public class XmlUtil {
        /**
         *   解析xml格式为Map集合
         */
        public static Map doXMLParse(String strxml)
        {
            strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
            if(null == strxml || "".equals(strxml)) {
                return null;
            }
            Map m = new HashMap();
            InputStream in = null;
            try {
                in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(in);
                Element root = doc.getRootElement();
                List list = root.getChildren();
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    Element e = (Element) it.next();
                    String k = e.getName();
                    String v = "";
                    List children = e.getChildren();
                    if(children.isEmpty()) {
                        v = e.getTextNormalize();
                    } else {
                        v = getChildrenText(children);
                    }
                    m.put(k, v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return m;
        }
    /**
     * 添加子节点*/
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }

}
