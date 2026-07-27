#ease_Split

# Ease Split — Complete Frontend + Backend Summary

**Ease Split** is being developed as a full-stack expense-sharing application.

Its main purpose is:

> Create an event → add members → record expenses → divide expenses equally → calculate each member's balance → determine who should pay whom.

The project currently uses:

```text
Frontend                    Backend                     Database
────────                    ───────                     ────────
React                       Spring Boot                 PostgreSQL
Vite                        Java                        SQL
React Router                Spring Web
Fetch API                   Spring Data JPA
                            Hibernate
                            Maven
```

The complete system looks like:

```text
                         USER
                           │
                           ▼
                    React Frontend
                    localhost:5173
                           │
                           │ HTTP / JSON
                           ▼
                   Spring Boot Backend
                    localhost:8080
                           │
                     REST Controllers
                           │
                           ▼
                        Services
                           │
                           ▼
                      Repositories
                           │
                           ▼
                   Hibernate / JPA
                           │
                           ▼
                      PostgreSQL
```

---

# 1. Complete project structure

Your GitHub repository is structured as a **monorepo**, meaning the frontend and backend live inside the same Git repository.

```text
ease_Split/
│
├── .git/
│
├── README.md
│
├── ease_splitBackend/
│   │
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   │
│   └── src/
│       └── main/
│           │
│           ├── java/
│           │   └── com/
│           │       └── ease_splitBackend/
│           │           └── ease_splitBackend/
│           │               │
│           │               ├── EaseSplitBackendApplication.java
│           │               │
│           │               ├── config/
│           │               │   └── WebConfig.java
│           │               │
│           │               ├── controller/
│           │               │   ├── EventController.java
│           │               │   ├── MemberController.java
│           │               │   ├── ExpenseController.java
│           │               │   ├── BalanceController.java
│           │               │   └── SettlementController.java
│           │               │
│           │               ├── dto/
│           │               │   ├── AddMemberRequest.java
│           │               │   ├── CreateExpenseRequest.java
│           │               │   ├── BalanceResponse.java
│           │               │   └── SettlementResponse.java
│           │               │
│           │               ├── entity/
│           │               │   ├── Event.java
│           │               │   ├── User.java
│           │               │   ├── EventMember.java
│           │               │   ├── Expense.java
│           │               │   └── ExpenseSplit.java
│           │               │
│           │               ├── repository/
│           │               │   ├── EventRepository.java
│           │               │   ├── UserRepository.java
│           │               │   ├── EventMemberRepository.java
│           │               │   ├── ExpenseRepository.java
│           │               │   └── ExpenseSplitRepository.java
│           │               │
│           │               └── service/
│           │                   ├── EventService.java
│           │                   ├── MemberService.java
│           │                   ├── ExpenseService.java
│           │                   ├── BalanceService.java
│           │                   ├── BalanceEntry.java
│           │                   └── SettlementService.java
│           │
│           └── resources/
│               ├── application.properties
│               ├── static/
│               └── templates/
│
│
└── ease_splitFrontend/
    │
    ├── package.json
    ├── vite.config.js
    ├── index.html
    │
    └── src/
        │
        ├── api/
        │   └── easeSplitApi.js
        │
        ├── components/
        │   ├── Navbar/
        │   │   ├── Navbar.jsx
        │   │   └── Navbar.css
        │   │
        │   ├── Footer/
        │   │   ├── Footer.jsx
        │   │   └── Footer.css
        │   │
        │   ├── MainContent/
        │   │   ├── MainContent.jsx
        │   │   └── MainContent.css
        │   │
        │   ├── MemberList/
        │   │   └── MemberList.jsx
        │   │
        │   ├── ExpenseList/
        │   │   └── ExpenseList.jsx
        │   │
        │   ├── BalanceList/
        │   │   └── BalanceList.jsx
        │   │
        │   └── SettlementList/
        │       └── SettlementList.jsx
        │
        ├── layouts/
        │   ├── MainLayout.jsx
        │   └── MainLayout.css
        │
        ├── pages/
        │   ├── Home.jsx
        │   ├── CreateEvent.jsx
        │   └── EventDetails.jsx
        │
        ├── routes/
        │   └── AppRoutes.jsx
        │
        ├── styles/
        │   └── global.css
        │
        ├── App.jsx
        └── main.jsx
```

This gives us a clean separation:

```text
ease_Split
│
├── ease_splitFrontend     → what the user sees
│
└── ease_splitBackend      → business logic + database
```

---

# PART I — BACKEND

# 2. Backend architecture

