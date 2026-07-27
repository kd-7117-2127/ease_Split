import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { createEvent } from "../api/easeSplitApi";
import "./CreateEvent.css";

function CreateEvent() {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();

    if (!name.trim()) {
      setError("Event name is required");
      return;
    }

    setSubmitting(true);
    setError(null);

    try {
      const event = await createEvent({
        name: name.trim(),
        description: description.trim(),
      });

      navigate(`/events/${event.id}`);
    } catch (err) {
      console.error(err);
      setError(err.message || "Failed to create event");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="app-container create-event-page">
      <div className="back-link">
        <Link to="/">← Back to Events</Link>
      </div>

      <div className="create-card card">
        <div className="form-header">
          <div className="form-icon">🎯</div>
          <h2>Create New Event</h2>
          <p>Set up an event or group to track shared expenses with friends.</p>
        </div>

        {error && <div className="alert-error">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Event Name *</label>
            <input
              type="text"
              className="form-control"
              placeholder="e.g. Goa Trip 2026, House Expenses"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label>Description</label>
            <textarea
              className="form-control"
              rows="3"
              placeholder="Add optional notes, rules, or details about this event..."
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>

          <div className="form-actions">
            <Link to="/" className="btn btn-secondary">
              Cancel
            </Link>
            <button
              type="submit"
              className="btn btn-primary"
              disabled={submitting}
            >
              {submitting ? "Creating Event..." : "Create Event"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CreateEvent;
