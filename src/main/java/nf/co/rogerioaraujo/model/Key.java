package nf.co.rogerioaraujo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Chave")
public class Key {


    @Id
    @GeneratedValue
    private long id;
    private long codigoEntidade;

    public Key(long codigoEntidade) {
        this.codigoEntidade = codigoEntidade;
    }

    public Key() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCodigoEntidade() {
        return codigoEntidade;
    }

    public void setCodigoEntidade(long codigoEntidade) {
        this.codigoEntidade = codigoEntidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return getId() == key.getId() &&
                getCodigoEntidade() == key.getCodigoEntidade();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCodigoEntidade());
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", codigoEntidade=" + codigoEntidade +
                '}';
    }
}