The Spring Boot application follows a layered architecture:

```text
                  HTTP Request
                       │
                       ▼
                 Controller
                       │
                       ▼
                   Service
                       │
                       ▼
                 Repository
                       │
                       ▼
               Hibernate / JPA
                       │
                       ▼
                  PostgreSQL
```

Each layer has a specific responsibility.

---

# 3. `EaseSplitBackendApplication.java`

Located at:

```text
ease_splitBackend/
└── src/main/java/
    └── com/ease_splitBackend/ease_splitBackend/
        └── EaseSplitBackendApplication.java
```

This is the starting point of Spring Boot.

Conceptually:

```java
@SpringBootApplication
public class EaseSplitBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            EaseSplitBackendApplication.class,
            args
        );
    }
}
```

Running:

```bash
./mvnw spring-boot:run
```

starts this application.

Spring then scans the subpackages:

```text
com.ease_splitBackend.ease_splitBackend
│
├── controller
├── service
├── repository
├── entity
├── dto
└── config
```

and automatically discovers Spring components.

---

# 4. `entity/`

The entity directory represents data that gets stored in PostgreSQL.

```text
entity/
├── Event.java
├── User.java
├── EventMember.java
├── Expense.java
└── ExpenseSplit.java
```

Each entity approximately corresponds to a database table.

---

# 5. `Event.java`

Represents an event/group.

Examples:

```text
Goa Trip
Birthday Party
Flat Expenses
College Tour
```

Conceptually:

```text
Event

id
name
description
createdAt
```

Database:

```text
events
────────────────────────────────────
id | name | description | created_at
```

Example:

```text
1 | Goa Trip | College trip | ...
```

The ID is automatically generated using JPA.

`createdAt` records when the event was created.

---

# 6. `User.java`

Represents a person participating in events.

```text
User

id
name
email
```

Database:

```text
users
────────────────────────
id | name | email
```

Example:

```text
1 | Krish | krish@example.com
2 | Aman  | aman@example.com
3 | Rahul | rahul@example.com
```

---

# 7. `EventMember.java`

A user and an event have a relationship.

Instead of putting users directly inside the event, we created a separate join entity:

```text
EventMember
```

Relationship:

```text
Event
  │
  │
  ▼
EventMember
  ▲
  │
  │
User
```

Or:

```text
Event ─────< EventMember >───── User
```

Database:

```text
event_members
────────────────────────────────
id | event_id | user_id | joined_at
```

Example:

```text
1 | 1 | 1
2 | 1 | 2
3 | 1 | 3
```

means:

```text
Event #1
├── User #1
├── User #2
└── User #3
```

This is useful because membership can later contain additional information such as:

```text
role
joinedAt
status
nickname
```

---

# 8. `Expense.java`

Represents something someone paid for.

For example:

```text
Hotel
$300
Paid by Krish
```

The entity contains approximately:

```text
Expense

id
event
paidBy
description
amount
createdAt
```

Database:

```text
expenses
──────────────────────────────────────────────
id | event_id | paid_by | description | amount
```

Example:

```text
1 | 1 | 1 | Hotel  | 300.00
2 | 1 | 2 | Dinner | 150.00
```

The relationship is:

```text
Event
  │
  └──── Expense
          │
          └──── paidBy → User
```

---

# 9. Why `BigDecimal` is used

Money is represented with:

```java
BigDecimal
```

instead of:

```java
double
```

because floating-point values can introduce precision errors.

For example, financial calculations require reliable decimal arithmetic, so values such as:

```text
$10.25
$33.33
$150.00
```

should use `BigDecimal`.

---

# 10. `ExpenseSplit.java`

An expense by itself doesn't tell us how much every person owes.

Suppose:

```text
Hotel = $300

Members:
Krish
Aman
Rahul
```

There are three members.

Equal share:

```text
$300 / 3 = $100
```

Therefore we create three expense splits:

```text
Krish → $100
Aman  → $100
Rahul → $100
```

`ExpenseSplit` contains approximately:

```text
id
expense
user
shareAmount
```

Database:

```text
expense_splits
────────────────────────────────────────
id | expense_id | user_id | share_amount
```

---

# 11. Database relationships

The important database model currently looks like:

```text
                       EVENT
                         │
             ┌───────────┴────────────┐
             │                        │
             ▼                        ▼
       EVENT_MEMBER                EXPENSE
             │                        │
             ▼                        │
           USER ◄─────────────────────┤
                                      │ paidBy
                                      │
                                      ▼
                               EXPENSE_SPLIT
                                      │
                                      ▼
                                    USER
```

