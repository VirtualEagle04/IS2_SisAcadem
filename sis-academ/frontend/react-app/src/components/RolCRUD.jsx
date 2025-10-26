import React, { useEffect, useState } from "react";
import axios from "axios";

const API_URL = "http://localhost:8080/api/roles";

export default function RolCRU() {
  const [roles, setRoles] = useState([]);
  const [formData, setFormData] = useState({
    idRol: null,
    nombre: "",
    soloLectura: false,
    rol: false,
    usuario: false,
    grado: false,
    curso: false,
    materia: false,
    actividad: false,
    horario: false,
    nota: false,
    asistencia: false,
    matricula: false,
  });
  const [editing, setEditing] = useState(false);

  const resetFormData = () => {
    setFormData({
      idRol: null,
      nombre: "",
      soloLectura: false,
      rol: false,
      usuario: false,
      grado: false,
      curso: false,
      materia: false,
      actividad: false,
      horario: false,
      nota: false,
      asistencia: false,
      matricula: false,
    });
  };

  // GET
  useEffect(() => {
    fetchRoles();
  }, []);

  const fetchRoles = async () => {
    const res = await axios.get(`${API_URL}/getall`);
    setRoles(res.data);
  };

  // POST & PUT
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (editing) {
      await axios.put(`${API_URL}/update/${formData.id}`, formData);
    } else {
      await axios.post(`${API_URL}/create`, formData);
    }

    resetFormData();
    setEditing(false);
    fetchRoles();
  };

  // EDIT
  const handleEdit = (rol) => {
    setFormData(rol);
    setEditing(true);
  };

  // DELETE
  const handleDelete = async (id) => {
    await axios.delete(`${API_URL}/delete/${id}`);
    fetchRoles();
  };

  return (
    <div>
      <h2>Gestión de Roles</h2>
      <form onSubmit={handleSubmit}>
        <input type="number" placeholder="ID Rol" value={formData.idRol} onChange={(e) => setFormData({...formData, idRol: e.target.value})} required />
        <input type="text" placeholder="Nombre" value={formData.nombre} onChange={(e) => setFormData({...formData, nombre: e.target.value})} required />
        <input type="checkbox" checked={formData.soloLectura} onChange={(e) => setFormData({...formData, soloLectura: e.target.checked})} /> Solo Lectura
        <input type="checkbox" checked={formData.rol} onChange={(e) => setFormData({...formData, rol: e.target.checked})} /> Rol
        <input type="checkbox" checked={formData.usuario} onChange={(e) => setFormData({...formData, usuario: e.target.checked})} /> Usuario
        <input type="checkbox" checked={formData.grado} onChange={(e) => setFormData({...formData, grado: e.target.checked})} /> Grado
        <input type="checkbox" checked={formData.curso} onChange={(e) => setFormData({...formData, curso: e.target.checked})} /> Curso
        <input type="checkbox" checked={formData.materia} onChange={(e) => setFormData({...formData, materia: e.target.checked})} /> Materia
        <input type="checkbox" checked={formData.actividad} onChange={(e) => setFormData({...formData, actividad: e.target.checked})} /> Actividad
        <input type="checkbox" checked={formData.horario} onChange={(e) => setFormData({...formData, horario: e.target.checked})} /> Horario
        <input type="checkbox" checked={formData.nota} onChange={(e) => setFormData({...formData, nota: e.target.checked})} /> Nota
        <input type="checkbox" checked={formData.asistencia} onChange={(e) => setFormData({...formData, asistencia: e.target.checked})} /> Asistencia
        <input type="checkbox" checked={formData.matricula} onChange={(e) => setFormData({...formData, matricula: e.target.checked})} /> Matricula
        
        <button type="submit">{editing ? "Actualizar Rol" : "Crear Rol"}</button>
      </form>
      
      <ul>
				{roles.map((r) => (
						<li key={r.idRol}>
								{r.nombre}
								{r.soloLectura ? "(Solo Lectura)" : ""}
								{r.rol ? " [Rol]" : ""}
								{r.usuario ? " [Usuario]" : ""}
								{r.grado ? " [Grado]" : ""}
								{r.curso ? " [Curso]" : ""}
								{r.materia ? " [Materia]" : ""}
								{r.actividad ? " [Actividad]" : ""}
								{r.horario ? " [Horario]" : ""}
								{r.nota ? " [Nota]" : ""}
								{r.asistencia ? " [Asistencia]" : ""}
								{r.matricula ? " [Matricula]" : ""}
								<button onClick={() => handleEdit(r)}>Editar</button>
								<button onClick={() => handleDelete(r.idRol)}>Eliminar</button>
						</li>
				))}
			</ul>
    </div>
  );
}
