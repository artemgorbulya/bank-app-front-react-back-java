import React from 'react';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import Grid from "@material-ui/core/Grid";

export default function Title(props) {
    return (
        <Typography component="h2" variant="h6" color="primary" gutterBottom>
            <Grid container justify="center" alignItems="center">
                {props.children}
            </Grid>
        </Typography>
    );
}

Title.propTypes = {
    children: PropTypes.node,
};