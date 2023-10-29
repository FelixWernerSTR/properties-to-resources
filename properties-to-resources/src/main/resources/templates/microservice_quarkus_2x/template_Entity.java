package [=mavenproject.groupId].domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.Date;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "[=mavenproject.entityName?lower_case]")
@RegisterForReflection
public class [=mavenproject.entityName] extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    public Long id;

    @Lob
    @Column(name = "description")
    public String description;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    public String name;

    @Size(max = 50)
    @Column(name = "vorname", length = 50)
    public String vorname;

    @Column(name = "geburts_datum")
    public Date geburtsDatum;

    @Size(max = 50)
    @Column(name = "strasse", length = 50)
    public String strasse;

    @Min(value = 0)
    @Max(value = 100000)
    @Column(name = "haus_nummer")
    public Integer hausNummer;

    @Min(value = 0)
    @Max(value = 100000)
    @Column(name = "plz")
    public Integer plz;

    @Size(max = 50)
    @Column(name = "land", length = 50)
    public String land;

//    @ManyToOne
//    @JoinColumn(name = "[=mavenproject.entityName?lower_case]_type_id")
//    @JsonbTransient
//    public [=mavenproject.entityName]Type [=mavenproject.entityName?lower_case]Type;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof [=mavenproject.entityName])) {
            return false;
        }
        return id != null && id.equals((([=mavenproject.entityName]) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "[=mavenproject.entityName]{" +
            "id=" +
            id +
            ", description='" +
            description +
            "'" +
            ", name='" +
            name +
            "'" +
            ", vorname='" +
            vorname +
            "'" +
            ", geburtsDatum='" +
            geburtsDatum +
            "'" +
            ", strasse='" +
            strasse +
            "'" +
            ", hausNummer=" +
            hausNummer +
            ", plz=" +
            plz +
            ", land='" +
            land +
            "'" +
            "}"
        );
    }

    public [=mavenproject.entityName] update() {
        return update(this);
    }

    public [=mavenproject.entityName] persistOrUpdate() {
        return persistOrUpdate(this);
    }

    public static [=mavenproject.entityName] update([=mavenproject.entityName] [=mavenproject.entityName?lower_case]) {
        if ([=mavenproject.entityName?lower_case] == null) {
            throw new IllegalArgumentException("[=mavenproject.entityName?lower_case] can't be null");
        }
        var entity = [=mavenproject.entityName].<[=mavenproject.entityName]>findById([=mavenproject.entityName?lower_case].id);
        if (entity != null) {
            entity.description = [=mavenproject.entityName?lower_case].description;
            entity.name = [=mavenproject.entityName?lower_case].name;
            entity.vorname = [=mavenproject.entityName?lower_case].vorname;
            entity.geburtsDatum = [=mavenproject.entityName?lower_case].geburtsDatum;
            entity.strasse = [=mavenproject.entityName?lower_case].strasse;
            entity.hausNummer = [=mavenproject.entityName?lower_case].hausNummer;
            entity.plz = [=mavenproject.entityName?lower_case].plz;
            entity.land = [=mavenproject.entityName?lower_case].land;
            //entity.[=mavenproject.entityName?lower_case]Type = [=mavenproject.entityName?lower_case].[=mavenproject.entityName?lower_case]Type;
        }
        return entity;
    }

    public static [=mavenproject.entityName] persistOrUpdate([=mavenproject.entityName] [=mavenproject.entityName?lower_case]) {
        if ([=mavenproject.entityName?lower_case] == null) {
            throw new IllegalArgumentException("[=mavenproject.entityName?lower_case] can't be null");
        }
        if ([=mavenproject.entityName?lower_case].id == null) {
            persist([=mavenproject.entityName?lower_case]);
            return [=mavenproject.entityName?lower_case];
        } else {
            return update([=mavenproject.entityName?lower_case]);
        }
    }
}