Another way to visualize it:

```text
events
  │
  ├────────────── event_members ─────── users
  │
  └────────────── expenses
                       │
                       ├──── paid_by ── users
                       │
                       └──── expense_splits ── users
```

---

# 12. `repository/`

Repository classes provide database access.

```text
repository/
├── EventRepository.java
├── UserRepository.java
├── EventMemberRepository.java
├── ExpenseRepository.java
└── ExpenseSplitRepository.java
```

They extend:

```java
JpaRepository<Entity, Long>
```

For example:

```java
public interface EventRepository
        extends JpaRepository<Event, Long> {
}
```

Spring automatically gives us:

```text
save()
findById()
findAll()
deleteById()
existsById()
...
```

without writing SQL manually.

---

# 13. Derived repository queries

We also created methods such as:

```java
List<EventMember> findByEventId(Long eventId);
```

Spring Data interprets the method name.

Conceptually:

```text
findBy
  Event
    Id
```

means:

```text
find EventMembers whose event.id equals eventId
```

Likewise:

```java
List<Expense> findByEventId(Long eventId);
```

gets expenses belonging to an event.

And:

```java
List<ExpenseSplit> findByExpenseEventId(Long eventId);
```

means:

```text
ExpenseSplit
     ↓
Expense
     ↓
Event
     ↓
id
```

This method became important for balance calculation.

---

# 14. `dto/`

DTO means **Data Transfer Object**.

```text
dto/
├── AddMemberRequest.java
├── CreateExpenseRequest.java
├── BalanceResponse.java
└── SettlementResponse.java
```

DTOs represent information entering or leaving the API.

They are not necessarily database tables.

---

# 15. `AddMemberRequest.java`

Used when React sends:

```json
{
    "name": "Rahul",
    "email": "rahul@example.com"
}
```

The controller converts this JSON into:

```text
AddMemberRequest
```

which contains approximately:

```text
name
email
```

---

# 16. `CreateExpenseRequest.java`

Used when the frontend sends:

```json
{
    "paidByUserId": 1,
    "description": "Hotel",
    "amount": 300
}
```

It contains:

```text
paidByUserId
description
amount
```

---

# 17. `BalanceResponse.java`

Used when returning calculated balances.

Example:

```json
{
    "userId": 1,
    "name": "Krish",
    "balance": 150.00
}
```

It isn't stored in PostgreSQL.

It is calculated dynamically.

---

# 18. `SettlementResponse.java`

Used when returning who should pay whom.

Example:

```json
{
    "fromUserId": 3,
    "fromUserName": "Rahul",
    "toUserId": 1,
    "toUserName": "Krish",
    "amount": 150.00
}
```

Meaning:

```text
Rahul ───── $150 ─────→ Krish
```

---

# 19. `service/`

The service directory contains the actual business logic.

```text
service/
├── EventService.java
├── MemberService.java
├── ExpenseService.java
├── BalanceService.java
├── BalanceEntry.java
└── SettlementService.java
```

This is where most of the interesting backend work happens.

---

# 20. `EventService.java`

Responsible for event operations.

For example:

```text
create event
get events
```

Flow:

```text
EventController
      │
      ▼
EventService
      │
      ▼
EventRepository
      │
      ▼
PostgreSQL
```

---

# 21. `MemberService.java`

Handles adding and retrieving event members.

When adding a member:

```text
Receive eventId + member information
               │
               ▼
           Find Event
               │
               ▼
           Create User
               │
               ▼
            Save User
               │
               ▼
        Create EventMember
               │
               ▼
      Connect Event + User
               │
               ▼
        Save EventMember
```

So:

```text
Goa Trip
   │
   ├── Krish
   ├── Aman
   └── Rahul
```

is represented through `EventMember`.

---

# 22. `ExpenseService.java`

Handles expense creation.

When React sends:

```json
{
    "paidByUserId": 1,
    "description": "Hotel",
    "amount": 300
}
```

the service:

```text
Find Event
    │
    ▼
Find Payer
    │
    ▼
Find Event Members
    │
    ▼
Create Expense
    │
    ▼
Save Expense
    │
    ▼
Calculate equal share
    │
    ▼
Create ExpenseSplit for every member
```

For:

```text
$300
3 members
```

it creates:

```text
Expense
Hotel = $300
     │
     ├── Krish $100
     ├── Aman  $100
     └── Rahul $100
```

---

# 23. Transactions

Expense creation involves several writes:

```text
INSERT Expense
INSERT ExpenseSplit
INSERT ExpenseSplit
INSERT ExpenseSplit
```

