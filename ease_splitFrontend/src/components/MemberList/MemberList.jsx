import { useState } from "react";
import { addMember } from "../../api/easeSplitApi";
import "./MemberList.css";

function MemberList({ eventId, members, onMemberAdded }) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();
    if (!name.trim()) return;

    setSubmitting(true);
    setError(null);

    try {
      await addMember(eventId, {
        name: name.trim(),
        email: email.trim() || undefined,
      });

      setName("");
      setEmail("");
      if (onMemberAdded) {
        onMemberAdded();
      }
    } catch (err) {
      setError(err.message || "Failed to add member");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="member-list-component card">
      <div className="component-header">
        <h3>👥 Members ({members.length})</h3>
      </div>

      {error && <div className="alert-error">{error}</div>}

      <div className="members-grid">
        {members.length === 0 ? (
          <p className="empty-text">No members added to this event yet.</p>
        ) : (
          members.map((member) => (
            <div key={member.id} className="member-chip">
              <div className="member-avatar">
                {member.name ? member.name.charAt(0).toUpperCase() : "?"}
              </div>
              <div className="member-info">
                <span className="member-name">{member.name}</span>
                {member.email && (
                  <span className="member-email">{member.email}</span>
                )}
              </div>
            </div>
          ))
        )}
      </div>

      <form onSubmit={handleSubmit} className="add-member-form">
        <h4>Add New Member</h4>
        <div className="form-row">
          <input
            type="text"
            className="form-control"
            placeholder="Member Name *"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <input
            type="email"
            className="form-control"
            placeholder="Email (Optional)"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <button
            type="submit"
            className="btn btn-primary btn-sm"
            disabled={submitting}
          >
            {submitting ? "Adding..." : "+ Add"}
          </button>
        </div>
      </form>
    </div>
  );
}

export default MemberList;
