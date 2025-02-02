// Event Management
// ---------------------------------------
const apiUrl = "http://localhost:8080/api/events";

async function loadEvents() {
  try {
    const response = await fetch(apiUrl);
    const events = await response.json();
    const tbody = document.querySelector(".custom-table tbody");
    tbody.innerHTML = "";

    events.forEach((event, index) => {
      const row = `
        <tr>
          <td>${index + 1}</td>
          <td>${event.name}</td>
          <td>${event.category}</td>
          <td>${event.location}</td>
          <td>${new Date(event.date).toLocaleDateString()}</td>
          <td>
            <img src="http://localhost:8080${
              event.posterImagePath
            }" alt="Poster" style="width: 30px; height: 30px;" />
            <br />
          </td>
          <td>
            <button class="btn btn-outline-success btn-sm" onclick="editEvent(${
              event.id
            })"><i class="bi bi-pencil"></i> Edit</button>
            <button class="btn btn-outline-danger btn-sm" onclick="deleteEvent(${
              event.id
            })"><i class="bi bi-trash"></i> Delete</button>
          </td>
        </tr>
      `;
      tbody.innerHTML += row;
    });
  } catch (error) {
    console.error("Error loading events:", error);
  }
}

async function saveEvent() {
  const formData = new FormData();
  const event = {
    name: document.getElementById("eventName").value,
    category: document.getElementById("eventCategory").value,
    location: document.getElementById("eventLocation").value,
    date: document.getElementById("eventDate").value,
    description: document.getElementById("eventDescription").value,
  };

  const poster = document.getElementById("eventPoster").files[0];
  formData.append("event", JSON.stringify(event));
  if (poster) {
    formData.append("poster", poster);
  }

  try {
    const response = await fetch(apiUrl, {
      method: "POST",
      body: formData,
    });

    if (response.ok) {
      alert("Event Created Successfully!");
      document
        .getElementById("createEventModal")
        .querySelector(".btn-close")
        .click();
      loadEvents();
    } else {
      alert("Error creating event!");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("An error occurred while creating the event.");
  }
}

async function deleteEvent(id) {
  if (confirm("Are you sure you want to delete this event?")) {
    try {
      const response = await fetch(`${apiUrl}/${id}`, { method: "DELETE" });
      if (response.ok) {
        alert("Event Deleted Successfully!");
        loadEvents();
      } else {
        alert("Error deleting event!");
      }
    } catch (error) {
      console.error("Error deleting event:", error);
    }
  }
}

async function editEvent(eventId) {
  try {
    const response = await fetch(`${apiUrl}/${eventId}`);
    const event = await response.json();

    document.getElementById("editEventId").value = event.id;
    document.getElementById("editEventName").value = event.name;
    document.getElementById("editEventCategory").value = event.category;
    document.getElementById("editEventLocation").value = event.location;
    document.getElementById("editEventDate").value = event.date.substring(
      0,
      10
    );

    document.getElementById("editEventDescription").value = event.description;

    const editModal = new bootstrap.Modal(
      document.getElementById("editEventModal")
    );
    editModal.show();
  } catch (error) {
    console.error("Error fetching event data:", error);
    alert("Failed to fetch event details.");
  }
}

async function updateEvent() {
  const eventId = document.getElementById("editEventId").value;
  const event = {
    name: document.getElementById("editEventName").value,
    category: document.getElementById("editEventCategory").value,
    location: document.getElementById("editEventLocation").value,
    date: document.getElementById("editEventDate").value,
    description: document.getElementById("editEventDescription").value,
  };

  try {
    const response = await fetch(`${apiUrl}/${eventId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(event),
    });

    if (response.ok) {
      alert("Event updated successfully!");
      document
        .getElementById("editEventModal")
        .querySelector(".btn-close")
        .click();
      loadEvents();
    } else {
      alert("Error updating event!");
    }
  } catch (error) {
    console.error("Error updating event:", error);
    alert("An error occurred while updating the event.");
  }
}

// JWT Token Management
// ---------------------------------------

// Decode a JWT token
function decodeToken(token) {
  try {
    const payloadBase64 = token.split(".")[1];
    const payloadJson = atob(payloadBase64);
    return JSON.parse(payloadJson);
  } catch (error) {
    console.error("Invalid token format", error);
    return null;
  }
}

function getInitialsFromEmail(email) {
  if (!email) return "NA";
  const parts = email.split("@")[0].split(/[._]/);
  const initials = parts.map((part) => part.charAt(0).toUpperCase()).join("");
  return initials.slice(0, 2);
}

function saveInitialsFromToken(token) {
  const decodedToken = decodeToken(token);
  if (decodedToken && (decodedToken.email || decodedToken.sub)) {
    const email = decodedToken.email || decodedToken.sub;
    const initials = getInitialsFromEmail(email);
    localStorage.setItem("userInitials", initials);
  } else {
    console.error("Token does not contain an email or subject.");
  }
}

function displayInitials() {
  const initials = localStorage.getItem("userInitials") || "NA";
  const profileCircle = document.querySelector(".profile");
  profileCircle.textContent = initials;
}

document.addEventListener("DOMContentLoaded", () => {
  loadEvents();

  const token = localStorage.getItem("authToken");
  if (token) {
    saveInitialsFromToken(token);
  }
  displayInitials();
});