We use:

```java
@Transactional
```

so they operate as one database transaction.

If everything succeeds:

```text
COMMIT
```

If something fails:

```text
ROLLBACK
```

This prevents partially-created expenses.

---

# 24. `BalanceService.java`

This calculates how much every member owes or should receive.

The fundamental formula is:

```text
BALANCE = TOTAL AMOUNT PAID - TOTAL SHARE OWED
```

Consider:

```text
Members:
Krish
Aman
Rahul
```

Expenses:

```text
Hotel       $300     paid by Krish
Dinner      $150     paid by Aman
```

Total expense:

```text
$450
```

With three members:

```text
each member's total share = $150
```

Payments:

```text
Krish = $300
Aman  = $150
Rahul = $0
```

Therefore:

```text
Krish
$300 - $150
= +$150

Aman
$150 - $150
= $0

Rahul
$0 - $150
= -$150
```

Interpretation:

```text
Positive balance
→ should receive money

Zero
→ completely settled

Negative balance
→ owes money
```

---

# 25. `BalanceEntry.java`

This was added as a helper class for the settlement algorithm.

It contains:

```text
userId
name
amount
```

It is important to understand that:

```text
BalanceEntry IS NOT a database entity.
```

Therefore it does **not** have:

```java
@Entity
```

and there is no:

```text
BalanceEntryRepository
```

or:

```text
balance_entry PostgreSQL table
```

It exists only temporarily in memory while calculating settlements.

---

# 26. `SettlementService.java`

The balance API might tell us:

```text
Krish   +$500
Aman    +$200
Rahul   -$300
Rohan   -$400
```

But that's not ideal for the user.

They want:

```text
Who pays whom?
```

Therefore `SettlementService` separates people into:

```text
Creditors                   Debtors
─────────                   ───────
Krish $500                  Rohan $400
Aman  $200                  Rahul $300
```

A creditor should receive money.

A debtor should pay money.

---

# 27. Settlement priority queues

We use:

```java
PriorityQueue<BalanceEntry>
```

for creditors and debtors.

The largest amounts are processed first.

Initially:

```text
Creditors             Debtors

Krish $500            Rohan $400
Aman  $200            Rahul $300
```

Take:

```text
Krish needs $500
Rohan owes $400
```

Settlement amount:

```text
min($500, $400)

= $400
```

So:

```text
Rohan ─── $400 ───→ Krish
```

Remaining:

```text
Krish = $100
Rohan = $0
```

Continue until everyone is settled.

---

# 28. `controller/`

Controllers expose the backend through HTTP.

```text
controller/
├── EventController.java
├── MemberController.java
├── ExpenseController.java
├── BalanceController.java
└── SettlementController.java
```

The controller should generally be thin:

```text
HTTP request
     │
     ▼
Controller
     │
     ▼
Service
```

Business logic stays in services.

---

# 29. Backend endpoints

The API currently provides approximately:

```text
EVENTS

GET  /api/events
POST /api/events


MEMBERS

GET  /api/events/{eventId}/members
POST /api/events/{eventId}/members


EXPENSES

GET  /api/events/{eventId}/expenses
POST /api/events/{eventId}/expenses


BALANCES

GET  /api/events/{eventId}/balances


SETTLEMENTS

GET  /api/events/{eventId}/settlements
```

These are the endpoints the React frontend is being built around.

---

# 30. `config/WebConfig.java`

We added this after encountering a CORS problem.

The frontend runs at:

```text
http://localhost:5173
```

while Spring Boot runs at:

```text
http://localhost:8080
```

These are different origins because:

```text
5173 ≠ 8080
```

The browser originally blocked:

```text
React
   │
   │ GET /api/events
   ▼
Spring Boot

❌ Access-Control-Allow-Origin missing
```

So we added:

```text
config/
└── WebConfig.java
```

with CORS configuration allowing:

```text
http://localhost:5173
```

to access:

```text
/api/**
```

Now the browser is allowed to communicate with Spring Boot.

---

# 31. `application.properties`

Located at:

```text
src/main/resources/application.properties
```

It configures:

```text
application name
database URL
database username
database password
Hibernate behavior
SQL logging
```

Conceptually:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/...
spring.datasource.username=...
spring.datasource.password=...

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

So:

```text
Spring Boot
     │
     │ JDBC
     ▼
PostgreSQL
```

For development, `ddl-auto=update` lets Hibernate update tables from entities.

Eventually database migrations such as Flyway would be preferable.

Credentials should also eventually be moved to environment variables rather than committed to Git.

