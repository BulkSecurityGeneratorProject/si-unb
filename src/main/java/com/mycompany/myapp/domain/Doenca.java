package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Doenca.
 */
@Entity
@Table(name = "doenca")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Doenca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cid")
    private String cid;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "doenca_estoque",
               joinColumns = @JoinColumn(name = "doenca_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "estoque_id", referencedColumnName = "id"))
    private Set<Remedio> estoques = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Doenca nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCid() {
        return cid;
    }

    public Doenca cid(String cid) {
        this.cid = cid;
        return this;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Set<Remedio> getEstoques() {
        return estoques;
    }

    public Doenca estoques(Set<Remedio> remedios) {
        this.estoques = remedios;
        return this;
    }

    public Doenca addEstoque(Remedio remedio) {
        this.estoques.add(remedio);
        remedio.getDoencas().add(this);
        return this;
    }

    public Doenca removeEstoque(Remedio remedio) {
        this.estoques.remove(remedio);
        remedio.getDoencas().remove(this);
        return this;
    }

    public void setEstoques(Set<Remedio> remedios) {
        this.estoques = remedios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doenca)) {
            return false;
        }
        return id != null && id.equals(((Doenca) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Doenca{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cid='" + getCid() + "'" +
            "}";
    }
}
