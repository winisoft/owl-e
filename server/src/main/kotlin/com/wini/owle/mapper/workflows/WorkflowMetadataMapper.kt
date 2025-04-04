package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.AuthorshipCreditDto
import com.wini.owle.dto.workflows.CommercialUseDto
import com.wini.owle.dto.workflows.ModificationTermsDto
import com.wini.owle.dto.workflows.WorkflowMetadataDto
import com.wini.owle.entity.workflows.AuthorshipCredit
import com.wini.owle.entity.workflows.CommercialUse
import com.wini.owle.entity.workflows.ModificationTerms
import com.wini.owle.entity.workflows.WorkflowMetadataDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import org.jetbrains.exposed.sql.SizedIterable


class WorkflowMetadataMapper(
    private val daoFactory: DaoFactory<WorkflowMetadataDao, Long>,
    private val workflowsMapper: WorkflowsMapper,
) : DbMapper<WorkflowMetadataDto, WorkflowMetadataDao>() {

    override fun toEntity(dto: WorkflowMetadataDto): WorkflowMetadataDao {
        val applyProps: WorkflowMetadataDao.() -> Unit = {
            name = dto.name
            version = dto.version
            copyrightInformation = dto.copyrightInformation
            contactInformation = dto.contactInformation
            thirdPartyLicenses = dto.thirdPartyLicenses
            discretionAdvisedContent = dto.discretionAdvisedContent
            commercialUsage = when (dto.commercialUsage) {
                CommercialUseDto.PersonalNonProfit -> CommercialUse.PERSONAL_NON_PROFIT
                CommercialUseDto.PersonalProfit -> CommercialUse.PERSONAL_PROFIT
                CommercialUseDto.Corporation -> CommercialUse.CORPORATION
            }
            creditNotation = when (dto.creditNotation) {
                AuthorshipCreditDto.Required -> AuthorshipCredit.REQUIRED
                AuthorshipCreditDto.Unnecessary -> AuthorshipCredit.UNNECESSARY
            }
            allowRedistribution = dto.allowRedistribution
            modification = when (dto.modification) {
                ModificationTermsDto.Prohibited -> ModificationTerms.PROHIBITED
                ModificationTermsDto.AllowModification -> ModificationTerms.ALLOW_MODIFICATION
                ModificationTermsDto.AllowModificationDistribution -> ModificationTerms.ALLOW_MODIFICATION_REDISTRIBUTION
            }
            distributionVersion = dto.distributionVersion
            distributionAcceptedAt = DateTimeConverter.fromString(dto.distributionAcceptedAt)
            distributionUpdatedAt = DateTimeConverter.fromString(dto.distributionUpdatedAt)
            workflow = workflowsMapper.toEntity(dto.workflow)
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
            commercialUsage = when (entity.commercialUsage) {
                CommercialUse.PERSONAL_NON_PROFIT -> CommercialUseDto.PersonalNonProfit
                CommercialUse.PERSONAL_PROFIT -> CommercialUseDto.PersonalProfit
                CommercialUse.CORPORATION -> CommercialUseDto.Corporation
            },
            creditNotation = when (entity.creditNotation) {
                AuthorshipCredit.REQUIRED -> AuthorshipCreditDto.Required
                AuthorshipCredit.UNNECESSARY -> AuthorshipCreditDto.Unnecessary
            },
            allowRedistribution = entity.allowRedistribution,
            modification = when (entity.modification) {
                ModificationTerms.PROHIBITED -> ModificationTermsDto.Prohibited
                ModificationTerms.ALLOW_MODIFICATION -> ModificationTermsDto.AllowModification
                ModificationTerms.ALLOW_MODIFICATION_REDISTRIBUTION -> ModificationTermsDto.AllowModificationDistribution
            },
            distributionVersion = entity.distributionVersion,
            distributionAcceptedAt = DateTimeConverter.toString(entity.distributionAcceptedAt),
            distributionUpdatedAt = DateTimeConverter.toString(entity.distributionUpdatedAt),
            workflow = workflowsMapper.toDto(entity.workflow),
            contributors = emptyList(),
            excludedSteps = emptyList()
        )
    }
}
