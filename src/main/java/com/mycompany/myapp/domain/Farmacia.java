package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Farmacia.
 */
@Entity
@Table(name = "farmacia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Farmacia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "longitude")
    private Long longitude;

    @Column(name = "latitude")
    private Long latitude;

    @ManyToOne
    @JsonIgnoreProperties("farmacias")
    private Estoque estoque;

    @ManyToOne
    @JsonIgnoreProperties("farmacias")
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

    public Farmacia nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public Farmacia cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public Farmacia endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getLongitude() {
        return longitude;
    }

    public Farmacia longitude(Long longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public Farmacia latitude(Long latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public Farmacia estoque(Estoque estoque) {
        this.estoque = estoque;
        return this;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public Farmacia reserva(Reserva reserva) {
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
        if (!(o instanceof Farmacia)) {
            return false;
        }
        return id != null && id.equals(((Farmacia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Farmacia{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            "}";
    }
}
