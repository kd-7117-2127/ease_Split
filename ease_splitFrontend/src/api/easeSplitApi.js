const BASE_URL = "http://localhost:8080/api";

async function request(url, options = {}) {

  const response = await fetch(`${BASE_URL}${url}`, {
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
    ...options,
  });

  if (!response.ok) {
    throw new Error(
      `Request failed with status ${response.status}`
    );
  }

  return response.json();
}


// =====================
// EVENTS
// =====================

export function getEvents() {
  return request("/events");
}

export function createEvent(event) {
  return request("/events", {
    method: "POST",
    body: JSON.stringify(event),
  });
}


// =====================
// MEMBERS
// =====================

export function getMembers(eventId) {
  return request(`/events/${eventId}/members`);
}

export function addMember(eventId, member) {
  return request(`/events/${eventId}/members`, {
    method: "POST",
    body: JSON.stringify(member),
  });
}


// =====================
// EXPENSES
// =====================

export function getExpenses(eventId) {
  return request(`/events/${eventId}/expenses`);
}

export function createExpense(eventId, expense) {
  return request(`/events/${eventId}/expenses`, {
    method: "POST",
    body: JSON.stringify(expense),
  });
}


// =====================
// BALANCES
// =====================

export function getBalances(eventId) {
  return request(`/events/${eventId}/balances`);
}


// =====================
// SETTLEMENTS
// =====================

export function getSettlements(eventId) {
  return request(`/events/${eventId}/settlements`);
}
