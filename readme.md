# Calculator

This is a simple JavaFX calculator application created with NetBeans. It demonstrates a small JavaFX app using FXML for the UI, a controller for user interactions, and a stylesheet for basic styling.

## Project structure and components

- `src/calculator/CalculatorApp.java` — Application entry point. Initializes JavaFX and loads the FXML UI.
- `src/calculator/UI/calculator.fxml` — The UI layout for the calculator (FXML). Students should continue to use FXML for all UI changes.
- `src/calculator/Controller/CalculatorController.java` — Controller class; connects UI elements declared in the FXML with application logic.
- `src/calculator/styles/styles.css` — Stylesheet for simple styling.
- `build/` — Compiled classes and build artifacts (ignored when contributing).

## What this project teaches

- Basic JavaFX application lifecycle
- FXML-based UI design and separation of concerns (UI vs controller)
- Styling JavaFX with CSS
- Handling user events and simple arithmetic logic

## How to run locally

You can open the project in NetBeans and run the `CalculatorApp` class. Alternatively build and run from the command line using the JDK and JavaFX SDK configured for your environment.

Minimal steps (NetBeans):

1. Open the project in NetBeans.
2. Right-click the project and choose Run.

If running outside the IDE you must supply the JavaFX modules on the module path; instructions vary by Java/JavaFX versions and OS.

## Ideas for student contributions (extra credit)

Students are encouraged to fork this repository and submit pull requests. High-quality contributions will earn extra credit. Below are concrete ideas ranging from small to advanced — pick one or more.

1. Scientific functions
   - Add sin, cos, tan, asin, acos, atan, ln, log10, exp and power operations.
   - Support expression input (e.g., allow parentheses and operator precedence) or add an RPN mode.

2. Unit converter module
   - Add a new view (FXML) and controller for converting units: length (meter  inch), weight (kg  lb), temperature (C  F), volume, and area.
   - Allow chaining conversions and copying results to clipboard.

3. History and memory
   - Add a session history panel showing past calculations.
   - Implement memory slots (M+, M-, MR, MC) with persistent storage (file or simple JSON) so history survives restarts.

4. Themes and modern styling
   - Replace the basic stylesheet with a modern, responsive theme. Use CSS variables to support light/dark themes.
   - Add animated transitions (button ripple, panel slide-in/out) using JavaFX animations and Timeline/KeyFrame.

5. Accessibility and keyboard support
   - Full keyboard navigation and shortcuts for numbers, operators, and functions.
   - Improve accessibility: readable font sizes, focus indicators, and support for screen readers where possible.

6. Internationalization (i18n)
   - Externalize strings and provide at least two locales (e.g., en and es). Show a settings menu to switch language at runtime.

7. Tests and CI
   - Add unit tests for the calculation logic (use JUnit). Create a GitHub Actions workflow to run the tests on PRs.

8. Mobile-friendly or responsive layout
   - Make the UI scale cleanly for narrow windows or embed a compact layout for small screens.

9. Plugin or extension system
   - Design a small plugin API so additional function modules can be dropped in without changing core code.

10. Deployment packaging
    - Provide a cross-platform native package using jpackage or create a runnable JAR with dependencies packed.

## Extra-credit rules and rubric

Follow these guidelines to earn extra credit. The instructor will evaluate PRs on the rubric below.

- Fork the repository, create a branch for your feature (feature/your-name-feature), implement your changes, and open a PR back to the original repo.
- Provide a clear PR description: what you changed, why, screenshots (if UI), and how to test your changes.

Rubric (total 100 points):

- Correctness & Functionality: 40 pts — Feature works as described and has no major bugs.
- Code quality & design: 20 pts — Clean, modular code; FXML for UI; separation between UI and logic.
- UI/UX & Styling: 15 pts — Modern look, responsive layout, and smooth animations where applicable.
- Tests & CI: 10 pts — Unit tests present and a green CI job.
- Documentation & PR quality: 10 pts — Clear README updates, comments, and a helpful PR description.
- Extra polish (i18n, persistence, accessibility): 5 pts — Bonus for accessibility, internationalization, or persistence.

Minimum requirements for extra credit PRs:

- Use FXML for any new or modified UI screens. Do not hard-code UI components in Java when adding views.
- Add or update CSS in `src/calculator/styles/styles.css` or add a new theme file and reference it from FXML or the App class.
- Keep UI logic in controller classes and keep calculation/business logic in separate classes where possible (makes unit testing easier).

## UI & FXML guidelines (must-follow)

- Use `calculator/UI/*.fxml` for UI changes. Name new views clearly (e.g., `converter.fxml`, `scientific.fxml`).
- Declare IDs for interactive controls and reference them in the controller with `@FXML` annotations.
- Use stylesheets for visuals — do not inline styles in FXML unless for tiny exceptions.
- Add smooth animations with JavaFX `Timeline`, `FadeTransition`, `TranslateTransition`, or CSS transitions for hover states.
- Keep accessibility in mind: add accessible text for icons, use appropriate contrast, and ensure focus traversal order.

## How to submit (step-by-step)

1. Fork this repository to your GitHub account.
2. Clone your fork locally and create a branch: `git checkout -b feature/yourname-descriptive`.
3. Implement your feature using FXML for UI and CSS for styling.
4. Add tests for new logic where applicable and run them locally.
5. Commit with clear messages and push your branch to your fork.
6. Open a Pull Request to the upstream repository and include:
   - Description of the feature
   - How to test it
   - Screenshots or GIFs for UI changes
   - Any notes about known limitations

## Review process

PRs will be reviewed by the instructor/TA. If small fixes are requested, please update the branch so we can re-review. Merged PRs that meet the rubric will receive extra credit according to the rubric score.

## Starter suggestions for students (first 3 small tasks)

1. Add a Degrees/Radians toggle and implement sin/cos/tan for the existing calculator layout.
2. Create a simple `converter.fxml` with fields to convert between meters and inches and wire it to a new controller.
3. Replace the current stylesheet with a clean light/dark theme using CSS variables and add a toggle in the UI.
