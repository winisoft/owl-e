package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowMetadataDto
import com.wini.owle.entity.workflows.WorkflowMetadataDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.enums.AuthorshipCreditMapper
import com.wini.owle.mapper.workflows.enums.CommercialUseMapper
import com.wini.owle.mapper.workflows.enums.ModificationTermsMapper


class WorkflowMetadataMapper(
    private val daoFactory: DaoFactory<WorkflowMetadataDao, Long>,
    private val workflowsMapper: WorkflowsMapper,
    private val commercialUserMapper: CommercialUseMapper,
    private val authorshipCreditMapper: AuthorshipCreditMapper,
    private val modificationTermsMapper: ModificationTermsMapper,
    private val dateTimeConverter: DateTimeConverter
) : DbMapper<WorkflowMetadataDto, WorkflowMetadataDao>() {

    override fun toEntity(dto: WorkflowMetadataDto): WorkflowMetadataDao {
        val applyProps: WorkflowMetadataDao.() -> Unit = {
            name = dto.name
            version = dto.version
            copyrightInformation = dto.copyrightInformation
            contactInformation = dto.contactInformation
            thirdPartyLicenses = dto.thirdPartyLicenses
            discretionAdvisedContent = dto.discretionAdvisedContent
            commercialUsage = commercialUserMapper.toEntity(dto.commercialUsage)
            creditNotation = authorshipCreditMapper.toEntity(dto.creditNotation)
            allowRedistribution = dto.allowRedistribution
            modification = modificationTermsMapper.toEntity(dto.modification)
            distributionVersion = dto.distributionVersion
            distributionAcceptedAt = dateTimeConverter.fromString(dto.distributionAcceptedAt)
            distributionUpdatedAt = dateTimeConverter.fromString(dto.distributionUpdatedAt)
        }

        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            WorkflowMetadataDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("WorkflowMetadata with id=${dto.id} not found")
    }

    override fun toDto(entity: WorkflowMetadataDao): WorkflowMetadataDto {
        return WorkflowMetadataDto(
            id = entity.id.value,
            name = entity.name,
            version = entity.version,
            copyrightInformation = entity.copyrightInformation,
            contactInformation = entity.contactInformation,
            thirdPartyLicenses = entity.thirdPartyLicenses,
            discretionAdvisedContent = entity.discretionAdvisedContent,
            commercialUsage = commercialUserMapper.toDto(entity.commercialUsage),
            creditNotation = authorshipCreditMapper.toDto(entity.creditNotation),
            modification = modificationTermsMapper.toDto(entity.modification),
            allowRedistribution = entity.allowRedistribution,
            distributionVersion = entity.distributionVersion,
            distributionAcceptedAt = dateTimeConverter.toString(entity.distributionAcceptedAt),
            distributionUpdatedAt = dateTimeConverter.toString(entity.distributionUpdatedAt),
            contributors = emptyList(),
            excludedSteps = emptyList()
        )
    }
}
