# Owl-e, by What Is Not Is

## Table of Contents
1. [Introduction](#introduction)
2. [Data Model](#data-model)
3. [Design Philosophy](#design-philosophy)
4. [Installation and Execution Environment](#installation)
5. [Application Logic](#application-logic)

## 1. Introduction

This document outlines the data model and application logic for Owl-e, a workflow system designed to support complex, hierarchical processes with a focus on modularity, safety, and flexibility. The system allows for the creation, execution, and management of workflows that can be interconnected and nested, providing a powerful tool for process automation and management.

### 1.1 Long-Form Introductory Presentation
Hello! I am What Is Not Is, and it's my great pleasure today to finally be launching a project I've been cooking up for some months now -- an AI innovation where the article about it doesn't begin with the word 'Introducing', and/or end with a big disappointment.

Quickly -- who am I? Why should you listen? A lifelong programmer with other qualifications we can talk about later in social science, as well as the arts and media production. What is the nature of my grievance with the current state of AI innovation? Glad I asked.

To put it generously, innovation is a funny thing. Sometimes you ship samples of tea in little baggies you had on hand and your customers write you letters to say "the tea was mediocre but where on Earth did you find these little tea permeable tea infusing baggies, I'll take a thousand", and you realize you've completely by accident invented a "game-changer". Most of the time, we invent in the direction of our vision with fingers crossed because someone told us it helps.

If you're like me, you've spent half of the last year dazzled by what you've seen AI do in a video demonstrating or describing it -- and the other half banging your head against a wall trying to get that solution to cooperate and work the way its authors said it would, all because your imagination fell right in line with the direction theirs was headed when they conceived of it. I'm not trying to paint an entire industry out to be scammers and charlatans. There is not a great shortage, but in large part this is just kind of how progress works. We can't say what we deserve if the entire range of what's *possible* even is a matter beyond the scope of what we can understand. The way we approach things is the way we're capable of making sense of them. I'll give you an example.

First we noticed, justifiably, that generative models have an astounding knack for composition in a given style, or an individual's unique voice. Well, why is that? The truth is, identifying the features that together comprise a sound statistical determination of "what makes a body of work *distinct*" is not an unimaginable challenge. There are a variety of birds with shockingly intelligent behavior that we now describe well or poorly as 'like a five year old' or something near it, but the actual equivolent value we're referencing, the five year old, is certainly not a master impressionist. This is the question of the nature of specialization. Isn't it problematically anthopo-centric in concept? How much of what's generalizable is needed before you can reach it? How much of who or what you are is mixed up in the business of *what* you do? Are you sure you know what that is? We certainly feel intuitively that we do, and those patterns -- of intonation, of word choice, word count, grammar, etc.-- with what makes our *self* feel unique. In other words, our expressing ourselves -- our expressions -- become the vehicle of, the evidence for, our being expressed in the sense of a thing's emergence, or anomlic occurrence. These traits are the water to our existential 'fish'.

Did you know there's a Post-It note stuck to the sleeve of your shirt right now? You will likely say "I knew there wasn't one, and there isn't." But is this really different from your "Has no Post-It note stuck to their sleeve presently" that was excluded from the traits about yourself with which you identify?

Bill Hader is a brilliant impressionist, yeah? [Edit in clip of Bill doing his tawn-tawn vocalizations impression]. Well, that doesn't make him capable of tolerating sub-zero conditions, or running for miles with a smuggler-turned-Rebellion-hero on his back.

What I'm saying, more precisely, is that it didn't *feel* like a huge leap in logic to go from point A -- "it can adopt a person's 'likeness', yet it fails to conduct complex tasks on its own" to point B -- "So we need to throw many variations of a person at the problem, variations in keeping with the structure of real professional organizations. We'll give each one a role, like a job title, and a little backstory slash description of their personality, an objective to meet, and a tool to meet it with software". Well -- that only seems like the right tool for the job because it's in the realm of what we currently understand as in the range of possibilities -- and a novel-seeming addition to the range, at that. But the job title part isn't going to help, really. Your job title may affect your salary and your self-esteem, but not so the AI model. If the 'Job Title Fairy' visits you in your sleep and changes yours with her magic wand, it doesn't retroactively unlock branches of the skill tree like you're Neo getting trained Kung Fu through a chrome spike in your brain. You can have every kind of privilege, but no natural talent -- or the inverse of these; be meticulous or be lucky. You can be privileged, brilliant, dedicated, and likable and still be a complete failure in life. You can take that one on authority from yours truly.

When you come right down to it, trying to Stephen King yourself a software implementation answer to your challenge is just a miserable, unwieldy, ineffective 'handle' to have on your problem. And if you've tried, you know I'm right. Yes, sometimes you can get an error and feed that error right back to the same model and get it to 'self-correct'. Unfortunately, extrapolating from *that* anecdotal event to conclude that all problems should be addressed that way, or even could be -- at least, from where we currently stand -- is just a preposterous reach.

Let's talk about how working with an AI partner *can* be really effective. Suppose you want to write a calculator app for mobile devices. You can ask the model to list a bunch of mathematical operations it needs to be able to support. Wow, that's much faster than you can list mathematical operations. Cool. So you iterate over each item in that list and get a bunch of functions that fulfill them. Still fast, and mostly correct to boot. We're still way ahead on time. But -- a bunch of them were only pseudo-implementations or outright hallucinations; you *do* want to double-check its work of course, so you ask it to write tests to know they're working. Wait -- this one isn't passing. You ask it to try again, it doesn't work. Hopefully soon you take a step back and think about where the disconnect in all of this is. Maybe *you* explained what you wanted badly, or didn't understand yourself what the operation is supposed to actually do. Maybe to save time you didn't read everything and it added a problem factor you won't offer a chance to fix any time soon. Maybe you convince yourself it's the model's fault. Maybe part of what it wrote doesn't integrate properly with aspects of the code base it had given you previously, because at the time you were discussing a different challenge at a different level of detail and that's long since out of scope and in excess of its rolling context window. "Well", you ask, impatient for the time advantage being squandered, "why didn't you anticipate this error condition that's suddenly popped up?"

"Well", it would probably reply with equivalent frustration if this were in its capacity, "why didn't *you* ask about the possible error conditions that could have popped up? I can't answer every question at once even if you somehow actually manage to *ask* them all at once! So -- okay, let's figure out a way to account for this error case..."

You see where this is going? It's going in the direction of just -- a job as a software developer. You're tempted to say "well, see, we needed a coder personality and an test engineer personality, then it would have worked itself out". NO! Because the part that's actually missing is you there setting the terms of what needs to be considered and when. What things are useful in what capacity, and additionally need to bear a consideration to what other things that are not precisely related necessarily, but relevant, absolutely, if you have enough context to keep it all relevant when it needs to be, or if not, hey everything slips through the cracks now and again, human and AI alike. It's different levels of context, for different considerations, in diverse sizes and shapes, with a diverse set of tools to bear on many different challenges, intended to be met at different depths of fulfillment.

If I'm making this all sound totally impossible to manage, well, yes and no. Yes, brains are amazing -- no, in the sense of this important point here: *Most of the time*? The workflow for accomplishing a complex task is actually pretty well established. We have this amazing new tool and it's miraculous that it's 90% accurate, but that 10% of failure cases tend to bring everything to total impassable catastrophe. You can't just say "be self-correcting" like it's "abra cadabra" -- at least, not yet, and when we reach that point, obviously, we won't *have to* say it.

If you want to tell the AI to take the first big swing at the problem and clean up the bad results in a way you *can* articulate, well -- then you *have to* articulate it. That's a valid approach. Alternatively, you could get the ball rolling and integrate AI function calling *where it's particularly adept at handling an issue*. Owl-E is designed with this sensibility governing it. 


## 2. Data Model

### 2.1 Core Entities

#### Workflows
- Represents the overall process
- Contains phases, steps, and data items
- Require the specific Python version with which it will be executed 
- Has a workspace for execution, where the database holding the process's state and files within the process's scope are accessed
- Can be chained one to another, where the result of the parent workflow can provide dependencies of the subsidiary

#### Phases
- Purely conceptual groupings of steps within a workflow
- Can be nested (phases within phases)
- Provides contextual scope and structure to the workflow for the sake of comprehensibility alone
- Supports a 'display_order', enabling the user to arrange phases into their preferred order 

#### Data Items
- Represents input, output, or intermediate data in a workflow
- Provides values that are passed to steps as their functions' parameters
- May hold values which must be known prior to the workflow's execution, which remain read-only
- Allows that its values are modified according to the policy of a 'revision' configured for a step, which occurs immediately after the tool function returns successfully
- Provides its values to 'inquisitions' executed prior to the execution flow being directed to a completed step's default next

#### Steps
- Individual actions or operations within a workflow or phase
- There is no anticipated execution sequence
- May be the 'workflow root' step, of which there may be only one associated with a workflow, as it represents the necessary first step to be executed 
- A 'default next' step may be defined, but it may be superseded in consideration of what is represented by other members of its type
- Where no 'default next' exists, and it is not redirected owing to these superseding elements' presence, the workflow concludes
- Associated with a specific tool function's execution

### Required Packages
- Define the python packages that will be utilized by the tool functions of steps in the workflow
- Following the M.O. of python virtual environment library dependency package version specifications 
- Allow for predictable and easily understood configurations and processes
- Support ease of configuration by informing (by version reference to a publicly-available dependency) about the types in use for the considerations of the other elements 

### 2.2 Supporting Entities

#### Provisions: Data Consumption
- Requests values from the workflow data points to fulfill a step's required parameter values
- Associate a step's parameter with a value to be retried from the data points found in state
- Are required where no default value exists for a given parameter in a step's tool function
- Are optionally defined for parameters with a default value
- May require that a specified de-serialization policy be executed on the value retrieved from the database
- May not provide the data point values of parent workflows to the steps of subsidiary workflows once they have begun

#### Revisions: Data Assignment
- Are optionally configured for a step
- Represent the assignment of values in the workflow data points 
- Define the policy under which the value will be assigned
- Can not occur if the step suffers an anticipated error
- Can not be superseded by some anticipated case causing a redirection
- May define some easily-undertaken modification of a step's result before its assignment
- Require that a serialization step is undertaken before attempting to assign a type that can not be stored in the SQLite state database

#### Error Watches: Step Failure Handling
- Articulate a specific type of exception (e.g., ValueError) that may be caught during a step's tool function executing
- Support specifying a list of strings that match function names within the call stack to serve as filters refining specific propagation paths only as valid matches of the anticipated error
- Where no call stack filters are applied, any instance of the error, regardless of where it was raised matches the configured error watch
- The values of its call stack have implicit meaning in their relative position
- Where configured with a call stack list specifying a single function, only where the error has occurred within that function or a function called by it has the error matched with the configured watch
- Where multiple functions are specified, each function in the list must appear in the error's call stack, in the given order (i.e., the topmost function in the list must call the second, and so on)
- It is not permitted for multiple error watches to specify the same monitored type and the same hierarchy of functions
- Where multiple error watches might be called fulfilled by a raised exception, the most specific (i.e. bearing the call stack filters of highest length) is the error watch matched
- Requires that the user configures a configured step the workflow should transition to, allowing for flexible error recovery processes

#### Inquisitions: Conditional Redirection
- Occur following a step's tool function's successful execution
- Represent a configured evaluation to be conducted using a value retrieved from the concluded step's workflow's data points
- May be used to detect logical errors, whose presence has not affected the step execution and must be determined
- May be used to detect any number of non-error conditions and, on returning True for its defined function, indicates that its configured 'on met' step should be the next to run
- Have a meaningful predefined sequence governing their execution order

#### Workflow Junctions
- Define points where one workflow can transition to another
- Allow for complex, interconnected workflow systems where a parent workflow's success is taken further
- Ensure the workflow's modularity
- Imply directionally acyclic motion, preventing any step in its own scope workflow from again executing while the process continues onto subsidiary workflows
- Implies finalization of its own data points' values -- no further provisions, revisions, or inquisitions may occur as its steps may not be executed
- In specifying the next workflow to execute, the next step may not be specified, as this is necessarily its single step serving as 'workflow head'.
- Must be associated with a step that has no default next step, and accordingly can only be triggered where not superseded by error watch fulfillment or inquisition evaluations resulting true. 

### Prerequisite Injections & Data Sharing
  - Allow for seamless data sharing between workflows
  - Supersede a workflow's 'readonly' required data point value, presupposing that one among its data points is the value provided
  - Are optional considerations; in the absence of any configured injection, the subsidiary workflow is self-reliant, likely reflecting an ill-advised configuration, but one still permitted as it is not possible to restrict step-associated tool functions from acting upon values that are not tracked within the workflow's data points

### Assignment Rules
- Define how data is transformed or assigned within steps
- Rules are associated with specific data types through the applicable_types system
- Examples include VALUE_EXACT, VALUE_INDEXED, VALUE_SPLIT, etc.
- May require an additional value being specified, e.g. the specific index to be stored where the rule defined indicates that the value returned by the tool function should have it's nth index accessed.
- The assignment rules presented to the user in the configuration of a 'revision' to occur after a step's execution, the specific set of assignment rules reflects only those associated with the type returned by the tool function

### Navigating Workflows: Folders and Tags
- Workflow folders allow users to group workflows into a logical well-organized structure of their choosing
- Workflow folders may themselves contain workflow folders
- A number of tags are pre-populated that serve as useful categorical filters to apply when browsing the Owl-E workflows available
- When the 'filter' icon button sitting at the application-level header is clicked, a toggle button is displayed for all workflow tags present locally, that when active triggers the display of any workflow associated with the tag


### 2.3 Schema Overview
[Redacted for contextual clarity]
```

## 3 Design Philosophy

Owl-e is a sophisticated workflow system designed to bridge the gap between practical automation needs and advanced AI safety considerations. While at its core, Owl-e may appear to be "nothing more than an imperative software execution framework," its unique architecture and approach offer significant advantages in multiple domains:

1. For end-users, it provides a powerful, flexible tool for automating complex processes and workflows.
2. For developers, it offers a robust platform for creating and managing interconnected systems.
3. For AI researchers, it presents a novel approach to addressing critical challenges in AI safety, autonomous learning, and self-improvement.

This document outlines the key conceptual strengths of our workflow system, demonstrating its potential across these domains.

## Key Concepts and Strengths

### 3.1. User-Centric Workflow Design

Owl-e empowers users to:

- Create complex, multi-step workflows without extensive programming knowledge
- Visualize and manage processes through an intuitive, hierarchical structure
- Easily modify and adapt workflows to changing needs
- Integrate various tools and services into a cohesive process

**Implication**: Owl-e democratizes process automation, allowing users from various backgrounds to create sophisticated workflows tailored to their specific needs.

### 3.2. Contextual Scoping

The workflow system's hierarchical structure of phases and steps provides a clear, scoped context for each operation. This approach:

- Reduces cognitive load for users (and computational complexity for AI) at each step
- Maintains focus on relevant information and objectives
- Mitigates the risk of context loss or topic drift in extended processes

**Implication**: This structure could potentially solve the context window limitations in current language models and other AI systems, allowing for more coherent and focused long-term reasoning.

### 3.3. Universal Task Representation

By representing complex tasks as sequences of well-defined steps, the system creates a universal language for describing processes. This approach:

- Allows for the breakdown of any task into "atomic" operations
- Facilitates the creation of generalized AI systems capable of tackling a wide range of tasks
- Enables the composition of complex behaviors from simpler, well-understood components

**Implication**: This universality could be key in developing AI systems with general intelligence capabilities, able to understand and execute a vast array of tasks using a common framework.

### 3.4. Modular and Reusable Components

Owl-e's design promotes:

- Creation of reusable workflow components
- Easy sharing and collaboration on workflow designs
- Building complex systems from simpler, well-tested modules

**Implication**: This modularity accelerates development, improves reliability, and facilitates the growth of a community-driven ecosystem of workflow components.

### 3.5. Rigorous Validation and Testing

Owl-e's unique approach allows for clear definition of inputs and expected outputs for each step and each workflow, effectively erasing the divide between implementating solutions and automated testing, and turning each step into a self-evident demonstration of its own reliability. This feature:

- Enables automated testing and validation
- Ensures that simulated environments are easily paramaterized and rapidly executed in a lightweight, isolated manner
- Allows for simplistic performance benchmarking, comparing workflows themselves, or comparing different LLM models under any battery of challenges, or comparing the last output against the expected output to offer a self-learniing system the feedback it needs to improve its own performance
- Facilitates continuous improvement through iterative refinement and optimization, as the workflow is fine-tuned to achieve specific goals -- whether those goals are inherent to the workflow's approach and its designer's vision, or rapidly executed attempts of a task by a machine learning system at a higher reward 
- Allows for precise measurements concerning accuracy, reliability, and performance under a common standard for effortless environment configuration and execution -- making peer-reviews of models, their published benchmarks, and the reproducability of any demonstration faster, more democratized, and more transparent.

**Implication**: This approach creates a pathway for both users and AI systems to engage in safe, verifiable, incremental imprvements, and engage more meaningfully with real results that speak for themselves.

### 3.6. Constrained Self-Improvement

By allowing modifications and optimizations within a predefined set of tools and operations, optionally within an unimpeachable containerized execution environment, the system enables:

- Self-improvement and optimization while maintaining safety boundaries and ethical outcomes, where advancements prove their merit through clear, measurable outcomes before being deployed unsupervised to unpredictable results.
- Clear metrics for success (e.g., 100% accuracy and reliability for a given task across a representative scope of possible influences)
- Focus on efficiency improvements once a task is "solved"

**Implication**: This addresses one of the core concerns in AI safety - allowing AI systems to improve themselves without risking uncontrolled or harmful changes.

### 3.7. Value Alignment Through Constraints

Rather than attempting to instill abstract values, the system bakes in value alignment at a fundamental level by:

- Limiting operations to a predefined set of tools and actions
- Creating a "sandbox" where processes can only operate in ways aligned with intended values and goals
- Engineers can demonstrate safety by demonstrated avoidance of tool 'stubs' representing hazzardous options approaches to task achievement that might reflect a poorly-aligned model 
- Allowing administrators to carefully curate the available tools and capabilities
- Enabling innovations to be made faster as we shorten the time between an announcement and broad consensus  about the mer, scientific rigor, exposing misguided or ineffective  broad consensus overly-cautious 

**Implication**: This approach offers a novel solution to the value alignment problem in AI safety, ensuring that AI systems remain aligned with human values even as they evolve and improve.

### 3.8. Simulation and Safe Testing

The ability to execute workflows in a simulated environment provides:

- A safe space for testing and validation without real-world consequences
- Opportunities for extensive "what-if" scenario testing
- A platform for AI to engage in "mental simulations" and planning

**Implication**: This feature is crucial for developing AI systems that can handle complex, real-world tasks without risking harm during the learning and improvement process.

### 3.9. Explainability and Auditability

The step-by-step nature of workflows enhances explainability by:

- Making the decision-making process transparent and traceable
- Allowing for detailed audits of behavior and decisions
- Facilitating the identification of specific steps or components that lead to particular outcomes

**Implication**: This addresses a major concern in current AI systems - the "black box" problem - by providing clear insights into how and why a system makes specific decisions.

### 3.10. Scalability and Integration

Owl-e's architecture supports:

- Seamless scaling from simple tasks to complex, distributed workflows
- Integration with existing tools, APIs, and services
- Adaptation to various execution environments (local, cloud, containerized)

**Implication**: This flexibility ensures that Owl-e can grow with user needs and adapt to evolving technological landscapes.

## Conclusion

Owl-e represents a significant advancement in workflow management and AI system design. For end-users, it offers an intuitive, powerful platform for process automation and optimization. For developers, it provides a flexible, modular framework for building complex systems. And for AI researchers, it presents a novel approach to addressing key challenges in AI safety and development.

By providing a structured yet flexible environment for task execution, self-improvement, and value alignment, Owl-e offers a promising path forward for developing systems that are not only powerful and self-improving but also inherently safe, explainable, and aligned with intended values.

As we continue to develop and refine Owl-e, we open up new possibilities for creating workflows and AI systems that can truly understand, plan, and execute complex tasks autonomously, all while maintaining the safety, control, and user-centricity that are crucial for responsible automation and AI development.


## 4. Installation and Execution Environment
- Workflows are designed to be -- ideally -- executed within a controlled environment, and must accordingly support workflow within a docker container. 
- Containerized execution is a recommended practice, rather than a matter of necessity; accordingly the default installation is the default installation methodology of the user's operating system
- The installation process must allow the user to 'opt in' to an installation in the local system that is itself fully dockerized
- Where the user chooses to opt in to a docker-only installation, if the operating system is Windows and the user has not previously enabled WSL2, they must agree to enabling WSL2 and the subsequent reboot before they can use the application, or decline to install locally as any other application.

## 5. Application Logic

### 5.1 Workflow Creation and Modification
In Owl-E, users can create workflows through an intuitive UI that allows them to configure various steps and phases. Workflows are composed of phases, which help users organize and visualize their tasks, but do not affect execution order.

To create or modify workflows, users can:

Traverse nested workflow folders to locate or create workflows.
Define phases, which are purely organizational.
Add steps within workflows using a plus icon button available in the hover state of any workflow element.
Configure step actions, tools, and data points associated with each step.
Each step is associated with a specific tool or function, which the user selects from a list of available tools (either predefined or custom-added by the user).

Tool Selection and Parameter Assignment:
Once a user has chosen a tool (e.g., pyautogui.moveTo), the UI will present available parameters from that tool’s documentation. The user will then be guided to assign values to those parameters using the existing data points in the workflow or prompted to define new data points if needed. Owl-E provides full documentation and parameter types for the selected tool, allowing the user to reason through the values required.

This process simplifies adding steps, tools, and parameters while ensuring the user does not need to switch between the application and external development environments like IDEs. The intuitive UI design streamlines tool selection, parameter assignment, and value configuration, making it accessible to users of various skill levels.

### 5.2 Execution Order
Head Step and End of Workflow
The workflow head step is always the first step executed in any workflow. This is a core rule in Owl-E and cannot be changed. Execution starts at the head step and proceeds according to the step-to-step configuration.

A final step is defined as any step that has no default_next value and no configured inspectors or inquisitions. This step will terminate the workflow unless an error occurs or inquisitions dictate otherwise.

If a step has a default_next configuration, the workflow will proceed to the next step as defined unless interrupted by an error or inquisition that causes a redirection.

Phases vs. Execution Order
Phases are purely organizational and do not affect the execution flow of a workflow. They exist to help users logically structure their steps, but the actual execution flow is governed by the following:

Workflow Head: The first step to execute.
Default Next: The next step to execute, unless otherwise redirected.
Error Watches: These monitor errors and allow the workflow to redirect based on error conditions.
Inquisitions: Inquisitions are conditional checks that can reroute execution based on the current state.
Execution order is fully controlled by the steps and their configurations, meaning workflows can freely move between phases if the user defines such transitions.

### 5.3 Tool Dependencies and Version Control
Owl-E provides a robust system for managing tool dependencies. When a user selects a tool for a step, they must also declare the required Python library and its version (e.g., pyautogui version 1.0.0). This ensures that all dependencies are properly tracked and installed in the execution environment.

To add a tool dependency:

Click on the Tool Dependency button when configuring a workflow.
Search for available Python libraries and select the desired version.
Assign the tool to a step in the workflow, ensuring that the tool’s function (e.g., pyautogui.moveTo) is available with the appropriate parameters and documentation.
Owl-E handles dependency management by ensuring that the necessary Python packages are installed and available in the execution environment before running the workflow. This process helps maintain reproducibility and allows users to manage complex workflows without worrying about mismatched versions or missing libraries.