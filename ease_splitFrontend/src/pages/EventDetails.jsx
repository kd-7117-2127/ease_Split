import { useEffect, useState, useCallback } from "react";
import { useParams, Link } from "react-router-dom";
import {
  getEventDetails,
  getMembers,
  getExpenses,
  getBalances,
  getSettlements,
} from "../api/easeSplitApi";

import MemberList from "../components/MemberList/MemberList";
import ExpenseList from "../components/ExpenseList/ExpenseList";
import BalanceList from "../components/BalanceList/BalanceList";
import SettlementList from "../components/SettlementList/SettlementList";

import "./EventDetails.css";

function EventDetails() {
  const { eventId } = useParams();

  const [event, setEvent] = useState(null);
  const [members, setMembers] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [balances, setBalances] = useState([]);
  const [settlements, setSettlements] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const loadAllData = useCallback(async () => {
    try {
      const [eventData, membersData, expensesData, balancesData, settlementsData] =
        await Promise.all([
          getEventDetails(eventId).catch(() => null),
          getMembers(eventId),
          getExpenses(eventId),
          getBalances(eventId),
          getSettlements(eventId),
        ]);

      if (eventData) {
        setEvent(eventData);
      }
      setMembers(membersData || []);
      setExpenses(expensesData || []);
      setBalances(balancesData || []);
      setSettlements(settlementsData || []);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch event data. Please verify the event ID.");
    } finally {
      setLoading(false);
    }
  }, [eventId]);

  useEffect(() => {
    void (async () => {
      await loadAllData();
    })();
  }, [loadAllData]);

  if (loading) {
    return (
      <div className="app-container loading-container">
        <div className="spinner"></div>
        <p>Loading event dashboard...</p>
      </div>
    );
  }

  const totalSpent = expenses.reduce(
    (sum, item) => sum + (parseFloat(item.amount) || 0),
    0
  );

  return (
    <div className="app-container event-details-page">
      <div className="dashboard-top-nav">
        <Link to="/" className="back-btn">
          ← Back to Events
        </Link>
      </div>

      {error && <div className="alert-error">{error}</div>}

      {/* Header & Stats Banner */}
      <div className="event-banner card">
        <div className="banner-info">
          <div className="banner-avatar">
            {event?.name ? event.name.charAt(0).toUpperCase() : "E"}
          </div>
          <div>
            <h1>{event?.name || `Event #${eventId}`}</h1>
            <p className="banner-desc">
              {event?.description || "Shared expense tracking event"}
            </p>
          </div>
        </div>

        <div className="banner-stats">
          <div className="stat-card">
            <span className="stat-label">Total Expense</span>
            <span className="stat-value primary-stat">${totalSpent.toFixed(2)}</span>
          </div>
          <div className="stat-card">
            <span className="stat-label">Members</span>
            <span className="stat-value">{members.length}</span>
          </div>
          <div className="stat-card">
            <span className="stat-label">Expenses</span>
            <span className="stat-value">{expenses.length}</span>
          </div>
        </div>
      </div>

      {/* Main Grid Layout */}
      <div className="dashboard-grid">
        <div className="grid-col left-col">
          <MemberList
            eventId={eventId}
            members={members}
            onMemberAdded={loadAllData}
          />
          <ExpenseList
            eventId={eventId}
            expenses={expenses}
            members={members}
            onExpenseAdded={loadAllData}
          />
        </div>

        <div className="grid-col right-col">
          <BalanceList balances={balances} />
          <SettlementList settlements={settlements} />
        </div>
      </div>
    </div>
  );
}

export default EventDetails;
