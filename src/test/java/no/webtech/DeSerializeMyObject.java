package no.webtech;

import no.webtech.objectserializeutil.DomainObjectSerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class DeSerializeMyObject {

    private static Logger logger = LoggerFactory.getLogger(DeSerializeMyObject.class);
    public static void main(String[] a) {
        ExampleDomainObject e = CreateExampleDomainObject.createExampleDomainObject();
        DomainObjectSerializeUtil.dumpToFile(e,"uniqfilenamebase","comment");

        String fileName = DomainObjectSerializeUtil.serializedObjectFileName("uniqfilenamebase");
        Serializable serializable = DomainObjectSerializeUtil.loadFromFile(fileName);

        System.out.println(serializable.toString());
    }


}
