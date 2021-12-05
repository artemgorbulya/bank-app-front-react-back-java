import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Title from './Title';
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
    seeMore: {
        marginTop: theme.spacing(3),
    },
}));

export default function Orders({name, mail, age, id, phone, accounts}) {
    const classes = useStyles();
    return (
        <React.Fragment>
            <Title>
                <Typography component="h6" variant="subtitle1" color="secondary" gutterBottom>
                   ID: {id}&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;
                </Typography>
                <Typography component="h6" variant="subtitle1" color="secondary" gutterBottom>
                   Name: {name}&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;
                </Typography>
                <Typography component="h6" variant="subtitle1" color="secondary" gutterBottom>
                   Email: {mail}&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;
                </Typography>
                <Typography component="h6" variant="subtitle1" color="secondary" gutterBottom>
                   Age: {age}&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;
                </Typography>
                <Typography component="h6" variant="subtitle1" color="secondary" gutterBottom>
                   Phone: {phone}&nbsp;&nbsp;&nbsp;
                </Typography>
            </Title>
            <Table size="small">
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Number</TableCell>
                        <TableCell>Currency</TableCell>
                        <TableCell>Balance</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    { accounts.map((i, n) => (
                        
                        <TableRow key={n}>
                            <TableCell>{i.id}</TableCell>
                            <TableCell>{i.number}</TableCell>
                            <TableCell>{i.currency}</TableCell>
                            <TableCell>{i.balance}</TableCell>
                        </TableRow> 
                    ))}
                </TableBody>
            </Table>
            <div className={classes.seeMore}>
                <hr/>
            </div>
        </React.Fragment>
    );
}