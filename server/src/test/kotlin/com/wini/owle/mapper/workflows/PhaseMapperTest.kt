package com.wini.owle.mapper.workflows

import com.wini.owle.dto.DtoTestInstanceFactory
import com.wini.owle.dto.PhaseDto
import com.wini.owle.dto.WorkflowDto
import com.wini.owle.entity.Phases
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.entity.workflows.WorkflowDao
import org.jetbrains.exposed.dao.id.EntityID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class PhasesMapperTest {
//
//    private lateinit var workflowMapper: WorkflowsMapper
//    private lateinit var phasesMapper: PhasesMapper
//    private lateinit var workflowDto: WorkflowDto
//    private lateinit var workflowDao: WorkflowDao
//
//    @BeforeEach
//    fun setUp() {
//        workflowMapper = mock(WorkflowsMapper::class.java)
//        phasesMapper = PhasesMapper(workflowMapper)
//        //workflowDto = mock(WorkflowDto::class.java)
//        workflowDao = mock(WorkflowDao::class.java)
//
//        workflowDto = DtoTestInstanceFactory.workflowDto()
//        `when`(workflowMapper.toEntity(any())).thenReturn(workflowDao)
//    }
//
//    @Test
//    fun `should map PhaseDto to PhaseDao when id is 0`() {
//        val phaseDto = PhaseDto(
//            id = 0L,
//            workflow = workflowDto,
//            parentPhase = null,
//            name = "Phase 1",
//            description = "Description 1",
//            displayOrder = 1
//        )
//
//        val phaseDao = mock(PhaseDao::class.java)
//        `when`(PhaseDao.new(any())).thenReturn(phaseDao)
//        `when`(phaseDao.workflow).thenReturn(workflowDao)
//        `when`(phaseDao.parentPhase).thenReturn(null)
//        `when`(phaseDao.name).thenReturn("Phase 1")
//        `when`(phaseDao.description).thenReturn("Description 1")
//        `when`(phaseDao.displayOrder).thenReturn(1)
//
//        val result = phasesMapper.toEntity(phaseDto)
//
//        assertEquals("Phase 1", result.name)
//        assertEquals("Description 1", result.description)
//        assertEquals(1, result.displayOrder)
//        verify(workflowMapper).toEntity(workflowDto)
//    }
//
//    @Test
//    fun `should throw exception when PhaseDao with given id is not found`() {
//        val phaseDto = mock(PhaseDto::class.java)
//        `when`(phaseDto.id).thenReturn(99L)
//        `when`(PhaseDao.findById(99L)).thenReturn(null)
//
//        assertThrows<IllegalStateException> {
//            phasesMapper.toEntity(phaseDto)
//        }
//    }
//
//    @Test
//    fun `should map PhaseDao to PhaseDto`() {
//        val workflowDao = mock(WorkflowDao::class.java)
//        val workflowDto = mock(WorkflowDto::class.java)
//        val phaseDao = mock(PhaseDao::class.java)
//
//        `when`(phaseDao.id).thenReturn(EntityID(1, Phases))
//        `when`(phaseDao.workflow).thenReturn(workflowDao)
//        `when`(phaseDao.parentPhase).thenReturn(null)
//        `when`(phaseDao.name).thenReturn("Phase 1")
//        `when`(phaseDao.description).thenReturn("Description 1")
//        `when`(phaseDao.displayOrder).thenReturn(1)
//        `when`(workflowMapper.toDto(workflowDao)).thenReturn(workflowDto)
//
//        val phaseDto = phasesMapper.toDto(phaseDao)
//
//        assertEquals(1L, phaseDto.id)
//        assertEquals("Phase 1", phaseDto.name)
//        assertEquals("Description 1", phaseDto.description)
//        assertEquals(1, phaseDto.displayOrder)
//        verify(workflowMapper).toDto(workflowDao)
//    }
//}
//
