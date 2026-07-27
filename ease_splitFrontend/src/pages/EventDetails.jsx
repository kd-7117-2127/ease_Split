import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import {
  getMembers,
  getExpenses,
  getBalances,
  getSettlements
} from "../api/easeSplitApi";

function EventDetails() {

  const { eventId } = useParams();

  const [members, setMembers] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [balances, setBalances] = useState([]);
  const [settlements, setSettlements] =
    useState([]);

  async function loadData() {

    try {

      const [
        membersData,
        expensesData,
        balancesData,
        settlementsData
      ] = await Promise.all([
        getMembers(eventId),
        getExpenses(eventId),
        getBalances(eventId),
        getSettlements(eventId)
      ]);

      setMembers(membersData);
      setExpenses(expensesData);
      setBalances(balancesData);
      setSettlements(settlementsData);

    } catch (error) {

      console.error(error);

    }
  }

  useEffect(() => {
    loadData();
  }, [eventId]);

  return (
    <section>

      <h1>Event Dashboard</h1>

      <h2>Members</h2>

      {members.map((member) => (
        <p key={member.id}>
          {member.user.name}
        </p>
      ))}


      <h2>Expenses</h2>

      {expenses.map((expense) => (
        <div key={expense.id}>

          <strong>
            {expense.description}
          </strong>

          {" - $"}

          {expense.amount}

          {" - Paid by "}

          {expense.paidBy.name}

        </div>
      ))}


      <h2>Balances</h2>

      {balances.map((balance) => (
        <p key={balance.userId}>

          {balance.name}

          {" : $"}

          {balance.balance}

        </p>
      ))}


      <h2>Settlements</h2>

      {settlements.map((settlement, index) => (
        <p key={index}>

          {settlement.fromUserName}

          {" pays "}

          {settlement.toUserName}

          {" : $"}

          {settlement.amount}

        </p>
      ))}

    </section>
  );
}

export default EventDetails;
