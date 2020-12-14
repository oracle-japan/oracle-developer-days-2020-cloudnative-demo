
package devdays.demo.mp.kohaku;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * A Kohaku entity class.
 *
 */
@Entity(name = "Kohaku")
@Table(name = "KOHAKU")
@Access(AccessType.PROPERTY)
@NamedQueries({
        @NamedQuery(name = "getKohaku",
                    query = "SELECT k FROM Kohaku k"),
        @NamedQuery(name = "getKohakuByName",
                    query = "SELECT k FROM Kohaku k WHERE k.name = :name")
})
public class Kohaku {

    private int id;

    private String name;

    private int count;

    public Kohaku() {
    }

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic(optional = false)
    @Column(nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
