
## Table of Contents
1. [Introduction](#introduction)
2. [Data Model](#data-model)
3. [Application Logic](#application-logic)
4. [Workflow Composition](#workflow-composition)
5. [Data Assignment and Overrides](#data-assignment-and-overrides)
6. [Execution and Validation](#execution-and-validation)
7. [Safety and Constraints](#safety-and-constraints)

## 1. Introduction

Owl-e is a sophisticated workflow automation and agentic AI software platform designed to bridge the gap between practical automation needs and advanced AI safety considerations. While at its core, Owl-e may appear to be "nothing more than an imperative software execution framework," its unique architecture and approach offer significant advantages in multiple domains:

1. For end-users, it provides a powerful, flexible tool for automating complex processes and workflows.
2. For developers, it offers a robust platform for creating and managing interconnected systems.
3. For AI researchers, it presents a novel approach to addressing critical challenges in AI safety, autonomous learning, and self-improvement.

This document outlines the key conceptual strengths of our workflow system, demonstrating its potential across these domains.

### Conceptual Strengths


#### User-Centric Workflow Design

Owl-e empowers users to:

- Create complex, multi-step workflows without extensive programming knowledge
- Visualize and manage processes through an intuitive, hierarchical structure
- Easily modify and adapt workflows to changing needs
- Integrate various tools and services into a cohesive process

**Implication**: Owl-e democratizes process automation, allowing users from various backgrounds to create sophisticated workflows tailored to their specific needs.

#### Contextual Scoping

The workflow system's hierarchical structure of phases and steps provides a clear, scoped context for each operation. This approach:

- Reduces cognitive load for users (and computational complexity for AI) at each step
- Maintains focus on relevant information and objectives
- Mitigates the risk of context loss or topic drift in extended processes

**Implication**: This structure could potentially solve the context window limitations in current language models and other AI systems, allowing for more coherent and focused long-term reasoning.

#### Universal Task Representation

By representing complex tasks as sequences of well-defined steps, the system creates a universal language for describing processes. This approach:

- Allows for the breakdown of any task into "atomic" operations
- Facilitates the creation of generalized AI systems capable of tackling a wide range of tasks
- Enables the composition of complex behaviors from simpler, well-understood components

**Implication**: This universality could be key in developing AI systems with general intelligence capabilities, able to understand and execute a vast array of tasks using a common framework.

#### Modular and Reusable Components

Owl-e's design promotes:

- Creation of reusable workflow components
- Easy sharing and collaboration on workflow designs
- Building complex systems from simpler, well-tested modules

**Implication**: This modularity accelerates development, improves reliability, and facilitates the growth of a community-driven ecosystem of workflow components.

#### Rigorous Validation and Testing

The system's structure allows for clear definition of inputs and expected outputs for each workflow, effectively turning each into a testable function. This feature:

- Enables automated testing and validation
- Facilitates continuous improvement through iterative refinement
- Allows for precise measurement of accuracy, reliability, and performance

**Implication**: This approach creates a pathway for both users and AI systems to engage in safe, verifiable self-improvement, optimizing processes while maintaining reliability and alignment with intended goals.

#### Constrained Self-Improvement

By allowing modifications and optimizations within a predefined set of tools and operations, the system enables:

- Self-improvement and optimization while maintaining safety boundaries
- Clear metrics for success (e.g., 100% accuracy and reliability for a given task)
- Focus on efficiency improvements once a task is "solved"

**Implication**: This addresses one of the core concerns in AI safety - allowing AI systems to improve themselves without risking uncontrolled or harmful changes.

#### Value Alignment Through Constraints

Rather than attempting to instill abstract values, the system bakes in value alignment at a fundamental level by:

- Limiting operations to a predefined set of tools and actions
- Creating a "sandbox" where processes can only operate in ways aligned with intended values and goals
- Allowing administrators to carefully curate the available tools and capabilities

**Implication**: This approach offers a novel solution to the value alignment problem in AI safety, ensuring that AI systems remain aligned with human values even as they evolve and improve.

#### Simulation and Safe Testing

The ability to execute workflows in a simulated environment provides:

- A safe space for testing and validation without real-world consequences
- Opportunities for extensive "what-if" scenario testing
- A platform for AI to engage in "mental simulations" and planning

**Implication**: This feature is crucial for developing AI systems that can handle complex, real-world tasks without risking harm during the learning and improvement process.

#### Comprehensibility and Auditability

The step-by-step nature of workflows makes working with them easier by:

- Making the decision-making process transparent and traceable
- Allowing for detailed audits of behavior and decisions
- Facilitating the identification of specific steps or components that lead to particular outcomes

**Implication**: This addresses a major concern in current AI systems - the "black box" problem - by providing clear insights into how and why a system makes specific decisions.

#### Scalability and Integration

Owl-e's architecture supports:

- Seamless scaling from simple tasks to complex, distributed workflows
- Integration with existing tools, APIs, and services
- Adaptation to various execution environments (local, cloud, containerized)

**Implication**: This flexibility ensures that Owl-e can grow with user needs and adapt to evolving technological landscapes.



### Colloquial Long-Form Introductory Presentation Essay

Hello! I am What Is Not Is, and it's my great pleasure today to finally be launching a project I've been cooking up for some months now -- an AI innovation where the article about it doesn't begin with the word 'Introducing', and/or end with a big disappointment.

Quickly -- who am I? Why should you listen? A lifelong programmer with other qualifications we can talk about later in social science, as well as the arts and media production. What is the nature of my grievance with the current state of AI innovation? Glad I asked.

To put it generously, innovation is a funny thing. Sometimes you ship samples of tea in little baggies you had on hand and your customers write you letters to say "the tea was mediocre but where on Earth did you find these little tea permeable tea infusing baggies, I'll take a thousand", and you realize you've completely by accident invented a "game-changer". Most of the time, we invent in the direction of our vision with fingers crossed because someone told us it helps.

If you're like me, you've spent half of the last year dazzled by what you've seen AI do in a video demonstrating or describing it -- and the other half banging your head against a wall trying to get that solution to cooperate and work the way its authors said it would, all because your imagination fell right in line with the direction theirs was headed when they conceived of it. I'm not trying to paint an entire industry out to be scammers and charlatans. There is not a great shortage, but in large part this is just kind of how progress works. We can't say what we deserve if the entire range of what's *possible* even is a matter beyond the scope of what we can understand. The way we approach things is the way we're capable of making sense of them. I'll give you an example.

First we noticed, justifiably, that generative models have an astounding knack for composition in a given style, or an individual's unique voice. Well, why is that? The truth is, identifying the features that together comprise a sound statistical determination of "what makes a body of work *distinct*" is not an unimaginable challenge. There are a variety of birds with shockingly intelligent behavior that we now describe well or poorly as 'like a five year old' or something near it, but the actual equivalent value we're referencing, the five year old, is certainly not a master impressionist. This is the question of the nature of specialization. Isn't it problematically anthopo-centric in concept? How much of what's generalizable is needed before you can reach it? How much of who or what you are is mixed up in the business of *what* you do? Are you sure you know what that is? We certainly feel intuitively that we do, and those patterns -- of intonation, of word choice, word count, grammar, etc.-- with what makes our *self* feel unique. In other words, our expressing ourselves -- our expressions -- become the vehicle of, the evidence for, our being expressed in the sense of a thing's emergence, or unexpected occurrence. These traits are the water to our existential 'fish'.

Did you know there's a Post-It note stuck to the sleeve of your shirt right now? You will likely say "I knew there wasn't one, and there isn't." But is this really different from your "Has no Post-It note stuck to their sleeve presently" that was excluded from the traits about yourself with which you identify?

Bill Hader is a brilliant impressionist, yeah? [Edit in clip of Bill doing his tauntaun vocalizations impression]. Well, that doesn't make him capable of tolerating sub-zero conditions, or running for miles with a smuggler-turned-Rebellion-hero on his back.

What I'm saying, more precisely, is that it didn't *feel* like a huge leap in logic to go from point A -- "it can adopt a person's 'likeness', yet it fails to conduct complex tasks on its own" to point B -- "So we need to throw many variations of a person at the problem, variations in keeping with the structure of real professional organizations. We'll give each one a role, like a job title, and a little backstory slash description of their personality, an objective to meet, and a tool to meet it with software". Well -- that only seems like the right tool for the job because it's in the realm of what we currently understand as in the range of possibilities -- and a novel-seeming addition to the range, at that. But the job title part isn't going to help, really. Your job title may affect your salary and your self-esteem, but not so the AI model. If the 'Job Title Fairy' visits you in your sleep and changes yours with her magic wand, it doesn't retroactively unlock branches of the skill tree like you're Neo getting trained Kung Fu through a chrome spike in your brain. You can have every kind of privilege, but no natural talent -- or the inverse of these; be meticulous or be lucky. You can be privileged, brilliant, dedicated, and likable and still be a complete failure in life. You can take that one on authority from yours truly.

When you come right down to it, trying to Stephen King yourself a software implementation answer to your challenge is just a miserable, unwieldy, ineffective 'handle' to have on your problem. And if you've tried, you know I'm right. Yes, sometimes you can get an error and feed that error right back to the same model and get it to 'self-correct'. Unfortunately, extrapolating from *that* anecdotal event to conclude that all problems should be addressed that way, or even could be -- at least, from where we currently stand -- is just a preposterous reach.

Let's talk about how working with an AI partner *can* be really effective. Suppose you want to write a calculator app for mobile devices. You can ask the model to list a bunch of mathematical operations it needs to be able to support. Wow, that's much faster than you can list mathematical operations. Cool. So you iterate over each item in that list and get a bunch of functions that fulfill them. Still fast, and mostly correct to boot. We're still way ahead on time. But -- a bunch of them were only pseudo-implementations or outright hallucinations; you *do* want to double-check its work of course, so you ask it to write tests to know they're working. Wait -- this one isn't passing. You ask it to try again, it doesn't work. Hopefully soon you take a step back and think about where the disconnect in all of this is. Maybe *you* explained what you wanted badly, or didn't understand yourself what the operation is supposed to actually do. Maybe to save time you didn't read everything and it added a problem factor you won't offer a chance to fix any time soon. Maybe you convince yourself it's the model's fault. Maybe part of what it wrote doesn't integrate properly with aspects of the code base it had given you previously, because at the time you were discussing a different challenge at a different level of detail and that's long since out of scope and in excess of its rolling context window. "Well", you ask, impatient for the time advantage being squandered, "why didn't you anticipate this error condition that's suddenly popped up?"

"Well", it would probably reply with equivalent frustration if this were in its capacity, "why didn't *you* ask about the possible error conditions that could have popped up? I can't answer every question at once even if you somehow actually manage to *ask* them all at once! So -- okay, let's figure out a way to account for this error case..."

You see where this is going? It's going in the direction of just -- a job as a software developer. You're tempted to say "well, see, we needed a coder personality and an test engineer personality, then it would have worked itself out". NO! Because the part that's actually missing is you there setting the terms of what needs to be considered and when. What things are useful in what capacity, and additionally need to bear a consideration to what other things that are not precisely related necessarily, but relevant, absolutely, if you have enough context to keep it all relevant when it needs to be, or if not, hey everything slips through the cracks now and again, human and AI alike. It's different levels of context, for different considerations, in diverse sizes and shapes, with a diverse set of tools to bear on many different challenges, intended to be met at different depths of fulfillment.

If I'm making this all sound totally impossible to manage, well, yes and no. Yes, brains are amazing -- no, in the sense of this important point here: *Most of the time*? The workflow for accomplishing a complex task is actually pretty well established. We have this amazing new tool and it's miraculous that it's 90% accurate, but that 10% of failure cases tend to bring everything to total impassable catastrophe. You can't just say "be self-correcting" like it's "abracadabra" -- at least, not yet, and when we reach that point, obviously, we won't *have to* say it.

If you want to tell the AI to take the first big swing at the problem and clean up the bad results in a way you *can* articulate, well -- then you *have to* articulate it. That's a valid approach. Alternatively, you could get the ball rolling and integrate AI function calling *where it's particularly adept at handling an issue*. Owl-E is designed with this sensibility governing it.

### Conclusion

Owl-e represents a significant advancement in workflow management and AI system design. For end-users, it offers an intuitive, powerful platform for process automation and optimization. For developers, it provides a flexible, modular framework for building complex systems. And for AI researchers, it presents a novel approach to addressing key challenges in AI safety and development.

By providing a structured yet flexible environment for task execution, self-improvement, and value alignment, Owl-e offers a promising path forward for developing systems that are not only powerful and self-improving but also inherently safe, explainable, and aligned with intended values.

As we continue to develop and refine Owl-e, we open up new possibilities for creating workflows and AI systems that can truly understand, plan, and execute complex tasks autonomously, all while maintaining the safety, control, and user-centricity that are crucial for responsible automation and AI development.




## 2. Data Model

**Relational Data Model Overview**

### Environs
	com.wini.owle.environs

Contains types that facilitate the proper management of the data types the user intends their workflows to interact with as data points that are tracked during the workflow's execution.

#### Package

**Description:** Represents a Python library package, storing details like name, disambiguation, and version specification.

**Attributes:**
- `id`: (PK) Unique identifier for the package.
- `name`: Name of the package.
- `disambiguation`: Optional field for distinguishing between similarly named packages.
- `versionSpecId`: (FK) Links to the version specification.

**Relationships:**
- **Version Spec:** `versionSpecId → VersionSpecEntity.id` *(Many-to-One)*

---

#### ToolPackageDependency
**Description:** Links a tool to the packages it depends on, with specific version constraints.

**Attributes:**
- `toolId`: (FK) Links to the associated tool.
- `packageId`: (FK) Links to the dependent package.
- `versionSpecId`: (FK) Links to the version specification.

**Relationships:**
- **Tool:** `toolId → ToolEntity.id` *(Many-to-One)*
- **Package:** `packageId → PackageEntity.id` *(Many-to-One)*
- **Version Spec:** `versionSpecId → VersionSpecEntity.id` *(Many-to-One)*

**Constraints:**
- Composite Primary Key: (`toolId`, `packageId`)

---

#### VersionSpec
**Description:** Specifies version constraints for packages, including minimum, maximum, or exact versions.

**Attributes:**
- `id`: (PK) Unique identifier for the version specification.
- `versionMin`: Minimum version allowed.
- `versionMax`: Maximum version allowed.
- `versionExact`: Exact version required.

---










### Runs
	com.wini.owle.environs

Contains types that facilitate modeling the execution of workflows.

#### BenchmarkingData
**Description:** Collects overall benchmarking data for a workflow execution.

**Attributes:**
- `id`: (PK) Unique identifier for the benchmarking data.
- `workflowExecutionId`: (FK) Links to the associated workflow execution.
- `totalExecutionTime`: Total execution time in seconds.
- `totalSteps`: Total number of steps executed.
- `aiDeterminedSteps`: Number of AI-determined steps.
- `predeterminedSteps`: Number of predetermined steps.

**Relationships:**
- **Workflow Execution:** `workflowExecutionId → WorkflowExecutionEntity.id` *(Many-to-One)*

---

#### ExecutionLog
**Description:** Stores individual log messages generated during step execution.

**Attributes:**
- `id`: (PK) Unique identifier for the log entry.
- `stepExecutionId`: (FK) Links to the associated step execution.
- `timestamp`: Timestamp of the log entry.
- `logLevel`: Severity level of the log (DEBUG, INFO, etc.).
- `message`: Content of the log message.

**Relationships:**
- **Step Execution:** `stepExecutionId → StepExecutionEntity.id` *(Many-to-One)*

---

#### StepBenchmarkingData
**Description:** Provides detailed benchmarking data for an individual step, including execution time and resource usage.

**Attributes:**
- `id`: (PK) Unique identifier for the benchmarking record.
- `stepExecutionId`: (FK) Links to the associated step execution.
- `executionTime`: Execution time in seconds.
- `resourceUsage`: JSON-encoded resource usage data.

**Relationships:**
- **Step Execution:** `stepExecutionId → StepExecutionEntity.id` *(Many-to-One)*

---

#### StepExecution
**Description:** Represents the execution instance of a step within a workflow, tracking execution details.

**Attributes:**
- `id`: (PK) Unique identifier for the step execution.
- `workflowExecutionId`: (FK) Links to the associated workflow execution.
- `stepId`: (FK) Links to the executed step.
- `startedAt`: Start timestamp.
- `completedAt`: Completion timestamp.
- `status`: Current status (running, completed, failed, etc.).

**Relationships:**
- **Workflow Execution:** `workflowExecutionId → WorkflowExecutionEntity.id` *(Many-to-One)*
- **Step:** `stepId → StepEntity.id` *(Many-to-One)*

---

#### TestCase
**Description:** Represents a test case to validate workflows or steps.

**Attributes:**
- `id`: (PK) Unique identifier for the test case.
- `userId`: (FK) Links to the user owning this test case.
- `workflowId`: (FK) Links to the targeted workflow.
- `stepId`: (FK) Links to the targeted step.
- `name`: Name of the test case.
- `description`: Description of the test case purpose.

**Relationships:**
- **User:** `userId → UserEntity.id` *(Many-to-One)*
- **Workflow:** `workflowId → WorkflowEntity.id` *(Many-to-One)*
- **Step:** `stepId → StepEntity.id` *(Many-to-One)*

---

#### TestExpectedOutcome
**Description:** Defines expected outcomes for a test case, specifying values and comparison operators.

**Attributes:**
- `id`: (PK) Unique identifier for the expected outcome.
- `testCaseId`: (FK) Links to the associated test case.
- `dataPointName`: Name of the data point being validated.
- `expectedValue`: The expected value of the data point.
- `comparisonOperator`: Operator for comparison (equals, not_equals, etc.).

**Relationships:**
- **Test Case:** `testCaseId → TestCaseEntity.id` *(Many-to-One)*

---

#### TestInput
**Description:** Defines input values for a test case, specifying data points and their values.

**Attributes:**
- `id`: (PK) Unique identifier for the test input.
- `testCaseId`: (FK) Links to the associated test case.
- `dataPointName`: Name of the data point.
- `value`: Assigned value for the test.
- `generationMethod`: Method of generation (manual, AI-generated).

**Relationships:**
- **Test Case:** `testCaseId → TestCaseEntity.id` *(Many-to-One)*

---

#### TestResult
**Description:** Records the outcome of a test case execution.

**Attributes:**
- `id`: (PK) Unique identifier for the test result.
- `testCaseId`: (FK) Links to the associated test case.
- `workflowExecutionId`: (FK) Links to the associated workflow execution.
- `passed`: Boolean indicating success or failure.
- `executedAt`: Execution timestamp.

**Relationships:**
- **Test Case:** `testCaseId → TestCaseEntity.id` *(Many-to-One)*
- **Workflow Execution:** `workflowExecutionId → WorkflowExecutionEntity.id` *(Many-to-One)*

---

#### WorkflowExecution
**Description:** Represents an execution instance of a workflow, tracking execution details and user involvement.

**Attributes:**
- `id`: (PK) Unique identifier for the workflow execution.
- `workflowId`: (FK) Links to the executed workflow.
- `userId`: (FK) Links to the user initiating the execution.
- `startedAt`: Start timestamp.
- `completedAt`: Completion timestamp.
- `status`: Current status (running, completed, failed, cancelled).

**Relationships:**
- **Workflow:** `workflowId → WorkflowEntity.id` *(Many-to-One)*
- **User:** `userId → UserEntity.id` *(Many-to-One)*

**Relational Data Model Overview (Extended Types)**

#### ErrorWatchFn
**Description:** Represents a function filter in an error watch, specifying a function name and position in the call stack.

**Attributes:**
- `id`: (PK) Unique identifier for the error watch function filter.
- `errorWatchId`: (FK) Links to the associated error watch.
- `functionName`: Name of the function to monitor in the call stack.
- `position`: Position in the call stack to match.

**Relationships:**
- **Error Watch:** `errorWatchId → ErrorWatchEntity.id` *(Many-to-One)*

---
	






### Steps
	com.wini.owle.environs

Contains types that model the properties of workflow execution whose configuration is applied at the scope of an individual step.
#### Revision
**Description:** Tracks changes to a data point after a step's execution, recording the step and optional transformations applied.

**Attributes:**
- `id`: (PK) Unique identifier for the revision.
- `dataPointId`: (FK) Links to the affected data point.
- `stepId`: (FK) Links to the step responsible for the revision.
- `transformationId`: (FK) Optional reference to a transformation applied.

**Relationships:**
- **Data Point:** `dataPointId → DataPointEntity.id` *(Many-to-One)*
- **Step:** `stepId → StepEntity.id` *(Many-to-One)*
- **Transformation:** `transformationId → TransformationEntity.id` *(Many-to-One)*

---

#### ErrorWatch
**Description:** Monitors specific error types during a step's execution and defines actions for error handling.

**Attributes:**
- `id`: (PK) Unique identifier for the error watch.
- `stepId`: (FK) Links to the step monitoring the error.
- `errorType`: The type of error being watched.
- `onMetStepId`: (FK) Links to the step to execute if the error is triggered.

**Relationships:**
- **Step:** `stepId → StepEntity.id` *(Many-to-One)*
- **On Met Step:** `onMetStepId → StepEntity.id` *(Many-to-One)*

**Relational Data Model Overview (Additional Types)**

#### PackageRepo
**Description:** Represents potential sources or repositories for Python packages, such as PyPI or Conda.

**Attributes:**
- `id`: (PK) Unique identifier for the repository.
- `sourceType`: The type of repository (e.g., PyPI, Conda, custom).
- `sourceUrl`: URL of the repository.

---

#### Inquisition
**Description:** Represents a conditional check or "inquisition" within a workflow, determining redirection based on data point analysis.

**Attributes:**
- `id`: (PK) Unique identifier for the inquisition.
- `workflowStepId`: (FK) Links to the workflow step containing this inquisition.
- `class`: Specifies the class or type of inquisition.
- `dataPointId`: (FK) Links to the data point being analyzed.
- `onMetWorkflowStepId`: (FK) Links to the workflow step to execute if the condition is met.
- `executionOrder`: Order of execution within the step.

**Relationships:**
- **Workflow Step:** `workflowStepId → StepEntity.id` *(Many-to-One)*
- **Data Point:** `dataPointId → DataPointEntity.id` *(Many-to-One)*
- **On Met Workflow Step:** `onMetWorkflowStepId → StepEntity.id` *(Many-to-One)*

---

#### PreReqInjection
**Description:** Represents prerequisite data injection between workflows.

**Attributes:**
- `id`: (PK) Unique identifier for the injection.
- `junctionId`: (FK) Links to the workflow junction.
- `sourceDataPointId`: (FK) Links to the data point in the source workflow.
- `injectedDataPointId`: (FK) Links to the target workflow's data point.
- `name`: Optional name describing the injection.
- `description`: Optional details about the injection.

**Relationships:**
- **Workflow Junction:** `junctionId → WorkflowJunctionEntity.id` *(Many-to-One)*
- **Source Data Point:** `sourceDataPointId → DataPointEntity.id` *(Many-to-One)*
- **Injected Data Point:** `injectedDataPointId → DataPointEntity.id` *(Many-to-One)*

---

#### ProvisionDataPoint
**Description:** Links provisions to specific data points within a workflow.

**Attributes:**
- `id`: (PK) Unique identifier for the provision data point.
- `provisionId`: (FK) Links to the associated provision.
- `dataPointId`: (FK) Links to the data point.

**Relationships:**
- **Provision:** `provisionId → ProvisionEntity.id` *(Many-to-One)*
- **Data Point:** `dataPointId → DataPointEntity.id` *(Many-to-One)*

---

#### Provision
**Description:** Represents parameters and data provisions associated with a tool execution within a step.

**Attributes:**
- `id`: (PK) Unique identifier for the provision.
- `stepId`: (FK) Links to the associated step.
- `toolParamId`: (FK) Links to the tool parameter being provisioned.

**Relationships:**
- **Step:** `stepId → StepEntity.id` *(Many-to-One)*
- **Tool Parameter:** `toolParamId → ToolParamEntity.id` *(Many-to-One)*
- **Data Points:** One-to-Many relationship via `ProvisionDataPointEntity`.

---

#### WorkflowJunction
**Description:** Represents a transition between workflows, including conditions and prerequisites for the transition.

**Attributes:**
- `id`: (PK) Unique identifier for the junction.
- `sourceStepId`: (FK) Links to the step initiating the transition.
- `targetWorkflowId`: (FK) Links to the target workflow.
- `triggerInquisitionId`: (FK) Optional reference to the condition triggering the transition.

**Relationships:**
- **Source Step:** `sourceStepId → StepEntity.id` *(Many-to-One)*
- **Target Workflow:** `targetWorkflowId → WorkflowEntity.id` *(Many-to-One)*
- **Trigger Inquisition:** `triggerInquisitionId → InquisitionEntity.id` *(Many-to-One)*
- **Prerequisite Injections:** One-to-Many relationship via `PreReqInjectionEntity`.

---
	






### Transforms
	com.wini.owle.environs

Contains types useful for modeling a user's applied data processing that can be configured to properly manage the values contained in the workflow's data state.

#### AppliedTransformArg
**Description:** Represents arguments for an applied transformation.

**Attributes:**
- `id`: (PK) Unique identifier for the applied transform argument.
- `appliedTransformId`: (FK) Links to the applied transformation.
- `transformationArgId`: (FK) Links to the original transformation argument.
- `value`: Value assigned to this argument.

**Relationships:**
- **Applied Transformation:** `appliedTransformId → AppliedTransformEntity.id` *(Many-to-One)*
- **Transformation Argument:** `transformationArgId → TransformationArgEntity.id` *(Many-to-One)*

---

#### AppliedTransform
**Description:** Represents transformations applied to data points during workflow execution.

**Attributes:**
- `id`: (PK) Unique identifier for the applied transformation.
- `transformationId`: (FK) Links to the original transformation.
- `occurrence`: Context of the transformation (e.g., step execution or inquisition).
- `occurrenceId`: Identifier for the context instance.

**Relationships:**
- **Transformation:** `transformationId → TransformationEntity.id` *(Many-to-One)*
- **Arguments:** One-to-Many relationship via `AppliedTransformArgEntity`.

---

#### TransformationArg
**Description:** Represents an argument for a transformation, defining its type and optional description.

**Attributes:**
- `id`: (PK) Unique identifier for the transformation argument.
- `transformationId`: (FK) Links to the associated transformation.
- `typeId`: (FK) Links to the type definition for this argument.
- `name`: Name of the argument.
- `description`: Optional description of the argument.

**Relationships:**
- **Transformation:** `transformationId → TransformationEntity.id` *(Many-to-One)*
- **Type:** `typeId → TypeDeclarationEntity.id` *(Many-to-One)*

**Relational Data Model Overview (Transformations and Types)**

#### Transformation
**Description:** Represents a transformation process with a specified return type and related arguments.

**Attributes:**
- `id`: (PK) Unique identifier for the transformation.
- `name`: Name of the transformation.
- `description`: Detailed description of what the transformation does.
- `examples`: Optional examples demonstrating the transformation's usage.
- `returnsTypeId`: (FK) Reference to the return type of the transformation.

**Relationships:**
- **Return Type:** `returnsTypeId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Arguments:** One-to-Many relationship via `TransformationArgEntity`.
- **Target Types:** One-to-Many relationship via `TransformTargetTypeEntity`.

---

#### TransformTargetType
**Description:** Defines eligible types and result types for a transformation.

**Attributes:**
- `transformationId`: (FK, PK) Links to the associated transformation.
- `eligibleTypeDeclarationId`: (FK, PK) Links to the eligible type for this transformation.
- `resultTypeDeclarationId`: (FK) Links to the resulting type after the transformation.

**Relationships:**
- **Transformation:** `transformationId → TransformationEntity.id` *(Many-to-One)*
- **Eligible Type:** `eligibleTypeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Result Type:** `resultTypeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*

---








### Typing
	com.wini.owle.environs

Contains types useful for modeling python's data structures in order to properly plan interaction with a workflow's data.

#### TypeBound
**Description:** Captures constraints on type variables in complex type declarations.

**Attributes:**
- `id`: (PK) Unique identifier for the type bound.
- `typeVariableDeclarationId`: (FK) Links to the type variable.
- `boundTypeDeclarationId`: (FK) Links to the type declaration serving as the bound.

**Relationships:**
- **Type Variable:** `typeVariableDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Bound Type:** `boundTypeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*

---

#### TypeDeclaration
**Description:** Represents specific instances of types, including parameterized or nested types.

**Attributes:**
- `id`: (PK) Unique identifier for the type declaration.
- `typeDefinitionId`: (FK) Links to the core type definition.
- `name`: Optional alias for the type declaration.

**Relationships:**
- **Type Definition:** `typeDefinitionId → TypeDefinitionEntity.id` *(Many-to-One)*
- **Parameters:** One-to-Many relationship via `TypeParameterEntity`.
- **Bounds:** One-to-Many relationship via `TypeBoundEntity`.

---

#### TypeDefinition
**Description:** Defines core types, such as `int`, `list`, or `dict`, which can be instantiated in type declarations.

**Attributes:**
- `id`: (PK) Unique identifier for the type definition.
- `name`: Name of the type (e.g., `int`, `list`).
- `namespace`: Namespace of the type (e.g., `builtins`, `collections`).
- `versionSpecId`: (FK) Links to the version specification.

**Relationships:**
- **Version Specification:** `versionSpecId → VersionSpecEntity.id` *(Many-to-One)*
- **Declarations:** One-to-Many relationship via `TypeDeclarationEntity`.

---

#### TypeParameter
**Description:** Represents parameters in parameterized types, such as `List[int]`.

**Attributes:**
- `id`: (PK) Unique identifier for the type parameter.
- `typeDeclarationId`: (FK) Links to the parent type declaration.
- `position`: Position of the parameter within the type.
- `parameterTypeDeclarationId`: (FK) Links to the type of the parameter.

**Relationships:**
- **Parent Type Declaration:** `typeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Parameter Type:** `parameterTypeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*

---

#### TypeSchemaRef
**Description:** Represents references within type schemas, supporting nested or hierarchical type structures.

**Attributes:**
- `id`: (PK) Unique identifier for the schema reference.
- `parentRefId`: (FK) Optional reference to a parent schema for nested structures.
- `name`: Name of the property or field within the schema.
- `typeDeclarationId`: (FK) Links to the type declaration for this schema reference.
- `description`: Optional description of the schema reference.

**Relationships:**
- **Parent Schema:** `parentRefId → TypeSchemaRefEntity.id` *(Many-to-One)*
- **Type Declaration:** `typeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Child References:** One-to-Many recursive relationship via `parentRefId`.

**Relational Data Model Overview (Union, User, Variance, and Related Types)**

#### TypeUnionMember
**Description:** Represents individual member types within a union type declaration.

**Attributes:**
- `unionTypeDeclarationId`: (FK, PK) Links to the parent union type declaration.
- `memberTypeDeclarationId`: (FK, PK) Links to the specific type within the union.

**Relationships:**
- **Union Type Declaration:** `unionTypeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Member Type Declaration:** `memberTypeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*

---

#### TypeVariance
**Description:** Captures variance annotations on type variables, such as 'covariant', 'contravariant', and 'invariant'.

**Attributes:**
- `id`: (PK) Unique identifier for the type variance annotation.
- `typeVariableDeclarationId`: (FK) Links to the type variable declaration.
- `variance`: Specifies the type variance (`covariant`, `contravariant`, `invariant`).

**Relationships:**
- **Type Variable Declaration:** `typeVariableDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*

---








### User
	com.wini.owle.environs

Contains types that represent user-specific data that is not inherently associated with workflow modeling or execution.
#### TagRelation
**Description:** Represents a hierarchical relationship between tags, capturing parent-child associations.

**Attributes:**
- `parentTagId`: (FK) The parent tag in the relationship.
- `childTagId`: (FK) The child tag in the relationship.

**Relationships:**
- **Parent Tag:** `parentTagId → TagEntity.id` *(One-to-One)*
- **Child Tag:** `childTagId → TagEntity.id` *(One-to-One)*

**Constraints:**
- Composite Primary Key: (`parentTagId`, `childTagId`)

---

#### PaymentMethod
**Description:** Represents a payment method associated with a user, capturing details like card brand and last four digits.

**Attributes:**
- `stripePaymentMethodId`: (PK) Unique identifier sourced from Stripe.
- `userId`: (FK) Links to the owning user.
- `cardBrand`: The brand of the card (e.g., Visa, MasterCard).
- `lastFour`: The last four digits of the card.
- `isDefault`: Indicates if this is the user's default payment method.

**Relationships:**
- **User:** `userId → UserEntity.id` *(Many-to-One)*

---

#### Tag
**Description:** Represents a tag that can be associated with workflow steps, enabling categorization and filtering.

**Attributes:**
- `id`: (PK) Unique identifier for the tag.
- `name`: Unique name of the tag.
- `description`: Optional description of the tag.

**Relationships:**
- **Steps:** Many-to-Many relationship via `StepEntity` through `step_tags` table.

---







#### UserEntity
**Description:** Represents a user in the system with authentication, subscription details, and payment methods.

**Attributes:**
- `id`: (PK) Unique identifier for the user.
- `email`: User’s email address.
- `password`: Hashed password.
- `subscriptionStatus`: Current subscription status (`registered`, `active`, `cancelled`, `on hold`).
- `createdAt`: Timestamp for user creation.
- `updatedAt`: Timestamp for last update.

**Relationships:**
- **Payment Methods:** One-to-Many relationship via `PaymentMethodEntity`.



### Workflows
	com.wini.owle.environs

Contains types that represent the fundamental elements of Owle-E processes 

#### Workflow
**Description:** Represents a workflow, containing steps and data points. Metadata is stored as JSON for flexible information capture.

**Attributes:**
- `id`: (PK) Unique identifier for the workflow.
- `name`: Name of the workflow.
- `description`: Description of the workflow's purpose.
- `pythonVersion`: Specifies the Python version required to execute the workflow.
- `parentFolderId`: (FK) Links to the folder where this workflow is organized.
- `metadata`: JSON-encoded additional metadata.

**Relationships:**
- **Folder:** `parentFolderId → WorkflowFolderEntity.id` *(Many-to-One)*
- **Steps:** One-to-Many relationship via `StepEntity`.
- **DataPoints:** One-to-Many relationship via `DataPointEntity`.

---


#### WorkflowFolder
**Description:** Represents a folder for organizing workflows in a hierarchical structure.

**Attributes:**
- `id`: (PK) Unique identifier for the workflow folder.
- `parentFolderId`: (FK) Links to the parent folder in the hierarchy.
- `name`: Name of the folder.
- `description`: Optional description of the folder's purpose.

**Relationships:**
- **Parent Folder:** `parentFolderId → WorkflowFolderEntity.id` *(One-to-One)*
- **Subfolders:** Self-referential One-to-Many relationship.
- **Workflows:** One-to-Many relationship via `WorkflowEntity`.

---

#### Step
**Description:** Represents a step within a workflow, defining its tool dependency, next and previous steps, and whether it serves as the workflow's head step.

**Attributes:**
- `id`: (PK) Unique identifier for the step.
- `name`: Name of the step.
- `description`: Optional description of the step’s purpose.
- `workflowId`: (FK) Links to the workflow containing this step.
- `isWorkflowHead`: Indicates if this step is the first step in the workflow.
- `toolId`: (FK) Links to the tool required to execute this step.
- `nextStepId`: (FK) Links to the next step in the sequence.

**Relationships:**
- **Workflow:** `workflowId → WorkflowEntity.id` *(Many-to-One)*
- **Tool:** `toolId → ToolEntity.id` *(Many-to-One)*
- **Next Step:** `nextStepId → StepEntity.id` *(One-to-One)*
- **Previous Step:** Self-referential One-to-One relationship.

---

#### Tool
**Description:** Represents a tool utilized within a step, containing the tool's body, optional description, return type, and dependencies.

**Attributes:**
- `id`: (PK) Unique identifier for the tool.
- `name`: Name of the tool.
- `description`: Optional description of the tool.
- `body`: The main body or code of the tool.
- `returnTypeDeclarationId`: (FK) Links to the return type definition.

**Relationships:**
- **Return Type:** `returnTypeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Steps:** One-to-Many relationship via `StepEntity`.
- **Package Dependencies:** One-to-Many relationship via `ToolPackageDependencyEntity`.

---

#### ToolParam
**Description:** Represents parameters for tools within workflows, linking to their type declarations.

**Attributes:**
- `id`: (PK) Unique identifier for the tool parameter.
- `toolId`: (FK) Links to the tool associated with this parameter.
- `name`: Name of the parameter.
- `typeDeclarationId`: (FK) Links to the type declaration of the parameter.
- `hasDefault`: Boolean indicating if the parameter has a default value.

**Relationships:**
- **Tool:** `toolId → ToolEntity.id` *(Many-to-One)*
- **Type Declaration:** `typeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*

#### User
**Description:** Represents a user in the system with authentication, subscription details, and payment methods.

**Attributes:**
- `id`: (PK) Unique identifier for the user.
- `email`: User’s email address.
- `password`: Hashed password.
- `subscriptionStatus`: Current subscription status (`registered`, `active`, `cancelled`, `on hold`).
- `createdAt`: Timestamp for user creation.
- `updatedAt`: Timestamp for last update.

**Relationships:**
- **Payment Methods:** One-to-Many relationship via `PaymentMethodEntity`.

---

#### DataPoint
**Description:** Represents a data point used within workflows and steps, capturing input/output details.

**Attributes:**
- `id`: (PK) Unique identifier for the data point.
- `name`: Name of the data point.
- `typeDeclarationId`: (FK) Links to the type declaration of the data point.
- `description`: Optional description.

**Relationships:**
- **Type Declaration:** `typeDeclarationId → TypeDeclarationEntity.id` *(Many-to-One)*
- **Provisions:** One-to-Many relationship via `ProvisionDataPointEntity`.

---

#### Phase
**Description:** Represents a phase within a workflow, grouping steps or subphases for organization.

**Attributes:**
- `id`: (PK) Unique identifier for the phase.
- `workflowId`: (FK) Links to the workflow containing this phase.
- `parentPhaseId`: (FK) Optional reference to a parent phase for hierarchical organization.
- `name`: Name of the phase.
- `description`: Optional description.

**Relationships:**
- **Workflow:** `workflowId → WorkflowEntity.id` *(Many-to-One)*
- **Parent Phase:** `parentPhaseId → PhaseEntity.id` *(Many-to-One)*
- **Child Phases:** One-to-Many recursive relationship via `parentPhaseId`.

---







## 3. Application Logic

### 3.1 Workflow Creation and Modification
- Users can create workflows by defining phases and steps
- Steps are associated with specific tools or functions
- Data items are defined with types, default values, and storage capacities
- Workflows can be interconnected using workflow junctions

### 3.2 Data Flow
- Data items are consumed and produced by steps
- Assignment rules define how data is transformed or assigned
- Data can be passed between workflows through consumer injections

### 3.3 Execution Order
- Steps within a phase are executed based on their display_order
- The default_next_step_id in the steps table provides basic sequencing
- Phases are executed based on their hierarchical structure and display_order
- Workflow junctions allow for dynamic transitions between workflows

### 3.4 Validation and Error Handling
- Inspectors associated with steps provide runtime validation
- Inspectors can redirect workflow execution based on conditions
- Inspectors can trigger transitions to other workflows

## 4. Workflow Composition

### 4.1 Interconnecting Workflows
- Workflows can be interconnected using the workflow_junctions table
- Transitions between workflows can be triggered by steps or inspectors
- Child workflows maintain their internal integrity during execution

### 4.2 Data Sharing
- Data is shared between workflows through the consumer_injections mechanism
- Allows for controlled data passage between interconnected workflows
- Maintains clear boundaries and data integrity between workflows

## 5. Data Assignment and Overrides

### 5.1 Assignment Rules
- Define how data is transformed or assigned within steps
- Rules are associated with specific data types through the applicable_types system
- Examples include VALUE_EXACT, VALUE_INDEXED, VALUE_SPLIT, etc.

### 5.2 Data Injection
- Allows parent workflows to provide data to child workflows at junction points
- Defined through the consumer_injections table
- Ensures controlled and explicit data sharing between workflows

## 6. Execution and Validation

### 6.1 Execution Flow
1. Start with the head step of the workflow
2. Execute steps in order, respecting phase hierarchy and default next steps
3. Apply assignment rules for data transformations
4. Run inspectors for validation and potential redirection
5. Transition to other workflows at junction points as needed

### 6.2 Validation
- Inspectors provide runtime validation for steps
- Can redirect workflow based on specific conditions
- Ensure data integrity and workflow correctness
- Can trigger transitions to other workflows

## 7. Safety and Constraints

### 7.1 Execution Environment
- Workflows are designed to be executed in a controlled environment
- The specific nature of this environment (e.g., containerization) is implementation-dependent

### 7.2 Tool Constraints
- Steps are associated with tools through a VARCHAR field
- Implementation should ensure that only approved tools can be used

### 7.3 Data Integrity
- Data items have a store_cap to control mutability
- Consumer injections provide controlled data sharing between workflows

### 7.4 Workflow Integrity
- Only one head step per workflow is enforced through a unique index
- Workflow junctions provide controlled transitions between workflows

This documentation provides a comprehensive overview of the Owl-e workflow system's data model and application logic. It serves as a foundation for implementing, maintaining, and extending the system. The design emphasizes modularity, safety, and flexibility, allowing for the creation of complex, interconnected workflow systems while maintaining clear boundaries and data integrity between individual workflows.