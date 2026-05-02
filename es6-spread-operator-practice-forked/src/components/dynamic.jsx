import React from "react";
let s = [];
export { s };
const API_URL = `${window.location.protocol}//${window.location.hostname}:8080`;

function Dyna(props) {
  const [hove, sethover] = React.useState(false);

  function ddo() {
    sethover(true);
  }

  function unddo() {
    sethover(false);
  }

  // 🔥 DELETE FUNCTION
  function handleDelete() {
    const uuid = props.uuid;

    // ✅ Call backend
    fetch(API_URL+"/api/todo/v1/delete", {
      method: "DELETE",
      headers: {
        uid: uuid
      }
    })
      .then((res) => res.text())
      .then((data) => {
        console.log("Deleted:", data);

        // ✅ Update UI (remove item)
        props.ff(prev => prev.filter(item => item.uuid !== uuid));
      })
      .catch((err) => console.error("Delete error:", err));
  }

  return (
    <div className="row">
      <div
        className="col-lg-6 col-md-12"
        onMouseOver={ddo}
        onMouseOut={unddo}
      >
        {props.lll + " - "}

        <button className="de" onClick={handleDelete}>
          Delete
        </button>

        {hove && (
          <div>
            <p>created time {props.llll}</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default Dyna;