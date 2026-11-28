# Software Testing Semester Project Report

## Cover Information

- Course: Software Testing (2010031410)
- Milestone: Milestone 1
- Group number: 2
- Student names and IDs:
 1. Dana Mohammad Asad (2332838)
 2. Abeer Alaa Maabreh (2338150)
- Repository URL: https://github.com/abeermaabreh/software-testing-ticket-booking
- Final commit hash: f8dadabb0d659496a9fad5498df011de06177d6b

## 1. System and Test Scope

The implemented scope covers unit testing `BookingManager` for ticket booking validation, total charge calculation, event availability checks, payment gateway invocation, booking persistence, and confirmation notifications, including boundary value tests (`1` and `5` tickets) and constructor `null`-dependency checks (`IPaymentGateway`, `IEventRepository`, `INotificationService`). Real external services (`IPaymentGateway`, `IEventRepository`, `INotificationService`), full end-to-end integration/database persistence, and Milestone 2 input-space / pair-wise combinatorial test suites are intentionally out of scope for Milestone 1.

## 2. Milestone 1: Design and Testability

`BookingManager` separates booking orchestration and validation rules from external side effects by depending on `IPaymentGateway`, `IEventRepository`, and `INotificationService` interfaces rather than concrete implementations. Constructor dependency injection makes the class straightforward to test in isolation: tests can pass Mockito mocks (or `null` when testing constructor guard clauses) and use JUnit 5 `@BeforeEach` to reset fresh mocks and a clean `BookingManager` instance before each test without hitting real networks, databases, or notification providers.

## 3. Milestone 1: TDD Evidence

### Behavior 1

- Red commit: a074127d9cb3ce493f1e6e799fea80db2acbedcf
- Green commit: c45685d3d46b3925cc92319c5eadf42ba907f193
- Refactor commit, if applicable: f8dadab
- Short explanation: Added a test expecting rejection of a `null` `BookingRequest` (Red), implemented the `null` booking request check so it passes (Green), and extracted shared mock setup into `@BeforeEach` (Refactor).

### Behavior 2

- Red commit: 853d5cc
- Green commit: 7d42b16
- Refactor commit, if applicable: f8dadab
- Short explanation: Added a failing constructor test for a `null` payment gateway (Red), added constructor `null` validation so it passes (Green), and cleaned up repeated mock initialization in `@BeforeEach` (Refactor).

## 4. Limitation of Mocking

Mocked dependencies verify interactions and return canned responses rather than real implementations, so tests can pass even when a mock's stubbed behavior or method signature drifts from real production behavior or integration edge cases.

## 5. Milestone 2: Input Space Model

*Milestone 2 — to be completed in Milestone 2.*

## 6. Constraints and Infeasible Combinations

*Milestone 2 — to be completed in Milestone 2.*

## 7. Pair-Wise Test Set

*Milestone 2 — to be completed in Milestone 2.*

## 8. Limitation of Pair-Wise Testing

*Milestone 2 — to be completed in Milestone 2.*

## 9. Team Contribution Matrix

*See completed `TEAM_CONTRIBUTION_MATRIX.md`.*

## Appendix A: AI Use Log

*See completed `AI_USE_LOG.md`.*