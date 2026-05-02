import React from "react";
import Dyna, { s } from "./dynamic";

function List() {
  const [txt, settxt] = React.useState("Add notes");
  var [ll, setll] = React.useState([]);
  const [file, setFile] = React.useState(null);
  const [progress, setProgress] = React.useState(0);
const [status, setStatus] = React.useState("");
const API_URL = `${window.location.protocol}//${window.location.hostname}:8080`;
  function handleFileChange(event) {
  setFile(event.target.files[0]);
}
  function uploadFile() {
  if (!file) {
    setStatus("No file selected");
    return;
  }

  const formData = new FormData();
  formData.append("file", file);

  const xhr = new XMLHttpRequest();

  xhr.open("POST", API_URL+"/api/todo/v1/upload");

  // 📊 Track upload progress
  xhr.upload.onprogress = function (event) {
    if (event.lengthComputable) {
      const percent = Math.round((event.loaded / event.total) * 100);
      setProgress(percent);
      setStatus("Uploading...");
    }
  };

  // ✅ Success
  xhr.onload = function () {
    if (xhr.status === 200) {
      setStatus("Upload completed ✅");
    } else {
      setStatus("Upload failed ❌");
    }
  };

  // ❌ Error
  xhr.onerror = function () {
    setStatus("Upload error ❌");
  };

  xhr.send(formData);
}
  React.useEffect(() => {
  fetch(API_URL+"/api/todo/v1/getAll")
    .then((response) => response.json())
    .then((data) => {
      console.log("Fetched todos:", data);

      const formatted = data.map((item) => ({
        uuid: item.uuid,
        content: item.content,
        time: item.createdTime
          ? new Date(item.createdTime).toLocaleTimeString()
          : ""
      }));

      console.log("Formatted:", formatted); // 👈 add this

      setll(formatted);
    })
    .catch((error) => {
      console.error("Error fetching todos:", error);
    });
}, []);// 👈 runs only once on load
  const [hove, sethover] = React.useState(false);
  var l = "";
  function clear(event) {
    event.target.value = "";
    settxt("");
  }
  function clicked(event) {
    l = event.target.value;
    settxt(event.target.value);
    console.log(txt);
  }

  function fun() {
  const newTodo = {
    content: txt,
    userName: "aswin", // you can make this dynamic later
    createdTime: null,
    updatedTime: null
  };

  // 1. Update UI
//  setll(prev => [
//   ...prev,
//   {
//     uuid: Date.now().toString(),   // temporary id
//     content: txt,
//     time: new Date().toLocaleTimeString()
//   }
// ]);

  // 2. Call API
  fetch(API_URL+"/api/todo/v1/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(newTodo)
  })
    .then((response) => response.json())
    .then((data) => {
  setll(prev => [
    ...prev,
    {
      uuid: data.uuid,   // ✅ use backend UUID
      content: data.content,
      time: new Date().toLocaleTimeString()
    }
  ]);
})
    .catch((error) => {
      console.error("Error:", error);
    });

  // 3. Clear input
  settxt("");
}
function unfun() {
  if (ll.length === 0) return;

  // 👉 Get first item (to delete from DB)
  const firstItem = ll[0];

  // 👉 Call DELETE API
  fetch(API_URL+"/api/todo/v1/delete", {
    method: "DELETE",
    headers: {
      uid: firstItem.uuid
    }
  })
    .then((res) => res.text())
    .then((data) => {
      console.log("Deleted:", data);

      // ✅ Update UI safely (NO mutation)
      setll(prev => prev.slice(1));
    })
    .catch((err) => console.error("Delete error:", err));

  // UI toggle logic (your existing logic)
  settxt("");
  if (hove) {
    unddo();
  } else {
    ddo();
  }
}
  function ddo() {
    sethover(true);
  }
  function unddo() {
    sethover(false);
  }
  function style(event) {
    console.log(event.target.id);
  }

  var x = [];

  var j = 0;
  function enter(event) {
    if (event.key === "Enter") {
      fun();
      settxt("");
    }
  }
  function dell() {
    ll.map(function (j) {
      var l = s.length;
      for (var i = 0; i < l; i++) {
        if (j[1] !== s[i][1]) {
          x.push(j);
        }
      }
      return null;
    });

    x = [];
    console.log(x);
  }

  return (
    <div className="container" onKeyDown={enter}>
      <div className="heading">
        <h1>To-Do List</h1>
      </div>
      <div className="form">
        <input
          type="text"
          name="todo"
          onClick={clear}
          onChange={clicked}
          value={txt}
        />
        <button onClick={fun}>
          <span>Add</span>
        </button>
        <button onClick={unfun}>
          <span>Delete the first element</span>
        </button>
        <input type="file" onChange={handleFileChange} />
        <button onClick={uploadFile}>
          <span>Upload File</span>
        </button>
        <div>
        <p>{status}</p>
        <progress value={progress} max="100"></progress>
        <p>{progress}%</p>
      </div>
      </div>
      <div>
        {ll.map(function (k) {
          j = j + 1;
          return <Dyna key={k.uuid} tym={j} lll={k.content} llll={k.time} li={ll} uuid={k.uuid} ff={setll} />;
        })}
      </div>
    </div>
  );
}
export default List;
//1776277634889
//1776277684728
