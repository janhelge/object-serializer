package no.webtech;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateExampleDomainObject {

    public static void main(String[] a) {
        System.out.println(createExampleDomainObject());
    }

    public static ExampleDomainObject createExampleDomainObject() {
        ExampleDomainObject e = new ExampleDomainObject();
        e.attributes = new HashMap();
        e.attributes.put("Ex1", "val1");
        e.attributes.put("Ex2", "val2");
        e.created =  new Timestamp(System.currentTimeMillis());
        e.attachments = new ArrayList();
        e.attachments.add((new String("Attachment 1. ABC..XYZ")));
        e.attachments.add((new String("Attachment 2. blablbla")));
        return e;
    }
}
