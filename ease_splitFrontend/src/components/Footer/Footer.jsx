import "./Footer.css";

function Footer() {
  return (
    <footer className="footer">
      <div className="footer-container">
        <p>© {new Date().getFullYear()} EaseSplit. Smart Expense Splitting & Debt Settlement Application.</p>
      </div>
    </footer>
  );
}

export default Footer;
