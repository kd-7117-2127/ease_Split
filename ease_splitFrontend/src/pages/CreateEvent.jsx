import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { createEvent } from "../api/easeSplitApi";

function CreateEvent() {

  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [description, setDescription] =
    useState("");

  async function handleSubmit(e) {

    e.preventDefault();

    try {

      const event = await createEvent({
        name,
        description,
      });

      navigate(`/events/${event.id}`);

    } catch (error) {

      console.error(error);

    }
  }

  return (
    <section>

      <h1>Create Event</h1>

      <form onSubmit={handleSubmit}>

        <div>
          <label>Event Name</label>

          <input
            type="text"
            value={name}
            onChange={(e) =>
              setName(e.target.value)
            }
            required
          />
        </div>

        <div>
          <label>Description</label>

          <textarea
            value={description}
            onChange={(e) =>
              setDescription(e.target.value)
            }
          />
        </div>

        <button type="submit">
          Create Event
        </button>

      </form>

    </section>
  );
}

export default CreateEvent;
