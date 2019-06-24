package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SaudeApp;
import com.mycompany.myapp.domain.Estoque;
import com.mycompany.myapp.repository.EstoqueRepository;
import com.mycompany.myapp.service.EstoqueService;
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
 * Integration tests for the {@Link EstoqueResource} REST controller.
 */
@SpringBootTest(classes = SaudeApp.class)
public class EstoqueResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueService estoqueService;

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

    private MockMvc restEstoqueMockMvc;

    private Estoque estoque;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstoqueResource estoqueResource = new EstoqueResource(estoqueService);
        this.restEstoqueMockMvc = MockMvcBuilders.standaloneSetup(estoqueResource)
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
    public static Estoque createEntity(EntityManager em) {
        Estoque estoque = new Estoque()
            .quantidade(DEFAULT_QUANTIDADE);
        return estoque;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estoque createUpdatedEntity(EntityManager em) {
        Estoque estoque = new Estoque()
            .quantidade(UPDATED_QUANTIDADE);
        return estoque;
    }

    @BeforeEach
    public void initTest() {
        estoque = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstoque() throws Exception {
        int databaseSizeBeforeCreate = estoqueRepository.findAll().size();

        // Create the Estoque
        restEstoqueMockMvc.perform(post("/api/estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isCreated());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeCreate + 1);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
    }

    @Test
    @Transactional
    public void createEstoqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estoqueRepository.findAll().size();

        // Create the Estoque with an existing ID
        estoque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstoqueMockMvc.perform(post("/api/estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstoques() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get all the estoqueList
        restEstoqueMockMvc.perform(get("/api/estoques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estoque.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)));
    }
    
    @Test
    @Transactional
    public void getEstoque() throws Exception {
        // Initialize the database
        estoqueRepository.saveAndFlush(estoque);

        // Get the estoque
        restEstoqueMockMvc.perform(get("/api/estoques/{id}", estoque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estoque.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingEstoque() throws Exception {
        // Get the estoque
        restEstoqueMockMvc.perform(get("/api/estoques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstoque() throws Exception {
        // Initialize the database
        estoqueService.save(estoque);

        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Update the estoque
        Estoque updatedEstoque = estoqueRepository.findById(estoque.getId()).get();
        // Disconnect from session so that the updates on updatedEstoque are not directly saved in db
        em.detach(updatedEstoque);
        updatedEstoque
            .quantidade(UPDATED_QUANTIDADE);

        restEstoqueMockMvc.perform(put("/api/estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstoque)))
            .andExpect(status().isOk());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
        Estoque testEstoque = estoqueList.get(estoqueList.size() - 1);
        assertThat(testEstoque.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingEstoque() throws Exception {
        int databaseSizeBeforeUpdate = estoqueRepository.findAll().size();

        // Create the Estoque

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstoqueMockMvc.perform(put("/api/estoques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estoque)))
            .andExpect(status().isBadRequest());

        // Validate the Estoque in the database
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstoque() throws Exception {
        // Initialize the database
        estoqueService.save(estoque);

        int databaseSizeBeforeDelete = estoqueRepository.findAll().size();

        // Delete the estoque
        restEstoqueMockMvc.perform(delete("/api/estoques/{id}", estoque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estoque> estoqueList = estoqueRepository.findAll();
        assertThat(estoqueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estoque.class);
        Estoque estoque1 = new Estoque();
        estoque1.setId(1L);
        Estoque estoque2 = new Estoque();
        estoque2.setId(estoque1.getId());
        assertThat(estoque1).isEqualTo(estoque2);
        estoque2.setId(2L);
        assertThat(estoque1).isNotEqualTo(estoque2);
        estoque1.setId(null);
        assertThat(estoque1).isNotEqualTo(estoque2);
    }
}
