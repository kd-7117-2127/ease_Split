import { Routes, Route } from "react-router-dom";

import MainLayout from "../layouts/MainLayout";

import Home from "../pages/Home";
import CreateEvent from "../pages/CreateEvent";
import EventDetails from "../pages/EventDetails";

function AppRoutes() {
  return (
    <Routes>

      <Route path="/" element={<MainLayout />}>

        <Route index element={<Home />} />

        <Route
          path="events/new"
          element={<CreateEvent />}
        />

        <Route
          path="events/:eventId"
          element={<EventDetails />}
        />

      </Route>

    </Routes>
  );
}

export default AppRoutes;
