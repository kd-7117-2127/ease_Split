Overall Flow

When you start the application using

npm run dev

Vite loads the application and executes main.jsx.

The execution flow is

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
 ┌────┴─────────────┐
 ▼                  ▼
Navbar         MainContent(Outlet)
                     │
                     ▼
         Home/About/Services/Contact
                     │
                     ▼
                 Footer

Notice that Navbar and Footer never change. Only the content inside the Outlet changes.

Step 1 : main.jsx
import { BrowserRouter } from "react-router-dom";

This is the entry point of the application.

ReactDOM.createRoot(document.getElementById("root")).render(
    <BrowserRouter>
        <App />
    </BrowserRouter>
);
What BrowserRouter does

BrowserRouter watches the browser URL.

For example,

localhost:5173/

or

localhost:5173/about

or

localhost:5173/contact

Whenever the URL changes, BrowserRouter tells React Router which component should be displayed.

Without BrowserRouter,

<Route> doesn't work.
<NavLink> doesn't work.
<Outlet> doesn't work.

It is the parent of the whole routing system.

Step 2 : App.jsx
function App() {
    return <AppRoutes />;
}

App is intentionally very small.

Its only responsibility is

"Load all the routes."

Later, if you add

ThemeProvider
Redux
Context API
Authentication

they will usually wrap AppRoutes.

Example

<AppProvider>
    <AppRoutes />
</AppProvider>
Step 3 : AppRoutes.jsx

This file contains all routing information.

<Routes>

    <Route path="/" element={<MainLayout />}>

        <Route index element={<Home />} />

        <Route path="about" element={<About />} />

        <Route path="services" element={<Services />} />

        <Route path="contact" element={<Contact />} />

    </Route>

</Routes>
Understanding nested routing

This is the most important concept.

Here

MainLayout

becomes the parent.

Everything inside

<Route path="/" element={<MainLayout />}>

is a child.

Think of it like

MainLayout

    Home

    About

    Services

    Contact

The layout never changes.

Only the children change.

What does index mean?
<Route index element={<Home />} />

means

"/"

If the URL is

localhost:5173/

show

<Home />
Child routes
<Route path="about" element={<About />} />

means

localhost:5173/about

show

<About />

Similarly,

/services

/contact

display their corresponding components.

Step 4 : MainLayout.jsx

This is the page skeleton.

<>
    <Navbar />

    <MainContent />

    <Footer />
</>

Think of it as

+-----------------------+
| Navbar                |
+-----------------------+
|                       |
|      MainContent      |
|                       |
+-----------------------+
| Footer                |
+-----------------------+

Every page uses this same structure.

So if you go

Home

↓

About

↓

Services

Navbar never reloads.

Footer never reloads.

Only MainContent changes.

This makes the application much faster.

Step 5 : MainContent.jsx
<main>

    <Outlet />

</main>

This file is extremely important.

What is Outlet?

Outlet is simply

"Render whichever child route is currently active."

Think of it as an empty placeholder.

Initially

Outlet

contains

Home

When you click About,

Outlet

becomes

About

Click Contact,

Outlet

becomes

Contact

Nothing else changes.

Visualization

MainLayout

Navbar

↓

Outlet

↓

Home

Later

MainLayout

Navbar

↓

Outlet

↓

About

Same layout.

Different page.

Step 6 : Navbar.jsx

Navbar contains

<NavLink to="/">
<NavLink to="/about">

etc.

Unlike <a> tags,

NavLink

does NOT refresh the page.
changes the URL.
tells React Router to update Outlet.

That is why React applications feel instant.

Why NavLink instead of Link?

Both navigate.

But NavLink additionally knows

"Am I the active page?"

So

.active

gets applied automatically.

Example

Home page

Home   About   Contact

^

blue

About page

Home   About   Contact

        ^

       blue
Step 7 : Footer.jsx

Footer is just another reusable component.

Because it lives inside MainLayout,

it is displayed on every page.

Step 8 : Individual Pages

Example

Home.jsx

About.jsx

Services.jsx

Contact.jsx

Each page returns its own content.

Example

function About() {

    return (

        <section>

            <h1>About</h1>

        </section>

    );

}

They don't know anything about Navbar.

They don't know anything about Footer.

Their only job is to display their own page.

Styling

Every component imports its own CSS.

Example

Navbar.jsx

↓

Navbar.css

Footer

↓

Footer.css

Home

↓

Home.css

This makes styles modular and easier to maintain.

Additionally,

global.css

contains

CSS reset
font
body styling
universal selectors

Everything common goes there.

Routing Example

Suppose the browser URL is

localhost:5173/contact

The router reads

/contact

It finds

<Route path="contact">

Then it renders

MainLayout

↓

Navbar

↓

Outlet

↓

Contact

↓

Footer

The browser finally displays

--------------------------------
 Navbar
--------------------------------


 Contact Page


--------------------------------
 Footer
--------------------------------

Now the user clicks

Services

The URL changes to

/services

React Router updates only the Outlet.

The UI becomes

--------------------------------
 Navbar
--------------------------------


 Services Page


--------------------------------
 Footer
--------------------------------

Notice

Navbar was not recreated.
Footer was not recreated.
Only the page component inside Outlet changed.

This selective rendering is one of the reasons single-page applications feel fast and responsive.

Why this architecture is recommended
Separation of concerns: Routing, layout, reusable components, pages, and styles each have a dedicated place.
Reusability: Navbar and Footer are written once and shared across all pages.
Scalability: Adding a new page only requires creating the page component and adding one route.
Maintainability: Component-specific styles stay isolated, reducing unintended side effects.
Performance: Because React Router swaps only the Outlet content instead of reloading the entire page, navigation is smooth and efficient.
