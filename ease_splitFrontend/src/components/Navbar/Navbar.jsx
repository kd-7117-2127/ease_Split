import { NavLink, Link } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  return (
    <header className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          <div className="brand-icon">⚡</div>
          <span className="brand-title">Ease<span className="brand-accent">Split</span></span>
        </Link>

        <nav className="navbar-nav">
          <NavLink
            to="/"
            end
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            Events
          </NavLink>
          <NavLink
            to="/events/new"
            className={({ isActive }) =>
              isActive ? "nav-link active" : "nav-link"
            }
          >
            + Create Event
          </NavLink>
        </nav>
      </div>
    </header>
  );
}

export default Navbar;
