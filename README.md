# Owl-E 

Owl-E is a sophisticated workflow automation and agentic AI software platform designed to bridge the gap between practical automation needs and advanced AI safety considerations. While at its core, Owl-e may appear to be "nothing more than an imperative software execution framework," its unique architecture and approach offer significant advantages in multiple domains:

1. For end-users, it provides a powerful, flexible tool for automating complex processes and workflows.
2. For developers, it offers a robust platform for creating and managing interconnected systems.
3. For AI researchers, it presents a novel approach to addressing critical challenges in AI safety, autonomous learning, and self-improvement.


For an in-depth exploration, reference the Owl-E documentation `/docs/Owl-E.md`


* `/composeApp` is for code that will be shared across Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/server` is for the Ktor server application backend.

* `/shared` is for the code that will be shared between all targets in the project.
