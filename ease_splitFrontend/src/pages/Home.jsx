import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getEvents } from "../api/easeSplitApi";
import { Plus, ArrowRight, Calendar, Wallet, ChevronRight } from "../components/icons";
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

  return (
    <div className="app-container home-page">
      <section className="hero">
        <p className="hero-overline">EaseSplit</p>
        <h1 className="hero-title">
          Split the cost.<br />
          Settle the <span className="accent-word">balance</span>.
        </h1>
        <p className="hero-sub">
          Track shared expenses with the people you trust. No fuss, no
          awkward math — everyone knows exactly what they owe.
        </p>
        <div className="hero-actions">
          <Link to="/events/new" className="btn btn-primary">
            <Plus size={16} />
            Create an event
          </Link>
          <a href="#events" className="btn btn-ghost">
            Your events
            <ArrowRight size={16} />
          </a>
        </div>
      </section>

      <section id="events" className="events-section">
        <div className="events-heading">
          <h2>
            Your events
            {!loading && !error && events.length > 0 && (
              <span className="count">{events.length}</span>
            )}
          </h2>
          <div className="heading-rule" aria-hidden="true"></div>
        </div>

        {error && <div className="alert-error">{error}</div>}

        {loading ? (
          <div className="loading-container">
            <div className="spinner"></div>
            <p>Loading your events…</p>
          </div>
        ) : events.length === 0 ? (
          <div className="empty-state card">
            <div className="empty-icon">
              <Wallet size={28} />
            </div>
            <h3>Nothing here yet</h3>
            <p>
              Start with a trip, a flat, or a dinner out. Everyone gets a
              fair share, and the math takes care of itself.
            </p>
            <Link to="/events/new" className="btn btn-primary">
              <Plus size={16} />
              Create your first event
            </Link>
          </div>
        ) : (
          <div className="events-grid">
            {events.map((event) => (
              <Link
                key={event.id}
                to={`/events/${event.id}`}
                className="event-card card"
              >
                <div className="event-card-top">
                  <div className="event-avatar" aria-hidden="true">
                    {event.name ? event.name.charAt(0).toUpperCase() : "E"}
                  </div>
                  <ChevronRight size={18} className="event-arrow" />
                </div>

                <div className="event-card-body">
                  <h3>{event.name}</h3>
                  <p className="event-description">
                    {event.description || "Shared expenses"}
                  </p>
                </div>

                <div className="event-card-footer">
                  <Calendar size={14} />
                  <span className="event-date">
                    {event.createdAt
                      ? new Date(event.createdAt).toLocaleDateString(undefined, {
                          day: "numeric",
                          month: "short",
                          year: "numeric",
                        })
                      : "Recently created"}
                  </span>
                </div>
              </Link>
            ))}
          </div>
        )}
      </section>
    </div>
  );
}

export default Home;
