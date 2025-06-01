
*Priority 5*: Ensure all of the Mapper types have a complete implementation of their `toDto` and `toEntity` functions. The `toDto` function can be called without help beyond some constructor-injected collaborator DbMapper type for a field holding a nested complex type. The `toEntity` function only needs to call the DaoDelegate `findOrCreate` function (passing the dto instance being mapped) with a higher-order function that applies values to properties on the result.

Here are two strong examples.

```kotlin

class PackageDistRequirementMapper(  
    private val daoDelegate: DaoDelegate<ULong, PackageDistRequirement, PackageDistRequirementEntity>,  
    private val distributionMapper: DistributionMapper,  
    private val versionMapper: VersionMapper  
): DbMapper<ULong, PackageDistRequirement, PackageDistRequirementEntity> {  
  
    override fun toEntity(dto: PackageDistRequirement): PackageDistRequirementEntity {  
        return daoDelegate.findOrCreate(dto) {  
            dependentDistribution = distributionMapper.toEntity(dto.dependentDistribution)  
            dependentVersion = versionMapper.toEntity(dto.dependentVersion)  
            requiredDistribution = distributionMapper.toEntity(dto.requiredDistribution)  
            requiredVersion = dto.requiredVersion?.let { versionMapper.toEntity(it) }  
            operator = dto.operator  
            wildcardSegment = dto.wildcardSegment  
        }  
    }  
  
    override fun toDto(entity: PackageDistRequirementEntity): PackageDistRequirement {  
        return PackageDistRequirement(  
            id = entity.id.value,  
            dependentDistribution = distributionMapper.toDto(entity.requiredDistribution),  
            dependentVersion = versionMapper.toDto(entity.dependentVersion),  
            requiredDistribution = distributionMapper.toDto(entity.requiredDistribution),  
            requiredVersion = entity.requiredVersion?.run { versionMapper.toDto(this) },  
            operator = entity.operator,  
            wildcardSegment = entity.wildcardSegment  
        )  
    }  
}


class EnvMarkerComparisonMapper(  
    private val daoDelegate: DaoDelegate<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity>,  
    val envMarkerExprMapper: EnvMarkerExpressionMapper  
): DbMapper<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity> {  
  
    override fun toEntity(dto: EnvMarkerComparison): EnvMarkerComparisonEntity {  
        return daoDelegate.findOrCreate(dto) {  
            expression = envMarkerExprMapper.toEntity(dto.expression)  
            variable = dto.variable  
            operator = dto.operator  
            value = dto.value  
        }  
    }  
  
    override fun toDto(entity: EnvMarkerComparisonEntity): EnvMarkerComparison {  
        return EnvMarkerComparison(  
            id = entity.id.value,  
            expression = envMarkerExprMapper.toDto(entity.expression),  
            variable = entity.variable,  
            operator = entity.operator,  
            value = entity.value  
        )  
    }  
  
}

```
