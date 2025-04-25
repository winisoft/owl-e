package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.AuthorshipCreditDto
import com.wini.owle.entity.workflows.enums.AuthorshipCreditEntity


class AuthorshipCreditMapper {

    fun toEntity(dto: AuthorshipCreditDto): AuthorshipCreditEntity {
        return when(dto) {
            AuthorshipCreditDto.REQUIRED -> AuthorshipCreditEntity.REQUIRED
            AuthorshipCreditDto.UNNECESSARY -> AuthorshipCreditEntity.UNNECESSARY
        }
    }

    fun toDto(entity: AuthorshipCreditEntity): AuthorshipCreditDto {
        return when(entity) {
            AuthorshipCreditEntity.REQUIRED -> AuthorshipCreditDto.REQUIRED
            AuthorshipCreditEntity.UNNECESSARY -> AuthorshipCreditDto.UNNECESSARY
        }
    }

}