---

# PART II — FRONTEND

# 32. Frontend architecture

The React application follows:

```text
main.jsx
   │
   ▼
App.jsx
   │
   ▼
AppRoutes.jsx
   │
   ▼
MainLayout.jsx
   │
   ├── Navbar
   │
   ├── MainContent
   │       │
   │       └── Outlet
   │
   └── Footer
```

`Outlet` changes depending on the current route.

---

# 33. `main.jsx`

This is the frontend entry point.

It renders React into:

```html
<div id="root"></div>
```

from `index.html`.

It also wraps the application in:

```jsx
<BrowserRouter>
```

so React Router works.

Conceptually:

```text
index.html
    │
    ▼
<div id="root">
    │
    ▼
main.jsx
    │
    ▼
BrowserRouter
    │
    ▼
App
```

---

# 34. `App.jsx`

`App.jsx` is deliberately small.

It delegates routing to:

```text
AppRoutes
```

So:

```text
main.jsx
   ↓
App.jsx
   ↓
AppRoutes.jsx
```

---

# 35. `routes/AppRoutes.jsx`

This determines which page appears for a URL.

Current routes:

```text
/                    → Home

/events/new          → CreateEvent

/events/:eventId     → EventDetails
```

`:eventId` is dynamic.

For example:

```text
/events/1
/events/2
/events/15
```

all load:

```text
EventDetails.jsx
```

but with different IDs.

---

# 36. `layouts/MainLayout.jsx`

The layout defines the shared application structure:

```text
┌─────────────────────────────────┐
│             Navbar              │
├─────────────────────────────────┤
│                                 │
│          MainContent            │
│                                 │
├─────────────────────────────────┤
│             Footer              │
└─────────────────────────────────┘
```

Regardless of whether the user visits:

```text
/
/events/new
/events/5
```

the navbar and footer remain.

Only `MainContent` changes.

---

# 37. `components/MainContent/`

Contains:

```text
MainContent.jsx
MainContent.css
```

`MainContent.jsx` uses:

```jsx
<Outlet />
```

This is extremely important.

React Router places the currently selected child page inside the outlet.

For example:

```text
URL = /
        │
        ▼
Outlet
        │
        ▼
Home


URL = /events/new
        │
        ▼
Outlet
        │
        ▼
CreateEvent


URL = /events/7
        │
        ▼
Outlet
        │
        ▼
EventDetails
```

---

# 38. `components/Navbar/`

Contains:

```text
Navbar.jsx
Navbar.css
```

The navbar provides links such as:

```text
Ease Split

Events
Create Event
```

It uses React Router's:

```jsx
<NavLink>
```

rather than regular `<a>` navigation.

That lets React Router navigate without reloading the entire application.

---

# 39. `components/Footer/`

Contains:

```text
Footer.jsx
Footer.css
```

The footer is shared across all pages through `MainLayout`.

---

# 40. `api/easeSplitApi.js`

This is one of the most important frontend files.

Instead of writing:

```javascript
fetch(...)
```

inside every React component, all backend communication is centralized here.

It defines:

```text
getEvents()
createEvent()

getMembers()
addMember()

getExpenses()
createExpense()

getBalances()

getSettlements()
```

The base backend URL is currently:

```text
http://localhost:8080/api
```

The mapping is:

```text
Frontend function                  Spring Boot

getEvents()
        ────────────────────────→ GET /api/events

createEvent()
        ────────────────────────→ POST /api/events


getMembers(eventId)
        ────────────────────────→ GET /api/events/{id}/members

addMember(eventId, member)
        ────────────────────────→ POST /api/events/{id}/members


getExpenses(eventId)
        ────────────────────────→ GET /api/events/{id}/expenses

createExpense(eventId, expense)
        ────────────────────────→ POST /api/events/{id}/expenses


getBalances(eventId)
        ────────────────────────→ GET /api/events/{id}/balances


getSettlements(eventId)
        ────────────────────────→ GET /api/events/{id}/settlements
```

This gives us a clean separation:

```text
React UI
   │
   ▼
easeSplitApi.js
   │
   ▼
Spring Boot
```

---

# 41. `pages/Home.jsx`

The home page displays events.

When the page loads:

```javascript
useEffect(...)
```

calls:

```javascript
getEvents()
```

The complete flow is:

```text
Home renders
    │
    ▼
useEffect()
    │
    ▼
getEvents()
    │
    ▼
easeSplitApi.js
    │
    ▼
GET localhost:8080/api/events
    │
    ▼
EventController
    │
    ▼
EventService
    │
    ▼
EventRepository
    │
    ▼
PostgreSQL
    │
    ▼
JSON
    │
    ▼
React
    │
    ▼
setEvents(data)
    │
    ▼
Home rerenders
```

