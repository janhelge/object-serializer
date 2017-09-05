package no.webtech;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ExampleDomainObject implements Serializable {
    public Map<String, Object> attributes;
    public List<String> attachments;
    public Timestamp created;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (created != null) {
            sb.append("created: ").append(created.toString());
        } else {
            sb.append("created is <null>.");
        }
        if (attributes != null) {
            sb.append(", attributes: ").append(attributes.toString());
        } else {
            sb.append(", attributes is <null>. ");
        }
        if (attachments != null) {
            sb.append(", attachments: ").append(attachments.toString());
        } else {
            sb.append(", attachments is <null>.");
        }
        return sb.toString();
    }
}
