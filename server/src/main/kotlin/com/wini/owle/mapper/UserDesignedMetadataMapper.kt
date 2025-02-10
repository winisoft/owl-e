package com.wini.owle.mapper

import com.wini.owle.dto.*
import com.wini.owle.models.metadata.UserDesignedMetadata
import com.wini.owle.dto.CommercialUsageDto as CommercialUsageDto
import com.wini.owle.models.CommercialUsage as CommercialUsageEntity
import com.wini.owle.dto.CreditNotationDto as CreditNotationDto
import com.wini.owle.models.CreditNotation as CreditNotationEntity
import com.wini.owle.dto.ModificationDto as ModificationDto
import com.wini.owle.models.Modification as ModificationEntity
import com.wini.owle.dto.DistributionAgreementDto as DistributionAgreementDto
import com.wini.owle.models.DistributionAgreement as DistributionAgreementEntity
import com.wini.owle.dto.SharingConfigDto as SharingConfigDto


class UserDesignedMetadataMapper {
    fun toEntity(dto: UserDesignedMetadataDto): UserDesignedMetadata {
        dto.run {
            return UserDesignedMetadata(
                name=name,
                version=version,
                authors=authors,
                copyrightInformation=copyrightInformation,
                contactInformation=contactInformation,
                thirdPartyLicenses=thirdPartyLicenses,
                discretionAdvisedContent=discretionAdvisedContent,
//                commercialUsage=when(commercialUsage){
//                    CommercialUsageDto.PERSONAL_NON_PROFIT -> CommercialUsageEntity.PERSONAL_NON_PROFIT
//                    CommercialUsageDto.PERSONAL_PROFIT -> CommercialUsageEntity.PERSONAL_PROFIT
//                    CommercialUsageDto.CORPORATION -> CommercialUsageEntity.CORPORATION
//                },
//                creditNotation=when(creditNotation){
//                    CreditNotationDto.REQUIRED -> CreditNotationEntity.REQUIRED
//                    CreditNotationDto.UNNECESSARY -> CreditNotationEntity.UNNECESSARY
//                },
//                allowRedistribution=allowRedistribution,
//                modification=when(modification){
//                    ModificationDto.PROHIBITED -> ModificationEntity.PROHIBITED
//                    ModificationDto.ALLOW_MODIFICATION -> ModificationEntity.ALLOW_MODIFICATION
//                    ModificationDto.ALLOW_MODIFICATION_REDISTRIBUTION -> ModificationEntity.ALLOW_MODIFICATION_REDISTRIBUTION
//                },
//                distributionAgreement=DistributionAgreementEntity(
//                    version=distributionAgreement.version,
//                    acceptedAt=distributionAgreement.acceptedAt,
//                    updatedAt=distributionAgreement.updatedAt
//                ),
//                sharingConfig=SharingConfigEntity(
//                    includeAllSteps=sharingConfig.includeAllSteps,
//                    excludedSteps=sharingConfig.excludedSteps
//                )
            )
        }

    }

    fun toDto(metadata: UserDesignedMetadata): UserDesignedMetadataDto {
        metadata.run {

            return UserDesignedMetadataDto(
                name=name,
                version=version,
                authors=authors,
                copyrightInformation=copyrightInformation,
                contactInformation=contactInformation,
                thirdPartyLicenses=thirdPartyLicenses,
                discretionAdvisedContent=discretionAdvisedContent,
//                commercialUsage=when(commercialUsage){
//                    CommercialUsageEntity.PERSONAL_NON_PROFIT -> CommercialUsageDto.PERSONAL_NON_PROFIT
//                    CommercialUsageEntity.PERSONAL_PROFIT -> CommercialUsageDto.PERSONAL_PROFIT
//                    CommercialUsageEntity.CORPORATION -> CommercialUsageDto.CORPORATION
//                },
//                creditNotation=when(creditNotation){
//                    CreditNotationEntity.REQUIRED -> CreditNotationDto.REQUIRED
//                    CreditNotationEntity.UNNECESSARY -> CreditNotationDto.UNNECESSARY
//                },
//                allowRedistribution=allowRedistribution,
//                modification=when(modification){
//                    ModificationEntity.PROHIBITED -> ModificationDto.PROHIBITED
//                    ModificationEntity.ALLOW_MODIFICATION -> ModificationDto.ALLOW_MODIFICATION
//                    ModificationEntity.ALLOW_MODIFICATION_REDISTRIBUTION -> ModificationDto.ALLOW_MODIFICATION_REDISTRIBUTION
//                },
//                distributionAgreement=DistributionAgreementDto(
//                    version=distributionAgreement.version,
//                    acceptedAt=distributionAgreement.acceptedAt,
//                    updatedAt=distributionAgreement.updatedAt
//                ),
//                sharingConfig=SharingConfigDto(
//                    includeAllSteps=sharingConfig.includeAllSteps,
//                    excludedSteps=sharingConfig.excludedSteps
//                )
            )
        }
    }
}