package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Estoque.
 */
@Entity
@Table(name = "estoque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantidade")
    private Integer quantidade;

    @OneToMany(mappedBy = "estoque")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Farmacia> farmacias = new HashSet<>();

    @OneToMany(mappedBy = "estoque")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Remedio> remedios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Estoque quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Set<Farmacia> getFarmacias() {
        return farmacias;
    }

    public Estoque farmacias(Set<Farmacia> farmacias) {
        this.farmacias = farmacias;
        return this;
    }

    public Estoque addFarmacia(Farmacia farmacia) {
        this.farmacias.add(farmacia);
        farmacia.setEstoque(this);
        return this;
    }

    public Estoque removeFarmacia(Farmacia farmacia) {
        this.farmacias.remove(farmacia);
        farmacia.setEstoque(null);
        return this;
    }

    public void setFarmacias(Set<Farmacia> farmacias) {
        this.farmacias = farmacias;
    }

    public Set<Remedio> getRemedios() {
        return remedios;
    }

    public Estoque remedios(Set<Remedio> remedios) {
        this.remedios = remedios;
        return this;
    }

    public Estoque addRemedio(Remedio remedio) {
        this.remedios.add(remedio);
        remedio.setEstoque(this);
        return this;
    }

    public Estoque removeRemedio(Remedio remedio) {
        this.remedios.remove(remedio);
        remedio.setEstoque(null);
        return this;
    }

    public void setRemedios(Set<Remedio> remedios) {
        this.remedios = remedios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estoque)) {
            return false;
        }
        return id != null && id.equals(((Estoque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Estoque{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            "}";
    }
}
