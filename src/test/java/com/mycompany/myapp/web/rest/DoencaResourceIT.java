package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SaudeApp;
import com.mycompany.myapp.domain.Doenca;
import com.mycompany.myapp.repository.DoencaRepository;
import com.mycompany.myapp.service.DoencaService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link DoencaResource} REST controller.
 */
@SpringBootTest(classes = SaudeApp.class)
public class DoencaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CID = "AAAAAAAAAA";
    private static final String UPDATED_CID = "BBBBBBBBBB";

    @Autowired
    private DoencaRepository doencaRepository;

    @Mock
    private DoencaRepository doencaRepositoryMock;

    @Mock
    private DoencaService doencaServiceMock;

    @Autowired
    private DoencaService doencaService;

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

    private MockMvc restDoencaMockMvc;

    private Doenca doenca;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoencaResource doencaResource = new DoencaResource(doencaService);
        this.restDoencaMockMvc = MockMvcBuilders.standaloneSetup(doencaResource)
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
    public static Doenca createEntity(EntityManager em) {
        Doenca doenca = new Doenca()
            .nome(DEFAULT_NOME)
            .cid(DEFAULT_CID);
        return doenca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doenca createUpdatedEntity(EntityManager em) {
        Doenca doenca = new Doenca()
            .nome(UPDATED_NOME)
            .cid(UPDATED_CID);
        return doenca;
    }

    @BeforeEach
    public void initTest() {
        doenca = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoenca() throws Exception {
        int databaseSizeBeforeCreate = doencaRepository.findAll().size();

        // Create the Doenca
        restDoencaMockMvc.perform(post("/api/doencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenca)))
            .andExpect(status().isCreated());

        // Validate the Doenca in the database
        List<Doenca> doencaList = doencaRepository.findAll();
        assertThat(doencaList).hasSize(databaseSizeBeforeCreate + 1);
        Doenca testDoenca = doencaList.get(doencaList.size() - 1);
        assertThat(testDoenca.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDoenca.getCid()).isEqualTo(DEFAULT_CID);
    }

    @Test
    @Transactional
    public void createDoencaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doencaRepository.findAll().size();

        // Create the Doenca with an existing ID
        doenca.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoencaMockMvc.perform(post("/api/doencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenca)))
            .andExpect(status().isBadRequest());

        // Validate the Doenca in the database
        List<Doenca> doencaList = doencaRepository.findAll();
        assertThat(doencaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDoencas() throws Exception {
        // Initialize the database
        doencaRepository.saveAndFlush(doenca);

        // Get all the doencaList
        restDoencaMockMvc.perform(get("/api/doencas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doenca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cid").value(hasItem(DEFAULT_CID.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDoencasWithEagerRelationshipsIsEnabled() throws Exception {
        DoencaResource doencaResource = new DoencaResource(doencaServiceMock);
        when(doencaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDoencaMockMvc = MockMvcBuilders.standaloneSetup(doencaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDoencaMockMvc.perform(get("/api/doencas?eagerload=true"))
        .andExpect(status().isOk());

        verify(doencaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDoencasWithEagerRelationshipsIsNotEnabled() throws Exception {
        DoencaResource doencaResource = new DoencaResource(doencaServiceMock);
            when(doencaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDoencaMockMvc = MockMvcBuilders.standaloneSetup(doencaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDoencaMockMvc.perform(get("/api/doencas?eagerload=true"))
        .andExpect(status().isOk());

            verify(doencaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDoenca() throws Exception {
        // Initialize the database
        doencaRepository.saveAndFlush(doenca);

        // Get the doenca
        restDoencaMockMvc.perform(get("/api/doencas/{id}", doenca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doenca.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cid").value(DEFAULT_CID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoenca() throws Exception {
        // Get the doenca
        restDoencaMockMvc.perform(get("/api/doencas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoenca() throws Exception {
        // Initialize the database
        doencaService.save(doenca);

        int databaseSizeBeforeUpdate = doencaRepository.findAll().size();

        // Update the doenca
        Doenca updatedDoenca = doencaRepository.findById(doenca.getId()).get();
        // Disconnect from session so that the updates on updatedDoenca are not directly saved in db
        em.detach(updatedDoenca);
        updatedDoenca
            .nome(UPDATED_NOME)
            .cid(UPDATED_CID);

        restDoencaMockMvc.perform(put("/api/doencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoenca)))
            .andExpect(status().isOk());

        // Validate the Doenca in the database
        List<Doenca> doencaList = doencaRepository.findAll();
        assertThat(doencaList).hasSize(databaseSizeBeforeUpdate);
        Doenca testDoenca = doencaList.get(doencaList.size() - 1);
        assertThat(testDoenca.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDoenca.getCid()).isEqualTo(UPDATED_CID);
    }

    @Test
    @Transactional
    public void updateNonExistingDoenca() throws Exception {
        int databaseSizeBeforeUpdate = doencaRepository.findAll().size();

        // Create the Doenca

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoencaMockMvc.perform(put("/api/doencas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doenca)))
            .andExpect(status().isBadRequest());

        // Validate the Doenca in the database
        List<Doenca> doencaList = doencaRepository.findAll();
        assertThat(doencaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoenca() throws Exception {
        // Initialize the database
        doencaService.save(doenca);

        int databaseSizeBeforeDelete = doencaRepository.findAll().size();

        // Delete the doenca
        restDoencaMockMvc.perform(delete("/api/doencas/{id}", doenca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doenca> doencaList = doencaRepository.findAll();
        assertThat(doencaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doenca.class);
        Doenca doenca1 = new Doenca();
        doenca1.setId(1L);
        Doenca doenca2 = new Doenca();
        doenca2.setId(doenca1.getId());
        assertThat(doenca1).isEqualTo(doenca2);
        doenca2.setId(2L);
        assertThat(doenca1).isNotEqualTo(doenca2);
        doenca1.setId(null);
        assertThat(doenca1).isNotEqualTo(doenca2);
    }
}
