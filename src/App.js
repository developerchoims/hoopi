import './App.css';
import { useState, useEffect} from 'react';
import axios from 'axios';

function App() {

  const [name, setName] = useState('');
  const [msg, setMsg] = useState('');

  useEffect(()=>{
    axios.get("/auth")
      .then((response) => { setMsg(response.data.name); 
                        setName(response.data.id);
                      });
  }, []);

  return (
    <div className="App">
      <ul>
        <li>{msg}</li>
        <li>{name}</li>
      </ul>
    </div>
  );
}

export default App;
