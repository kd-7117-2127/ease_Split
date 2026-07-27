import { useState } from "react";
import { createExpense } from "../../api/easeSplitApi";
import "./ExpenseList.css";

function ExpenseList({ eventId, expenses, members, onExpenseAdded }) {
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [paidByUserId, setPaidByUserId] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);

  async function handleSubmit(e) {
    e.preventDefault();
    if (!description.trim() || !amount || !paidByUserId) {
      setError("Please fill in all fields");
      return;
    }

    setSubmitting(true);
    setError(null);

    try {
      await createExpense(eventId, {
        description: description.trim(),
        amount: parseFloat(amount),
        paidByUserId: parseInt(paidByUserId, 10),
      });

      setDescription("");
      setAmount("");
      setPaidByUserId("");
      if (onExpenseAdded) {
        onExpenseAdded();
      }
    } catch (err) {
      setError(err.message || "Failed to add expense");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="expense-list-component card">
      <div className="component-header">
        <h3>💸 Expenses ({expenses.length})</h3>
      </div>

      {error && <div className="alert-error">{error}</div>}

      <div className="expenses-timeline">
        {expenses.length === 0 ? (
          <p className="empty-text">No expenses recorded yet.</p>
        ) : (
          expenses.map((expense) => (
            <div key={expense.id} className="expense-item">
              <div className="expense-main">
                <div className="expense-icon">🧾</div>
                <div className="expense-details">
                  <span className="expense-title">{expense.description}</span>
                  <span className="expense-payer">
                    Paid by <strong>{expense.paidByName || "Unknown"}</strong>
                  </span>
                </div>
              </div>
              <div className="expense-amount">
                ${Number(expense.amount).toFixed(2)}
              </div>
            </div>
          ))
        )}
      </div>

      <form onSubmit={handleSubmit} className="add-expense-form">
        <h4>Add New Expense</h4>

        {members.length === 0 ? (
          <p className="warning-text">⚠️ Please add at least one member before adding an expense.</p>
        ) : (
          <div className="form-grid">
            <div className="form-group">
              <label>Description *</label>
              <input
                type="text"
                className="form-control"
                placeholder="e.g. Hotel, Dinner, Cab"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label>Amount ($) *</label>
              <input
                type="number"
                step="0.01"
                min="0.01"
                className="form-control"
                placeholder="0.00"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                required
              />
            </div>

            <div className="form-group">
              <label>Paid By *</label>
              <select
                className="form-control"
                value={paidByUserId}
                onChange={(e) => setPaidByUserId(e.target.value)}
                required
              >
                <option value="">-- Select Member --</option>
                {members.map((member) => (
                  <option key={member.userId || member.id} value={member.userId || member.id}>
                    {member.name}
                  </option>
                ))}
              </select>
            </div>

            <div className="form-action">
              <button
                type="submit"
                className="btn btn-primary"
                disabled={submitting}
              >
                {submitting ? "Adding..." : "+ Add Expense"}
              </button>
            </div>
          </div>
        )}
      </form>
    </div>
  );
}

export default ExpenseList;
