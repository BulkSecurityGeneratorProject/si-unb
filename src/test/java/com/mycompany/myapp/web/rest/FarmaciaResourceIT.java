package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SaudeApp;
import com.mycompany.myapp.domain.Farmacia;
import com.mycompany.myapp.repository.FarmaciaRepository;
import com.mycompany.myapp.service.FarmaciaService;
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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FarmaciaResource} REST controller.
 */
@SpringBootTest(classes = SaudeApp.class)
public class FarmaciaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final Long DEFAULT_LONGITUDE = 1L;
    private static final Long UPDATED_LONGITUDE = 2L;

    private static final Long DEFAULT_LATITUDE = 1L;
    private static final Long UPDATED_LATITUDE = 2L;

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Autowired
    private FarmaciaService farmaciaService;

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

    private MockMvc restFarmaciaMockMvc;

    private Farmacia farmacia;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FarmaciaResource farmaciaResource = new FarmaciaResource(farmaciaService);
        this.restFarmaciaMockMvc = MockMvcBuilders.standaloneSetup(farmaciaResource)
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
    public static Farmacia createEntity(EntityManager em) {
        Farmacia farmacia = new Farmacia()
            .nome(DEFAULT_NOME)
            .cidade(DEFAULT_CIDADE)
            .endereco(DEFAULT_ENDERECO)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE);
        return farmacia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farmacia createUpdatedEntity(EntityManager em) {
        Farmacia farmacia = new Farmacia()
            .nome(UPDATED_NOME)
            .cidade(UPDATED_CIDADE)
            .endereco(UPDATED_ENDERECO)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);
        return farmacia;
    }

    @BeforeEach
    public void initTest() {
        farmacia = createEntity(em);
    }

    @Test
    @Transactional
    public void createFarmacia() throws Exception {
        int databaseSizeBeforeCreate = farmaciaRepository.findAll().size();

        // Create the Farmacia
        restFarmaciaMockMvc.perform(post("/api/farmacias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacia)))
            .andExpect(status().isCreated());

        // Validate the Farmacia in the database
        List<Farmacia> farmaciaList = farmaciaRepository.findAll();
        assertThat(farmaciaList).hasSize(databaseSizeBeforeCreate + 1);
        Farmacia testFarmacia = farmaciaList.get(farmaciaList.size() - 1);
        assertThat(testFarmacia.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFarmacia.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testFarmacia.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testFarmacia.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testFarmacia.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
    }

    @Test
    @Transactional
    public void createFarmaciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = farmaciaRepository.findAll().size();

        // Create the Farmacia with an existing ID
        farmacia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmaciaMockMvc.perform(post("/api/farmacias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacia)))
            .andExpect(status().isBadRequest());

        // Validate the Farmacia in the database
        List<Farmacia> farmaciaList = farmaciaRepository.findAll();
        assertThat(farmaciaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFarmacias() throws Exception {
        // Initialize the database
        farmaciaRepository.saveAndFlush(farmacia);

        // Get all the farmaciaList
        restFarmaciaMockMvc.perform(get("/api/farmacias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmacia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())));
    }
    
    @Test
    @Transactional
    public void getFarmacia() throws Exception {
        // Initialize the database
        farmaciaRepository.saveAndFlush(farmacia);

        // Get the farmacia
        restFarmaciaMockMvc.perform(get("/api/farmacias/{id}", farmacia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(farmacia.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFarmacia() throws Exception {
        // Get the farmacia
        restFarmaciaMockMvc.perform(get("/api/farmacias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFarmacia() throws Exception {
        // Initialize the database
        farmaciaService.save(farmacia);

        int databaseSizeBeforeUpdate = farmaciaRepository.findAll().size();

        // Update the farmacia
        Farmacia updatedFarmacia = farmaciaRepository.findById(farmacia.getId()).get();
        // Disconnect from session so that the updates on updatedFarmacia are not directly saved in db
        em.detach(updatedFarmacia);
        updatedFarmacia
            .nome(UPDATED_NOME)
            .cidade(UPDATED_CIDADE)
            .endereco(UPDATED_ENDERECO)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE);

        restFarmaciaMockMvc.perform(put("/api/farmacias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFarmacia)))
            .andExpect(status().isOk());

        // Validate the Farmacia in the database
        List<Farmacia> farmaciaList = farmaciaRepository.findAll();
        assertThat(farmaciaList).hasSize(databaseSizeBeforeUpdate);
        Farmacia testFarmacia = farmaciaList.get(farmaciaList.size() - 1);
        assertThat(testFarmacia.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFarmacia.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testFarmacia.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testFarmacia.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testFarmacia.getLatitude()).isEqualTo(UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingFarmacia() throws Exception {
        int databaseSizeBeforeUpdate = farmaciaRepository.findAll().size();

        // Create the Farmacia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmaciaMockMvc.perform(put("/api/farmacias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farmacia)))
            .andExpect(status().isBadRequest());

        // Validate the Farmacia in the database
        List<Farmacia> farmaciaList = farmaciaRepository.findAll();
        assertThat(farmaciaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFarmacia() throws Exception {
        // Initialize the database
        farmaciaService.save(farmacia);

        int databaseSizeBeforeDelete = farmaciaRepository.findAll().size();

        // Delete the farmacia
        restFarmaciaMockMvc.perform(delete("/api/farmacias/{id}", farmacia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Farmacia> farmaciaList = farmaciaRepository.findAll();
        assertThat(farmaciaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Farmacia.class);
        Farmacia farmacia1 = new Farmacia();
        farmacia1.setId(1L);
        Farmacia farmacia2 = new Farmacia();
        farmacia2.setId(farmacia1.getId());
        assertThat(farmacia1).isEqualTo(farmacia2);
        farmacia2.setId(2L);
        assertThat(farmacia1).isNotEqualTo(farmacia2);
        farmacia1.setId(null);
        assertThat(farmacia1).isNotEqualTo(farmacia2);
    }
}
