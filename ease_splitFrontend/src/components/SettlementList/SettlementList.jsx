import "./SettlementList.css";

function SettlementList({ settlements }) {
  return (
    <div className="settlement-list-component card">
      <div className="component-header">
        <h3>🤝 Suggested Settlements ({settlements.length})</h3>
      </div>

      <div className="settlements-container">
        {settlements.length === 0 ? (
          <div className="all-settled-box">
            <span className="settled-icon">🎉</span>
            <p>All group members are completely settled up!</p>
          </div>
        ) : (
          settlements.map((item, index) => (
            <div key={index} className="settlement-card">
              <div className="party debtor">
                <span className="party-role">Debtor</span>
                <span className="party-name">{item.fromUserName}</span>
              </div>

              <div className="transfer-arrow">
                <span className="arrow-text">pays</span>
                <div className="arrow-line">
                  <span className="transfer-amount">${Number(item.amount).toFixed(2)}</span>
                  <span className="arrow-head">➔</span>
                </div>
              </div>

              <div className="party creditor">
                <span className="party-role">Creditor</span>
                <span className="party-name">{item.toUserName}</span>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default SettlementList;
