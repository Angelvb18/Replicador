import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import './Conexion.css'
function Conexion({show_Conexion,handleClose_Conexion}) {
  
  return (
    <>
      <Modal
        show={show_Conexion}
        onHide={handleClose_Conexion}
        backdrop="static"
        keyboard={false}
        fullscreen = {true}
      >
        <Modal.Header closeButton>
          <Modal.Title>Configuración</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <div className='connection_container'>
            <div className='Conection_Space'>
                <h2>Base de Datos Origen</h2>
                
            </div>
            <div className='Conection_Space'>
              <h2 >Base de Datos Destino</h2>
            </div>
          </div>

        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose_Conexion}>
            Close
          </Button>
          <Button variant="primary">Understood</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Conexion;