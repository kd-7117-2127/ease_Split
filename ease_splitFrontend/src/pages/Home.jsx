import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getEvents } from "../api/easeSplitApi";
import "./Home.css";

function Home() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function loadEvents() {
      try {
        const data = await getEvents();
        setEvents(data);
      } catch (err) {
        console.error(err);
        setError("Failed to load events. Make sure backend server is running.");
      } finally {
        setLoading(false);
      }
    }

    loadEvents();
  }, []);

  if (loading) {
    return (
      <div className="app-container loading-container">
        <div className="spinner"></div>
        <p>Loading your events...</p>
      </div>
    );
  }

  return (
    <div className="app-container home-page">
      <div className="page-header">
        <div>
          <h1>My Events</h1>
          <p className="subtitle">Manage group expenses, split bills, and track settlements.</p>
        </div>
        <Link to="/events/new" className="btn btn-primary">
          + Create New Event
        </Link>
      </div>

      {error && <div className="alert-error">{error}</div>}

      {events.length === 0 ? (
        <div className="empty-state card">
          <div className="empty-icon">🎪</div>
          <h2>No Events Found</h2>
          <p>Create your first event (e.g., "Goa Trip", "Flat Expenses") to start splitting bills!</p>
          <Link to="/events/new" className="btn btn-primary" style={{ marginTop: "1rem" }}>
            Create Event
          </Link>
        </div>
      ) : (
        <div className="events-grid">
          {events.map((event) => (
            <div key={event.id} className="event-card card card-hover">
              <div className="event-card-header">
                <div className="event-avatar">
                  {event.name ? event.name.charAt(0).toUpperCase() : "E"}
                </div>
                <h3>{event.name}</h3>
              </div>

              <p className="event-description">
                {event.description || "No description provided."}
              </p>

              <div className="event-card-footer">
                <span className="event-date">
                  {event.createdAt ? new Date(event.createdAt).toLocaleDateString() : "Recently created"}
                </span>
                <Link to={`/events/${event.id}`} className="btn btn-secondary btn-sm">
                  Open Dashboard ➔
                </Link>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Home;
