import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

function Replicacion({show_Replicacion,handleClose_Replicacion}) {
  
  return (
    <>
      <Modal
        show={show_Replicacion}
        onHide={handleClose_Replicacion}
        backdrop="static"
        keyboard={false}
        fullscreen = {true}
      >
        <Modal.Header closeButton>
          <Modal.Title>Modal title</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          I will not close if you click outside me. Don not even try to press
          escape key.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose_Replicacion}>
            Close
          </Button>
          <Button variant="primary">Understood</Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default Replicacion;