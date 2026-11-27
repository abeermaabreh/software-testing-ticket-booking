# AI Use Log

Complete this log only for material AI assistance that influenced assessed code, tests, test data, models, or report analysis. Minor autocomplete, formatting, and grammar corrections do not require separate entries.

| Date | Tool | Purpose and prompt summary | Files or sections affected | How the output was checked | Decision |
| --- | --- | --- | --- | --- | --- |
| 2026-11-23 to 2026-11-24 | ChatGPT | Assisted with initial TDD test cases, booking flow/total calculation logic, and input/sold-out/payment validation tests | `src/main/java/edu/course/booking/BookingManager.java`, `src/main/java/edu/course/booking/BookingRequest.java`, `src/test/java/edu/course/booking/BookingManagerTest.java` | Reviewed generated tests/code, ran `mvn test` across TDD red/green/refactor steps, and checked behavior against requirements | Modified |
| 2026-11-26 | Google Gemini | Discussed boundary value test cases for ticket quantities and reviewed null-dependency validation logic for `BookingManager` constructor | `src/main/java/edu/course/booking/BookingManager.java`, `src/test/java/edu/course/booking/BookingManagerTest.java` | Cross-checked against project requirements, edited code directly, and ran `mvn test` to verify red/green test results | Modified |
| 2026-11-26 | Google Gemini | Checked refactoring approach for extracting repeated mock initialization into `@BeforeEach` in JUnit 5/Mockito tests | `src/test/java/edu/course/booking/BookingManagerTest.java` | Reviewed each test method manually, fixed constructor arguments and cleanup, and ran `mvn test` to confirm all tests pass | Modified |

## Student responsibility

For every retained output, confirm that the team:

- [x] checked it against the project requirements;
- [x] executed and reviewed affected tests;
- [x] verified expected results independently; and
- [x] can explain and modify the submitted work.