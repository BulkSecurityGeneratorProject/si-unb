package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Remedio.
 */
@Entity
@Table(name = "remedio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Remedio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "laboratorio")
    private String laboratorio;

    @ManyToMany(mappedBy = "estoques")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Doenca> doencas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("remedios")
    private Estoque estoque;

    @ManyToOne
    @JsonIgnoreProperties("remedios")
    private Reserva reserva;

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

    public Remedio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public Remedio laboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
        return this;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Set<Doenca> getDoencas() {
        return doencas;
    }

    public Remedio doencas(Set<Doenca> doencas) {
        this.doencas = doencas;
        return this;
    }

    public Remedio addDoenca(Doenca doenca) {
        this.doencas.add(doenca);
        doenca.getEstoques().add(this);
        return this;
    }

    public Remedio removeDoenca(Doenca doenca) {
        this.doencas.remove(doenca);
        doenca.getEstoques().remove(this);
        return this;
    }

    public void setDoencas(Set<Doenca> doencas) {
        this.doencas = doencas;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public Remedio estoque(Estoque estoque) {
        this.estoque = estoque;
        return this;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public Remedio reserva(Reserva reserva) {
        this.reserva = reserva;
        return this;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remedio)) {
            return false;
        }
        return id != null && id.equals(((Remedio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Remedio{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", laboratorio='" + getLaboratorio() + "'" +
            "}";
    }
}
