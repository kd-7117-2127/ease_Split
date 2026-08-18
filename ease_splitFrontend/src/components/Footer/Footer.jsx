import "./Footer.css";

function Footer() {
  return (
    <footer className="footer">
      <div className="footer-container">
        <p className="footer-wordmark">EaseSplit</p>
        <p className="footer-note">
          Smart expense splitting and debt settlement.
        </p>
        <p className="footer-legal">
          © {new Date().getFullYear()} EaseSplit
        </p>
      </div>
    </footer>
  );
}

export default Footer;
