import { NavLink } from "react-router-dom";

import "./Navbar.css";

function Navbar() {
  return (
    <nav className="navbar">

      <NavLink to="/" className="logo">
        Ease Split
      </NavLink>

      <div className="nav-links">

        <NavLink to="/">
          Events
        </NavLink>

        <NavLink to="/events/new">
          Create Event
        </NavLink>

      </div>

    </nav>
  );
}

export default Navbar;
