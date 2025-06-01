
uses types_inspect to interrogate the types in the stdlib of python in whatever version is available in the environment currently running.

*Priority 4*: We had almost completed an effort to automate this process more effectively by using subprocess commands to create and activate a venv for the next version to introspect on. The challenge that disrupted that effort surrounded the challenge of wisely reinstalling the necessary dependencies for the version