Then React displays something like:

```text
My Events

┌────────────────────────┐
│ Goa Trip               │
│ College trip           │
│                        │
│ Open Event             │
└────────────────────────┘

┌────────────────────────┐
│ Birthday Party         │
│ Weekend party          │
│                        │
│ Open Event             │
└────────────────────────┘
```

---

# 42. `pages/CreateEvent.jsx`

This page contains a form:

```text
Create Event

Event Name
[_____________________]

Description
[_____________________]
[_____________________]

[ Create Event ]
```

React stores form values using:

```javascript
useState()
```

When submitted:

```text
User submits form
       │
       ▼
handleSubmit()
       │
       ▼
createEvent()
       │
       ▼
POST /api/events
       │
       ▼
Spring Boot
       │
       ▼
PostgreSQL
       │
       ▼
Created event returned
       │
       ▼
navigate("/events/{id}")
```

For example, if the backend creates:

```json
{
    "id": 7,
    "name": "Goa Trip"
}
```

React redirects to:

```text
/events/7
```

---

# 43. `pages/EventDetails.jsx`

This is intended to become the **main dashboard** for an event.

It obtains the dynamic ID with:

```javascript
const { eventId } = useParams();
```

So:

```text
/events/7

      ↓

eventId = 7
```

It then loads:

```text
members
expenses
balances
settlements
```

using:

```javascript
Promise.all(...)
```

Conceptually:

```text
                 EventDetails
                      │
          ┌───────────┼───────────┐
          │           │           │
          ▼           ▼           ▼
       Members     Expenses    Balances
          │                       │
          └───────────┬───────────┘
                      ▼
                 Settlements
```

All four requests can happen concurrently.

---

# 44. Event dashboard goal

The page is eventually intended to look approximately like:

```text
┌─────────────────────────────────────────────┐
│                  Goa Trip                   │
│           College trip with friends         │
├─────────────────────┬───────────────────────┤
│ Members             │ Add Member            │
│                     │                       │
│ Krish               │ Name  [__________]    │
│ Aman                │ Email [__________]    │
│ Rahul               │ [ Add Member ]        │
├─────────────────────┴───────────────────────┤
│ Expenses                                    │
│                                             │
│ Hotel       Krish                 $300      │
│ Dinner      Aman                  $150      │
│                                             │
│                 [ + Add Expense ]           │
├─────────────────────┬───────────────────────┤
│ Balances            │ Settle Up             │
│                     │                       │
│ Krish   +$150       │ Rahul                 │
│ Aman       $0       │    ↓ $150             │
│ Rahul   -$150       │ Krish                 │
└─────────────────────┴───────────────────────┘
```

---

# 45. `components/MemberList/`

Contains:

```text
MemberList.jsx
```

It was initially created as a placeholder for moving member-related UI out of `EventDetails`.

Eventually its responsibility will be:

```text
display members
+
add member form
```

It will communicate with:

```text
getMembers()
addMember()
```

---

# 46. `components/ExpenseList/`

Contains:

```text
ExpenseList.jsx
```

Its eventual responsibility is:

```text
display expenses
+
add expense form
```

The form will contain:

```text
Description
Amount
Paid By
```

For example:

```text
Description
[ Hotel              ]

Amount
[ 300                ]

Paid By
[ Krish            ▼ ]

[ Add Expense ]
```

The payer dropdown comes from the event's members.

---

# 47. `components/BalanceList/`

Contains:

```text
BalanceList.jsx
```

It will display:

```text
Krish     +$150
Aman         $0
Rahul     -$150
```

The data comes from:

```text
GET /api/events/{eventId}/balances
```

Eventually positive, negative and zero balances can have different visual styles.

---

# 48. `components/SettlementList/`

Contains:

```text
SettlementList.jsx
```

It will display results such as:

```text
Rahul
  │
  │ pays $150
  ▼
Krish
```

from:

```text
GET /api/events/{eventId}/settlements
```

---

# 49. `styles/global.css`

Contains application-wide styling.

Things such as:

```css
* {
    box-sizing: border-box;
}
```

and common styling for:

```text
body
buttons
inputs
textarea
select
links
```

belong here.

Component-specific CSS stays beside the corresponding component.

For example:

```text
Navbar/
├── Navbar.jsx
└── Navbar.css
```

---

# 50. Complete frontend request lifecycle

