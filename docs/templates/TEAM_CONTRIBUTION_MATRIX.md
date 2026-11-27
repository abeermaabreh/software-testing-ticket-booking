# Team Contribution Matrix

| Student name and ID | Main responsibilities | Key files or report sections | Representative commit hashes | Contribution confirmed by team |
| --- | --- | --- | --- | --- |
| Abeer Alaa aldin Maabreh (2338150) | Set up the repository and domain model, implemented core `BookingManager` booking workflow, total amount calculation, customer/event/ticket/price validation, sold-out and payment failure tests via TDD, and finalized submission checklist and manifest | `src/main/java/edu/course/booking/BookingManager.java`, `src/main/java/edu/course/booking/BookingRequest.java`, `src/test/java/edu/course/booking/BookingManagerTest.java`, `docs/templates/SUBMISSION_CHECKLIST.md`, `docs/templates/SUBMISSION_MANIFEST.txt` | `18f4e7e`, `1d91cb4`, `36848aa`, `4228988`, `8b21f7f`, `34a23db` | Yes |
| Dana Mohammad Asad (2332838) | Expanded TDD suite with negative/whitespace input tests, ticket quantity boundary value analysis (`1` and `5` tickets), null `BookingRequest` and constructor dependency guards (`IPaymentGateway`, `IEventRepository`, `INotificationService`), refactored shared mock setup into `@BeforeEach`, and authored Milestone 1 report sections (`Sections 1–4`) and AI use log | `src/test/java/edu/course/booking/BookingManagerTest.java`, `src/main/java/edu/course/booking/BookingManager.java`, `docs/templates/REPORT_TEMPLATE.md`, `docs/templates/AI_USE_LOG.md`, `docs/templates/TEAM_CONTRIBUTION_MATRIX.md` | `a074127`, `c45685d`, `10ab00b`, `853d5cc`, `f7ef723`, `f8dadab` | Yes |

## Notes

- Record meaningful contributions, not only the number of commits.
- Every student should commit through their own GitHub account.
- Update this matrix throughout the project rather than reconstructing it at the deadline.