package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SaudeApp;
import com.mycompany.myapp.domain.Reserva;
import com.mycompany.myapp.repository.ReservaRepository;
import com.mycompany.myapp.service.ReservaService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ReservaResource} REST controller.
 */
@SpringBootTest(classes = SaudeApp.class)
public class ReservaResourceIT {

    private static final Integer DEFAULT_CPF_PACIENTE = 1;
    private static final Integer UPDATED_CPF_PACIENTE = 2;

    private static final String DEFAULT_NOME_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PACIENTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final ZonedDateTime DEFAULT_HORA_RESERVA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_RESERVA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReservaMockMvc;

    private Reserva reserva;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReservaResource reservaResource = new ReservaResource(reservaService);
        this.restReservaMockMvc = MockMvcBuilders.standaloneSetup(reservaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reserva createEntity(EntityManager em) {
        Reserva reserva = new Reserva()
            .cpfPaciente(DEFAULT_CPF_PACIENTE)
            .nomePaciente(DEFAULT_NOME_PACIENTE)
            .quantidade(DEFAULT_QUANTIDADE)
            .horaReserva(DEFAULT_HORA_RESERVA);
        return reserva;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reserva createUpdatedEntity(EntityManager em) {
        Reserva reserva = new Reserva()
            .cpfPaciente(UPDATED_CPF_PACIENTE)
            .nomePaciente(UPDATED_NOME_PACIENTE)
            .quantidade(UPDATED_QUANTIDADE)
            .horaReserva(UPDATED_HORA_RESERVA);
        return reserva;
    }

    @BeforeEach
    public void initTest() {
        reserva = createEntity(em);
    }

    @Test
    @Transactional
    public void createReserva() throws Exception {
        int databaseSizeBeforeCreate = reservaRepository.findAll().size();

        // Create the Reserva
        restReservaMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isCreated());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeCreate + 1);
        Reserva testReserva = reservaList.get(reservaList.size() - 1);
        assertThat(testReserva.getCpfPaciente()).isEqualTo(DEFAULT_CPF_PACIENTE);
        assertThat(testReserva.getNomePaciente()).isEqualTo(DEFAULT_NOME_PACIENTE);
        assertThat(testReserva.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testReserva.getHoraReserva()).isEqualTo(DEFAULT_HORA_RESERVA);
    }

    @Test
    @Transactional
    public void createReservaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservaRepository.findAll().size();

        // Create the Reserva with an existing ID
        reserva.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservaMockMvc.perform(post("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isBadRequest());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReservas() throws Exception {
        // Initialize the database
        reservaRepository.saveAndFlush(reserva);

        // Get all the reservaList
        restReservaMockMvc.perform(get("/api/reservas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reserva.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpfPaciente").value(hasItem(DEFAULT_CPF_PACIENTE)))
            .andExpect(jsonPath("$.[*].nomePaciente").value(hasItem(DEFAULT_NOME_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].horaReserva").value(hasItem(sameInstant(DEFAULT_HORA_RESERVA))));
    }
    
    @Test
    @Transactional
    public void getReserva() throws Exception {
        // Initialize the database
        reservaRepository.saveAndFlush(reserva);

        // Get the reserva
        restReservaMockMvc.perform(get("/api/reservas/{id}", reserva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reserva.getId().intValue()))
            .andExpect(jsonPath("$.cpfPaciente").value(DEFAULT_CPF_PACIENTE))
            .andExpect(jsonPath("$.nomePaciente").value(DEFAULT_NOME_PACIENTE.toString()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.horaReserva").value(sameInstant(DEFAULT_HORA_RESERVA)));
    }

    @Test
    @Transactional
    public void getNonExistingReserva() throws Exception {
        // Get the reserva
        restReservaMockMvc.perform(get("/api/reservas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReserva() throws Exception {
        // Initialize the database
        reservaService.save(reserva);

        int databaseSizeBeforeUpdate = reservaRepository.findAll().size();

        // Update the reserva
        Reserva updatedReserva = reservaRepository.findById(reserva.getId()).get();
        // Disconnect from session so that the updates on updatedReserva are not directly saved in db
        em.detach(updatedReserva);
        updatedReserva
            .cpfPaciente(UPDATED_CPF_PACIENTE)
            .nomePaciente(UPDATED_NOME_PACIENTE)
            .quantidade(UPDATED_QUANTIDADE)
            .horaReserva(UPDATED_HORA_RESERVA);

        restReservaMockMvc.perform(put("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReserva)))
            .andExpect(status().isOk());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeUpdate);
        Reserva testReserva = reservaList.get(reservaList.size() - 1);
        assertThat(testReserva.getCpfPaciente()).isEqualTo(UPDATED_CPF_PACIENTE);
        assertThat(testReserva.getNomePaciente()).isEqualTo(UPDATED_NOME_PACIENTE);
        assertThat(testReserva.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testReserva.getHoraReserva()).isEqualTo(UPDATED_HORA_RESERVA);
    }

    @Test
    @Transactional
    public void updateNonExistingReserva() throws Exception {
        int databaseSizeBeforeUpdate = reservaRepository.findAll().size();

        // Create the Reserva

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservaMockMvc.perform(put("/api/reservas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reserva)))
            .andExpect(status().isBadRequest());

        // Validate the Reserva in the database
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReserva() throws Exception {
        // Initialize the database
        reservaService.save(reserva);

        int databaseSizeBeforeDelete = reservaRepository.findAll().size();

        // Delete the reserva
        restReservaMockMvc.perform(delete("/api/reservas/{id}", reserva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reserva> reservaList = reservaRepository.findAll();
        assertThat(reservaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reserva.class);
        Reserva reserva1 = new Reserva();
        reserva1.setId(1L);
        Reserva reserva2 = new Reserva();
        reserva2.setId(reserva1.getId());
        assertThat(reserva1).isEqualTo(reserva2);
        reserva2.setId(2L);
        assertThat(reserva1).isNotEqualTo(reserva2);
        reserva1.setId(null);
        assertThat(reserva1).isNotEqualTo(reserva2);
    }
}