Suppose the user visits:

```text
http://localhost:5173/
```

The chain is:

```text
Browser
   │
   ▼
main.jsx
   │
   ▼
BrowserRouter
   │
   ▼
App.jsx
   │
   ▼
AppRoutes
   │
   ▼
"/" matches Home
   │
   ▼
MainLayout
   │
   ├── Navbar
   │
   ├── MainContent
   │       │
   │       └── Outlet
   │             │
   │             ▼
   │            Home
   │
   └── Footer
```

Then `Home` does:

```text
Home
 │
 ▼
getEvents()
 │
 ▼
easeSplitApi.js
 │
 ▼
HTTP GET
 │
 ▼
Spring Boot
```

---

# 51. Complete full-stack request lifecycle

Suppose React requests:

```text
GET /api/events
```

The complete architecture becomes:

```text
React Home.jsx
      │
      │ getEvents()
      ▼
easeSplitApi.js
      │
      │ HTTP GET
      ▼
EventController
      │
      ▼
EventService
      │
      ▼
EventRepository
      │
      ▼
Hibernate
      │
      ▼
PostgreSQL
      │
      │ SELECT ...
      ▼
EventRepository
      │
      ▼
EventService
      │
      ▼
EventController
      │
      │ JSON
      ▼
easeSplitApi.js
      │
      ▼
Home.jsx
      │
      ▼
setEvents()
      │
      ▼
Browser UI
```

That is the fundamental full-stack architecture of Ease Split.

---

# 52. Complete expense lifecycle

This is the most important business flow in the application.

Suppose:

```text
Event:
Goa Trip

Members:
Krish
Aman
Rahul
```

The user enters:

```text
Hotel
$300
Paid by Krish
```

Frontend:

```text
Expense form
     │
     ▼
createExpense()
     │
     ▼
POST /api/events/1/expenses
```

Backend:

```text
ExpenseController
      │
      ▼
ExpenseService
      │
      ├── Find Event
      ├── Find Krish
      ├── Find members
      │
      ▼
Create Expense
      │
      ▼
$300 / 3
      │
      ▼
$100 each
```

Database:

```text
Expense

Hotel
$300
Krish
```

plus:

```text
ExpenseSplit

Krish → $100
Aman  → $100
Rahul → $100
```

---

# 53. Complete balance lifecycle

Now suppose Aman also pays:

```text
Dinner
$150
```

The backend calculates:

```text
TOTAL PAID

Krish = $300
Aman  = $150
Rahul = $0
```

Shares:

```text
TOTAL OWED

Krish = $150
Aman  = $150
Rahul = $150
```

Then:

```text
balance = paid - owed
```

giving:

```text
Krish = +$150
Aman  = $0
Rahul = -$150
```

---

# 54. Complete settlement lifecycle

`SettlementService` receives:

```text
Krish    +$150
Aman       $0
Rahul    -$150
```

It creates:

```text
Creditors

Krish $150
```

and:

```text
Debtors

Rahul $150
```

Then:

```text
min($150, $150)
```

produces:

```text
Rahul ─── $150 ───→ Krish
```

The frontend can then display:

```text
Rahul pays Krish $150
```

---

# 55. Current status of the integration

We've already crossed several important stages.

### Working conceptually / implemented

```text
Backend structure                  ✓

PostgreSQL connection              ✓

Entities                           ✓

Repositories                       ✓

Event APIs                         ✓

Member APIs                        ✓

Expense APIs                       ✓

Equal split logic                  ✓

Balance calculation                ✓

Settlement calculation             ✓

Settlement priority queues         ✓

React structure                    ✓

React Router                       ✓

API layer                          ✓

Home page                          ✓

Create Event page                  ✓

Event Details foundation           ✓

CORS configuration                 ✓
```

### Current integration issue

Initially the browser reported:

```text
Access-Control-Allow-Origin missing
```

We added `WebConfig.java`, so requests started reaching Spring Boot.

After that React received:

```text
HTTP 500
```

This is actually progress:

```text
Before:

React ──X──> Spring Boot
       CORS


Now:

React ─────> Spring Boot
                │
                X
            Backend error
```

So the next debugging work is on the backend runtime/JPA side rather than React's networking layer.

---

# 56. Package problem we corrected

One significant backend problem occurred because some files used:

```text
com.easesplit.ease_splitBackend
```

while the actual generated project uses:

```text
com.ease_splitBackend.ease_splitBackend
```

The project needs to consistently use:

```text
com.ease_splitBackend.ease_splitBackend
```

Therefore:

