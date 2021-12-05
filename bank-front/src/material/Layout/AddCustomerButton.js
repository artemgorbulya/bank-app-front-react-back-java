import React, {useRef} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Button from "@material-ui/core/Button";
import axios from "axios";


function getModalStyle() {
    const top = 50;
    const left = 50;

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}

const useStyles = makeStyles((theme) => ({
    paper: {
        position: 'absolute',
        width: 400,
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column"
    },
    openModal: {
        backgroundColor: "green",
        color: "black"
    },
    input: {
        padding: 10,
        margin: 10,
        border: '2px solid #000',
        borderColor: "green"
    }
}));

export default function SimpleModal({color, caption, data}) {
    const classes = useStyles();
    // getModalStyle is not a pure function, we roll the style only on the first render
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);

    const name = useRef(null);
    const mail = useRef(null);
    const age = useRef(null);
    const password = useRef(null);
    const phone = useRef(null);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const addCustomer = () => {
        if (name.current.value !== "" && name.current.value !== "" && age.current.value !== "") {
            axios.post('customers', {
                name: name.current.value,
                email: mail.current.value,
                age: age.current.value,
                password: password.current.value,
                phone: phone.current.value
            });

            window.location.reload();
        }
    };

    const body = (
        <div style={modalStyle} className={classes.paper}>
            <h3>Add Customer</h3>
            <input className={classes.input} ref={name} type="text" placeholder="Enter name"/>
            <input className={classes.input} ref={mail} type="email" placeholder="Enter e-mail"/>
            <input className={classes.input} ref={password} type="password" placeholder="Enter password"/>
            <input className={classes.input} ref={age} type="number" min="18" placeholder="Enter age"/>
            <input className={classes.input} ref={phone} type="text" placeholder="Enter phone"/>
            <hr/>
            <Button variant="contained" color="primary" onClick={addCustomer} >Add Customer</Button>
        </div>
    );

    return (
        <div>
            <Button  variant="contained" className={classes.openModal} type="button" onClick={handleOpen}>
                Add Customer
            </Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                {body}
            </Modal>
        </div>
    );
}