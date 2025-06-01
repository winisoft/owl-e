
The DtoValidator defines a contract whereby a list of com.wini.owle.error.ConstraintError represent both the policy a field must obey and the the evaluation applied to the KProperty1 field to determine compliance. 

The type-schema module's com.wini.owle.ksp.ValidatorGeneratorProcessor ensures that every type decorated with the @ModelDefinition annotation has a generated DtoValidator implementation relevant to its requirements.