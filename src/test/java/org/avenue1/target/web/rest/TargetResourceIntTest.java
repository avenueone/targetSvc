package org.avenue1.target.web.rest;

import org.avenue1.target.TargetSvcApp;

import org.avenue1.target.domain.Target;
import org.avenue1.target.domain.enumeration.InstrumentTypeEnum;
import org.avenue1.target.repository.TargetRepository;
import org.avenue1.target.service.TargetService;
import org.avenue1.target.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.avenue1.target.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.avenue1.target.domain.enumeration.TargetTypeEnum;
/**
 * Test class for the TargetResource REST controller.
 *
 * @see TargetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TargetSvcApp.class)
public class TargetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final TargetTypeEnum DEFAULT_TARGET_TYPE = TargetTypeEnum.STORE;
    private static final TargetTypeEnum UPDATED_TARGET_TYPE = TargetTypeEnum.STOREGROUP;

    private static final String DEFAULT_PARENT = "AAAAAAAAAA";
    private static final String UPDATED_PARENT = "BBBBBBBBBB";

    private static final InstrumentTypeEnum DEFAULT_INSTRUMENT_TYPE = InstrumentTypeEnum.FLYER;
    private static final InstrumentTypeEnum UPDATED_INSTRUMENT_TYPE = InstrumentTypeEnum.WEB;

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private TargetService targetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTargetMockMvc;

    private Target target;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TargetResource targetResource = new TargetResource(targetService);
        this.restTargetMockMvc = MockMvcBuilders.standaloneSetup(targetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Target createEntity() {
        Target target = new Target()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .targetType(DEFAULT_TARGET_TYPE)
            .parent(DEFAULT_PARENT)
            .instrumentType(DEFAULT_INSTRUMENT_TYPE);
        return target;
    }

    @Before
    public void initTest() {
        targetRepository.deleteAll();
        target = createEntity();
    }

    @Test
    public void getInvalidTargetType() throws Exception {


        restTargetMockMvc.perform(get("/api/targetsByType/INVALID_TYPE")
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isInternalServerError());


    }

    @Test
    public void createTarget() throws Exception {
        int databaseSizeBeforeCreate = targetRepository.findAll().size();

        // Create the Target
        restTargetMockMvc.perform(post("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isCreated());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeCreate + 1);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTarget.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTarget.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testTarget.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTarget.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTarget.getTargetType()).isEqualTo(DEFAULT_TARGET_TYPE);
        assertThat(testTarget.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testTarget.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
    }

    @Test
    public void createTargetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = targetRepository.findAll().size();

        // Create the Target with an existing ID
        target.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTargetMockMvc.perform(post("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetRepository.findAll().size();
        // set the field null
        target.setName(null);

        // Create the Target, which fails.

        restTargetMockMvc.perform(post("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isBadRequest());

        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTargetTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetRepository.findAll().size();
        // set the field null
        target.setTargetType(null);

        // Create the Target, which fails.

        restTargetMockMvc.perform(post("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isBadRequest());

        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkInstrumentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetRepository.findAll().size();
        // set the field null
        target.setInstrumentType(null);

        // Create the Target, which fails.

        restTargetMockMvc.perform(post("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isBadRequest());

        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTargets() throws Exception {
        // Initialize the database
        targetRepository.save(target);

        // Get all the targetList
        restTargetMockMvc.perform(get("/api/targets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(target.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].targetType").value(hasItem(DEFAULT_TARGET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.toString())))
            .andExpect(jsonPath("$.[*].instrumentType").value(hasItem(DEFAULT_INSTRUMENT_TYPE.toString())));
    }
    
    @Test
    public void getTarget() throws Exception {
        // Initialize the database
        targetRepository.save(target);

        // Get the target
        restTargetMockMvc.perform(get("/api/targets/{id}", target.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(target.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.targetType").value(DEFAULT_TARGET_TYPE.toString()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.toString()))
            .andExpect(jsonPath("$.instrumentType").value(DEFAULT_INSTRUMENT_TYPE.toString()));
    }

    @Test
    public void getNonExistingTarget() throws Exception {
        // Get the target
        restTargetMockMvc.perform(get("/api/targets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTarget() throws Exception {
        // Initialize the database
        targetService.save(target);

        int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Update the target
        Target updatedTarget = targetRepository.findById(target.getId()).get();
        updatedTarget
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .targetType(UPDATED_TARGET_TYPE)
            .parent(UPDATED_PARENT)
            .instrumentType(UPDATED_INSTRUMENT_TYPE);

        restTargetMockMvc.perform(put("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarget)))
            .andExpect(status().isOk());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTarget.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTarget.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testTarget.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTarget.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTarget.getTargetType()).isEqualTo(UPDATED_TARGET_TYPE);
        assertThat(testTarget.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testTarget.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
    }

    @Test
    public void updateNonExistingTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Create the Target

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetMockMvc.perform(put("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTarget() throws Exception {
        // Initialize the database
        targetService.save(target);

        int databaseSizeBeforeDelete = targetRepository.findAll().size();

        // Get the target
        restTargetMockMvc.perform(delete("/api/targets/{id}", target.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Target.class);
        Target target1 = new Target();
        target1.setId("id1");
        Target target2 = new Target();
        target2.setId(target1.getId());
        assertThat(target1).isEqualTo(target2);
        target2.setId("id2");
        assertThat(target1).isNotEqualTo(target2);
        target1.setId(null);
        assertThat(target1).isNotEqualTo(target2);
    }
}
