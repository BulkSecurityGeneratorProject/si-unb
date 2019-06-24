package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Reserva.
 */
@Entity
@Table(name = "reserva")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cpf_paciente")
    private Integer cpfPaciente;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "hora_reserva")
    private ZonedDateTime horaReserva;

    @OneToMany(mappedBy = "reserva")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Remedio> remedios = new HashSet<>();

    @OneToMany(mappedBy = "reserva")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Farmacia> farmacias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCpfPaciente() {
        return cpfPaciente;
    }

    public Reserva cpfPaciente(Integer cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
        return this;
    }

    public void setCpfPaciente(Integer cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public Reserva nomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
        return this;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Reserva quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public ZonedDateTime getHoraReserva() {
        return horaReserva;
    }

    public Reserva horaReserva(ZonedDateTime horaReserva) {
        this.horaReserva = horaReserva;
        return this;
    }

    public void setHoraReserva(ZonedDateTime horaReserva) {
        this.horaReserva = horaReserva;
    }

    public Set<Remedio> getRemedios() {
        return remedios;
    }

    public Reserva remedios(Set<Remedio> remedios) {
        this.remedios = remedios;
        return this;
    }

    public Reserva addRemedio(Remedio remedio) {
        this.remedios.add(remedio);
        remedio.setReserva(this);
        return this;
    }

    public Reserva removeRemedio(Remedio remedio) {
        this.remedios.remove(remedio);
        remedio.setReserva(null);
        return this;
    }

    public void setRemedios(Set<Remedio> remedios) {
        this.remedios = remedios;
    }

    public Set<Farmacia> getFarmacias() {
        return farmacias;
    }

    public Reserva farmacias(Set<Farmacia> farmacias) {
        this.farmacias = farmacias;
        return this;
    }

    public Reserva addFarmacia(Farmacia farmacia) {
        this.farmacias.add(farmacia);
        farmacia.setReserva(this);
        return this;
    }

    public Reserva removeFarmacia(Farmacia farmacia) {
        this.farmacias.remove(farmacia);
        farmacia.setReserva(null);
        return this;
    }

    public void setFarmacias(Set<Farmacia> farmacias) {
        this.farmacias = farmacias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reserva)) {
            return false;
        }
        return id != null && id.equals(((Reserva) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reserva{" +
            "id=" + getId() +
            ", cpfPaciente=" + getCpfPaciente() +
            ", nomePaciente='" + getNomePaciente() + "'" +
            ", quantidade=" + getQuantidade() +
            ", horaReserva='" + getHoraReserva() + "'" +
            "}";
    }
}
