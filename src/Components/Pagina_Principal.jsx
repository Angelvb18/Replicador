import React, { useState } from "react";
import "./Pagina_Principal.css";
import "../Styles/Botones.css";
import Replicacion from "./MiniVentanas/Replicacion/Replicacion"
import Conexion from "./MiniVentanas/Conexion/Conexion";
function P_Principal() {
  const [show_Conexion, setShow_Conexion] = useState(false);
  const handleClose_Conexion = () => setShow_Conexion(false);
  const handleShow_Conexion = () => setShow_Conexion(true);
  const [show_Replicacion, setShow_Replicacion] = useState(false);
  const handleClose_Replicacion = () => setShow_Replicacion(false);
  const handleShow_Replicacion = () => setShow_Replicacion(true);
  
  return (
    <>
      <div className="container-botton-Principal">
        <h1 className="h_principal">Menu</h1>
        <button onClick={handleShow_Conexion} className="boton_5">
          <p>Configuración</p>
        </button>
        <button  onClick={handleShow_Replicacion} className="boton_1">
          <p>Replicacion</p>
        </button>
      </div>
      <Conexion show_Conexion = {show_Conexion} handleClose_Conexion= {handleClose_Conexion}/>
      <Replicacion show_Replicacion = {show_Replicacion} handleClose_Replicacion = {handleClose_Replicacion}/>
    </>
  );
}

export default P_Principal;
