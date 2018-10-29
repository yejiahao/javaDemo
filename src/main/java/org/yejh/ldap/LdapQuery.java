package org.yejh.ldap;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.*;

public class LdapQuery {
    private static DirContext context;

    private static String host = "1.2.3.4";
    private static int port = 389;
    private static String bindDN = "cn=a b";
    private static String bindPassword = "password";
    private static String baseDN = "dc=ldap,dc=yejh,dc=cn";

    public static void main(String[] args) throws NamingException {
        LdapQuery.setAuthConnection(host, port, bindDN, bindPassword);
        List<Map<String, String>> result = LdapQuery.executeQuery(null, null);
        System.out.println(result);
    }

    private static void setAuthConnection(String host, int port, String username, String password) throws NamingException {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");// 设置连接LDAP的实现工厂
        env.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);// 指定LDAP服务器的主机名和端口号
        env.put(Context.SECURITY_AUTHENTICATION, "simple");// 给环境提供认证方法,有SIMPLE、SSL/TLS和SASL
        env.put(Context.SECURITY_PRINCIPAL, username);// 指定进入的目录识别名DN
        env.put(Context.SECURITY_CREDENTIALS, password);// 进入的目录密码

        context = new InitialDirContext(env);
        System.out.println("init LDAP connection succeed! url: ldap://" + host + ":" + port);
    }

    public static List<Map<String, String>> executeQuery(String filter, String[] returnAttrs) throws NamingException {
        NamingEnumeration<Binding> bindings = context.listBindings("ou=Internal,ou=People");// 列举内部人员
        while (bindings.hasMore()) {
            Binding bd = bindings.next();
            System.out.println(bd.getName() + ": " + bd.getObject());
        }

        // 根据结点的DN来查找它的所有属性, 然后再从属性中得到所有的值,注意一个属性可以有多个值
        for (NamingEnumeration ae = context.getAttributes("uid=00012047,ou=Internal,ou=People").getAll(); ae.hasMore(); ) {
            // 获取一个属性
            Attribute attr = (Attribute) ae.next();
            for (NamingEnumeration ve = attr.getAll(); ve.hasMore(); ) {
                System.out.println(String.format("Attribute = %s, Value = %s", attr.getID(), ve.next()));
            }
        }

        SearchControls cons = new SearchControls();
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
        cons.setReturningAttributes(returnAttrs);

        List<Map<String, String>> ldapList = new ArrayList<>();
        try {
            NamingEnumeration<SearchResult> list = context.search(baseDN, filter, cons);
            while (list != null && list.hasMoreElements()) {
                SearchResult element = list.nextElement();
                Map<String, String> item = new HashMap<>();
                item.put("dn", element.getNameInNamespace());

                Attributes attrs = element.getAttributes();
                if (attrs != null) {
                    for (NamingEnumeration<? extends Attribute> ne = attrs.getAll(); ne.hasMoreElements(); ) {
                        Attribute attr = ne.next();
                        String attrId = attr.getID();

                        for (Enumeration<?> e = attr.getAll(); e.hasMoreElements(); ) {
                            String sAttr = (String) e.nextElement();
                            item.put(attrId, sAttr);
                        }
                    }
                    System.out.println("dn: " + item.get("dn") + " has been added to result");
                    ldapList.add(item);
                } else {
                    System.out.println("dn: " + item.get("dn") + " has no attr, skipped");
                }
            }
            System.out.println("search LDAP succeed: " + ldapList.size() + " item(s)");
        } finally {
            if (context != null) {
                context.close();
            }
        }
        return ldapList;
    }

}

