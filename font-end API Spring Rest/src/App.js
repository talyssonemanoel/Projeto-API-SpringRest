import React, { useState, useRef, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.css';

import axios from 'axios';
import logo from './images/uern.png';


function App() {
  const [users, setUsers] = useState([]);
  const [editingUser, setEditingUser] = useState(null);
  const userInput = useRef(null);
  const [newUser, setNewUser] = useState({ nome: '', email: '', telefone: '' });
  const [editUserData, setEditUserData] = useState({ nome: '', email: '', telefone: '' });
  const [showPopup, setShowPopup] = useState(false);


  const [tasks, setTasks] = useState([]);
  const [editingTask, setEditingTask] = useState(null);
  const taskInput = useRef(null);
  const [newTask, setNewTask] = useState({cliente: {id: null,nome: ''},conteudo:{titulo: '',subtitulo: '',texto: ''}}, {status:''});

  useEffect(() => {
    async function fetchUsers() {
      const response = await fetch('http://localhost:8080/users');
      const data = await response.json();
      setUsers(data);
    }

    async function fetchTasks() {
      const response = await fetch('http://localhost:8080/tasks');
      const data = await response.json();
      setTasks(data);
    }

    fetchUsers();
    fetchTasks();
  }, []);

  function addUser() {
    const { nome, email, telefone } = newUser;
  
    if (nome && email && telefone) {
      const data = { nome, email, telefone };
      const config = { headers: { 'Content-Type': 'application/json' } };
  
      if (editingUser !== null) {
        const updatedUsers = [...users];
        updatedUsers[editingUser] = newUser;
        setUsers(updatedUsers);
        setEditingUser(null);
      } else {
        axios.post('http://localhost:8080/users', data, config)
          .then(response => {
            const newUser = response.data;
            setUsers([...users, newUser]);
            setNewUser({ nome: '', email: '', telefone: '' });
          })
          .catch(error => {
            console.log(error);
          });
      }
    }
  }

  function deleteUser(userId) {
    const index = users.findIndex(user => user.id === userId);
    const newUsers = [...users];
    newUsers.splice(index, 1);
    setUsers(newUsers);
  
    axios.delete(`http://localhost:8080/users/${userId}`)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }

  function deleteTask(taskId) {
    const index = users.findIndex(task => task.id === taskId);
    const newTasks = [...tasks];
    newTasks.splice(index, 1);
    setTasks(newTasks);
  
    axios.put(`http://localhost:8080/${taskId}/finalizacao`)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error);
      });
  }

  function editUser(index) {
    setEditingUser(index);
    userInput.current.value = users[index];
    userInput.current.focus();
  }

  function openPopup(index) {
    setEditingUser(index);
    setEditUserData(users[index]);
    setShowPopup(true);
  }

  function closePopup() {
    setEditingUser(null);
    setEditUserData({ nome: '', email: '', telefone: '' });
    setShowPopup(false);
  }

  function updateUser() {
    const { nome, email, telefone } = editUserData;
  
    if (nome && email && telefone) {
      const data = { nome, email, telefone };
      const config = { headers: { 'Content-Type': 'application/json' } };
  
      axios.put(`http://localhost:8080/users/${users[editingUser].id}`, data, config)
        .then(response => {
          const updatedUser = response.data;
          const updatedUsers = [...users];
          updatedUsers[editingUser] = updatedUser;
          setUsers(updatedUsers);
          closePopup();
        })
        .catch(error => {
          console.log(error);
        });
    }
  }

  return (
    <div style={{ textAlign: 'center'}}>
      <div style={{backgroundColor: '#174ea6'}}>
        <div style={{verticalAlign: 'middle'}}>
          <img src={logo} alt="logo" style={{ width: '150px', float: 'left'}} />
        </div>
        <div>
          <h1 style={{fontWeight: 'bold', color: 'white'}}>LISTA DE USUÁRIOS</h1>
        </div>
      </div>
      {showPopup && (
        <div className="popup">
          <div className="popup-inner">
            <h2>Edit User</h2>
            <div>
              <label htmlFor="nome">Nome:</label>
              <input type="text" id="nome" name="nome" value={editUserData.nome} onChange={(e) => setEditUserData({...editUserData, nome: e.target.value})} />
            </div>
            <div>
              <label htmlFor="email">Email:</label>
              <input type="text" id="email" name="email" value={editUserData.email} onChange={(e) => setEditUserData({...editUserData, email: e.target.value})} />
            </div>
            <div>
              <label htmlFor="telefone">Telefone:</label>
              <input type="text" id="telefone" name="telefone" value={editUserData.telefone} onChange={(e) => setEditUserData({...editUserData, telefone: e.target.value})} />
            </div>
            <button onClick={updateUser} type="button" className="btn btn-success">Salvar</button>
            <button onClick={closePopup} type="button" className="btn btn-danger">Cancelar</button>
          </div>
        </div>
      )}
      <ul className="list-group list-group-flush">
      {users.map((user, index) => (
  <li key={index} className="list-group-item" style={{ textAlign: 'center' }}>
    <div style={{ display: 'flex', flexDirection: 'column' }}>
      <div style={{backgroundColor: 'gray'}}>
        <div style={{fontSize: 20, fontWeight: 'bold'}}>
          <span>{user.nome}</span>
        </div>
        <div style={{fontSize: 13, color: 'white'}}>
          <span >Email: {user.email}, </span>
          <span >Telefone: {user.telefone}</span>
        </div>
      </div>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Tarefa</th>
            <th scope="col">Descrição</th>
            <th scope="col">Status</th>
            <th scope="col">Ação</th>
          </tr>
        </thead>
        <tbody>
        {tasks
          .filter(task => task.cliente.id === user.id)
          .map((task, index) => (
            <tr key={index}>
              <td>
                <span>
                  {task.conteudo.titulo}
                </span>
              </td>
              <td>
                <span>
                  {task.conteudo.subtitulo}
                </span>
              </td>
              <td>
                <span>
                  {task.status}
                </span>
              </td>
              <td>
                <span>
                  <button onClick={() => deleteTask(task.id)} type="button" className="btn btn-danger " style={{margin: '2px'}}>Finalizar</button>
                </span>
              </td>
            </tr>
          ))}          
        </tbody>
      </table>
      <div>
        <button onClick={() => deleteUser(user.id)} type="button" className="btn btn-danger" style={{margin: '2px'}}>Remover usuário</button>
        <button onClick={() => openPopup(index)} type="button" className="btn btn-warning" style={{margin: '2px'}}>Editar usuário</button>
      </div>
    </div>
  </li>
))}
      </ul>
      <div>
      <div>
        <div>
          <label htmlFor="nome">Nome:</label>
          <input type="text" id="nome" name="nome" value={newUser.nome} onChange={(e) => setNewUser({...newUser, nome: e.target.value})} />
        </div>
        <div>
          <label htmlFor="email">Email:</label>
          <input type="text" id="email" name="email" value={newUser.email} onChange={(e) => setNewUser({...newUser, email: e.target.value})} />
        </div>
        <div>
          <label htmlFor="telefone">Telefone:</label>
          <input type="text" id="telefone" name="telefone" value={newUser.telefone} onChange={(e) => setNewUser({...newUser, telefone: e.target.value})} />
          </div>
          <button onClick={addUser} type="button" className="btn btn-success">{editingUser !== null ? 'Edit' : 'Add'}</button>
        </div>
      </div>
    </div>
  );
}

export default App;