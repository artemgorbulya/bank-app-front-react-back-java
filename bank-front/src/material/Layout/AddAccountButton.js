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
        color: "black",
        marginRight: 20
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

    const currency = useRef(null);
    const balance = useRef(null);
    const customerID = useRef(null);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const addAccount = () => {
        if ( currency.current.value !== "" && customerID.current.value !== "") {
            axios.post(`/accounts/customer/${customerID.current.value}`, {
                currency: currency.current.value,
                balance: balance.current.value 
            });

            window.location.reload();
        }
    };

    const body = (
        <div style={modalStyle} className={classes.paper}>

        <h3>Add Account</h3>
            <select className={classes.input} ref={currency} id="cars">
                <option value="USD">USD</option>
                <option value="EUR">EUR</option>
                <option value="UAH">UAH</option>
                <option value="CHF">CHF</option>
                <option value="GBP">GBP</option>
            </select>
            <input className={classes.input} ref={customerID} type="number" min="0" placeholder="Enter customer ID"/>
            <input className={classes.input} ref={balance} type="number" min="0" placeholder="Enter balance"/>
            <hr/>
            <Button variant="contained" color="primary" onClick={addAccount}>Add Account</Button>
        </div>
    );

    return (
        <div>
            <Button className={classes.openModal} type="button" onClick={handleOpen}>
                Add Account
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
