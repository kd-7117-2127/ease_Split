import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { getEvents } from "../api/easeSplitApi";

function Home() {

  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    async function loadEvents() {

      try {

        const data = await getEvents();

        setEvents(data);

      } catch (error) {

        console.error(error);

      } finally {

        setLoading(false);

      }

    }

    loadEvents();

  }, []);

  if (loading) {
    return <h2>Loading...</h2>;
  }

  return (
    <section>

      <h1>My Events</h1>

      <Link to="/events/new">
        Create New Event
      </Link>

      <div>

        {events.map((event) => (

          <div key={event.id}>

            <h2>{event.name}</h2>

            <p>{event.description}</p>

            <Link to={`/events/${event.id}`}>
              Open Event
            </Link>

          </div>

        ))}

      </div>

    </section>
  );
}

export default Home;
