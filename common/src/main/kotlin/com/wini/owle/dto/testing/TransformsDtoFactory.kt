package com.wini.owle.dto.testing

import com.wini.owle.dto.transforms.Transform
import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.dto.typing.TypeNode

/**
 * Canned [transforms] DTO instances for tests.
 *
 * One factory per DTO package, composed by [DtoTestInstanceFactory]. Cross-package
 * instances are obtained from the sibling factory that owns them, so each file stays
 * responsible for exactly the types its own package declares.
 */
object TransformsDtoFactory {

    fun transformTargetType(
        transform: Transform = transformDto(),
        eligibleTypeNode: TypeNode = TypingDtoFactory.typeNode(),
        returnTypeNode: TypeNode = TypingDtoFactory.typeNode()
    ): TransformTargetType = TransformTargetType(
        transform = transform,
        eligibleTypeNode = eligibleTypeNode,
        returnTypeNode = returnTypeNode
    )

    fun transformDto(
        id: ULong = 1UL,
        name: String = "Transformation Name",
        description: String = "Transformation Description",
        examples: String? = "Example",
        returnsType: TypeNode = TypingDtoFactory.typeNode()
    ) : Transform {
        return Transform(
            id = id,
            name = name,
            description = description,
            examples = examples
        )
    }

    fun transformOccurrenceArgDto(
        id: ULong = 0UL,
        transformOccurrence: TransformOccurrence = transformOccurrenceDto(),
        transformationArg: TransformArg = transformArgDto(),
        value: String = ""
    ) : TransformOccurrenceArg {
        return TransformOccurrenceArg(
            id = id,
            transformOccurrence = transformOccurrence,
            transformArg = transformationArg,
            value = value
        )
    }

    fun transformArgDto(
        id: ULong = 0UL,
        transformation: Transform = transformDto(),
        name: String = "Transformation Arg Name",
        typeNode: TypeNode = TypingDtoFactory.typeNode(),
        description: String? = "Transformation Arg Description"
    ): TransformArg {
        return TransformArg(
            id = id,
            transform = transformation,
            name = name,
            typeNode = typeNode,
            description = description
        )
    }

    fun transformOccurrenceDto(
        id: ULong = 0UL,
        transformation: Transform = transformDto(),
        occurrence: com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator = com.wini.owle.dto.transforms.enums.TransformOccurrenceInitiator.DATA_POINT_INJECTION,
        resultType: TypeNode = TypingDtoFactory.typeNode()
    ) : TransformOccurrence {
        return TransformOccurrence(
            id = id,
            transform = transformation,
            initiator = occurrence,
        )
    }
}
