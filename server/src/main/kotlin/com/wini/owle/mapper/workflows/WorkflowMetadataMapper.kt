package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowMetadata
import com.wini.owle.entity.workflows.WorkflowMetadataEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.enums.AuthorshipCreditMapper
import com.wini.owle.mapper.workflows.enums.CommercialUseMapper
import com.wini.owle.mapper.workflows.enums.ModificationTermsMapper


class WorkflowMetadataMapper(
    private val daoDelegate: DaoDelegate<ULong, WorkflowMetadata, WorkflowMetadataEntity>,
    private val workflowMapper: WorkflowMapper,
    private val commercialUserMapper: CommercialUseMapper,
    private val authorshipCreditMapper: AuthorshipCreditMapper,
    private val modificationTermsMapper: ModificationTermsMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<ULong, WorkflowMetadata, WorkflowMetadataEntity> {

    override fun toEntity(dto: WorkflowMetadata): WorkflowMetadataEntity {
        return daoDelegate.findOrCreate(dto) {
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
    }

    override fun toDto(entity: WorkflowMetadataEntity): WorkflowMetadata {
        return WorkflowMetadata(
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
            excludedSteps = emptyList()
        )
    }
}
