Authentication App

A simple Android application demonstrating an authentication flow (login & registration) with local persistence. Built following modern Android development practices, referencing the provided Figma design.

Features
	•	Login & Registration
Stores users in a local database.
Keeps the user logged in between sessions.
	•	Validation & Errors
Displays messages for invalid fields, existing users, or incorrect credentials.
	•	Home Screen
Greets the logged-in user and provides a log-out option.

Project Structure
	•	UI: Jetpack Compose, MVVM architecture, Hilt for DI.
	•	Database: Local persistence of user data (e.g., Room).
	•	Navigation: Simple flow from Auth → Home, with user session tracking.

Setup
	1.	Clone the repo.
	2.	Open in Android Studio.
	3.	Run on an emulator or device.

Tests
	•	Unit tests for business logic.
	•	Compose UI tests for screen interactions.
