import { NavLink, Link } from "react-router-dom";
import { Plus } from "../icons";
import "./Navbar.css";

function Navbar() {
  return (
    <header className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-brand">
          <span className="brand-mark" aria-hidden="true">E</span>
          <span className="brand-title">EaseSplit</span>
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
          <Link to="/events/new" className="btn btn-primary btn-sm navbar-cta">
            <Plus size={14} />
            Create Event
          </Link>
        </nav>
      </div>
    </header>
  );
}

export default Navbar;
