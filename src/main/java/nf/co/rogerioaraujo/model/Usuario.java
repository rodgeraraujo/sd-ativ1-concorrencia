package nf.co.rogerioaraujo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private long id;
    private String nome;
    private boolean atualizado;
    private boolean deletado;

    public Usuario() {
    }

    public Usuario(String nome, boolean atualizado, boolean deletado) {
        this.nome = nome;
        this.atualizado = atualizado;
        this.deletado = deletado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtualizado() {
        return atualizado;
    }

    public void setAtualizado(boolean atualizado) {
        this.atualizado = atualizado;
    }

    public boolean isDeletado() {
        return deletado;
    }

    public void setDeletado(boolean deletado) {
        this.deletado = deletado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getId() == usuario.getId() &&
                isAtualizado() == usuario.isAtualizado() &&
                isDeletado() == usuario.isDeletado() &&
                getNome().equals(usuario.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), isAtualizado(), isDeletado());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", atualizado=" + atualizado +
                ", deletado=" + deletado +
                '}';
    }
}
