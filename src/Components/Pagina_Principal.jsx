import React, { useState } from "react";
import "./Pagina_Principal.css"
import "../Styles/Botones.css"
function P_Principal() {
    
    return (
      <div className="container-botton-Principal">
        <h1 className="h_principal">Menu</h1>
        <button
          className="boton_5"
          
        >
          <p>Conexión</p>
        </button>
        <button
          className="boton_1"
          
        >
          <p>Replicacion</p>
        </button>
        
      </div>
      
    );
  }
  
  export default P_Principal;