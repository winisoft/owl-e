package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.AuthorshipCreditDto
import com.wini.owle.entity.workflows.enums.AuthorshipCredit


class AuthorshipCreditMapper {

    fun toEntity(dto: AuthorshipCreditDto): AuthorshipCredit {
        return when(dto) {
            AuthorshipCreditDto.Required -> AuthorshipCredit.REQUIRED
            AuthorshipCreditDto.Unnecessary -> AuthorshipCredit.UNNECESSARY
        }
    }

    fun toDto(entity: AuthorshipCredit): AuthorshipCreditDto {
        return when(entity) {
            AuthorshipCredit.REQUIRED -> AuthorshipCreditDto.Required
            AuthorshipCredit.UNNECESSARY -> AuthorshipCreditDto.Unnecessary
        }
    }

}