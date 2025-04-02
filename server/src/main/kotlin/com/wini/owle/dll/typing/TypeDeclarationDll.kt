package com.wini.owle.dll.typing

import com.wini.owle.async.AsyncError
import com.wini.owle.async.AsyncResult
import com.wini.owle.dll.DLL
import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.services.typing.TypeDeclarationService
import com.wini.owle.services.typing.TypeParameterService
import com.wini.owle.services.typing.TypeUnionMemberService

class TypeDeclarationDll(
    private val typeDeclarationService: TypeDeclarationService,
    private val typeUnionMemberService: TypeUnionMemberService,
    private val typeParameterService: TypeParameterService
): DLL<Long, TypeDeclarationDto> {

    override fun getAll(): AsyncResult<List<TypeDeclarationDto>> = typeDeclarationService.getAll()
    override fun getById(id: Long): AsyncResult<TypeDeclarationDto?> = typeDeclarationService.getById(id)
    override fun create(dto: TypeDeclarationDto): AsyncResult<TypeDeclarationDto> = typeDeclarationService.create(dto)
    override fun create(dtos: List<TypeDeclarationDto>): AsyncResult<List<TypeDeclarationDto>> = typeDeclarationService.create(dtos)
    override fun update(dto: TypeDeclarationDto): AsyncResult<TypeDeclarationDto> = typeDeclarationService.update(dto)
    override fun update(dtos: List<TypeDeclarationDto>): AsyncResult<List<TypeDeclarationDto>> = typeDeclarationService.update(dtos)
    override fun delete(id: Long): AsyncResult<Boolean> = typeDeclarationService.delete(id)
    override fun delete(ids: List<Long>): AsyncResult<Boolean> = typeDeclarationService.delete(ids)

    /**
     * Reconstructs a canonical string representation of a type from the database.
     *
     * Example outputs could be:
     *  - "int"
     *  - "Dict[str, int]"
     *  - "Union[int, str]"
     *  - "List[List\[int\]]"
     *
     * @param typeDeclarationId the primary key of the TypeDeclarationEntity to reconstruct
     * @return a human-readable representation of that type
     */
    fun reconstructType(typeDeclarationId: Long): AsyncResult<String> {
        // 1. Fetch the TypeDeclaration record (with its FK to TypeDefinition)
        val declaration = typeDeclarationService.getById(typeDeclarationId).run {
            if (this is AsyncResult.Success)
                data ?: return AsyncResult.Failure(AsyncError.UnknownError("No TypeDeclaration found for id=$typeDeclarationId"))
            else
                return this as AsyncResult.Failure
        }

        // 3. If this is a Union, handle union logic
        //    (We assume that if TypeDefinitionEntity.name == "Union", then
        //     we should look up TypeUnionMemberEntity for its members.)
        if (declaration.typeDefinition.name == "Union") {
            val unionMembers = typeUnionMemberService.getByUnionTypeDeclarationId(typeDeclarationId).run {
                if (this is AsyncResult.Success)
                    data
                else
                    return this as AsyncResult.Failure
            }
            if (unionMembers.isEmpty()) {
                // Edge case: Union with no members
                return AsyncResult.Success("Union[]")
            }
            // Reconstruct each member's string representation, join them with ", "
            val memberStrings = unionMembers.map { member ->
                reconstructType(member.memberTypeDeclarationId)
            }
            return AsyncResult.Success("Union[${memberStrings.joinToString(", ")}]")
        }

        // 4. Otherwise, fetch any type parameters for this type (ordered by position)
        val parameters = typeParameterService.getByTypeDeclarationIdOrderByPosition(typeDeclarationId).run {
            if (this is AsyncResult.Success) data else return this as AsyncResult.Failure
        }
        if (parameters.isEmpty()) {
            // This is a non-parameterized, non-Union type
            return AsyncResult.Success(declaration.typeDefinition.name ?: "") //TODO
        } else {
            // Recursively reconstruct each parameter
            val paramStrings = parameters.map { param ->
                reconstructType(param.parameterTypeDeclaration.id)
            } // TODO below typeDefinition?.name ?: ""
            return AsyncResult.Success("${declaration.typeDefinition.name ?: ""}[${paramStrings.joinToString(", ")}]")
        }
    }

}