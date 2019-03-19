# Innometrics IntelliJ tracker plugin
Innometrics tracker plugin for IntelliJ platform based IDEs, that performs non-invasive measurements.

##  Main  user interface
Plugin provides user interface for following functionality:
- User authorization
- Manual metrics submission
- Logout

These actions can be accessed via a  toolbar menu: `Tools` -> `Innometrics`.

## Installation
As of now, plugin requires manual installation.
1. Download .zip image of this repository.
2. In your IDE, select `Settings` -> `Plugins` -> `Install plugin from disk` -> select downloaded `.zip`.

## Dependencies
Plugin relies on IntelliJ platform APIs and [InnometricsJavaCore](https://github.com/WingedDoom/innometrics-java-core) library.

## Functionality
Plugin can track and automatically submit activities of following types:

- intellij_lines_change
- intellij_lines_insert
- intellij_lines_delete
- intellij_tab_name