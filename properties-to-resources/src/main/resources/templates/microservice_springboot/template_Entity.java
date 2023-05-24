package [=mavenproject.groupId].domain;

import java.io.Serializable;
import java.time.LocalDate;

public class [=mavenproject.entityName] implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private LocalDate date;

    private Integer code;


    public String getName() {
        return this.name;
    }

    public [=mavenproject.entityName] name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public [=mavenproject.entityName] description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public [=mavenproject.entityName] date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCode() {
        return this.code;
    }

    public [=mavenproject.entityName] code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    // prettier-ignore
    @Override
    public String toString() {
        return "[=mavenproject.entityName]{" +
            "name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", code=" + getCode() +
            "}";
    }
}
