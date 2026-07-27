import "./BalanceList.css";

function BalanceList({ balances }) {
  return (
    <div className="balance-list-component card">
      <div className="component-header">
        <h3>⚖️ Net Balances ({balances.length})</h3>
      </div>

      <div className="balances-grid">
        {balances.length === 0 ? (
          <p className="empty-text">No balance data available yet.</p>
        ) : (
          balances.map((item) => {
            const bal = Number(item.balance);
            let badgeClass = "badge-neutral";
            let statusText = "Settled";
            let formattedAmount = `$${Math.abs(bal).toFixed(2)}`;

            if (bal > 0) {
              badgeClass = "badge-positive";
              statusText = `Gets back ${formattedAmount}`;
            } else if (bal < 0) {
              badgeClass = "badge-negative";
              statusText = `Owes ${formattedAmount}`;
            }

            return (
              <div key={item.userId} className="balance-card">
                <div className="balance-header">
                  <span className="user-name">{item.name}</span>
                  <span className={`badge ${badgeClass}`}>{statusText}</span>
                </div>
                <div className="balance-value">
                  <span className={bal > 0 ? "amount-pos" : bal < 0 ? "amount-neg" : "amount-zero"}>
                    {bal > 0 ? `+$${bal.toFixed(2)}` : bal < 0 ? `-$${Math.abs(bal).toFixed(2)}` : "$0.00"}
                  </span>
                </div>
              </div>
            );
          })
        )}
      </div>
    </div>
  );
}

export default BalanceList;