```text
controller
→ com.ease_splitBackend.ease_splitBackend.controller

dto
→ com.ease_splitBackend.ease_splitBackend.dto

entity
→ com.ease_splitBackend.ease_splitBackend.entity

repository
→ com.ease_splitBackend.ease_splitBackend.repository

service
→ com.ease_splitBackend.ease_splitBackend.service

config
→ com.ease_splitBackend.ease_splitBackend.config
```

We also found `ExpenseSplit.java` had accidentally received two package declarations, which needed correction.

---

# 57. Git repository issue we corrected

Your repository contains:

```text
ease_Split/
├── ease_splitBackend/
└── ease_splitFrontend/
```

But the frontend had previously been initialized as a separate Git repository.

That created:

```text
ease_Split/.git
```

and previously:

```text
ease_Split/ease_splitFrontend/.git
```

Git therefore treated the frontend as a gitlink/submodule-like entry instead of ordinary files.

You removed the nested:

```text
ease_splitFrontend/.git
```

which was correct.

The desired structure is:

```text
ease_Split/
│
├── .git/                       ← only Git repository
│
├── ease_splitBackend/
│
├── ease_splitFrontend/
│
└── README.md
```

Both frontend and backend should be tracked by the **same repository**.

---

# 58. What remains to be developed

There are several important things still left.

The immediate priority should be:

```text
Fix current HTTP 500
        │
        ▼
Verify Event APIs
        │
        ▼
Verify Member APIs
        │
        ▼
Verify Expense APIs
        │
        ▼
Verify Balance API
        │
        ▼
Verify Settlement API
        │
        ▼
Finish MemberList
        │
        ▼
Finish ExpenseList
        │
        ▼
Finish BalanceList
        │
        ▼
Finish SettlementList
        │
        ▼
Style dashboard
```

After that we should improve the backend with:

```text
Validation
Exception handling
Duplicate-member prevention
Payer membership validation
Correct monetary rounding
Update APIs
Delete APIs
Actual settlement recording
Authentication
Authorization
Tests
Production deployment
```

---

# 59. One important distinction: suggested vs actual settlements

Currently:

```text
GET /api/events/{eventId}/settlements
```

**calculates what people should pay**.

It does not mean they actually paid.

For example:

```text
Rahul → Krish $150
```

currently means:

> Ease Split recommends Rahul pay Krish $150.

Eventually we should create something like:

```text
SettlementPayment

id
event
fromUser
toUser
amount
paidAt
```

Then we could support:

```text
Rahul owes Krish $150
        │
        ▼
Rahul actually pays
        │
        ▼
Record SettlementPayment
        │
        ▼
Balance becomes $0
```

---

# 60. Overall architecture

Putting everything together:

```text
┌───────────────────────────────────────────────────────────────┐
│                         EASE SPLIT                            │
└───────────────────────────────────────────────────────────────┘

                         Browser
                            │
                            ▼
                  ┌──────────────────┐
                  │      React       │
                  │   Vite :5173     │
                  └────────┬─────────┘
                           │
                ┌──────────┴──────────┐
                │                     │
                ▼                     ▼
          React Router            Components
                │
                ▼
             Pages
                │
                ▼
         easeSplitApi.js
                │
                │ HTTP / JSON
                ▼
       ┌────────────────────┐
       │    Spring Boot     │
       │       :8080        │
       └──────────┬─────────┘
                  │
                  ▼
             Controllers
                  │
                  ▼
               Services
          ┌───────┼──────────┐
          │       │          │
          ▼       ▼          ▼
       Expense  Balance  Settlement
       Logic     Logic      Logic
          │       │          │
          └───────┼──────────┘
                  │
                  ▼
             Repositories
                  │
                  ▼
            Hibernate / JPA
                  │
                  ▼
       ┌────────────────────┐
       │     PostgreSQL     │
       └────────────────────┘
```

And from a **business perspective**, the entire application is:

```text
                    Create Event
                         │
                         ▼
                     Add Members
                         │
                         ▼
                    Add Expenses
                         │
                         ▼
                  Generate Splits
                         │
                         ▼
                 Calculate Balances
                         │
                         ▼
              Creditors / Debtors
                         │
                         ▼
                Settlement Algorithm
                         │
                         ▼
                  Who Pays Whom
```

That is the complete state of **Ease Split so far**: the backend has the relational model and core expense/balance/settlement business logic, while the frontend has the routing/layout/API foundation needed to expose those features. The immediate next task is to resolve the backend `500` response and verify each API independently before finishing the event dashboard components.

