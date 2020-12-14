package devdays.demo.mp.trendword;

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
 * A Kimetsu entity class. A Kimetsu is represented as a triple of an
 * ID, a name and a type.
 *
 */
@Entity(name = "TrendWord")
@Table(name = "TRENDWORD")
@Access(AccessType.PROPERTY)
@NamedQueries({
        @NamedQuery(name = "getTrendWord",
                    query = "SELECT t FROM TrendWord t"),
        @NamedQuery(name = "getTrendWordByName",
                    query = "SELECT t FROM TrendWord t WHERE t.name = :name")
})
public class TrendWord {

    private int id;

    private String name;

    private int rank;

    public TrendWord() {
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

    @Column(nullable = false)
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
