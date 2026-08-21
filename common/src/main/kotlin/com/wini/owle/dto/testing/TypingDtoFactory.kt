package com.wini.owle.dto.testing

import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.dto.typing.TypeUnionMember
import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.dto.typing.enums.VarianceCardinality
import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.dto.typing.CallableSignature

/**
 * Canned [typing] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object TypingDtoFactory {

    fun typeUnionMember(
        unionTypeNode: TypeNode = typeNode(),
        memberTypeNode: TypeNode = typeNode(),
    ): TypeUnionMember = TypeUnionMember(
        unionTypeNode = unionTypeNode,
        memberTypeNode = memberTypeNode
    )

    fun typeSchemaRefDto(
        id: ULong = 1UL,
        name: String = "exampleSchema",
        typeDeclaration: TypeNode = typeNode(),
        description: String = "Schema reference description",
    ) : TypeSchemaRef {
        return TypeSchemaRef(
            id = id,
            name = name,
            typeNode = typeDeclaration,
            description = description
        )
    }

    fun typeNode(): TypeNode = TypeNode(
        id = 1UL,
        version = EnvironsDtoFactory.version(),
        null,
        "",
        false
    )

    fun typeUnionMemberDto(
        unionTypeDeclarationId: TypeNode = typeNode(),
        memberTypeDeclarationId: TypeNode = typeNode()
    ): TypeUnionMember {
        return TypeUnionMember(
            unionTypeNode = unionTypeDeclarationId,
            memberTypeNode = memberTypeDeclarationId
        )
    }

    fun typeVarianceDto(
        id: ULong = 23UL,
        typeVariableDeclaration: TypeNode = typeNode(),
        varianceCardinality: VarianceCardinality = VarianceCardinality.COVARIANT
    ): TypeVarVariance {
        return TypeVarVariance(
            id = 1UL,
            typeVarNode = typeVariableDeclaration,
            varianceCardinality = varianceCardinality
        )
    }

    fun typeVarBoundDto(
        id: ULong = 1UL,
        boundTypeDeclaration: TypeNode = typeNode(),
        typeVariableDeclaration: TypeNode = typeNode()
    ) : TypeVarBound {
        return TypeVarBound(
            id = 1UL,
            boundTypeNode = boundTypeDeclaration,
            typeVarNode = typeVariableDeclaration
        )
    }

    fun callableSignatureDto(
        id: ULong = 1UL,
        typeNode: TypeNode = typeNode(),
        returnTypeNode: TypeNode = typeNode(),
        docstring: String? = "An example callable.",
        isStatic: Boolean = false,
        isClassmethod: Boolean = false
    ): CallableSignature = CallableSignature(
        id = id,
        typeNode = typeNode,
        returnTypeNode = returnTypeNode,
        docstring = docstring,
        isStatic = isStatic,
        isClassmethod = isClassmethod
    )

    fun callableParameterDto(
        id: ULong = 1UL,
        signature: CallableSignature = callableSignatureDto(),
        paramTypeNode: TypeNode = typeNode(),
        position: UInt = 0u,
        paramName: String = "url",
        isVarArg: Boolean = false,
        isKwarg: Boolean = false,
        isPositionalOnly: Boolean = false,
        isKeywordOnly: Boolean = false,
        defaultValueText: String? = null
    ): CallableParameter = CallableParameter(
        id = id,
        signature = signature,
        paramTypeNode = paramTypeNode,
        position = position,
        paramName = paramName,
        isVarArg = isVarArg,
        isKwarg = isKwarg,
        isPositionalOnly = isPositionalOnly,
        isKeywordOnly = isKeywordOnly,
        defaultValueText = defaultValueText
    )
}
